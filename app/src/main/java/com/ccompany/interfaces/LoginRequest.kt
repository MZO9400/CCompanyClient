package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
