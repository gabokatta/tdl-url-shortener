package com.tdl.urlshort.dtos

import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Positive

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
    val lastUsage: Date)
