package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class RegisterResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String
)
