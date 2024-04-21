package com.example.kotlinspring.exception

import com.example.kotlinspring.model.enum.ReturnCode

open class BaseException(
    val returnCode: ReturnCode,
    message: String?,
    cause: Throwable? = null
) : Exception(message, cause)