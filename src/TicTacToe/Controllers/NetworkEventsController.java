package TicTacToe.Controllers;

import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.PlayerListResponse;
import Framework.Networking.Response.Response;
import TicTacToe.Start;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peterzen on 2017-04-06.
 * Part of the tictactoe project.
 */
public class NetworkEventsController implements NetworkEvents {
    private static final int BOARDSIZE = 3;
    private static BoardController boardController = Start.;

    @Override
    public void challengeCancelled(Response response) {
        System.out.println("challengeCancelled event called!");
    }

    @Override
    public void challengeReceived(Response response) {
        System.out.println("challengeReceived event called!");
    }

    @Override
    public void ErrorReceived(Response response) {
        System.out.println("ErrorReceived event called!");
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
    public void moveReceived(Response response) {
        // @ TODO Wat komt er eigenlijk exactly uit die response, een 1d coordinaat en turn? moet ik die zelf splitsen?
        // movingplayer, movedetails, moveposition?
        // opvangen in een moveResponse van maken en dan de fields gebruiken..

        // List of coordinates

        // retrieving coordinates based on given position on the board
        int[] coordinates = listOfCoordinates.get(response);
        int x = coordinates[0];
        int y = coordinates[1];

        // Hoe komen we bij de boardcontroller? De instantie zit in de BaseController, de basecontroller
        // wordt in start aangemaakt.. Getter maar alleen als ik een instantie van basecontroller maak..
        // getter in start, die dan maar? dan moet ik een instantie van start aanmaken..
        // This is not helping me..

        // update view via BoardController
        // setMove(x, y, turn);

        // Wat moet er geupdate worden aan boardcontrollers?

        System.out.println("moveReceived event called!");
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
