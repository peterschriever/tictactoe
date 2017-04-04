import models.AI;
import models.TicTacToe;

import java.util.Scanner;


/**
 * Created by peterzen on 2017-03-23.
 * Part of the tictactoe project.
 */
public class Main {

    public static void main(String[] args) {
        new Game();
    }

    /**
     * Game class. Can be moved to the controller. When a user clicks a pane, the doTurn is invokec for the x and y of that pane
     */
    private static class Game {

        private char player;
        private TicTacToe ticTacToe;
        private AI computer;

        private boolean hasWinner = false;


        private Game() {
            this.player = 'x';
            this.ticTacToe = new TicTacToe();
            this.computer = new AI(this.ticTacToe, 'o');

            this.ticTacToe.showBoard();

            this.askForInput();
        }

        private void askForInput() {
            this.checkWinner();

            if(!this.hasWinner) {
                Scanner reader = new Scanner(System.in);  // Reading from commandline
                System.out.println("Waar wil je je " + this.player + " zetten? (x <spatie> y) ");

                this.doTurn(reader.nextLine());
            }
        }

        private void doTurn(String turn) {
            String[] turnPlace = turn.split("\\s+");


            if(ticTacToe.doTurn(Integer.valueOf(turnPlace[0]), Integer.valueOf(turnPlace[1]), this.player)) { // y x
                //player set the turn
                computer.doTurn(ticTacToe.getBoard());
                ticTacToe.showBoard();

                this.askForInput();
            }
            else {
                //ticTacToe.showBoard();
                System.out.println("Dit hokje is al bezet of bestaat niet. Probeer het opnieuw");
                this.askForInput();
            }
        }

        private void checkWinner() {
            if(ticTacToe.checkForWinner(this.player)) {
                System.out.println(this.player + " is the winner");
                this.hasWinner = true;
            }
            else if(ticTacToe.checkForWinner(computer.getPlayer())) {
                System.out.println(computer.getPlayer() + " is the winner");
                this.hasWinner = true;
            }
            else if(ticTacToe.checkDraw()) {
                System.out.println("Draw!");
                this.hasWinner = true;
            }
        }
    }
}
