package com.syntj

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.newHttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class NjStocks {

    private val client: HttpClient = newHttpClient()

    private val quoteUri = "https://finnhub.io/api/v1/quote?symbol=DBX&token=brgrd1frh5r9t6gjc440"

    fun run() {
        println("aoesunthaoesunth")

        val request: HttpRequest = HttpRequest.newBuilder(
            URI(quoteUri)
        ).build()

        val bodyHandler: HttpResponse.BodyHandler<String> = HttpResponse.BodyHandlers.ofString()

        val response: HttpResponse<String> = client.send(request, bodyHandler)

        println(response.body())
    }
}


fun main() {
    NjStocks().run()
}
