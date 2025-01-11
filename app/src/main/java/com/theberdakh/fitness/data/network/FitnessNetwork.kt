package com.theberdakh.fitness.data.network

import com.google.gson.GsonBuilder
import com.theberdakh.fitness.data.network.retrofit.FitnessNetworkApi
import com.theberdakh.fitness.data.preferences.FitnessPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
private const val QUERY_INTERCEPTOR = "QUERY_INTERCEPTOR"

object FitnessNetwork {
    val module = module {
        factory<Converter.Factory> {
            val gson = GsonBuilder()
                .setLenient().create()
            GsonConverterFactory.create(gson)
        }

        factory<Interceptor>(named(LOGGING_INTERCEPTOR)) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            }
        }

        factory(named(QUERY_INTERCEPTOR)) {
            val preferences: FitnessPreferences = get()

            Interceptor { chain ->/*
                // Log the request body
                val original = chain.request()
                // Rebuild the request with a potentially modified URL
                val newRequest = original.newBuilder()
                    .header("Authorization", getAuthorisationHeader(preferences) ?: "")
                    .build()

                newRequest.body?.let { body ->
                    val buffer = Buffer()
                    body.writeTo(buffer)
                    Log.i(TAG, "This is request: ${buffer.readString(Charsets.UTF_8)}")
                }
                newRequest.headers.forEach {
                    Log.i(TAG, "This is request header: ${it.first} : ${it.second}")
                }

                val response = chain.proceed(original)
                val responseBodyString = response.body?.string()
                Log.i(TAG, "This is response: $responseBodyString")

                // Rebuild the response with the logged body and an Authorization header if available
                response.newBuilder()
                    .body(responseBodyString?.toResponseBody(response.body?.contentType()))
                    .build()*/
                val newUrl = chain.request().url
                    .newBuilder()

                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl.build())
                    .addHeader("Authorization", "Bearer ${preferences.getUserSession().accessToken}")
                    .build()
                chain.proceed(newRequest)
            }
        }

        factory {
            OkHttpClient.Builder().apply {
                addInterceptor(get<Interceptor>(named(QUERY_INTERCEPTOR)))
                addInterceptor(get<Interceptor>(named(LOGGING_INTERCEPTOR)))
            }.build()
        }

        single {
            Retrofit.Builder()
                .baseUrl("https://api.fit.iztileuoff.uz/")
                .client(get())
                .addConverterFactory(get())
                .build()
        }

        single { get<Retrofit>().create(FitnessNetworkApi::class.java) }


    }

    /**
     * This method helps to only add Bearer token Authorisation token if it's stored in SharedPreferences and not null*/

}