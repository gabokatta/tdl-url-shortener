package com.tdl.urlshort.dtos

import java.util.*

data class URLMetrics(val originalURL: String, val shortURL:String, val usageCount: Int, val lastUsage: Date)
