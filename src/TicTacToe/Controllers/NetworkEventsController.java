package TicTacToe.Controllers;

import Framework.GUI.Board;
import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.MoveResponse;
import Framework.Networking.Response.PlayerListResponse;
import Framework.Networking.Response.Response;
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
    public void challengeCancelled(Response response) {
        System.out.println("challengeCancelled event called!");
    }

    @Override
    public void challengeReceived(Response response) {
        System.out.println("challengeReceived event called!");
    }

    @Override
    public void gameEnded(Response response) {
        System.out.println("gameEnded event called!");
    }

    @Override
    public void gameListReceived(Response response) {
        System.out.println("gameListReceived event called!");
    }

    @Override
    public void matchReceived(Response response) {
        System.out.println("matchReceived event called!");
    }

    @Override
    public void moveReceived(MoveResponse response) {
        String player = response.getMovingPlayer();
        int position = response.getMovePosition();
        BoardController boardController = Start.getBaseController().getBoardController();
        int[] coordinates = boardController.getListOfCoordinates().get(response);

        int x = coordinates[0];
        int y = coordinates[1];

        // update view via BoardController
        boardController.setMove(x, y, player);

        // Wat moet er geupdate worden aan boardcontrollers?

    }

    @Override
    public void ourTurn(Response response) {
        System.out.println("ourTurn event called!");
    }

    @Override
    public void playerListReceived(Response response) {
        System.out.println("playerListReceived event called!");
        System.out.println("Hello world from the NetworkEventsController!");
    }

    @Override
    public void errorReceived(Response response) {
        System.out.println("errorReceived event called!");
    }
}
