package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class CompaniesResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Company>
)
