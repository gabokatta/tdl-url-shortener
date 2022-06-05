package com.tdl.urlshort

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.integration.ClientAndServer
import org.mockserver.junit.jupiter.MockServerExtension

@MicronautTest(environments = ["test"])
@ExtendWith(MockServerExtension::class)
open class BaseIntegrationTest {

    @Inject
    @field:Client("/")
    private lateinit var client: HttpClient

    private lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setupServer() {
        mockServer = ClientAndServer(1207)
    }

    @AfterEach
    fun destroyServer() {
        mockServer.close()
    }

    fun callAPI(httpMethod: HttpMethod, url: String, body: String? = null): HttpResponse<String> {
        val httpRequest: MutableHttpRequest<String> = HttpRequest.create(httpMethod, url)
        if (body != null)
            httpRequest.body(body)
        return client.toBlocking().exchange(httpRequest, String::class.java)
    }
}