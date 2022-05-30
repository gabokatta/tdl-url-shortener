package com.tdl.urlshort.dtos

import com.tdl.urlshort.util.HashUtils
import java.util.*

data class URLMetrics(val originalDomain: String, val hash: String, val usageCount: Int, val lastUsage: Date) {

    private val longURL : String = originalDomain
    private val shortURL : String = HashUtils.buildURL(hash)
    private val timesUsed : Int = usageCount
    private val lastUsed : Date = lastUsage

}
