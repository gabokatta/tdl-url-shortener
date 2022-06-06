package com.tdl.urlshort

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.database.repository.URLRepository
import com.tdl.urlshort.dtos.URLMetrics
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class URLMetricsIntegrationTest : BaseIntegrationTest(){
    @Inject
    lateinit var urlRepository: URLRepository

    @BeforeAll
    fun populateRepository() {
        val goodRegister = URLRegister("https://github.com/", "12345", 2, Calendar.getInstance().time)
        val badRegister = URLRegister("hubgit.moc", "54321", 1, Calendar.getInstance().time)
        listOf(goodRegister, badRegister).forEach(urlRepository::save)
    }

    @Test
    fun testHashNotFound() {
        val response = Assertions.assertThrows(HttpClientResponseException::class.java) {
            callAPI(HttpMethod.GET, "/uba/tdl/metrics/123")
        }
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.status)
    }

    @Test
    fun testGetMetrics() {
        val response = callAPI(HttpMethod.GET, "/uba/tdl/metrics/12345")
        Assertions.assertEquals(HttpStatus.OK, response.status)
    }

    @Test
    fun testGetMetricsAgain() {
        val response = callAPI(HttpMethod.GET, "/uba/tdl/metrics/54321")
        Assertions.assertEquals(HttpStatus.OK, response.status)
    }
}