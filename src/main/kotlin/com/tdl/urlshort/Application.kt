package com.tdl.urlshort

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*
import org.slf4j.LoggerFactory

class Application

private val logger = LoggerFactory.getLogger(Application::class.java)

fun main(args: Array<String>) {

    val scope = System.getenv("SCOPE");
    build()
        .args(*args)
        .packages("com.tdl.urlshort")
        .environments(scope.substringAfterLast("-"))
        .start()

    logger.info("Application UP with scope: {}", scope)
}

