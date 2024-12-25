package com.theberdakh.fitness.core.network

import com.google.gson.GsonBuilder
import com.theberdakh.fitness.core.network.api.FitnessNetworkApi
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

object CoreNetwork {
    val module = module {



        factory<Converter.Factory> {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            GsonConverterFactory.create(gson)
        }

        factory<Interceptor>(named(LOGGING_INTERCEPTOR)){
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        factory(named(QUERY_INTERCEPTOR)) {
            Interceptor { chain ->
                val newUrl = chain.request().url
                    .newBuilder()
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
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
}