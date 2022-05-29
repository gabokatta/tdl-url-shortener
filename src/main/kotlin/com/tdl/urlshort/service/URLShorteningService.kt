package com.tdl.urlshort.service

import com.tdl.urlshort.database.repository.ShortenerRepository
import jakarta.inject.Singleton

@Singleton
class URLShorteningService(private val dbService: ShortenerRepository) : ShorteningService {

    override fun shortenURL() : Long {
        TODO("Not yet implemented")
    }

    override fun redirectURL() : Long {
        TODO("Not yet implemented")
    }

    override fun getUsageMetrics() : Long {
        TODO("Not yet implemented")
    }
}