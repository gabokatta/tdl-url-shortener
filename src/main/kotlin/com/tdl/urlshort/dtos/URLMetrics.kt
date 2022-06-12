package com.tdl.urlshort.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Positive

@Introspected
data class URLMetrics(

    @field:NotBlank
    val originalURL: String,

    @field:NotBlank
    val shortURL:String,

    @field:NotNull
    @field:Positive
    val usageCount: Int,

    @field:PastOrPresent
    @field:NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val lastUsage: Date)
