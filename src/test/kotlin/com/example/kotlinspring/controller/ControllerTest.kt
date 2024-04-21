package com.example.kotlinspring.controller

import com.example.kotlinspring.service.SpringService
import com.example.kotlinspring.util.TestBase
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ControllerTest: TestBase() {

    @InjectMocks
    private lateinit var controller: Controller

    @Mock
    private lateinit var springService: SpringService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun searchUser() {
        assertDoesNotThrow{controller.searchUser(encodedUserName)}
    }

    @Test
    fun createUser() {
        assertDoesNotThrow{controller.createUser(mockCreateUserRequest())}
    }

    @Test
    fun updateUser() {
        assertDoesNotThrow{controller.updateUser(mockUpdateUserRequest())}
    }

    @Test
    fun deleteUser() {
        assertDoesNotThrow{controller.deleteUser(1)}
    }

    @Test
    fun loginUser() {
        assertDoesNotThrow{controller.loginUser(mockLoginUserRequest())}
    }
}