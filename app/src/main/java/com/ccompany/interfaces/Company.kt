package com.ccompany.interfaces

interface Location {
    val latitude: Double
    val longitude: Double
}

interface Company {
    val id: String
    val name: String
    val address: String
    val phone: String
    val logo: String
    val description: String
    val geolocation: Location
}
