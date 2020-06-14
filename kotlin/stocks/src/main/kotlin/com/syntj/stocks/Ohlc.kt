package com.syntj.stocks

data class Ohlc(
    val c: Double,
    val h: Double,
    val l: Double,
    var o: Double,
    val pc: Double,
    val t: Long
)