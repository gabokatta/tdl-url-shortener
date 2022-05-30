package com.tdl.urlshort.database.repository
import com.mongodb.MongoClientSettings
import com.tdl.urlshort.database.model.URL
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import javax.validation.Valid

interface URLRepository {

    fun list(): List<URL>
    fun save(@Valid entry: URL)

    fun getCodec() : CodecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(
            PojoCodecProvider
            .builder()
            .register(URL::class.java)
            .build()
        )
    )

}