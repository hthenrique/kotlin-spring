package com.example.kotlinspring.exception

import com.example.kotlinspring.model.enum.ReturnCode

class DatabaseException(
    returnCode: ReturnCode,
    cause: Throwable
): BaseException(returnCode, cause.localizedMessage, cause) {
}