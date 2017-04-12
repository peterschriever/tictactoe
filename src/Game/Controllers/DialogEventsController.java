package Game.Controllers;

import Framework.Dialogs.DialogEvents;
import Framework.Networking.Request.ChallengeAcceptRequest;
import Game.StartGame;

import java.io.IOException;

/**
 * Created by peterzen on 2017-04-11.
 * Part of the tictactoe project.
 */
public class DialogEventsController implements DialogEvents {
    @Override
    public void attemptLogin(String playerName) {
        StartGame.getBaseController().attemptPlayerLogin(playerName);
    }

    @Override
    public void challengeReceived(int challengeNr){
        ChallengeAcceptRequest acceptRequest = new ChallengeAcceptRequest(StartGame.getConn(), challengeNr);
        try {
            acceptRequest.execute();
            System.out.println("AcceptRequest executed");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
