package TicTacToe;

import Framework.AI.BotInterface;
import Framework.Config;
import Framework.Dialogs.DialogEvents;
import Framework.Game.GameLogicInterface;
import Framework.GameStart;
import Framework.Networking.Connection;
import Framework.Networking.ConnectionInterface;
import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.ChallengeReceivedResponse;
import Framework.Networking.Response.MoveResponse;
import Framework.Networking.Response.OurTurnResponse;
import Framework.Networking.Response.Response;
import Framework.Networking.SimulatedConnection;
import TicTacToe.Controllers.BaseController;
import TicTacToe.Controllers.DialogEventsController;
import TicTacToe.Controllers.NetworkEventsController;
import TicTacToe.Models.AI;
import TicTacToe.Models.TTTGame;
import javafx.application.Application;
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
public class Start extends Application implements GameStart {
    private Scene scene;
    private Stage stage;
    private static ConnectionInterface conn;
    private static ConnectionInterface oldConn;
    private static final NetworkEvents networkEventHandler = new NetworkEventsController();
    private final static DialogEvents dialogEventsController = new DialogEventsController();
    private final static BaseController baseController = new BaseController();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(Config.get("network", "host"));
        launch(args);
    }

    public static void main(String[] args, Stage stage, Scene scene) throws IOException, InterruptedException {
        System.out.println(Config.get("network", "test"));
        new Start(stage, scene);
    }

//    public TTTGame.Start(Stage stage, Scene scene) throws IOException, InterruptedException {
//        this.stage = stage;
//        this.scene = scene;
//
//        // Make connection with GameServer
//        // @TODO: maybe place this in the initialize of the BaseController (make connection after GUI finished starting)
//        String host = Config.get("network", "host");
//        int port = Integer.parseInt(Config.get("network", "port"));
//        conn = new Connection(host, port, networkEventHandler);
//        conn.setupInputObserver();
//
//        Request playerList = new GetPlayerListRequest(conn);
//        playerList.execute();
//    }

    public Start(Stage stage, Scene scene) throws IOException {
        // Scene meegegeven die weer wordt vervangen door updateGameScene method. --> dus, is dit nodig?
        this.stage = stage;
        this.scene = scene;
        // setup and save the connection
        String host = Config.get("network", "host");
        int port = Integer.parseInt(Config.get("network", "port"));
        conn = new Connection(host, port, networkEventHandler);
        if (!stage.isShowing()) {
            stage.show();
        }

        // update and show the GUI
        updateGameScene();
        this.start();
    }

    public Start() {
        // This constructor only exists to support stand-alone starting
    }

    public static DialogEvents getDialogEventsController() {
        return dialogEventsController;
    }

    public void updateGameScene() throws IOException {
        // Load view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Framework/GUI/fxml/View.fxml"));
        fxmlLoader.setController(getBaseController());
        Parent root = fxmlLoader.load();

        Scene gameScene = new Scene(root);
        this.scene = gameScene;
        this.stage.setScene(gameScene);
    }

    public static BaseController getBaseController() {
        return baseController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // when being started standalone
        new Start(stage, null);
    }

    @Override
    public void start() {
        // when started from either the framework or standalone

        // DEBUG: test effect of s MoveResponse
        MoveResponse moveResponse = new MoveResponse("O", "Details", 5);
        moveResponse.executeCallback();

        // DEBUG: test the effect of a GameEndedResponse
        // Response gameEndResponse = new GameEndResponse(0, 0, "hello world", "DRAW");
        // gameEndResponse.executeCallback();
        Response ourTurn = new OurTurnResponse("turnip");
        ourTurn.executeCallback();

        // DEBUG: test the effect of ChallengeReceivedResponse
        //Response challengeReceiveResponse = new ChallengeReceivedResponse("Eran", "tic-tac-toe", 1234 );
        //challengeReceiveResponse.executeCallback();
    }

    public static ConnectionInterface getConn() {
        return conn;
    }

    public static void toggleConnection() throws IOException {
        ConnectionInterface tempConn;
        if (conn instanceof Connection) {
            if (oldConn == null) {
                GameLogicInterface gameLogic = getBaseController().getBoardController().getGameLogic();
                BotInterface bot = getBaseController().getBoardController().getAI();
                oldConn = new SimulatedConnection("Tic-tac-toe", gameLogic, bot, networkEventHandler);
            }
        }
        // swaperoo: swap the Simulated and real Connection objects around
        tempConn = conn;
        conn = oldConn;
        oldConn = tempConn;
        System.out.println("now using: " + conn);
        System.out.println("before we used: " + oldConn);
    }

    /**
     * Game class. Can be moved to the controller. When a user clicks a pane, the doTurn is invokec for the x and y of that pane
     */
    private static class Game {

        private char player;
        private TTTGame TTTGame;
        private AI computer;

        private boolean hasWinner = false;


        private Game() {
            this.player = 'x';
            this.TTTGame = new TTTGame();
            this.computer = new AI(this.TTTGame, 'o');

            this.TTTGame.showBoard();

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

            if (TTTGame.doTurn(Integer.valueOf(turnPlace[0]), Integer.valueOf(turnPlace[1]), this.player)) { // y x
                //player set the turn
                computer.doTurn(TTTGame.getBoard());
                TTTGame.showBoard();

                this.askForInput();
            } else {
                //TTTGame.showBoard();
                System.out.println("Dit hokje is al bezet of bestaat niet. Probeer het opnieuw");
                this.askForInput();
            }
        }

        private void checkWinner() {
            if (TTTGame.checkForWinner(this.player)) {
                System.out.println(this.player + " is the winner");
                this.hasWinner = true;
            } else if (TTTGame.checkForWinner(computer.getPlayer())) {
                System.out.println(computer.getPlayer() + " is the winner");
                this.hasWinner = true;
            } else if (TTTGame.checkDraw()) {
                System.out.println("Draw!");
                this.hasWinner = true;
            }
        }
    }
}
