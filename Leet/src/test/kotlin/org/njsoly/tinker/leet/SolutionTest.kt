package org.njsoly.tinker.leet

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class SolutionTest {
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

    @Test
    fun testPlayer1() {
        val moves = intArrayOf(
            3, 3, 3, 2, 4, 2, 2, 4, 4, 0, 6, 3, 4, 3, 4, 4, 2, 3, 6, 0, 0, 0, 6, 6, 0, 0, 2, 2, 6,
            6, 5
        )
        runTheTest(moves, "Player 1 wins!")
    }

    @Test
    fun testDiagonalWin() {
        val moves = intArrayOf(3, 3, 4, 2, 3, 2, 4, 4, 5, 6, 3, 4, 0, 4, 4, 3, 0, 1, 1, 2, 2)
        runTheTest(moves, "Player 1 wins!")
    }

    @Test
    fun testPlayer2() {
        val moves = intArrayOf(0, 1, 2, 1, 3, 1, 4, 1)
        runTheTest(moves, "Player 2 wins!")
    }

    @Test
    fun testPlayer2Diag() {
        val moves = intArrayOf(2, 2, 1, 3, 1, 1, 0, 3, 0, 4, 0, 0)
        runTheTest(moves, "Player 2 wins!")
    }


    // Runs through 100 random games side by side with solution.
    @Test
    fun RandomTest() {
        for (testNum in 0..99) {
            val solution = Connect4Solution()
            val game = Connect4()

            for (moveNum in 0..44) {
                val move = (Math.random() * 7).toInt()
                val response = game.play(move)
                val expected = solution.play(move)
                Assertions.assertEquals(expected, response, "Should return: '" + expected + "'")
            }
        }
    }

    internal inner class Connect4Solution {
        var grid: Array<IntArray?> = Array<IntArray?>(7) { IntArray(6) }
        var winner: Int = 0
        var currentPlayer: Int = 1

        fun checkLine(x: Int, y: Int, dx: Int, dy: Int): Int {
            val type = grid[x]!![y]
            for (i in 1..3) {
                val tx = x + i * dx
                val ty = y + i * dy
                if (tx < 7 && ty >= 0 && ty < 6) {
                    if (type != grid[tx]!![ty]) {
                        return 0
                    }
                } else {
                    return 0
                }
            }
            return type
        }

        fun checkWins() {
            var isdraw = true
            for (x in 0..6) {
                for (y in 0..5) {
                    if (grid[x]!![y] == 0) {
                        isdraw = false
                    }
                }
            }
            if (isdraw) {
                winner = 3
                return
            }
            for (x in 0..6) {
                for (y in 0..5) {
                    var result = checkLine(x, y, 1, 0)
                    if (result != 0) {
                        winner = result
                        return
                    }
                    result = checkLine(x, y, 0, 1)
                    if (result != 0) {
                        winner = result
                        return
                    }
                    result = checkLine(x, y, 1, 1)
                    if (result != 0) {
                        winner = result
                        return
                    }
                    result = checkLine(x, y, 1, -1)
                    if (result != 0) {
                        winner = result
                        return
                    }
                }
            }
        }

        fun play(column: Int): String {
            if (winner != 0) {
                return "Game has finished!"
            }
            var didMove = false
            val currentPlayerSaved = currentPlayer
            for (y in 0..5) {
                if (grid[column]!![y] == 0) {
                    grid[column]!![y] = currentPlayer
                    currentPlayer = if (currentPlayer == 1) 2 else 1
                    didMove = true
                    break
                }
            }
            if (!didMove) {
                return "Column full!"
            }
            checkWins()
            if (winner == 0) {
                return "Player " + currentPlayerSaved + " has a turn"
            } else {
                return "Player " + winner + " wins!"
            }
        }
    }
}
