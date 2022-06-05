package com.tdl.urlshort.service

import com.tdl.urlshort.database.model.URL
import com.tdl.urlshort.dtos.ApiResponse
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.URLMetrics

interface ShorteningService {

    fun shortenURL(url:LongURL) : URL
    fun redirectURL(hash:String) : ApiResponse
    fun getUsageMetrics(hash:String) : URLMetrics

}