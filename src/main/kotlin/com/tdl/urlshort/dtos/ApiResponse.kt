package com.tdl.urlshort.dtos

import io.micronaut.core.annotation.Introspected

@Introspected
data class ApiResponse(val message : String? = "Default API message.")
