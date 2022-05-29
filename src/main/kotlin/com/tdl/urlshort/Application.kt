package com.tdl.urlshort

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.slf4j.LoggerFactory

@OpenAPIDefinition(
    info = Info(
        title = "URL Shortener",
        description = "URL Shortening API - Coded in Kotlin - Powered by Micronaut",
    )
)
class Application

private val logger = LoggerFactory.getLogger(Application::class.java)

fun main(args: Array<String>) {
    val scope = System.getenv("SCOPE")
    build()
        .args(*args)
        .packages("com.tdl.urlshort")
        .environments(scope.substringAfterLast("-"))
        .start()
    logger.info("Application UP with scope: {}", scope)
}

