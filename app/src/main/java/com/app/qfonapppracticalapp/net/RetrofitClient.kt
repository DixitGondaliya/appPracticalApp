package com.app.qfonapppracticalapp.net

import com.google.gson.GsonBuilder
import com.intuit.sdp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    var BASE_URL = " https://general.63-141-249-130.plesk.page/"

    var apiInterface: ApiInterface? = null

    fun getApiInterfaceWithoutAuth(): ApiInterface? {
        return if (apiInterface == null) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient =
                OkHttpClient.Builder()  .addInterceptor(interceptor).addInterceptor(
                    Interceptor { chain ->
                        val request =
                            chain.request().newBuilder().addHeader("app_type", "user")
                                .addHeader("device_type", "android")
                                .addHeader("version", BuildConfig.VERSION_NAME).build()
                        chain.proceed(request)
                    })
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .cache(null)
                    .readTimeout(1, TimeUnit.MINUTES).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            apiInterface = retrofit.create(ApiInterface::class.java)
            apiInterface
        } else {
            apiInterface
        }
    }
}