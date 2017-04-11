package TicTacToe.Controllers;

import Framework.Dialogs.*;
import Framework.GUI.Base;
import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.*;
import TicTacToe.Start;
import javafx.application.Platform;


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
        AbstractDialog challengeDialog = new ChallengeReceivedDialog(Start.getDialogEventsController(), response.getChallenger(), response.getChallengeNumber());
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
        Start.getBaseController().getBoardController().resetGameLogic();

        // reset / update BoardView look (via BoardController)
        Start.getBaseController().getBoardController().loadPreGameBoardState();

        // reset GUI (enable ControlsController buttons)
        Start.getBaseController().getControlsController().enableControls();
    }

    @Override
    public void gameListReceived(GameListResponse response) {
        System.out.println("gameListReceived event called!");
    }

    @Override
    public void matchReceived(MatchReceivedResponse matchReceivedResponse) {
        //Reset the board
        Start.getBaseController().getBoardController().loadPreGameBoardState();

        //Disable the controls
        Start.getBaseController().getControlsController().disableControls();
    }

    @Override
    public void moveReceived(MoveResponse response) {
        String player = response.getMovingPlayer();
        if (player.equals(Start.getBaseController().getLoggedInPlayer())) {
            return; // ignore moves we have made ourselves.
        }
        if (response.getMoveDetails() != null && response.getMoveDetails().equals("Illegal move")) {
            return; // ignore an illegal move, to prevent getting exceptions on our position conversion
        }

        int position = response.getMovePosition();
        BoardController boardController = Start.getBaseController().getBoardController();
        int[] coordinates = boardController.getListOfCoordinates().get(position);

        int x = coordinates[0];
        int y = coordinates[1];
        // update view via BoardController
        boardController.setMove(x, y, player);
    }

    @Override
    public void ourTurn(OurTurnResponse ourTurnResponse) {
        // update GUI (and enable possibility to move) to reflect turn change
        Start.getBaseController().getBoardController().setOurTurn();
    }

    @Override
    public void playerListReceived(PlayerListResponse response) {
        Start.getBaseController().getControlsController().updatePlayerList(response.getPlayerList());
    }

    @Override
    public void errorReceived(ErrorResponse errorResponse) {
        DialogInterface errorDialog = new ErrorDialog("Network error occurred", "Possible cause: " + errorResponse.getRequest().toString());
        Platform.runLater(errorDialog::display);
    }
}
