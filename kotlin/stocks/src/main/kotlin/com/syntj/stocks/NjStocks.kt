package com.syntj.stocks

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
        val API_KEY: String? = EnvGetter().getEnv("finnhub_api_key")
    }

    private val baseQuoteUri = "https://finnhub.io/api/v1/quote"


    fun run() {
        println("************** NjStocks **************")

        val quoteString: HttpResponse<String> = requestQuoteAsString(WatchedStock.DBX)

        println(quoteString.body())

    }




}


fun main() {
    NjStocks().run()
}
