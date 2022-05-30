package com.tdl.urlshort.database.repository

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.naming.Named
import jakarta.inject.Singleton
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotBlank


@Introspected
data class DbUrlEntry @Creator @BsonCreator constructor(
    @field:BsonProperty("url") @param:BsonProperty("url") @field:NotBlank val url: String,
    @field:BsonProperty("hash") @param:BsonProperty("hash") @field:NotBlank val hash: String
    ) {
}


@ConfigurationProperties("db")
interface MongoDbConfiguration : Named {

    val collection: String
}



@Singleton
open class ShortenerDB (
    private val mongoConf: MongoDbConfiguration,
    private val mongoClient: MongoClient
    ) : ShortenerRepository {

    private val urlRegistry: CodecRegistry = fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().register(DbUrlEntry::class.java).build())
    )
    override fun save(@Valid entry: DbUrlEntry) {
        collection.insertOne(entry)
    }
    override fun list(): List<DbUrlEntry> = collection.find().into(ArrayList())

    private val collection: MongoCollection<DbUrlEntry>
        get() = mongoClient.getDatabase(mongoConf.name)
            .getCollection(mongoConf.collection, DbUrlEntry::class.java)
            .withCodecRegistry(urlRegistry)
}