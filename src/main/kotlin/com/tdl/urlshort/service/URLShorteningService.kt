package com.tdl.urlshort.service

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.database.repository.URLRepository
import com.tdl.urlshort.dtos.ApiResponse
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.URLMetrics
import com.tdl.urlshort.util.HashUtils
import com.tdl.urlshort.util.URLUtils
import jakarta.inject.Singleton

@Singleton
class URLShorteningService(private val repository: URLRepository,
                           private val hashUtils: HashUtils,
                           private val urlUtils: URLUtils) : ShorteningService
{
    override fun shortenURL(url : LongURL) : URLRegister = TODO("Not yet implemented")

    override fun redirectURL(hash:String) : ApiResponse = TODO("Not yet implemented")

    override fun getUsageMetrics(hash : String) : URLMetrics = TODO("Not yet implemented")
}