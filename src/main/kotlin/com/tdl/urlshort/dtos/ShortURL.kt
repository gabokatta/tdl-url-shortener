package com.tdl.urlshort.dtos

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class ShortURL(@field:NotBlank val url : String)
