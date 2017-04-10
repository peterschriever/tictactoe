package TicTacToe.Controllers;

import Framework.Networking.NetworkEvents;
import Framework.Networking.Response.*;
import TicTacToe.Start;

/**
 * Created by peterzen on 2017-04-06.
 * Part of the tictactoe project.
 */
public class NetworkEventsController implements NetworkEvents {

    @Override
    public void challengeCancelled(ChallengeCancelledResponse challengeCancelledResponse) {

    }

    @Override
    public void challengeReceived(ChallengeReceivedResponse challengeReceivedResponse) {

    }

    @Override
    public void gameEnded(GameEndResponse gameEndResponse) {

    }

    @Override
    public void gameListReceived(GameListResponse gameListResponse) {

    }

    @Override
    public void matchReceived(MatchReceivedResponse matchReceivedResponse) {

    }

    @Override
    public void moveReceived(MoveResponse moveResponse) {

    }

    @Override
    public void ourTurn(OurTurnResponse ourTurnResponse) {

    }

    @Override
    public void playerListReceived(PlayerListResponse playerListResponse) {

    }

    @Override
    public void errorReceived(ErrorResponse errorResponse) {

    }
}
