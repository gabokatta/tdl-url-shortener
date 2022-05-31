package com.tdl.urlshort.exceptions

import io.micronaut.http.HttpStatus

class InvalidURL : APIException {
    constructor(message : String) : super(message)
    constructor(message: String, cause : Throwable) : super(message, cause)

    override fun getStatus(): HttpStatus = HttpStatus.BAD_REQUEST

}