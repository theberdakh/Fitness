package com.theberdakh.fitness.core.network

import com.google.gson.GsonBuilder
import com.theberdakh.fitness.core.network.api.FitnessNetworkApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object CoreNetwork {
    val module = module {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.fit.iztileuoff.uz/api/v1/")
            .addConverterFactory(StringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        single { retrofit.create(FitnessNetworkApi::class.java) }
    }
}