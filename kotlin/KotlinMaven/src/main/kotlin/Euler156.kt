import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

/**
 * https://projecteuler.net/problem=156
 *
 * Starting from zero the natural numbers are written down in base 10 like this:
 * 0 1 2 3 4 5 6 7 8 9 10 11 12....
 *
 * Consider the digit d=1. After we write down each number n, we will update the number of 1s
 * that have occurred and call this number f(n,1). The first values for f(n,1), then, are as follows:
 *
 *
 * n	f(n,1)
 * 0	0
 * 1	1
 * 2	1
 * 3	1
 * 4	1
 * 5	1
 * 6	1
 * 7	1
 * 8	1
 * 9	1
 * 10	2
 * 11	4
 * 12	5
 *
 * Note that f(n,1) never equals 3.
 * So the first two solutions of the equation f(n,1)=n are n=0 and n=1. The next solution is n=199981.
 *
 * In the same manner the function f(n,d) gives the total number of digits d that have been written down after the number n has been written.
 * In fact, for every digit d ≠ 0, 0 is the first solution of the equation f(n,d)=n.
 *
 * Let s(d) be the sum of all the solutions for which f(n,d)=n.
 * You are given that s(1)=22786974071.
 *
 * Find ∑ s(d) for 1 ≤ d ≤ 9.
 *
 * Note: if, for some n, f(n,d)=n for more than one value of d this value of n is counted again for every value of d for which f(n,d)=n.
 */
class Euler156 {

    companion object {
        const val MAX_N = 1111111110L
//        var initMillis = Instant.now(Clock.systemUTC().withZone(ZoneId.of("America/Chicago"))).toEpochMilli()
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
                "${m}m ${s}.${ms}s"
            } else {
                "${s}.${ms}s"
            }
        }

        fun readableTimeSinceInit() : String {
            return readableTimeFromMillis(millisSinceInit())
        }

    }


    val solutions: HashMap<Int, LinkedList<Soln>> = HashMap<Int, LinkedList<Soln>>()
    val s: ArrayList<Long> = ArrayList()

    /** returns all the solutions of f(n,d) */
    fun findSolutionsofFofNandD (d: Int = 1, maxN: Long = MAX_N) : List<Long> {
        var lastSoln: Long? = 0L
        solutions[d] = LinkedList<Soln>()

        while (lastSoln != null && lastSoln < maxN) {
            lastSoln = findNextSolutionOfF_N_D(lastSoln, d, maxN) ?: break

            s[d] += lastSoln
//            println(
//                "solution: f(n,$d): n = $lastSoln. s($d) is now at ${s[d]}, after ${solutions[d]!!.size} solutions, ${
//                    readableTimeFromMillis(
//                        millisSinceInit()
//                    )
//                } into run"
//            )

            solutions[d]!!.add(Soln(lastSoln, d))

            if (s[d] >= 22786974071L) {
                break
            }
        }

        println("done searching.")
        return solutions[d]!!.map{it.n}
    }

    /**
     * [solution] - The last point (so far) where f(n,d)=n.  Serves as the starting point for f and n.
     * returns null if maxN reached before next solution. */
    private fun findNextSolutionOfF_N_D(solution: Long = 0L, d: Int = 1, maxN: Long = MAX_N) : Long? {

        var f = solution

        for (n in solution+1 .. maxN) {
            f += n.toString().count { digit -> digit.toString().toInt() == d }

            if (f == n) {
                return f
            }
        }

        return null
    }

    class Soln (val n: Long, val d: Int)

    init {
        for (i in 0..10) {
            s.add(0)
        }
    }

}

fun main() {
    println("Euler #156.")

    val euler = Euler156()
    val timesElapsed = mutableMapOf<Int, String>()

    for (d in 1..9) {
        println("searching for solutions for f(n,$d).")
        
        val solutions = euler.findSolutionsofFofNandD(d, Euler156.MAX_N)
        timesElapsed[d] = Euler156.readableTimeSinceInit()

        println("found ${solutions.size} solutions for f(n,$d), for sum s($d) = $solutions.sum()")
    }

    println("finished.  total sum: ${euler.s.sum()}" +
            "\ntimes: \n" + timesElapsed.values)

}
