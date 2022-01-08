package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)

class LoginResponse (
    @SerializedName("user")
    val user: User,
    @SerializedName("token")
    val token: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String
)
