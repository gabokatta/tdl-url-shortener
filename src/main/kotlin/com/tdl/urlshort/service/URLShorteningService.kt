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
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.*
import kotlin.streams.toList

@Singleton
class URLShorteningService(
    private val repository: URLRepository,
    private val client: HttpClient,
    private val hashUtils: HashUtils,
    private val urlUtils: URLUtils
) : ShorteningService {

    private val logger = LoggerFactory.getLogger(URLShorteningService::class.java)
    private val apiScope = CoroutineScope(Dispatchers.IO + CoroutineName("API Call Scope"))
    private val taskScope = CoroutineScope(Dispatchers.Default + CoroutineName("Heavy Task Scope"))

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

    override fun searchSites(keywords: Keywords): List<SearchResult> = runBlocking {
        val sites = repository.list().map { register -> register.url }.toSet()
        return@runBlocking sites.stream()
            .map { site -> taskScope.async { searchKeywords(site, keywords.values) } }
            .toList()
            .awaitAll()
            .filter { result -> result.matchingWords.isNotEmpty() }
    }

    private suspend fun searchKeywords(url: String, words: List<String>): SearchResult {

        val getResult = apiScope.async { retrieveSiteContents(url) }

        val matches = words.stream()
            .map { word -> taskScope.async { siteContains(getResult.await(), word) } }
            .toList()
            .awaitAll()
            .filter { result -> result.found }
            .map { found -> found.word }

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

    private fun siteContains(siteContents: String, word: String): WordFound {
        return WordFound(word, siteContents.contains(word))
    }


}
