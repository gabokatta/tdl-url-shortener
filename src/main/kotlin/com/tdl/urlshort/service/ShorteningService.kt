package com.tdl.urlshort.service

interface ShorteningService {

    fun shortenURL() : Long
    fun redirectURL() : Long
    fun getUsageMetrics() : Long

}