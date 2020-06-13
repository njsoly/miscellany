package com.syntj

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.newHttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.logging.Logger

class NjStocks {

    private val client: HttpClient = newHttpClient()
    private val logger = Logger.getLogger(javaClass.simpleName)

    companion object {
        @JvmStatic
        val API_KEY: String? = System.getenv("finnhub_api_key")
    }

    private val baseQuoteUri = "https://finnhub.io/api/v1/quote"

    private fun buildQuoteRequestUri(symbol: String): String {
        var uri = "$baseQuoteUri?symbol=$symbol"
        if (API_KEY != null){
            uri += "&token=$API_KEY"
        }
        logger.info("uri for quote $symbol: $uri")
        return uri
    }

    fun run() {
        println("************** NjStocks **************")

        val request: HttpRequest = HttpRequest.newBuilder(
            URI(buildQuoteRequestUri("DBX"))
        ).build()

        val bodyHandler: HttpResponse.BodyHandler<String> = HttpResponse.BodyHandlers.ofString()

        val response: HttpResponse<String> = client.send(request, bodyHandler)
        println(response.body())

//        println("System.getenv():${System.getenv()}")
        println("System.getenv():${System.getenv("finnhub_api_key")}")
    }
}


fun main() {
    NjStocks().run()
}
