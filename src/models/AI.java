package models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Eran on 29-3-2017.
 */
public class AI {

    private boolean finishedTurn = false;

    private TicTacToe ticTacToe;

    private HashMap<String, Integer> possibleTurns;

    private char player;

    public AI(TicTacToe ticTacToe, char player) {
        this.ticTacToe = ticTacToe;
        this.possibleTurns = new HashMap<>();

        this.player = player;
    }

    public void doTurn(char[][] board) {
        this.finishedTurn = false;

        //check de open vakjes.
        //dan: check of de andere gebruiker kan winnen met een van de open vakjes.
        //zoja: geef dat vakje een score van 10. Sla op in mogelijke zet array
        //Check per vakje of de AI kan winnen ná het zetten van het vakje. Zoja: geef score van 5 aan dat vakje
        //Anders: geef score -1
        char[][] testBoard = board;
        int score = 0;
        int vakje = 0;
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                if(board[x][y] == ' ') {
                    score = this.getScore(x, y, testBoard);
                    this.possibleTurns.put("" + x + y, score);
                }
                else {
                    this.possibleTurns.put("" + x + y, 0);
                }
            }
        }

        System.out.println(this.possibleTurns);

        this.placeTurn();

        this.finishedTurn = true;

    }

    public int getScore(int x, int y, char[][] board) {
        board[x][y] = this.player;
        //check if I can win with this position
        if(this.checkIfICanWin(x, y, board)) {
            board[x][y] = ' ';
            return 100;
        }

        //check if the other player can win with this position
        if(this.checkIfOtherCanWin(x, y, board)) {
            board[x][y] = ' ';
            return 50;
        }

        if(this.getScore(x, y, board) > )
        board[x][y] = ' ';
        //check if I can with after placing this position
        return 1;
    }

    private boolean checkIfICanWin(int x, int y, char[][] testBoard) {
        testBoard[x][y] = this.player;

        return (testBoard[x][0] == this.player && testBoard[x][1] == this.player && testBoard[x][2] == this.player) ||
                (testBoard[0][y] == this.player && testBoard[1][y] == this.player && testBoard[2][y] == this.player) ||
                (testBoard[0][0] == this.player && testBoard[1][1] == this.player && testBoard[2][2] == this.player) ||
                (testBoard[0][2] == this.player && testBoard[1][1] == this.player && testBoard[2][0] == this.player);
    }

    private boolean checkIfOtherCanWin(int x, int y, char[][] testBoard) {
        char player = this.oppositePlayer();
        testBoard[x][y] = player;

        return (testBoard[x][0] == player && testBoard[x][1] == player && testBoard[x][2] == player) ||
                (testBoard[0][y] == player && testBoard[1][y] == player && testBoard[2][y] == player) ||
                (testBoard[0][0] == player && testBoard[1][1] == player && testBoard[2][2] == player) ||
                (testBoard[0][2] == player && testBoard[1][1] == player && testBoard[2][0] == player);
    }

    public void placeTurn() {

        //Initialize the biggest key-value pair
        Map.Entry<String, Integer> biggest = null;

        //Loop through all key-value pairs in the possible turns (all potential positions)
        for (Map.Entry<String, Integer> entry : this.possibleTurns.entrySet())
        {
            //If a value is bigger than the current iteration, replace the former key-value pair with this new one.
            //The biggest key-value pair has the higest priority
            if (biggest == null || entry.getValue().compareTo(biggest.getValue()) > 0)
            {
                biggest = entry;
            }
        }

        //Get the current playing board
        char[][] newBoard = this.ticTacToe.getBoard();
        //Subtract the x value from the key
        int x = Integer.valueOf(biggest.getKey().split("")[0]);

        //subtract the y value from the key
        int y = Integer.valueOf(biggest.getKey().split("")[1]);

        //make a new board where the AI placed his turn
        newBoard[x][y] = this.player;

        //Replace the tic-tac-toe board with the board where the AI placed his turn
        this.ticTacToe.setBoard(newBoard);


        //Debug
        System.out.println(Arrays.deepToString(newBoard));

    }

    private boolean checkForWinningVakje(int x, int y, char[][] testBoard, boolean otherPlayer) {
        char player = this.oppositePlayer();

        if((testBoard[x][0] == player && testBoard[x][1] == player && testBoard[x][2] == player) ||
           (testBoard[0][y] == player && testBoard[1][y] == player && testBoard[2][y] == player) ||
           (testBoard[0][0] == player && testBoard[1][1] == player && testBoard[2][2] == player) ||
           (testBoard[0][2] == player && testBoard[1][1] == player && testBoard[2][0] == player)) {
            return true;
        }

        return false;
    }

    private char oppositePlayer() {
        if(this.player == 'o') {
            return 'x';
        }
        return 'o';

    }


//    private char convertIntToPlace(int place) {
//        char[][] board = this.ticTacToe.getBoard();
//        for(int x = 0; x < board.length; x++) {
//            for(int y = 0; y < board[x].length; y++) {
//                if(x + y == place)
//                    return board[x][y];
//            }
//        }
//    }

    /**
     * Checks whether the AI has finished its turn
     * @return boolean
     */
    public boolean hasFinishedTurn() {
        return this.finishedTurn;
    }
}
