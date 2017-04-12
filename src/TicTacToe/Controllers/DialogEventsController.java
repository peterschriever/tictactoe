package TicTacToe.Controllers;

import Framework.Dialogs.DialogEvents;
import Framework.Networking.Request.ChallengeAcceptRequest;
import TicTacToe.Start;

import java.io.IOException;

/**
 * Created by peterzen on 2017-04-11.
 * Part of the tictactoe project.
 */
public class DialogEventsController implements DialogEvents {
    @Override
    public void attemptLogin(String playerName) {
        Start.getBaseController().attemptPlayerLogin(playerName);
    }

    @Override
    public void challengeReceived(int challengeNr){
        ChallengeAcceptRequest acceptRequest = new ChallengeAcceptRequest(Start.getConn(), challengeNr);
        try {
            acceptRequest.execute();
            System.out.println("AcceptRequest executed");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupConnection(String ipAddress, String portNr){};
}
