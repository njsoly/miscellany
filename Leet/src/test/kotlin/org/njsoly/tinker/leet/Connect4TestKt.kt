package org.njsoly.tinker.leet

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class Connect4TestKt {

    private fun runTheTest(moves: IntArray, expected: Array<String>) {
        val game = Connect4Kt()
        for (i in moves.indices) {
            val response = game.play(moves[i])
            assertEquals(expected[i], response, "Result $i should return: '${expected[i]}'")
        }
    }

    private fun runTheTest(moves: IntArray, lastExpected: String) {
        val game = Connect4Kt()
        var response: String? = null
        for (i in moves.indices) {
            response = game.play(moves[i])
        }
        assertEquals(lastExpected, response, "Result ${moves.size} should return: '$lastExpected'")
    }

    @Test
    fun test1() {
        val moves = intArrayOf(0, 1, 0, 1, 0, 1, 0)
        val expectedResponses = arrayOf(
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
