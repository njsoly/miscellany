package com.syntj.stocks

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.logging.Logger

class FinnhubRequester {

    companion object {
        val API_KEY: String? = EnvGetter().getEnv("finnhub_api_key")

        const val baseQuoteUri = "https://finnhub.io/api/v1/quote"
    }

    val logger = Logger.getLogger(javaClass.simpleName)

    init {

    }

    private val client: HttpClient = HttpClient.newHttpClient()

    fun requestQuoteAsString(symbol: WatchedStock) : HttpResponse<String> {
        val request: HttpRequest = HttpRequest.newBuilder(
            URI(buildQuoteRequestUri(symbol))
        ).build()

        val bodyHandler: HttpResponse.BodyHandler<String> = HttpResponse.BodyHandlers.ofString()

        return client.send(request, bodyHandler)
    }
    private fun buildQuoteRequestUri(symbol: String): String {
        var uri = "$baseQuoteUri?symbol=$symbol"
        if (API_KEY != null){
            uri += "&token=${API_KEY}"
        }
        logger.info("uri for quote $symbol: $uri")
        return uri
    }

    private fun buildQuoteRequestUri(symbol: WatchedStock): String {
        return buildQuoteRequestUri(symbol.name)
    }
}