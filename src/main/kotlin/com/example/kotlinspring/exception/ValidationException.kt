package com.example.kotlinspring.exception

import com.example.kotlinspring.model.enum.ReturnCode

class ValidationException(
    returnCode: ReturnCode,
    message: String?
): BaseException(returnCode, message) {
}