package com.example.kotlinspring.util

import java.security.MessageDigest

class HashUtil {
    companion object{
        fun hashPassword(password: String, salt: String): String {
            val saltedPassword = salt + password
            val md = MessageDigest.getInstance("SHA-512")
            val bytes = md.digest(saltedPassword.toByteArray(Charsets.UTF_8))
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}