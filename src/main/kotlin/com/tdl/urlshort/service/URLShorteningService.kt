package com.tdl.urlshort.service

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.database.repository.URLRepository
import com.tdl.urlshort.dtos.*
import com.tdl.urlshort.exceptions.InvalidURL
import com.tdl.urlshort.exceptions.URLNotFound
import com.tdl.urlshort.util.HashUtils
import com.tdl.urlshort.util.URLUtils
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.exceptions.ReadTimeoutException
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.*

@Singleton
class URLShorteningService(
    private val repository: URLRepository,
    private val client: HttpClient,
    private val hashUtils: HashUtils,
    private val urlUtils: URLUtils
) : ShorteningService {

    private val logger = LoggerFactory.getLogger(URLShorteningService::class.java)

    override fun shortenURL(url: LongURL): ShortURL {
        if (!urlUtils.isValidURL(url.url))
            throw InvalidURL(url.url)
        var hash = hashUtils.generateHash(url.url)
        var i = 0
        while (repository.find(hash) != null) {
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

    override fun getUsageMetrics(hash: String): URLMetrics {
        val urlRegister = repository.find(hash) ?: throw URLNotFound("Could not find Metrics for hash: $hash")
        return URLMetrics(
            urlRegister.url,
            urlUtils.buildURL(urlRegister.hash),
            urlRegister.timesUsed,
            urlRegister.lastUsed
        )
    }

    override fun searchSites(keywords: Keywords): List<SearchResult> {
        val sites = repository.list().map { register -> register.url }.toSet()
        return sites.map { site -> searchKeywords(site, keywords.values) }
            .filter { result -> result.matchingWords.isNotEmpty() }
    }

    private fun searchKeywords(url: String, words: List<String>): SearchResult {
        val getResult = retrieveSiteContents(url)
        val matches = words.filter { word -> siteContains(getResult, word) }
        return SearchResult(url, matches)
    }

    private fun retrieveSiteContents(url: String): String {
        logger.info("GET REQUEST: {}", url)
        var response = ""
        try {
            val request: HttpRequest<String> = HttpRequest.create(HttpMethod.GET, url)
            response = client.toBlocking().retrieve(request)
        } catch (e: HttpClientResponseException) {
            logger.info("GET REQUEST: {} failed with status code: {}", url, e.status)
        } catch (e: ReadTimeoutException) {
            logger.info("GET REQUEST: {} failed with read-timeout.", url)
        }
        return response
    }

    private fun siteContains(siteContents: String, word: String): Boolean = siteContents.contains(word)


}
