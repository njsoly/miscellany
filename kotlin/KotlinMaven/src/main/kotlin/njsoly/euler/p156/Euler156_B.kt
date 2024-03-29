package njsoly.euler.p156

import njsoly.util.DateUtil.Companion.readableTimeSinceInit
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
 * Note: if, for some n, f(n,d)=n for more than one value of d this value of n is counted again
 * for every value of d for which f(n,d)=n.
 *
 * Useful reference on some fun theory:
 * https://www.quora.com/How-many-times-does-the-digit-1-appear-in-the-counting-numbers-from-1-to-1-000-000-One-to-a-million
 */
class Euler156_B {

    companion object {
        const val MAX_N = 1111111110L
    }

    val solutions: HashMap<Int, LinkedList<Long>> = HashMap<Int, LinkedList<Long>>()
    val s: ArrayList<Long> = ArrayList()
    private val f: ArrayList<Long> = ArrayList()

    /** returns all the solutions of f(n,d) */
    fun findSolutionsOfF_OfNAndD (maxN: Long = MAX_N): HashMap<Int, LinkedList<Long>> {
        println("trying for solutions of f(n,d) for 1 <= d <= 9, 0 <= n <= $maxN")

        for (n in 0 .. maxN) {
            for (d in 1..9) {

                f[d] = f[d] + n.toString().count { digit -> digit.toString().toInt() == d }

                if (f[d] == n) {
                    solutions[d]!!.add(n)
                    s[d] += n
                }
            }

            if (n % 25000000L == 0L) {
                println("t = ${readableTimeSinceInit()}, n = $n, current sums: $s. s(1..9) = ${s.sum()}")
            }
        }

        return solutions
    }

    init {
        for (i in 0..10) {
            s.add(0)
            f.add(0)
            solutions[i] = LinkedList()
        }
    }
}

fun main() {
    println("Euler #156 B.")

    val euler = Euler156_B()
    val timesElapsed = mutableMapOf<Int, String>()

    val solutions = euler.findSolutionsOfF_OfNAndD(maxN = 111111L)
    timesElapsed[0] = readableTimeSinceInit()

    for (d in 1..9) {
        println("found ${solutions.size} solutions for f(n,$d), for sum s($d) = ${solutions[d]!!.sum()}, s(1..$d) = ${euler.s.sum()}")
    }

    println("finished in ${readableTimeSinceInit()}.  total sum: ${euler.s.sum()}" +
            "\ntimes: \n" + timesElapsed.values
    )

} // end main
