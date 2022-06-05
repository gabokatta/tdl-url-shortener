package com.tdl.urlshort.database.repository
import com.mongodb.MongoClientSettings
import com.tdl.urlshort.database.model.URLRegister
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import javax.validation.Valid

interface URLRepository {

    fun find(hash: String): URLRegister?
    fun list(): List<URLRegister>
    fun save(@Valid entry: URLRegister)

    fun getCodec() : CodecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(
            PojoCodecProvider
            .builder()
            .register(URLRegister::class.java)
            .build()
        )
    )

}