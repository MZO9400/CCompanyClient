package com.ccompany.service

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

internal object APIClient {
    val client: Retrofit
        get() {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl("https://c-company.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
}
