package com.tdl.urlshort.database.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.PositiveOrZero

@Introspected
data class URLRegister @Creator @BsonCreator constructor(

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
    @field:PositiveOrZero
    var timesUsed: Int = 0,

    @field:BsonProperty("last_used")
    @param:BsonProperty("last_used")
    @field:PastOrPresent
    @field:NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    var lastUsed: Date = Calendar.getInstance().time
)
