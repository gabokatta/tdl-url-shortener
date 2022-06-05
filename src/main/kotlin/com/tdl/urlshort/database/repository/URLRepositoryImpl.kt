package com.tdl.urlshort.database.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.tdl.urlshort.configuration.MongoConfiguration
import com.tdl.urlshort.database.model.URLRegister
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import javax.validation.Valid

@Singleton
@Requires(env=["local", "prod"], notEnv=["test"])
open class URLRepositoryImpl (
    private val mongoConfiguration: MongoConfiguration,
    private val mongoClient: MongoClient
    ) : URLRepository {
    override fun save(@Valid entry: URLRegister) {
        collection.insertOne(entry)
    }

    override fun find(hash: String): URLRegister? {
        return collection.find(Filters.eq("hash", hash)).first()
    }

    override fun list(): List<URLRegister> = collection.find().into(ArrayList())

    private val collection: MongoCollection<URLRegister> get() = mongoClient
            .getDatabase(mongoConfiguration.name)
            .getCollection(mongoConfiguration.collection, URLRegister::class.java)
            .withCodecRegistry(this.getCodec())
}
