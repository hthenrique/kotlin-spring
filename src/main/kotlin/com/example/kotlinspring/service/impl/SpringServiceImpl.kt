package com.example.kotlinspring.service.impl

import com.example.kotlinspring.exception.DatabaseException
import com.example.kotlinspring.exception.ValidationException
import com.example.kotlinspring.model.crud.CreateUserRequest
import com.example.kotlinspring.model.IResponse
import com.example.kotlinspring.model.ServiceResponse
import com.example.kotlinspring.model.authentication.LoginUserRequest
import com.example.kotlinspring.model.crud.SearchUserResponse
import com.example.kotlinspring.model.crud.UpdateUserRequest
import com.example.kotlinspring.model.db.UserDatabase
import com.example.kotlinspring.model.enum.ReturnCode
import com.example.kotlinspring.repository.UserRepository
import com.example.kotlinspring.service.SpringService
import com.example.kotlinspring.util.HashUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpringServiceImpl(@Autowired private val userRepository: UserRepository) : SpringService{

    private val DATABASE_RESULT_INDEX = 0

    companion object {
        private val logger = LoggerFactory.getLogger(SpringServiceImpl::class.java)
    }

    override fun searchUser(userId: String): ServiceResponse {
        val listUserDatabase = getUserByEmail(userId)
        if (listUserDatabase.isNullOrEmpty()){
            throw ValidationException(ReturnCode.NOT_FOUND, "User not found")
        }
        val userDatabase: UserDatabase = listUserDatabase[DATABASE_RESULT_INDEX]

        val searchUserResponse = SearchUserResponse(
            userDatabase.user_id,
            userDatabase.user_name,
            userDatabase.user_email
        )
        logger.info("User found with uid=${userDatabase.user_id}")
        return ServiceResponse(ReturnCode.SUCCESS.code, searchUserResponse)
    }

    override fun createUser(request: CreateUserRequest): ServiceResponse {
        val listUserDatabase = getUserByEmail(request.email)
        if (listUserDatabase.isNotEmpty()){
            throw ValidationException(ReturnCode.ALREADY_EXISTS, "User exists")
        }

        var userDatabase = UserDatabase.fromCreateUserRequest(request)
        userDatabase = saveUserInDatabase(userDatabase)
        logger.info("User created: uid=${userDatabase.user_id}")
        return ServiceResponse(ReturnCode.SUCCESS.code, null)
    }

    override fun updateUser(request: UpdateUserRequest): ServiceResponse {
        val userDatabase = validateAndFindUser(request.uid)
        updateFields(userDatabase, request)
        saveUserInDatabase(userDatabase)
        logger.info("User updated: uid=${userDatabase.user_id}")
        return ServiceResponse(ReturnCode.SUCCESS.code, null)
    }

    override fun deleteUser(uid: Long): ServiceResponse {
        val userDatabase = getUserByUid(uid) ?: throw ValidationException(ReturnCode.NOT_FOUND, "User not found")
        deleteUserInDatabase(userDatabase)
        logger.info("User deleted with success: uid=${userDatabase.user_id}")
        return ServiceResponse(ReturnCode.SUCCESS.code, null)
    }

    override fun login(request: LoginUserRequest): ServiceResponse {
        val listUserDatabase = getUserByEmail(request.username)
        if (listUserDatabase.isEmpty()){
            throw ValidationException(ReturnCode.NOT_FOUND, "User not found")
        }
        val userDatabase: UserDatabase = listUserDatabase[0]
        val hashedRequestPassword = HashUtil.hashPassword(request.password, userDatabase.user_salt)

        if(hashedRequestPassword != userDatabase.user_password){
            throw ValidationException(ReturnCode.INVALID_CREDENTIALS, "Invalid Credentials")
        }
        logger.info("Login successful")
        return ServiceResponse(ReturnCode.SUCCESS.code, null)
    }

    private fun getUserByUid(uid: Long): UserDatabase? {
        try {
            return userRepository.findById(uid).orElse(null)
        }catch (exception: Exception){
            throw DatabaseException(ReturnCode.INTERNAL_SERVER_ERROR, exception)
        }
    }

    private fun getUserByEmail(email: String?): List<UserDatabase> {
        try {
            return userRepository.findByEmail(email)
        }catch (exception: Exception){
            throw DatabaseException(ReturnCode.INTERNAL_SERVER_ERROR, exception)
        }
    }

    private fun saveUserInDatabase(userDatabase: UserDatabase): UserDatabase {
        try {
            return userRepository.saveAndFlush(userDatabase)
        }catch (exception: Exception){
            throw DatabaseException(ReturnCode.INTERNAL_SERVER_ERROR, exception)
        }
    }

    private fun deleteUserInDatabase(userDatabase: UserDatabase) {
        try {
            userRepository.delete(userDatabase)
        }catch (exception: Exception){
            throw DatabaseException(ReturnCode.INTERNAL_SERVER_ERROR, exception)
        }
    }

    private fun validateAndFindUser(uid: Long?): UserDatabase {
        if (uid == null){
            throw ValidationException(ReturnCode.INVALID_PARAMETERS, "'uid' are empty")
        }
        return getUserByUid(uid) ?: throw ValidationException(ReturnCode.NOT_FOUND, "User not found")
    }

    private fun updateFields(userDatabase: UserDatabase, request: UpdateUserRequest) {
        request.name?.takeIf { it.isNotBlank() }?.let { userDatabase.user_name = it }
        request.email?.takeIf { it.isNotBlank() }?.let { userDatabase.user_email = it }
    }

}