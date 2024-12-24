package com.theberdakh.fitness.core.network

import com.theberdakh.fitness.core.network.model.MessageModel
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class StringConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type == MessageModel::class.java) {
            return Converter<ResponseBody, MessageModel> { responseBody ->
                val message = responseBody.string()
                MessageModel(message = message)
            }
        }
        return null
    }
}