package com.emersonfiwre.testesofie.service.repository.remote

import com.emersonfiwre.testesofie.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var retrofit: Retrofit
        private const val BASE_URL =
            "https://9g1borgfz0.execute-api.sa-east-1.amazonaws.com/beta/"

        private fun getRetrofitInstance(): Retrofit {
            if (!Companion::retrofit.isInitialized) {
                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request =
                            chain.request()
                                .newBuilder()
                                .addHeader(TaskConstants.HEADER.CONTENT_TYPE, "application/json")
                                .build()
                        return chain.proceed(request)
                    }
                })
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}