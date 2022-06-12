package com.tdl.urlshort.service

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.database.repository.URLRepository
import com.tdl.urlshort.dtos.LongURL
import com.tdl.urlshort.dtos.URLMetrics
import com.tdl.urlshort.exceptions.URLNotFound
import java.net.URI
import com.tdl.urlshort.exceptions.InvalidURL
import com.tdl.urlshort.util.HashUtils
import com.tdl.urlshort.util.URLUtils
import jakarta.inject.Singleton
import java.util.Calendar

@Singleton
class URLShorteningService(
    private val repository: URLRepository,
    private val hashUtils: HashUtils,
    private val urlUtils: URLUtils
) : ShorteningService {
  
    override fun shortenURL(url: LongURL): ShortURL {
        if (!urlUtils.isValidURL(url.url))
            throw InvalidURL(url.url)
        var hash = hashUtils.generateHash(url.url)
        var i = 0
        while(repository.find(hash) != null){
            hash = hashUtils.generateHash(url.url + i)
            i++
        }
        val entry = URLRegister(url.url, hash, 0, Calendar.getInstance().time)
        repository.save(entry)
        return ShortURL(urlUtils.buildURL(entry.hash))
    }

    override fun redirectURL(hash: String): URI {

        val urlRegister = repository.find(hash) ?: throw URLNotFound("Short URL with hash: $hash not found.")
        urlRegister.lastUsed = Calendar.getInstance().time
        urlRegister.timesUsed += 1
        repository.update(urlRegister)
        return URI(urlRegister.url)
        
    }

     override fun getUsageMetrics(hash : String) : URLMetrics {

        val urlRegister = repository.find(hash)
                ?: throw URLNotFound("Could not find Metrics for hash: $hash")

        return URLMetrics(
                originalURL = urlRegister.url,
                shortURL = urlUtils.buildURL(urlRegister.hash),
                usageCount = urlRegister.timesUsed,
                lastUsage = urlRegister.lastUsed);

    }
}
