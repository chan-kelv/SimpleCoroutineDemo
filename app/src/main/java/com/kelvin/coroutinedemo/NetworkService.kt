package com.kelvin.coroutinedemo

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class NetworkService {

    // Suspend function will return when complete
    suspend fun getHomePage(): String {
        val retroClient = Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            baseUrl("http://localhost:8080")
        }.build()

        return (retroClient.create(NetworkInterface::class.java)).getSiteHome()
    }
    interface NetworkInterface {
        @GET("/")
        suspend fun getSiteHome():String
    }
}