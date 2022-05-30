package com.tdl.urlshort.controller

import com.tdl.urlshort.database.model.URL
import com.tdl.urlshort.dtos.ApiResponse
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.ShortURL
import com.tdl.urlshort.dtos.URLMetrics
import com.tdl.urlshort.service.ShorteningService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.swagger.v3.oas.annotations.tags.Tag
import javax.validation.Valid

@Controller("/uba/tdl")
@ExecuteOn(TaskExecutors.IO)
open class ShortenerController(private val shorteningService : ShorteningService) {

    @Get("/redirect")
    @Tag(name = "URL Operations")
    open fun redirectURL(@Valid @Body url : ShortURL) : HttpResponse<ApiResponse> = HttpResponse.ok(shorteningService.redirectURL(url))

    @Post("/shorten")
    @Tag(name = "URL Operations")
    open fun shortenURL(@Valid @Body url : LongURL) : HttpResponse<URL> = HttpResponse.created(shorteningService.shortenURL(url))

    @Get("/metrics")
    @Tag(name = "URL Operations")
    open fun getMetrics(@Valid @Body url : ShortURL) : HttpResponse<URLMetrics> = HttpResponse.ok(shorteningService.getUsageMetrics(url))


}