package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class RegisterRequest (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String
)
