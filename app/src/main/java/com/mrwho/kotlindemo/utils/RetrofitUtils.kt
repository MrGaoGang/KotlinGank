package com.mrwho.kotlindemo.utils

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.net
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
class RetrofitUtils private constructor() {
    private var retrofit: Retrofit by Delegates.notNull()

    companion object {
        val instance: RetrofitUtils by lazy { RetrofitUtils() }
        private val BASE_URL = "http://gank.io/api/data/"
    }

    init {
        val httpLog = HttpLoggingInterceptor()
        httpLog.level = HttpLoggingInterceptor.Level.BODY
        val okhttp: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

        retrofit = Retrofit.Builder()
                .client(okhttp)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 12:37
     * MethodName:
     * Des：
     * Params：
     * Return:
     */
    fun <T> create(tClass: Class<T>): T {
        return retrofit.create(tClass)
    }

}