package org.njsoly.tinker.leet.connect4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Connect4Test {

    // verifies the response for every move
    void runTheTest(int[] moves, String[] expected) {
        Connect4 game = new Connect4();
        for (int i = 0; i < moves.length; i++) {
            String response = game.play(moves[i]);
            assertEquals(expected[i], response, "Result " + i + " should return: '" + expected + "'");
        }
    }

    // verifies the response after the last move
    void runTheTest(int[] moves, String lastExpected) {
        Connect4 game = new Connect4();
        String response = null;
        for (int i = 0; i < moves.length; i++) {
            response = game.play(moves[i]);
        }
        assertEquals(lastExpected, response, "Result " + moves.length + " should return: '" + lastExpected + "'");
    }

    @Test
    void test1() {
        int[] moves = { 0, 1, 0, 1, 0, 1, 0 };
        String[] expectedResponses = { "Player 1 has a turn", "Player 2 has a turn", "Player 1 has a turn",
                "Player 2 has a turn", "Player 1 has a turn", "Player 2 has a turn", "Player 1 wins!" };
        runTheTest(moves, expectedResponses);
    }

    @Test
    void testContinueAfterFinish() {
        int[] moves = new int[] { 1, 1, 2, 2, 3, 3, 4, 4 };
        runTheTest(moves, "Game has finished!");
    }

    @Test
    void testFullColumn() {
        int[] moves = new int[] { 4, 4, 4, 4, 4, 4, 4 };
        runTheTest(moves, "Column full!");
    }
}
