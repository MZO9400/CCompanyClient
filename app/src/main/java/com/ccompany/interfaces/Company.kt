package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class Company(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("geolocation")
    val geolocation: Location
)
