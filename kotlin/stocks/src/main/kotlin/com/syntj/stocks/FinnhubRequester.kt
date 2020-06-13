package com.syntj.stocks

import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class FinnhubRequester {

    fun requestQuoteAsString(symbol: WatchedStock) : HttpResponse<String> {
        val request: HttpRequest = HttpRequest.newBuilder(
            URI(buildQuoteRequestUri(symbol))
        ).build()

        val bodyHandler: HttpResponse.BodyHandler<String> = HttpResponse.BodyHandlers.ofString()

        return client.send(request, bodyHandler)
    }

    private fun buildQuoteRequestUri(symbol: String): String {
        var uri = "$baseQuoteUri?symbol=$symbol"
        if (NjStocks.API_KEY != null){
            uri += "&token=${NjStocks.API_KEY}"
        }
        logger.info("uri for quote $symbol: $uri")
        return uri
    }

    private fun buildQuoteRequestUri(symbol: WatchedStock): String {
        return buildQuoteRequestUri(symbol.name)
    }
}