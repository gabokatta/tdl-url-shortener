package com.tdl.urlshort.database.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.tdl.urlshort.configuration.MongoConfiguration
import com.tdl.urlshort.database.model.URL
import jakarta.inject.Singleton
import javax.validation.Valid

@Singleton
open class URLRepositoryImpl (
    private val mongoConfiguration: MongoConfiguration,
    private val mongoClient: MongoClient
    ) : URLRepository {

    override fun save(@Valid entry: URL) {
        collection.insertOne(entry)
    }
    override fun list(): List<URL> = collection.find().into(ArrayList())

    private val collection: MongoCollection<URL> get() = mongoClient
            .getDatabase(mongoConfiguration.name)
            .getCollection(mongoConfiguration.collection, URL::class.java)
            .withCodecRegistry(this.getCodec())
}
