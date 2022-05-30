package com.tdl.urlshort.dtos

import javax.validation.constraints.NotBlank

data class LongURL(@field:NotBlank val url : String)
