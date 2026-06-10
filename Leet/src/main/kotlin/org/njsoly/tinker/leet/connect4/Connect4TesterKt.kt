package org.njsoly.tinker.leet.connect4

fun main() {
    val game = Connect4Kt()
    announceMove(game, 1)

    repeat(20) {
        announceMove(game, (Math.random() * 3.0).toInt())
    }
}

fun announceMove(game: Connect4Kt, column: Int) {
    println("\tPlaying column $column")
    println(game.play(column))
}
