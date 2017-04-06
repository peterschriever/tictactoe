package Controllers;

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
        container.getChildren().add(partial);
    }
}
