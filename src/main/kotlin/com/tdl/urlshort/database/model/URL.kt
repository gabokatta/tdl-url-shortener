package com.tdl.urlshort.database.model

import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import javax.validation.constraints.NotBlank

@Introspected
data class URL @Creator @BsonCreator constructor(

    @field:BsonProperty("url")
    @param:BsonProperty("url")
    @field:NotBlank
    val url: String,

    @field:BsonProperty("hash")
    @param:BsonProperty("hash")
    @field:NotBlank val
    hash: String

) {
}
