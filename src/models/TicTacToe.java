package models;

import javafx.scene.layout.Pane;

/**
 * Created by Eran on 27-3-2017.
 */
public class TicTacToe {

    public char[][] board;

    public TicTacToe() {

        this.board = new char[3][3];

        this.initBoard();
    }

    private void initBoard() {
        for(int x = 0; x < this.board.length; x++) {
            for(int y = 0; y < this.board[x].length; y++) {
                this.board[x][y] = ' ';
            }
        }
    }

    public char[][] getBoard() {
        return this.board;
    }

    public void showBoard() {
        for(int x = 0; x < this.board.length; x++) {
            for(int y = 0; y < this.board[x].length; y++) {
                System.out.print(" " + this.board[x][y] + " | ");
            }
            System.out.println();
        }
    }

    public boolean doTurn(int x, int y, char player) {
        System.out.println(x);
        System.out.println(y);

        try {
            if(this.board[x - 1][y - 1] == ' ') {
                this.board[x - 1][y - 1] = player;

                return true;
            }
        }
        catch(IndexOutOfBoundsException e) {
            return false;
        }

        return false;
    }

    public boolean checkForWinner(char player) {
        //[[x, x, x],
        // [x, x, x],
        // [x, x, x]]

        for(int x = 0; x < this.board.length; x++) {
            //horizontal
            if(this.board[x][0] == player && this.board[x][1] == player && this.board[x][2] == player)
                return true;

            //vertical
            if(this.board[0][x] == player && this.board[1][x] == player && this.board[2][x] == player)
                return true;
        }

        //both diagonal
        if((this.board[0][0] == player && this.board[1][1] == player && this.board[2][2] == player) ||
            this.board[0][2] == player && this.board[1][1] == player && this.board[2][0] == player) {
            return true;
        }

        return false;
    }
}
