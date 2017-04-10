package TicTacToe.Controllers;

import Framework.GUI.Board;
import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.*;
import TicTacToe.Start;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println("challengeReceived event called!");
    }

    @Override
    public void gameEnded(GameEndResponse response) {
        System.out.println("gameEnded event called!");
    }

    @Override
    public void gameListReceived(GameListResponse response) {
        System.out.println("gameListReceived event called!");
    }

    @Override
    public void matchReceived(MatchReceivedResponse response) {
        System.out.println("matchReceived event called!");
    }

    @Override
    public void moveReceived(MoveResponse response) {
        String player = response.getMovingPlayer();
        int position = response.getMovePosition();
        BoardController boardController = Start.getBaseController().getBoardController();
        int[] coordinates = boardController.getListOfCoordinates().get(position);

        int x = coordinates[0];
        int y = coordinates[1];

        // update view via BoardController
        boardController.setMove(x, y, player);
    }

    @Override
    public void ourTurn(OurTurnResponse response) {
        System.out.println("ourTurn event called!");
    }

    @Override
    public void playerListReceived(PlayerListResponse response) {
        System.out.println("playerListReceived event called!");
        System.out.println("Hello world from the NetworkEventsController!");
    }

    @Override
    public void errorReceived(ErrorResponse response) {
        System.out.println("errorReceived event called!");
    }
}
