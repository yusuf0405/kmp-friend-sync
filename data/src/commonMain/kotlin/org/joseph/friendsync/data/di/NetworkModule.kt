package org.joseph.friendsync.data.di

import org.joseph.friendsync.data.BuildKonfig
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private val CURRENT_CONFIG = BuildKonfig.CURRENT_CONFIG
val BASE_URL = "http://$CURRENT_CONFIG:8080"

private const val DEFAULT_REQUEST_TIMEOUT_MILLS = 10_000L

internal val networkModule = module {
    single {
        HttpClient {
            defaultRequest {
                url.takeFrom(BASE_URL)
                contentType(Json)
            }
            install(HttpCache)

            install(HttpTimeout) {
                requestTimeoutMillis = DEFAULT_REQUEST_TIMEOUT_MILLS
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                        prettyPrint = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.i(message)
                    }
                }
            }
        }
    }
}