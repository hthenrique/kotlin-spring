package com.example.kotlinspring.controller

import com.example.kotlinspring.model.crud.CreateUserRequest
import com.example.kotlinspring.model.IResponse
import com.example.kotlinspring.model.ServiceResponse
import com.example.kotlinspring.model.authentication.LoginUserRequest
import com.example.kotlinspring.model.crud.UpdateUserRequest
import com.example.kotlinspring.service.SpringService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class Controller {

    companion object {
        private val logger = LoggerFactory.getLogger(Controller::class.java)
    }

    @Autowired
    private lateinit var springService: SpringService

    @GetMapping("/search")
    fun searchUser(@RequestParam username : String) : ResponseEntity<ServiceResponse> {
        val finalUserId = Base64.getUrlDecoder().decode(username).decodeToString()
        logger.info("Searching user: $finalUserId")
        return ResponseEntity.ok(springService.searchUser(finalUserId))
    }

    @PostMapping("/create")
    fun createUser(@RequestBody request: CreateUserRequest) : ResponseEntity<ServiceResponse> {
        logger.info("Creating user: ${request.email}")
        return ResponseEntity.ok(springService.createUser(request))
    }

    @PutMapping("/update")
    fun updateUser(@RequestBody request: UpdateUserRequest) : ResponseEntity<ServiceResponse> {
        logger.info("Updating user: uid=${request.uid}")
        return ResponseEntity.ok(springService.updateUser(request))
    }

    @DeleteMapping("/delete")
    fun deleteUser(@RequestParam userId : Long) : ResponseEntity<ServiceResponse> {
        logger.info("deleting user: uid=$userId")
        return ResponseEntity.ok(springService.deleteUser(userId))
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody request: LoginUserRequest) : ResponseEntity<ServiceResponse> {
        logger.info("Sign user: ${request.username}")
        return ResponseEntity.ok(springService.login(request))
    }
}