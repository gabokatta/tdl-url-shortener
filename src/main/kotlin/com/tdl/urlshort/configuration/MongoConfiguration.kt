package com.tdl.urlshort.configuration

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.naming.Named

@ConfigurationProperties("db")
interface MongoConfiguration : Named {

    val collection: String

}