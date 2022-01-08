package com.ccompany.interfaces

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
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
) : Parcelable
