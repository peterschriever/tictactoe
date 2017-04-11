package TicTacToe.Controllers;

import Framework.Dialogs.DialogEvents;
import TicTacToe.Start;

/**
 * Created by peterzen on 2017-04-11.
 * Part of the tictactoe project.
 */
public class DialogEventsController implements DialogEvents {
    @Override
    public void attemptLogin(String playerName) {
        Start.getBaseController().attemptPlayerLogin(playerName);
    }
}
