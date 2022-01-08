package com.ccompany.interfaces

interface User {
    val name: String
    val email: String
}

interface LoginResponse {
    val user: User
    val token: String
    val status: Boolean
    val message: String
}
