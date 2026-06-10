package org.njsoly.tinker.leet;

public class Connect4Tester {

    public static void announceMove(Connect4 game, int column){
        System.out.println("\tPlaying column " + column);
        System.out.println(game.play(column));
    }

    public static void main(String[] args) {
        Connect4 game = new Connect4();
        announceMove(game, 1);


        for(int i = 0; i < 20; i++){
            announceMove(game, ((int) (Math.random() * 3.0)));
        }
    }
}
