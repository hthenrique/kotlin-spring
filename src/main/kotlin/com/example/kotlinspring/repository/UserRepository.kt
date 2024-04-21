package com.example.kotlinspring.repository

import com.example.kotlinspring.model.db.UserDatabase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserDatabase, Long> {

    @Query(value = "SELECT * FROM public.users_table WHERE user_email = :email", nativeQuery = true)
    fun findByEmail(@Param("email") email: String?): List<UserDatabase>
}