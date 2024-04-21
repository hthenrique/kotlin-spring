package com.example.kotlinspring.error

import com.example.kotlinspring.controller.Controller
import com.example.kotlinspring.exception.BaseException
import com.example.kotlinspring.exception.DatabaseException
import com.example.kotlinspring.exception.ValidationException
import com.example.kotlinspring.model.error.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DefaultErrorHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(Controller::class.java)
    }

    @ExceptionHandler(ValidationException::class)
    fun exceptionHandler(exception: ValidationException): ResponseEntity<ErrorResponse> {
        return buildResponse(exception)
    }

    @ExceptionHandler(DatabaseException::class)
    fun exceptionHandler(exception: DatabaseException): ResponseEntity<ErrorResponse> {
        return buildResponse(exception)
    }

    private fun buildResponse(baseException: BaseException): ResponseEntity<ErrorResponse> {
        val returnCode = baseException.returnCode
        val errorResponse = ErrorResponse(returnCode.code, baseException.message)
        logger.error("Error: $errorResponse")
        return ResponseEntity.status(returnCode.httpStatus).body(errorResponse)
    }
}