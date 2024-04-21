package com.example.kotlinspring.model.enum

import org.springframework.http.HttpStatus

enum class ReturnCode(val code: String,val httpStatus: HttpStatus) {

    SUCCESS("2000", HttpStatus.OK),
    INVALID_PARAMETERS("4000", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("4010", HttpStatus.UNAUTHORIZED),
    NOT_FOUND("4040", HttpStatus.NOT_FOUND),
    ALREADY_EXISTS("4090", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR);
}