package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)
