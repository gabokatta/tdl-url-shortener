package com.tdl.urlshort

import com.tdl.urlshort.database.repository.URLRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["test"])
class MongoDBTest {

    @Inject
    lateinit var urlRepository: URLRepository

    @Test
    fun testMongoWorks() {
        Assertions.assertTrue(urlRepository.list().isEmpty())
    }
}