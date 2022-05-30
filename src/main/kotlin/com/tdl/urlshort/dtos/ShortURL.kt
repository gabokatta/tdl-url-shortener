package com.tdl.urlshort.dtos

import javax.validation.constraints.NotBlank

data class ShortURL(@field:NotBlank val url : String)
