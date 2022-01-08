package com.ccompany.interfaces

interface CompaniesResponse {
    val status: Boolean
    val message: String
    val data: List<Company>
}
