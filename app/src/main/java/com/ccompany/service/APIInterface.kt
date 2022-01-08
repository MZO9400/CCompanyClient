package com.ccompany.service

import com.ccompany.interfaces.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("/api/v1/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/v1/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("/api/v1/getCompanies")
    fun getCompanies(@Header("Authorization") token: String): Call<CompaniesResponse>
}
