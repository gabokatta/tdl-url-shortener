package com.tdl.urlshort.service

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.database.repository.URLRepository
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.URLMetrics
import com.tdl.urlshort.exceptions.URLNotFound
import com.tdl.urlshort.util.HashUtils
import com.tdl.urlshort.util.URLUtils
import jakarta.inject.Singleton
import java.net.URI
import java.util.*

@Singleton
class URLShorteningService(private val repository: URLRepository,
                           private val hashUtils: HashUtils,
                           private val urlUtils: URLUtils) : ShorteningService {
    override fun shortenURL(url: LongURL): URLRegister = TODO("Not yet implemented")

    override fun redirectURL(hash: String): URI {

        val urlRegister = repository.find(hash) ?: throw URLNotFound("Short URL with hash: $hash not found.")
        urlRegister.lastUsed = Calendar.getInstance().time
        urlRegister.timesUsed += 1
        repository.update(urlRegister)

        return URI(urlRegister.url)
    }

    override fun getUsageMetrics(hash: String): URLMetrics = TODO("Not yet implemented")
}