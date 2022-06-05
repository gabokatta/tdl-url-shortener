package com.tdl.urlshort.service

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.URLMetrics
import java.net.URI

interface ShorteningService {

    fun shortenURL(url: LongURL): URLRegister
    fun redirectURL(hash: String): URI
    fun getUsageMetrics(hash: String): URLMetrics

}