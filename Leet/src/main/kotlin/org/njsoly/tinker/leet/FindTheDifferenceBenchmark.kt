package org.njsoly.tinker.leet

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
open class FindTheDifferenceBenchmark {

    private val solution1 = FindTheDifference.Solution1()
    private val solution2 = FindTheDifference.Solution2()

    private val testCase1S = "abc"
    private val testCase1T = "abdc"

    private val testCase2S = ""
    private val testCase2T = "y"

    private val testCase3S = "aoeusnth"
    private val testCase3T = "snthaxoeu"

    @Benchmark
    fun solution1TestCase1(): Char {
        return solution1.findTheDifference(testCase1S, testCase1T)
    }

    @Benchmark
    fun solution1TestCase2(): Char {
        return solution1.findTheDifference(testCase2S, testCase2T)
    }

    @Benchmark
    fun solution1TestCase3(): Char {
        return solution1.findTheDifference(testCase3S, testCase3T)
    }

    @Benchmark
    fun solution2TestCase1(): Char {
        return solution2.findTheDifference(testCase1S, testCase1T)
    }

    @Benchmark
    fun solution2TestCase2(): Char {
        return solution2.findTheDifference(testCase2S, testCase2T)
    }

    @Benchmark
    fun solution2TestCase3(): Char {
        return solution2.findTheDifference(testCase3S, testCase3T)
    }
}
