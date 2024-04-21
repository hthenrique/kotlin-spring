package com.example.kotlinspring.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager


@Configuration
class DatabaseConfig {

    @Value("\${backend.database.url}")
    private lateinit var jdbcUrl: String

    @Value("\${backend.database.username}")
    private lateinit var usernamePath: String

    @Value("\${backend.database.password}")
    private lateinit var passwordPath: String

    @Bean
    fun contextSource(): DriverManagerDataSource? {
        try {
            val username = getText(usernamePath)
            val password = getText(passwordPath)

            val dataSource = DriverManagerDataSource()
            dataSource.setDriverClassName("org.postgresql.Driver")
            dataSource.url = jdbcUrl
            dataSource.username = username
            dataSource.password = password
            return dataSource
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getText(pathFile: String): String{
        val path = Paths.get(pathFile)
        return String(Files.readAllBytes(path))
    }
}