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
        for(int y = 0; y < this.board.length; y++) {
            for(int x = 0; x < this.board[y].length; x++) {
                this.board[y][x] = ' ';
            }
        }
    }

    public char[][] getBoard() {
        return this.board;
    }

    public void setBoard(char[][] newBoard) {
        this.board = newBoard;
    }

    public void showBoard() {
        for(int y = 0; y < this.board.length; y++) {
            for(int x = 0; x < this.board[y].length; x++) {
                System.out.print(" " + this.board[y][x] + " | ");
            }
            System.out.println();
        }
    }

    public boolean doTurn(int y, int x, char player) {
        try {
            if(this.board[y][x] == ' ') {
                this.board[y][x] = player;

                return true;
            }
        }
        catch(IndexOutOfBoundsException e) {
            return false;
        }

        return false;
    }

    public boolean checkForWinner(char player) {

        for(int y = 0; y < this.board.length; y++) {
            //horizontal
            if(this.board[y][0] == player && this.board[y][1] == player && this.board[y][2] == player)
                return true;

            //vertical
            if(this.board[0][y] == player && this.board[1][y] == player && this.board[2][y] == player)
                return true;
        }

        //both diagonal
        if((this.board[0][0] == player && this.board[1][1] == player && this.board[2][2] == player) ||
            this.board[0][2] == player && this.board[1][1] == player && this.board[2][0] == player) {
            return true;
        }

        return false;
    }

    public boolean checkDraw() {
        boolean filled = true;
        for(int y = 0; y < this.board.length; y++) {
            if( ( this.board[y][0] == ' ' || this.board[y][1] == ' ' || this.board[y][2] == ' ' ) )
                filled = false;
        }

        return filled && !this.checkForWinner('o') && !this.checkForWinner('y');
    }
}
