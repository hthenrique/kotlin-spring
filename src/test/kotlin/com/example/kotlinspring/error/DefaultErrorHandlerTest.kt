package com.example.kotlinspring.error

import com.example.kotlinspring.exception.DatabaseException
import com.example.kotlinspring.exception.ValidationException
import com.example.kotlinspring.model.enum.ReturnCode
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class DefaultErrorHandlerTest {

    @InjectMocks
    private lateinit var defaultErrorHandler: DefaultErrorHandler

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun exceptionHandler() {
        assertDoesNotThrow { defaultErrorHandler.exceptionHandler(ValidationException(ReturnCode.NOT_FOUND, "Message")) }
        assertDoesNotThrow { defaultErrorHandler.exceptionHandler(DatabaseException(ReturnCode.NOT_FOUND, Exception("Message"))) }
    }

    @Test
    fun testExceptionHandler() {
    }
}