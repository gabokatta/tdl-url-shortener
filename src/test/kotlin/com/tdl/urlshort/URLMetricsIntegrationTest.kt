package com.tdl.urlshort

import com.fasterxml.jackson.databind.ObjectMapper
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
class URLMetricsIntegrationTest : BaseIntegrationTest() {

    @Inject
    lateinit var urlRepository: URLRepository
    var mapper: ObjectMapper = ObjectMapper()
    private val testBeginDate: Date = Calendar.getInstance().time

    @BeforeAll
    fun populateRepository() {
        val register = URLRegister("https://github.com/", "12345", 2, testBeginDate)
        urlRepository.save(register)
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
        val expectedMetrics = URLMetrics("https://github.com/", "tdl.io/12345", 2, testBeginDate)
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertEquals(mapper.writeValueAsString(expectedMetrics), response.body())
    }

}