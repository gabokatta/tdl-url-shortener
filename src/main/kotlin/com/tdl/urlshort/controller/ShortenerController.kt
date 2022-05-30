package com.tdl.urlshort.controller

import com.tdl.urlshort.service.ShorteningService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/uba/tdl")
@ExecuteOn(TaskExecutors.IO)
class ShortenerController(private val shorteningService : ShorteningService) {

    @Get("/redirect")
    @Tag(name = "URL Operations")
    fun redirectURL() : HttpResponse<Long> = HttpResponse.ok(shorteningService.redirectURL())

    @Post("/shorten")
    @Tag(name = "URL Operations")
    fun shortenURL() : HttpResponse<Long> = HttpResponse.created(shorteningService.shortenURL())

    @Get("/metrics")
    @Tag(name = "URL Operations")
    fun getMetrics() : HttpResponse<Long> = HttpResponse.ok(shorteningService.getUsageMetrics())


}