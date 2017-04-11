package TicTacToe.Controllers;

import Framework.Config;
import Framework.Dialogs.DialogInterface;
import Framework.Dialogs.ErrorDialog;
import Framework.Networking.Connection;
import Framework.Networking.Request.ChallengeRequest;
import Framework.Networking.Request.Request;
import TicTacToe.Start;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Eran on 6-4-2017.
 */
public class ControlsController implements Initializable {

    /**
     * @var ListView The list with possible players
     */
    @FXML
    ListView<String> playerList;

    @FXML
    Button challengePlayer;
    @FXML
    Button challengeComputer;
    @FXML
    HBox controlsBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setting the player list

        this.initPlayerChallenging();

        this.initComputerChallenging();

    }

    private void initComputerChallenging() {
        challengeComputer.setOnAction(e -> this.challengeComputer());
    }

    private void challengeComputer() {
        try {
            Start.toggleConnection();
            Request challengeBot = new ChallengeRequest(Start.getConn(), Config.get("game", "botName"), "Tic-tac-toe");
            challengeBot.execute();
        } catch (InterruptedException | IOException e) {
            DialogInterface errorDialog = new ErrorDialog("Config error", "Could not load property: useCharacterForPlayer." +
                    "\nPlease check your game.properties file.");
            Platform.runLater(errorDialog::display);
        }
    }

    private void initPlayerChallenging() {
        ObservableList<String> possiblePlayers = FXCollections.observableArrayList(
                "Ruben", "Peter", "Eran");
        playerList.setItems(possiblePlayers);

        challengePlayer.setOnAction(e -> this.challengePlayer());

    }

    @FXML
    private void challengePlayer() {
        String selectedPlayer = playerList.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            //no player selected
            new ErrorDialog("Error", "Please select an user").display();
        } else {
            try {
                Connection connection = new Connection("145.33.225.170", 7789, new NetworkEventsController());
                //@todo: delete method?
                connection.setupInputObserver();

                ChallengeRequest request = new ChallengeRequest(connection, selectedPlayer, "Tic-Tac-Toe");
                request.execute();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePlayerList(List<String> playerList) {
        ObservableList<String> list = FXCollections.observableArrayList(playerList);
        this.playerList.setItems(list);
    }

    /**
     * Disable all the controls
     */
    public void disableControls() {
        if (!controlsBox.isDisable()) {
            controlsBox.setDisable(true);
        }
    }

    public void enableControls() {
        if (controlsBox.isDisable()) {
            controlsBox.setDisable(false);
        }
    }


}