package com.example.kotlinspring.model.crud

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class UpdateUserRequest(val uid: Long? = null,
                        val name: String? = null,
                        val email: String? = null) {
}