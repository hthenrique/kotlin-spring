package com.example.kotlinspring.model.crud

import com.example.kotlinspring.model.IResponse

data class SearchUserResponse(
    val uid: Int,
    val name: String,
    val email: String
): IResponse