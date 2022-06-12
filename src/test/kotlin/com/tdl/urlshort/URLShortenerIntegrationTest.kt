package com.tdl.urlshort

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class URLShortenerIntegrationTest : BaseIntegrationTest() {
    @Test
    fun testAddShort() {
        val response = callAPI(HttpMethod.POST, "/uba/tdl/shorten", "{\"url\": \"https://www.google.com\"}")
        Assertions.assertEquals(HttpStatus.CREATED, response.status)
    }

    @Test
    fun testAddSameShort() {
        var response = callAPI(HttpMethod.POST, "/uba/tdl/shorten", "{\"url\": \"https://www.google.com\"}")
        Assertions.assertEquals(HttpStatus.CREATED, response.status)
        val url1 = response.body()

        response = callAPI(HttpMethod.POST, "/uba/tdl/shorten", "{\"url\": \"https://www.google.com\"}")
        Assertions.assertEquals(HttpStatus.CREATED, response.status)
        val url2 = response.body()

        Assertions.assertNotEquals(url1, url2)
    }

    @Test
    fun testAddInvalidEmptyShort() {
        val response = Assertions.assertThrows(HttpClientResponseException::class.java) {
            callAPI(HttpMethod.POST, "/uba/tdl/shorten", "{\"url\": \"\"}")
        }
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.status)
    }

    @Test
    fun testAddInvalidMalformedShort() {
        val response = Assertions.assertThrows(HttpClientResponseException::class.java) {
            callAPI(HttpMethod.POST, "/uba/tdl/shorten", "{\"url\": \"google\"}")
        }
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.status)
    }
}