package com.tdl.urlshort.dtos

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class LongURL(@field:NotBlank val url : String)
