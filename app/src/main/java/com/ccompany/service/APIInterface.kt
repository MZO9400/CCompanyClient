package com.ccompany.service

import com.ccompany.interfaces.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("/api/v1/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/api/v1/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @GET("/api/v1/getCompanies")
    suspend fun getCompanies(@Header("Authorization") token: String): CompaniesResponse
}
