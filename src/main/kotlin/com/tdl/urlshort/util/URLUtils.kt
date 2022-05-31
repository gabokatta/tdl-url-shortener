package com.tdl.urlshort.util

import jakarta.inject.Singleton
import org.apache.commons.validator.routines.UrlValidator

@Singleton
class URLUtils {

    companion object {
        private const val BASE_URI = "tdl.io/"
        private val VALIDATOR = UrlValidator()
    }

    fun buildURL(hash : String) = BASE_URI + hash

    fun isValidURL(url : String) : Boolean {
        return VALIDATOR.isValid(url);
    }

}