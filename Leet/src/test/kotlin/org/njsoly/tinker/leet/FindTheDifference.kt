package org.njsoly.tinker.leet

class FindTheDifference {

    interface Solution {
        fun findTheDifference(s: String, t: String): Char
    }

    class Solution1 : Solution {
        override fun findTheDifference(s: String, t: String): Char {
            val sb = StringBuffer(t)
            s.forEach{ ch ->
                sb.deleteCharAt(sb.indexOf(ch))
            }
            return sb.toString()[0]
        }
    }

    class Solution2 : Solution {
        override fun findTheDifference(s: String, t: String): Char {
            val sb = StringBuffer(t)
            s.forEach{ ch ->
                sb.deleteCharAt(sb.indexOf(ch))
            }
            return sb.toString()[0]
        }
    }
}

fun main() {
    val testCases = listOf(
        Pair("abc", "abdc"),
        Pair("", "y"),
        Pair("aoeusnth", "snthaxoeu")
    )

    val solutions = listOf(
        FindTheDifference.Solution1(),
        FindTheDifference.Solution2(),
    )
    solutions.forEachIndexed { index, solution ->
        println("${solutions[index].javaClass.simpleName}")
        var totalTime = 0L
        testCases.forEach { testCase ->
            var result: Char = ' '
            val time = kotlin.system.measureNanoTime {
                result = solution.findTheDifference(testCase.first, testCase.second)
            }
            totalTime += time
            println("  Test case $testCase: $result (${time / 1000.0} μs)")
        }
        println("  Total time: ${totalTime / 1000.0} μs (${totalTime / 1_000_000.0} ms)")
        println()
    }
}
