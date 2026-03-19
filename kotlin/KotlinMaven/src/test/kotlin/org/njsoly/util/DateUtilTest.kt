package njsoly.util

import org.junit.Test
import java.util.*

class DateUtilTest {

    @Test
    fun `the default timezone is` () {
        val tz = TimeZone.getDefault()
        println("default timezone is: $tz")

    }
}