package org.njsoly.tinker.leet

import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder

fun main() {
    val opt = OptionsBuilder()
        .include(FindTheDifferenceBenchmark::class.java.canonicalName)
        .build()

    Runner(opt).run()
}
