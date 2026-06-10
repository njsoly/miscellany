package org.njsoly.tinker.leet.connect4;

public class Connect4 {

    int moveNumber = 0;
    int[][] gameBoard = new int[6][7];
    int gameWinner = -1;


    public String play(int column) {

        if (gameWinner >= 0) {
            return sayGameIsOver();
        }

        int player = moveNumber % 2 + 1;

        int row = nextAvailableSpace(column);
        if(row == -1) {
            return "Column full!";
        } else {
            gameBoard[row][column] = player;
        }

        boolean gameOver = isGameOver(row, column);
        if (gameOver){
            gameWinner = player;
            return sayWhoWon(gameWinner);
        } else {
            return "Player " + (moveNumber++ % 2 + 1) + " has a turn";
        }
    }

    boolean isGameOver(int row, int column) {
        if (checkVertical(row, column)){
            return true;
        }
        if (checkHorizontal(row, column)){
            return true;
        }
        if (checkDiagonal(row, column)) {
            return true;
        }
        return checkBackDiagonal(row, column);
    }

    boolean checkVertical(int row, int column) {
        int player = gameBoard[row][column];

        int tilesBehind = 0;
        int tilesInFront = 0;
        for(int j = row - 1; j >= 0; j--){
            if (gameBoard[j][column] == player){
                tilesBehind++;
            } else break;
        }
        for(int j = row + 1; j < gameBoard.length; j++){
            if (gameBoard[j][column] == player){
                tilesInFront++;
            } else break;
        }

        return tilesBehind + tilesInFront >= 3;
    }

    boolean checkHorizontal(int row, int column) {
        int player = gameBoard[row][column];

        int tilesBehind = 0;
        int tilesInFront = 0;

        for(int i = column - 1; i >= 0; i--){
            if (gameBoard[row][i] == player){
                tilesBehind++;
            } else {
                break;
            }

        }

        for(int i = column + 1; i < gameBoard[row].length; i++){
            if (gameBoard[row][i] == player){
                tilesInFront++;
            } else {
                break;
            }
        }

        return tilesBehind + tilesInFront >= 3;
    }

    boolean checkDiagonal(int row, int column) {
        int player = gameBoard[row][column];

        int tilesBehind = 0;
        int tilesInFront = 0;

        int r = row;
        int c = column;

        while (--r >= 0 && --c >= 0) {
            if (gameBoard[r][c] == player){
                tilesBehind++;
            } else break;
        }

        r = row;
        c = column;

        while (++r < gameBoard.length && ++c < gameBoard[row].length) {
            if (gameBoard[r][c] == player){
                tilesInFront++;
            } else break;
        }

        return tilesBehind + tilesInFront >= 3;
    }

    boolean checkBackDiagonal(int row, int column) {
        int player = gameBoard[row][column];

        int tilesBehind = 0;
        int tilesInFront = 0;

        int r = row;
        int c = column;

        while (++r < gameBoard.length && --c >= 0) {
            if (gameBoard[r][c] == player){
                tilesBehind++;
            } else break;
        }

        r = row;
        c = column;

        while (--r >= 0 && ++c < gameBoard[row].length) {
            if (gameBoard[r][c] == player){
                tilesInFront++;
            } else break;
        }

        return tilesBehind + tilesInFront >= 3;
    }

    int nextAvailableSpace(int col){
        for(int j = 0; j < gameBoard.length; j++){
            if (gameBoard[j][col] == 0){
                return j;
            }
        }
        return -1;
    }

    String sayWhoWon(int gameWinner){
        return "Player " + gameWinner + " wins!";
    }

    String sayGameIsOver(){
        return "Game has finished!";
    }
}
