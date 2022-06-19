package com.tdl.urlshort.controller

import com.tdl.urlshort.dtos.*
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

    @Get("/redirect/{hash}")
    @Operation(summary = "Redirects shortened URL to it's original domain.")
    @Tag(name = "Public URL Operations")
    open fun redirectURL(@PathVariable hash: String): HttpResponse<ApiResponse> =
        HttpResponse.redirect(shorteningService.redirectURL(hash))

    @Post("/shorten")
    @Operation(summary = "Shortens a given URL.")
    @Tag(name = "Public URL Operations")
    open fun shortenURL(@Valid @Body url: LongURL): HttpResponse<ShortURL> =
        HttpResponse.created(shorteningService.shortenURL(url))

    @Get("/metrics/{hash}")
    @Operation(summary = "Retrieves URL usage metrics.")
    @Tag(name = "Internal URL Operations")
    open fun getMetrics(@PathVariable hash: String): HttpResponse<URLMetrics> =
        HttpResponse.ok(shorteningService.getUsageMetrics(hash))

    @Post("/search")
    @Operation(summary = "Retrieves any shortened site that matches certain keywords.")
    @Tag(name = "Internal URL Operations")
    open fun searchSites(@Valid @Body keywords: Keywords): HttpResponse<List<SearchResult>> =
        HttpResponse.ok(shorteningService.searchSites(keywords))

}

