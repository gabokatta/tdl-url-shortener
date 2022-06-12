package com.tdl.urlshort.service

import com.tdl.urlshort.dtos.*
import java.net.URI

interface ShorteningService {

    fun shortenURL(url: LongURL): ShortURL
    fun redirectURL(hash: String): URI
    fun getUsageMetrics(hash: String): URLMetrics
    fun searchSites(keywords: Keywords): List<SearchResult>

}