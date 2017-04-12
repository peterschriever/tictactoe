package Game.Models;

import Framework.AI.BotInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AI implements BotInterface {

    private TTTGame TTTGame;

    private HashMap<String, Integer> possibleTurns;

    private char player;

    private int[] lastMove = new int[2];

    public AI(TTTGame TTTGame, char player) {
        this.TTTGame = TTTGame;
        this.possibleTurns = new HashMap<>();

        this.player = player;
    }

    public char getPlayer() {
        return this.player;
    }

    public int[] doTurn(char[][] board) {

        //check de open vakjes.
        //dan: check of de andere gebruiker kan winnen met een van de open vakjes.
        //zoja: geef dat vakje een score van 10. Sla op in mogelijke zet array
        //Check per vakje of de AI kan winnen ná het zetten van het vakje. Zoja: geef score van 5 aan dat vakje
        //Anders: geef score -1
        int score;
        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                if(board[y][x] == ' ') {
                    score = this.getScore(y, x, board);
                    this.possibleTurns.put("" + y + x, score);
                }
                else {
                    this.possibleTurns.put("" + y + x, -1);
                }
            }
        }

        System.out.println(this.possibleTurns);

        this.placeTurn();

        // Return this turns AI move [0:x, 1:y]
        return this.lastMove;
    }

    private int getScore(int y, int x, char[][] board) {
        board[y][x] = this.player;
        //check if I can win with this position
        if(this.checkIfICanWin(y, x, board)) {
            board[y][x] = ' ';
            return 10;
        }

        //check if the other player can win with this position
        if(this.checkIfOtherCanWin(y, x, board)) {
            board[y][x] = ' ';
            return 5;
        }

        board[y][x] = ' ';
        //check if I can with after placing this position
        return 0;
    }

    private boolean checkIfICanWin(int y, int x, char[][] testBoard) {
        testBoard[y][x] = this.player;

        return (testBoard[y][0] == this.player && testBoard[y][1] == this.player && testBoard[y][2] == this.player) ||
                (testBoard[0][x] == this.player && testBoard[1][x] == this.player && testBoard[2][x] == this.player) ||
                (testBoard[0][0] == this.player && testBoard[1][1] == this.player && testBoard[2][2] == this.player) ||
                (testBoard[0][2] == this.player && testBoard[1][1] == this.player && testBoard[2][0] == this.player);
    }

    private boolean checkIfOtherCanWin(int y, int x, char[][] testBoard) {
        char player = this.oppositePlayer();
        testBoard[y][x] = player;

        return (testBoard[y][0] == player && testBoard[y][1] == player && testBoard[y][2] == player) ||
                (testBoard[0][x] == player && testBoard[1][x] == player && testBoard[2][x] == player) ||
                (testBoard[0][0] == player && testBoard[1][1] == player && testBoard[2][2] == player) ||
                (testBoard[0][2] == player && testBoard[1][1] == player && testBoard[2][0] == player);
    }

    private void placeTurn() {

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
        char[][] newBoard = this.TTTGame.getBoard();
        //Subtract the x value from the key
        int y = Integer.valueOf(biggest.getKey().split("")[0]);

        //subtract the y value from the key
        int x = Integer.valueOf(biggest.getKey().split("")[1]);

        //make a new board where the AI placed his turn
        if(newBoard[y][x] == ' ') {
            newBoard[y][x] = this.player;
        }

        // use this to return the last AI move
        this.lastMove[0] = x;
        this.lastMove[1] = y;

        //Replace the tic-tac-toe board with the board where the AI placed his turn
        this.TTTGame.setBoard(newBoard);


        //Debug
        System.out.println(Arrays.deepToString(newBoard));

    }

    private char oppositePlayer() {
        if(this.player == 'o') {
            return 'x';
        }
        return 'o';
    }
}
