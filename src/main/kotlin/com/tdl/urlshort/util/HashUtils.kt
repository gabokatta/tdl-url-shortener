package com.tdl.urlshort.util

import com.google.common.hash.Hashing
import jakarta.inject.Singleton
import java.nio.charset.StandardCharsets

@Singleton
class HashUtils() {

    companion object {
        private const val HASH_LENGTH  = 6
    }

    fun generateHash(url : String) : String {
        return Hashing
            .sha256()
            .hashString(url, StandardCharsets.UTF_8)
            .toString()
            .take(HASH_LENGTH)
    }

}