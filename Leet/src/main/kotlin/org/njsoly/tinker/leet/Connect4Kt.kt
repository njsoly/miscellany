package org.njsoly.tinker.leet

class Connect4Kt {
    
    private var moveNumber = 0
    private val gameBoard = Array(6) { IntArray(7) }
    private var gameWinner = -1


    fun play(column: Int): String {

        if (gameWinner >= 0) {
            return sayGameIsOver()
        }

        val player = moveNumber % 2 + 1

        val row = nextAvailableSpace(column)
        if (row == -1) {
            return "Column full!"
        } else {
            gameBoard[row][column] = player
        }

        val gameOver = isGameOver(row, column)
        return if (gameOver) {
            gameWinner = player
            sayWhoWon(gameWinner)
        } else {
            "Player ${moveNumber++ % 2 + 1} has a turn"
        }
    }

    private fun isGameOver(row: Int, column: Int): Boolean {
        return checkVertical(row, column) ||
               checkHorizontal(row, column) ||
               checkDiagonal(row, column) ||
               checkBackDiagonal(row, column)
    }

    private fun checkVertical(row: Int, column: Int): Boolean {
        val player = gameBoard[row][column]

        var tilesBehind = 0
        var tilesInFront = 0
        for (j in row - 1 downTo 0) {
            if (gameBoard[j][column] == player) {
                tilesBehind++
            } else break
        }
        for (j in row + 1 until gameBoard.size) {
            if (gameBoard[j][column] == player) {
                tilesInFront++
            } else break
        }

        return tilesBehind + tilesInFront >= 3
    }

    private fun checkHorizontal(row: Int, column: Int): Boolean {
        val player = gameBoard[row][column]

        var tilesBehind = 0
        var tilesInFront = 0

        for (i in column - 1 downTo 0) {
            if (gameBoard[row][i] == player) {
                tilesBehind++
            } else {
                break
            }

        }

        for (i in column + 1 until gameBoard[row].size) {
            if (gameBoard[row][i] == player) {
                tilesInFront++
            } else {
                break
            }
        }

        return tilesBehind + tilesInFront >= 3
    }

    private fun checkDiagonal(row: Int, column: Int): Boolean {
        val player = gameBoard[row][column]

        var tilesBehind = 0
        var tilesInFront = 0

        var r = row
        var c = column

        while (--r >= 0 && --c >= 0) {
            if (gameBoard[r][c] == player) {
                tilesBehind++
            } else break
        }

        r = row
        c = column

        while (++r < gameBoard.size && ++c < gameBoard[row].size) {
            if (gameBoard[r][c] == player) {
                tilesInFront++
            } else break
        }

        return tilesBehind + tilesInFront >= 3
    }

    private fun checkBackDiagonal(row: Int, column: Int): Boolean {
        val player = gameBoard[row][column]

        var tilesBehind = 0
        var tilesInFront = 0

        var r = row
        var c = column

        while (++r < gameBoard.size && --c >= 0) {
            if (gameBoard[r][c] == player) {
                tilesBehind++
            } else break
        }

        r = row
        c = column

        while (--r >= 0 && ++c < gameBoard[row].size) {
            if (gameBoard[r][c] == player) {
                tilesInFront++
            } else break
        }

        return tilesBehind + tilesInFront >= 3
    }

    private fun nextAvailableSpace(col: Int): Int {
        for (j in gameBoard.indices) {
            if (gameBoard[j][col] == 0) {
                return j
            }
        }
        return -1
    }

    private fun sayWhoWon(gameWinner: Int): String {
        return "Player $gameWinner wins!"
    }

    private fun sayGameIsOver(): String {
        return "Game has finished!"
    }
}
