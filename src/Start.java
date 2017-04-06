import Framework.GUI.Base;
import Framework.GameStart;
import Models.AI;
import Models.TicTacToe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;


/**
 * Created by peterzen on 2017-03-23.
 * Part of the tictactoe project.
 */
public class Start implements GameStart {
    private Scene scene;
    private final Stage stage;

    public static void main(String[] args, Stage stage, Scene scene) throws IOException {
        new Start(stage, scene);
    }

    public Start(Stage stage, Scene scene) throws IOException {

        // Scene meegegeven die weer wordt vervangen door updateGameScene method. --> dus, is dit nodig?
        this.stage = stage;
        this.scene = scene;
        updateGameScene();
        this.start();
    }

    public void updateGameScene() throws IOException {
        // Load view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Framework/GUI/fxml/View.fxml"));
        fxmlLoader.setController(new Base());
        Parent root = fxmlLoader.load();

        Scene gameScene = new Scene(root);
        this.scene = gameScene; // update scene --> is dit nodig?
        this.stage.setScene(gameScene); // of getScene functie?
    }


    @Override
    public void start() {
        System.out.println("Real TTTGameStart.start was called!");

        // gui opstarten
        // - uitbreiding van framework gui (bord met 3*3 tiles) en extenden van BoardController
        // netwerkverbinding maken
        // inloggen / wachten op een game / inschrijven voor Tic-tac-toe
        // vervolgens reageren op events:
        // Maak NetworkEventsController (interface in framework toevoegen)
        // - start een game event
        // - doe een move event
        // - etc (basicly alles wat uit network responses kan)
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

            if (!this.hasWinner) {
                Scanner reader = new Scanner(System.in);  // Reading from commandline
                System.out.println("Waar wil je je " + this.player + " zetten? (x <spatie> y) ");

                this.doTurn(reader.nextLine());
            }
        }

        private void doTurn(String turn) {
            String[] turnPlace = turn.split("\\s+");

            if (ticTacToe.doTurn(Integer.valueOf(turnPlace[0]), Integer.valueOf(turnPlace[1]), this.player)) { // y x
                //player set the turn
                computer.doTurn(ticTacToe.getBoard());
                ticTacToe.showBoard();

                this.askForInput();
            } else {
                //ticTacToe.showBoard();
                System.out.println("Dit hokje is al bezet of bestaat niet. Probeer het opnieuw");
                this.askForInput();
            }
        }

        private void checkWinner() {
            if (ticTacToe.checkForWinner(this.player)) {
                System.out.println(this.player + " is the winner");
                this.hasWinner = true;
            } else if (ticTacToe.checkForWinner(computer.getPlayer())) {
                System.out.println(computer.getPlayer() + " is the winner");
                this.hasWinner = true;
            } else if (ticTacToe.checkDraw()) {
                System.out.println("Draw!");
                this.hasWinner = true;
            }
        }
    }
}
