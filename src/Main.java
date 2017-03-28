import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.TicTacToe;

import java.awt.*;
import java.util.Arrays;
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

        /**
         * @var char player Representing who's turn it is
         */
        private char player;

        /**
         * @var TicTacToe tictactoe
         */
        private TicTacToe ticTacToe;


        private Game() {
            this.player = 'x';

            this.ticTacToe = new TicTacToe();

            this.ticTacToe.showBoard();

            this.askForInput();
        }

        public void askForInput() {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Waar wil je je " + this.player + " zetten? (x <spatie> y) ");

            this.doTurn(reader.nextLine());
//            if(reader.next().matches("\\d\\s{1}\\d")) {
//                this.doTurn(reader.next());
//            }
//            else {
//                System.out.println("Je input is niet goed. De syntax moet zijn: x <spatie> y");
//                this.askForInput();
//            }
        }

        public void doTurn(String turn) {
            String[] turnPlace = turn.split("\\s+");

            if(ticTacToe.doTurn(Integer.valueOf(turnPlace[0]), Integer.valueOf(turnPlace[1]), this.player)) {
                //player set the turn
                ticTacToe.showBoard();
                if(ticTacToe.checkForWinner(this.player)) {
                    System.out.println(this.player + " is the winner");
                }
                else {
                    this.switchPlayer();
                    this.askForInput();
                }
            }
            else {
                System.out.println("Dit hokje is al bezet of bestaat niet. Probeer het opnieuw");
                this.askForInput();
            }
        }

        public void switchPlayer() {
            if(this.player == 'x') {
                this.player = 'y';
            }
            else {
                this.player = 'x';
            }
        }
    }
}
