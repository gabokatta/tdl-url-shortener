package com.tdl.urlshort.util

import jakarta.inject.Singleton
import java.security.SecureRandom

@Singleton
class HashGenerator() {

    private val secureRandom : SecureRandom = SecureRandom()
    companion object {
        private const val CHARS  = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        private const val BOUND = CHARS.length
        private const val HASH_LENGTH : Long = 6
    }

    fun generateHash() : String {
        val code : StringBuilder = StringBuilder()
        secureRandom
            .ints(HASH_LENGTH, 0, BOUND - 1)
            .forEach { index -> code.append(CHARS[index]) }
        return code.toString()
    }

}