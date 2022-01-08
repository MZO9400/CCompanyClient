package com.ccompany.interfaces

import com.google.gson.annotations.SerializedName

class Location(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)
