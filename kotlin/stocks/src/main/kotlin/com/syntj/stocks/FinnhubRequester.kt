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

    private val logger: Logger = Logger.getLogger(javaClass.simpleName)

    private val client: HttpClient = HttpClient.newHttpClient()

    /**
     * build and send for QUOTE endpoint to Finnhub API
     * expect a return in this form:
     * {"c":21.03,"h":21.28,"l":20.55,"o":21.04,"pc":20.42,"t":1592040051}
     */
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