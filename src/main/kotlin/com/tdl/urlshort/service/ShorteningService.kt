package com.tdl.urlshort.service

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.dtos.ApiResponse
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.URLMetrics

interface ShorteningService {

    fun shortenURL(url:LongURL) : URLRegister
    fun redirectURL(hash:String) : ApiResponse
    fun getUsageMetrics(hash:String) : URLMetrics

}