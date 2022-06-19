package com.tdl.urlshort.dtos

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank


@Introspected
data class SearchResult(

    @field:NotBlank
    val url: String,

    @field:NotBlank
    val matchingWords: List<String> = listOf()
)
