package njsoly.util

class DateUtil {

//    TimeZone.getDefault()

    companion object {
        var initMillis = System.currentTimeMillis()

        fun millisSinceInit() : Long {
            return System.currentTimeMillis() - initMillis
        }

        fun readableTimeFromMillis(millis: Long) : String {
            var x = millis
            val ms = millis % 1000
            x /= 1000
            val s = x % 60
            x /= 60
            val m = x

            return if (m > 0) {
                "${m}m ${s.toString().padStart(2, '0')}.${ms.toString().padStart(3, '0')}s"
            } else {
                "${s.toString().padStart(2, '0')}.${ms.toString().padStart(3, '0')}s"
            }
        }

        fun readableTimeSinceInit() : String {
            return readableTimeFromMillis(millisSinceInit())
        }
    }
}