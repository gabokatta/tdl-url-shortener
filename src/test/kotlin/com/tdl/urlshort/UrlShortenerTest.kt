package com.tdl.urlshort
import com.tdl.urlshort.database.repository.DbUrlEntry
import com.tdl.urlshort.database.repository.ShortenerRepository
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@MicronautTest(environments = ["prod"])
class UrlShortenerTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    lateinit var repo: ShortenerRepository

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

    @Test
    fun testMongoWorks() {
        repo.save(DbUrlEntry("dasd", "test"))
        Assertions.assertTrue(repo.list().isEmpty())
    }
}
