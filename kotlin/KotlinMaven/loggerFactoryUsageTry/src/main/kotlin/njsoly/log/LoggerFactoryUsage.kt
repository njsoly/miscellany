package njsoly.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Does not work.
 * TODO make this work
 */
open class LoggerFactoryUsage {
    val l: Logger = LoggerFactory.getLogger(this::class.java)

}
    fun main(args: Array<String>){
        println("args: $args")

        println("Hello, this is " + LoggerFactoryUsage::class.java)

        val lfu: LoggerFactoryUsage = LoggerFactoryUsage()
        lfu.l.info("snth")

    }
