package com.tdl.urlshort.database.repository
import javax.validation.Valid

interface ShortenerRepository {
    fun list(): List<DbUrlEntry>

    fun save(@Valid entry: DbUrlEntry)
}