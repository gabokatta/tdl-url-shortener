package com.tdl.urlshort.exceptions

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import  io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class APIExceptionHandler : ExceptionHandler<APIException, HttpResponse<*>> {

    private val log : Logger = LoggerFactory.getLogger(APIExceptionHandler::class.java)

    override fun handle(request : HttpRequest<*>?, exception : APIException): HttpResponse<String>? {
        log.error(exception.getBody())
        return HttpResponse.status<String>(exception.getStatus()).body(exception.getBody())
    }

}