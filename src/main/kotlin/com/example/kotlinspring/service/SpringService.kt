package com.example.kotlinspring.service

import com.example.kotlinspring.model.crud.CreateUserRequest
import com.example.kotlinspring.model.IResponse
import com.example.kotlinspring.model.ServiceResponse
import com.example.kotlinspring.model.authentication.LoginUserRequest
import com.example.kotlinspring.model.crud.UpdateUserRequest

interface SpringService {
    fun searchUser(userId: String): ServiceResponse
    fun createUser(request: CreateUserRequest): ServiceResponse
    fun updateUser(request: UpdateUserRequest): ServiceResponse
    fun deleteUser(uid: Long): ServiceResponse
    fun login(request: LoginUserRequest): ServiceResponse
}