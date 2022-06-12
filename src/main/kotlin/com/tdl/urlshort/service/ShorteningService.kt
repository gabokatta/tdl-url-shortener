package com.tdl.urlshort.service

import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.ShortURL
import com.tdl.urlshort.dtos.URLMetrics
import java.net.URI

interface ShorteningService {

    fun shortenURL(url: LongURL): ShortURL
    fun redirectURL(hash: String): URI
    fun getUsageMetrics(hash: String): URLMetrics

}