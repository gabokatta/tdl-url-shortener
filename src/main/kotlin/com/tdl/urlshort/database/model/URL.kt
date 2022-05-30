package com.tdl.urlshort.database.model

import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Positive

@Introspected
data class URL @Creator @BsonCreator constructor(

    @field:BsonProperty("url")
    @param:BsonProperty("url")
    @field:NotBlank
    val url: String,

    @field:BsonProperty("hash")
    @param:BsonProperty("hash")
    @field:NotBlank
    val hash: String,

    @field:BsonProperty("times_used")
    @param:BsonProperty("times_used")
    @field:NotNull
    @field:Positive
    val timesUsed: Int = 0,

    @field:BsonProperty("last_used")
    @param:BsonProperty("last_used")
    @field:PastOrPresent
    @field:NotNull
    val lastUsed: Date
)
