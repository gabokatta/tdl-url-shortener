package com.tdl.urlshort.exceptions

import io.micronaut.http.HttpStatus

class URLNotFound : APIException {
    constructor(message : String) : super(message)
    constructor(message: String, cause : Throwable) : super(message, cause)

    override fun getStatus(): HttpStatus = HttpStatus.NOT_FOUND
}