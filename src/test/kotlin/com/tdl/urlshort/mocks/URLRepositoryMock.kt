package com.tdl.urlshort.mocks

import com.tdl.urlshort.database.model.URLRegister
import com.tdl.urlshort.database.repository.URLRepository
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import javax.validation.Valid

@Singleton
@Requires(env=["test"], notEnv=["local", "prod"])
open class URLRepositoryMock () : URLRepository {
    private val collection = HashMap<String, URLRegister>()

    override fun save(@Valid entry: URLRegister) {
        collection[entry.hash] = entry
    }

    override fun find(hash: String): URLRegister? {
        return collection[hash]
    }

    override fun list(): List<URLRegister> = collection.values.toList()
}
