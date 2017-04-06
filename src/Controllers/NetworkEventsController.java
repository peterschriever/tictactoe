package Controllers;

import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.Response;

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
