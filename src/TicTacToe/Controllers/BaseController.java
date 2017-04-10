package TicTacToe.Controllers;

import Framework.GUI.Base;
import Framework.GUI.Board;
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
    protected void loadPartialViews() throws IOException {
        // Load MenuView.fxml
        this.container.getChildren().add(FXMLLoader.load(this.getClass().getResource("/Framework/GUI/fxml/MenuView.fxml")));
        // Load BoardView.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Framework/GUI/fxml/BoardView.fxml"));
        // Load ControllsView.fxml
        // TODO: Load controllsview

        this.boardController = new BoardController();
        fxmlLoader.setController(this.boardController);
        Parent partial = fxmlLoader.load();
        System.out.println("loading partials from BaseController!");
        container.getChildren().add(partial);

        this.controlsController = new ControlsController();
        fxmlLoader = new FXMLLoader(getClass().getResource("/TicTacToe/Views/controls.fxml"));
        fxmlLoader.setController(this.controlsController);
        Parent controls = fxmlLoader.load();
        container.getChildren().add(controls);
    }

    public Board getBoardController() {
        return boardController;
    }

    public ControlsController getControlsController() {
        return this.controlsController;
    }
}
