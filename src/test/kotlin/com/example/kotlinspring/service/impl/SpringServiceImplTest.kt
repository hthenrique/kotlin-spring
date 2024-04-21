package com.example.kotlinspring.service.impl

import com.example.kotlinspring.exception.DatabaseException
import com.example.kotlinspring.exception.ValidationException
import com.example.kotlinspring.model.authentication.LoginUserRequest
import com.example.kotlinspring.model.crud.UpdateUserRequest
import com.example.kotlinspring.model.db.UserDatabase
import com.example.kotlinspring.repository.UserRepository
import com.example.kotlinspring.util.TestBase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.dao.DataAccessException
import java.util.Optional

class SpringServiceImplTest : TestBase(){

    @InjectMocks
    private lateinit var springServiceImpl: SpringServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        springServiceImpl = SpringServiceImpl(userRepository)
    }

    @Test
    fun `success when search search by email return user list`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(mockListUserDatabase)
        assertDoesNotThrow { springServiceImpl.searchUser(encodedUserName) }
    }

    @Test
    fun `throw exception when search by email and return empty`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(null)
        assertThrows<ValidationException> { springServiceImpl.searchUser(encodedUserName) }
    }

    @Test
    fun `throw exception in database when search by email`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenThrow(NoSuchElementException())
        assertThrows<DatabaseException> { springServiceImpl.searchUser(encodedUserName) }
    }

    @Test
    fun `success when create an user`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(listOf())
        Mockito.`when`(userRepository.saveAndFlush(Mockito.any())).thenReturn(UserDatabase())
        assertDoesNotThrow { springServiceImpl.createUser(mockCreateUserRequest) }
    }

    @Test
    fun `throw exception when create an user then return a user exists`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(mockListUserDatabase)
        assertThrows<ValidationException> { springServiceImpl.createUser(mockCreateUserRequest) }
    }

    @Test
    fun `throw exception in database when create user`() {
        Mockito.`when`(userRepository.saveAndFlush(Mockito.any())).thenThrow(NoSuchElementException())
        assertThrows<DatabaseException> { springServiceImpl.createUser(mockCreateUserRequest) }
    }

    @Test
    fun `success when update an user`() {
        Mockito.`when`(userRepository.findById(Mockito.any())).thenReturn(mockOptionalUserDatabase)
        Mockito.`when`(userRepository.saveAndFlush(Mockito.any())).thenReturn(UserDatabase())
        assertDoesNotThrow { springServiceImpl.updateUser(mockUpdateUserRequest) }
    }

    @Test
    fun `throw exception when update an user because uid is null`() {
        mockUpdateUserRequest = UpdateUserRequest()
        assertThrows<ValidationException> { springServiceImpl.updateUser(mockUpdateUserRequest) }
    }

    @Test
    fun `throw exception in database when update an user`() {
        Mockito.`when`(userRepository.findById(Mockito.any())).thenThrow(NoSuchElementException())
        assertThrows<DatabaseException> { springServiceImpl.updateUser(mockUpdateUserRequest) }
    }

    @Test
    fun `success when delete an user`() {
        Mockito.`when`(userRepository.findById(Mockito.any())).thenReturn(mockOptionalUserDatabase)
        Mockito.doNothing().`when`(userRepository).delete(Mockito.any())
        assertDoesNotThrow { springServiceImpl.deleteUser(mockUserDatabase.user_id.toLong()) }
    }

    @Test
    fun `throw exception in database when delete an user`() {
        Mockito.`when`(userRepository.findById(Mockito.any())).thenReturn(mockOptionalUserDatabase)
        Mockito.`when`(userRepository.delete(Mockito.any())).thenThrow(NoSuchElementException())
        assertThrows<DatabaseException> { springServiceImpl.deleteUser(mockUserDatabase.user_id.toLong()) }
    }

    @Test
    fun `success when login an user`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(mockListUserDatabase)
        assertDoesNotThrow { springServiceImpl.login(mockLoginUserRequest) }
    }

    @Test
    fun `throw exception when login an user`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(mockListUserDatabase)
        assertThrows<ValidationException> { springServiceImpl.login(LoginUserRequest( "email", "pass")) }
    }

    @Test
    fun `throw exception when login an user and return empty value`() {
        Mockito.`when`(userRepository.findByEmail(Mockito.anyString())).thenReturn(listOf())
        assertThrows<ValidationException> { springServiceImpl.login(LoginUserRequest( "email", "pass")) }
    }
}