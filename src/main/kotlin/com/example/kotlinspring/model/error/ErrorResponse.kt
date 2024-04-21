package com.example.kotlinspring.model.error

data class ErrorResponse(
    val errorCode: String,
    val message: String?
) {
}