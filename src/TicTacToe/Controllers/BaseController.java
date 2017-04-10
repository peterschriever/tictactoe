package TicTacToe.Controllers;

import Framework.Config;
import Framework.Dialogs.AbstractDialog;
import Framework.Dialogs.ErrorDialog;
import Framework.Dialogs.UserNameDialog;
import Framework.GUI.Base;
import Framework.GUI.Board;
import Framework.Networking.Request.LoginRequest;
import Framework.Networking.Request.Request;
import TicTacToe.Start;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Class BaseController
 *
 * @author Ruben Buisman
 * @version 0.1 (06-04-2017)
 */
public class BaseController extends Base {

    private Board boardController;
    private ControlsController controlsController;

    @Override
    public void initialize() {
        super.initialize();
        // setup Connection response observer
        Start.getConn().setupInputObserver();
        attemptPlayerLogin();
    }

    private void attemptPlayerLogin() {
        Request loginRequest;
        ErrorDialog errorDialog;

        try {
            String playerName = Config.get("game", "playerName");
            if (playerName != null) {
                loginRequest = new LoginRequest(Start.getConn(), playerName);
                loginRequest.execute();
                System.out.println("Send login request for playerName: " + playerName);
                return;
            }
        } catch (IOException e) {
            errorDialog = new ErrorDialog("IOException: could not load from game.properties", "Please contact a project developer.");
            errorDialog.display();
        } catch (InterruptedException e) {
            errorDialog = new ErrorDialog("InterruptedException: could not send game server Request", "Please contact a project developer.");
            errorDialog.display();
        }

        // Config was null or failed: show UsernameDialog
        UserNameDialog loginDialog = new UserNameDialog();
        loginDialog.display();
    }

    @Override
    protected void loadPartialViews() throws IOException {
        // Load MenuView.fxml
        this.container.getChildren().add(FXMLLoader.load(this.getClass().getResource("/Framework/GUI/fxml/MenuView.fxml")));
        // Load BoardView.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Framework/GUI/fxml/BoardView.fxml"));
        // Load ControlsView.fxml
        // TODO: Load controlsview

        this.boardController = new BoardController();
        fxmlLoader.setController(this.boardController);
        Parent partial = fxmlLoader.load();
        System.out.println("loading partials from BaseController!");
        container.getChildren().add(partial);

        fxmlLoader = new FXMLLoader(getClass().getResource("/TicTacToe/Views/controls.fxml"));
        fxmlLoader.setController(new ControlsController());
        Parent controls = fxmlLoader.load();
        container.getChildren().add(controls);
    }

    public Board getBoardController() {
        return boardController;
    }
}
