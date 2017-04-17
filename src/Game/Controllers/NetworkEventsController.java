package Game.Controllers;

import Framework.Config;
import Framework.Dialogs.*;
import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.*;
import Game.Models.TTTGame;
import Game.StartGame;
import javafx.application.Platform;

import java.io.IOException;


/**
 * Created by peterzen on 2017-04-06.
 * Part of the tictactoe project.
 */
public class NetworkEventsController implements NetworkEvents {

    @Override
    public void challengeCancelled(ChallengeCancelledResponse response) {
        System.out.println("challengeCancelled event called!");
    }

    @Override
    public void challengeReceived(ChallengeReceivedResponse response) {
        AbstractDialog challengeDialog = new ChallengeReceivedDialog(StartGame.getDialogEventsController(), response.getChallenger(), response.getChallengeNumber());
        Platform.runLater(challengeDialog::display);
    }

    @Override
    public void gameEnded(GameEndResponse gameEndResponse) {
        System.out.println("GameEnded received!");

        // show GameEndedDialog
        String result = gameEndResponse.getResult();
        DialogInterface gameEndedDialog = new MessageDialog(
                "Game has ended!",
                "Game resulted in a " + result + "!",
                "Comment: " + gameEndResponse.getComment() + "\n"
                        + "Player one score: " + gameEndResponse.getPlayerOneScore() + "\n"
                        + "Player two score: " + gameEndResponse.getPlayerTwoScore()
        );
        Platform.runLater(gameEndedDialog::display);

        // reset / update game-logic models (via BoardController)
        StartGame.getBaseController().getBoardController().resetGameLogic();

        // reset / update BoardView look (via BoardController)
        StartGame.getBaseController().getBoardController().loadPreGameBoardState();
    }

    @Override
    public void gameListReceived(GameListResponse response) {
        System.out.println("gameListReceived event called!");
    }

    @Override
    public void matchReceived(MatchReceivedResponse matchReceivedResponse) {
        //Reset the board
        StartGame.getBaseController().getBoardController().loadPreGameBoardState();

        //Disable the controls
        StartGame.getBaseController().getControlsController().disableControls();
        StartGame.getBaseController().getControlsController().disableControls();

        StartGame.getBaseController().getBoardController().ttt = new TTTGame();
    }

    @Override
    public void moveReceived(MoveResponse response) {
        String player = response.getMovingPlayer();
        if (player.equals(StartGame.getBaseController().getLoggedInPlayer())) {
            return; // ignore moves we have made ourselves.
        }
        else {
            try {
                player = Config.get("game", "useCharacterForOpponent");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (response.getMoveDetails() != null && response.getMoveDetails().equals("Illegal move")) {
            return; // ignore an illegal move, to prevent getting exceptions on our position conversion
        }

        int position = response.getMovePosition();
        BoardController boardController = StartGame.getBaseController().getBoardController();
        int[] coordinates = boardController.getListOfCoordinates().get(position);
        System.out.println("MOVE Received: position: " + position + " coordinates[0]: " + coordinates[0] + " coordinates[1]: " + coordinates[1]);

        int x = coordinates[0];
        int y = coordinates[1];
        // update view via BoardController
        try {
            boardController.setMove(x, y, player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ourTurn(OurTurnResponse ourTurnResponse) {
        // update GUI (and enable possibility to move) to reflect turn change
        StartGame.getBaseController().getBoardController().setOurTurn();

        // let the AI generate a move if needed
        if (StartGame.getBaseController().getControlsController().isBotPlaying()) {
            StartGame.getBaseController().getBoardController().doAIMove();
        }
    }

    @Override
    public void playerListReceived(PlayerListResponse response) {
        StartGame.getBaseController().getControlsController().updatePlayerList(response.getPlayerList());
    }

    @Override
    public void errorReceived(ErrorResponse errorResponse) {
        DialogInterface errorDialog = new ErrorDialog("Network error occurred", "Possible cause: " + errorResponse.getRequest().toString());
        Platform.runLater(errorDialog::display);
    }
}
