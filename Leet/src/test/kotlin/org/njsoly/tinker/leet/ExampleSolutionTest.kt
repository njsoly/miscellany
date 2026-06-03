package org.njsoly.tinker.leet

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class ExampleSolutionTest {
    // verifies the response for every move
    fun runTheTest(moves: IntArray, expected: Array<String?>) {
        val game = Connect4()
        for (i in moves.indices) {
            val response = game.play(moves[i])
            Assertions.assertEquals(expected[i], response, "Should return: '" + expected + "'")
        }
    }

    // verifies the response after the last move
    fun runTheTest(moves: IntArray, lastExpected: String?) {
        val game = Connect4()
        var response: String? = null
        for (i in moves.indices) {
            response = game.play(moves[i])
        }
        Assertions.assertEquals(lastExpected, response, "Should return: '" + lastExpected + "'")
    }

    @Test
    fun test1() {
        val moves = intArrayOf(0, 1, 0, 1, 0, 1, 0)
        val expectedResponses = arrayOf<String?>(
            "Player 1 has a turn", "Player 2 has a turn", "Player 1 has a turn",
            "Player 2 has a turn", "Player 1 has a turn", "Player 2 has a turn", "Player 1 wins!"
        )
        runTheTest(moves, expectedResponses)
    }

    @Test
    fun testContinueAfterFinish() {
        val moves = intArrayOf(1, 1, 2, 2, 3, 3, 4, 4)
        runTheTest(moves, "Game has finished!")
    }

    @Test
    fun testFullColumn() {
        val moves = intArrayOf(4, 4, 4, 4, 4, 4, 4)
        runTheTest(moves, "Column full!")
    }
}
