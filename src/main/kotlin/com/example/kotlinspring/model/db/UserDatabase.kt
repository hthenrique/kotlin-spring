package com.example.kotlinspring.model.db

import com.example.kotlinspring.model.crud.CreateUserRequest
import com.example.kotlinspring.util.HashUtil
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users_table")
data class UserDatabase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    val user_id: Int,
    @Column(name = "user_name", nullable = false, unique = true)
    var user_name: String,
    @Column(name = "user_email", nullable = false, unique = true)
    var user_email: String,
    @Column(name = "user_password", nullable = false)
    var user_password: String,
    @Column(name = "user_salt", nullable = false)
    val user_salt: String
){
    constructor() : this(0 , "", "", "", "")

    companion object{
        fun fromCreateUserRequest(request: CreateUserRequest): UserDatabase {
            val salt = UUID.randomUUID().toString()
            val hashedPassword = HashUtil.hashPassword(request.password, salt)
            return UserDatabase(
                user_id = 0,
                user_name = request.name,
                user_email = request.email,
                user_password = hashedPassword,
                user_salt = salt
            )
        }
    }
}