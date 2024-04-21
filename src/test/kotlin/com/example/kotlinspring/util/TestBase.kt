package com.example.kotlinspring.util

import com.example.kotlinspring.model.authentication.LoginUserRequest
import com.example.kotlinspring.model.crud.CreateUserRequest
import com.example.kotlinspring.model.crud.UpdateUserRequest
import com.example.kotlinspring.model.db.UserDatabase
import java.util.*

open class TestBase {

    val encodedUserName: String = "aGVucmlxdWVAZW1haWwuY29t"

    var mockListUserDatabase = mockListUserDatabase()
    var mockOptionalUserDatabase = mockOptionalUserDatabase()
    var mockUserDatabase = mockUserDatabase()
    var mockCreateUserRequest = mockCreateUserRequest()
    var mockUpdateUserRequest = mockUpdateUserRequest()
    var mockLoginUserRequest = mockLoginUserRequest()

    fun mockListUserDatabase(): List<UserDatabase> {
        val listUserDatabase = listOf(mockUserDatabase())
        return listUserDatabase
    }

    fun mockOptionalUserDatabase(): Optional<UserDatabase> {
        return Optional.ofNullable(mockUserDatabase())
    }

    fun mockUserDatabase(): UserDatabase {
        return UserDatabase(
            1,
            "name",
            "email",
            "2908d2c28dfc047741fc590a026ffade237ab2ba7e1266f010fe49bde548b5987a534a86655a0d17f336588e540cd66f67234b152bbb645b4bb85758a1325d64",
            "salt")
    }

    fun mockCreateUserRequest(): CreateUserRequest {
        return CreateUserRequest("name", "email", "password")
    }

    fun mockUpdateUserRequest(): UpdateUserRequest {
        return UpdateUserRequest( 1,"name", "email")
    }

    fun mockLoginUserRequest(): LoginUserRequest {
        return LoginUserRequest( "email", "password")
    }
}