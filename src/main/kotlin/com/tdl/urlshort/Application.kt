package com.tdl.urlshort

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = Info(
            title = "url-shortener",
            version = "1.0"
    )
)
object Api {
}

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.tdl.urlshort")
        .eagerInitSingletons(true)
        .environments(System.getenv("SCOPE").substringAfterLast("-"))
        .start()
}

