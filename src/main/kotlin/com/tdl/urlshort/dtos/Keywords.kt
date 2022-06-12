package com.tdl.urlshort.dtos

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotEmpty

@Introspected
data class Keywords(@field:NotEmpty val values: List<String>)
