package com.tdl.urlshort.controller

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.dtos.ApiResponse
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.URLMetrics
import com.tdl.urlshort.service.ShorteningService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import javax.validation.Valid

@Controller("/uba/tdl")
@ExecuteOn(TaskExecutors.IO)
open class ShortenerController(private val shorteningService : ShorteningService) {

    @Post("/redirect/{hash}")
    @Operation(summary = "Redirects shortened URL to it's original domain.")
    @Tag(name = "Public URL Operations")
    open fun redirectURL(@PathVariable hash : String) : HttpResponse<ApiResponse> = HttpResponse.ok(shorteningService.redirectURL(hash))

    @Post("/shorten")
    @Operation(summary = "Shortens a given URL.")
    @Tag(name = "Public URL Operations")
    open fun shortenURL(@Valid @Body url : LongURL) : HttpResponse<URLRegister> = HttpResponse.created(shorteningService.shortenURL(url))

    @Get("/metrics/{hash}")
    @Operation(summary = "Retrieves URL usage metrics.")
    @Tag(name = "Internal URL Operations")
    open fun getMetrics(@PathVariable hash : String) : HttpResponse<URLMetrics> = HttpResponse.ok(shorteningService.getUsageMetrics(hash))

}
