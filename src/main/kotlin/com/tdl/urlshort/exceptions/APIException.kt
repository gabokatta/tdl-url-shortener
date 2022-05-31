package com.tdl.urlshort.exceptions

import io.micronaut.http.HttpStatus

open class APIException : RuntimeException {
    constructor(message : String) : super(message)
    constructor(message: String, cause : Throwable) : super(message, cause)

    open fun getStatus() : HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    open fun getBody() : String? = super.message
}