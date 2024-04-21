package com.example.kotlinspring.config

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.springframework.test.util.ReflectionTestUtils

class DatabaseConfigTest {

    @InjectMocks
    private lateinit var databaseConfig: DatabaseConfig

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        ReflectionTestUtils.setField(databaseConfig, "jdbcUrl", "jdbcUrl")
        ReflectionTestUtils.setField(databaseConfig, "usernamePath", "src/test/resources/username.txt")
        ReflectionTestUtils.setField(databaseConfig, "passwordPath", "src/test/resources/password.txt")
    }

    @Test
    fun contextSource() {
        assertDoesNotThrow{databaseConfig.contextSource()}
    }

    @Test
    fun contextSourceFail() {
        ReflectionTestUtils.setField(databaseConfig, "usernamePath", "username.txt")
        assertNull(databaseConfig.contextSource())
    }
}