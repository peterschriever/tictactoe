package Controllers;

import Framework.Config;
import Framework.GUI.Board;
import Views.CustomLabel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Created by femkeh on 06/04/17.
 */
public class BoardController extends Board {
    private static final int BOARDSIZE = 3;
    private Label[] listOfLabels;

    public void initialize() {
        drawGrid(BOARDSIZE);
        loadGrid();
    }

    private void makeLabels() {
        // @TODO
        // listOfLabels vullen
        for (int i = 0; i < BOARDSIZE*BOARDSIZE; i++) {
            Image image = new Image(getClass().getResourceAsStream("Empty.png"));
            CustomLabel label = new CustomLabel();
            label.setGraphic(new ImageView(image));
        }
    }

    private void loadGrid() {
        int i;
        int j;
        for (i = 0; i < BOARDSIZE; i++) {
            for (j = 0; j < BOARDSIZE; j++) {
                Image image = new Image(getClass().getResourceAsStream("Empty.png"));
                CustomLabel label = new CustomLabel();
                label.setGraphic(new ImageView(image));
                label.setX(j);
                label.setY(i);
                gridPane.setHalignment(label, HPos.CENTER);
                // gridPane.setGridLinesVisible(true);
                gridPane.add(label, j, i);
            }
        }
    }

    // Move received from within game
    public void clickToDoMove(ActionEvent actionEvent) throws IOException {
        CustomLabel label = (CustomLabel) actionEvent.getSource();
        int x = label.getX();
        int y = label.getY();
        String turn = Config.get("game", "useCharacterForPlayer");
        CustomLabel newLabel = makeLabel(x, y, turn);
        // @TODO
        // gaat remove goedkomen met de positie? gaat meestal op basis van de tekst van de label
        // die is er nu niet...
        gridPane.getChildren().remove(label);
        gridPane.add(newLabel, y, x);
    }

    // Move received from server
    public void setMove(int x, int y, String turn) {
        CustomLabel newLabel = makeLabel(x, y, turn);
        ObservableList<Node> childrenList = gridPane.getChildren();
        Node result;
        for (Node node : childrenList) {
            if(gridPane.getRowIndex(node) == y && gridPane.getColumnIndex(node) == x) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
        gridPane.add(newLabel, y, x);
    }

    private CustomLabel makeLabel(int x, int y, String turn) {
        CustomLabel newLabel = null;
        if (turn == "X") {
            Image image = new Image(getClass().getResourceAsStream("X.png"));
            newLabel.setGraphic(new ImageView(image));
            newLabel.setX(x);
            newLabel.setY(y);
        } else {
            Image image = new Image(getClass().getResourceAsStream("O.png"));
            newLabel.setGraphic(new ImageView(image));
            newLabel.setX(x);
            newLabel.setY(y);
        }
        return newLabel;
    }
}
