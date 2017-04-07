package Controllers;

import Framework.GUI.Board;
import Views.CustomLabel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by femkeh on 06/04/17.
 */
public class BoardController extends Board {
    private static final int BOARDSIZE = 3;
    private Label[] listOfLabels;

    // labels met on click
    // lijstje labels maken in de controller en die in het bordzetten en daar on click opzetten ipv id in fxml
    // en dan de action event meegeven per label.. als je em tekent met x en y
    // extra klasse speciaal voor mn eigen label, waarbij ik zelf x en y positie aan kan geven. (label javafx extenden?)

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
                // @TODO Waar plemp ik de images?
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
    public void clickToDoMove(ActionEvent actionEvent) {
        CustomLabel label = (CustomLabel) actionEvent.getSource();
        int x = label.getX();
        int y = label.getY();
        // @TODO Waar haal ik turn vandaan?
        String turn = getTurn();
        CustomLabel newLabel = makeLabel(x, y, turn);
        // @TODO gaat remove goedkomen met de positie? gaat meestal op basis van de tekst van de label
        // die is er nu niet...
        gridPane.getChildren().remove(label);
        gridPane.add(newLabel, y, x);
    }

    // Move received from server
    public void setMove(int x, int y, String turn) {
        CustomLabel newLabel = makeLabel(x, y, turn);
        // @TODO ouwe label nog ophalen
        ObservableList<Node> childrens = gridPane.getChildren();
        Node result;
        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == y && gridPane.getColumnIndex(node) == x) {
                result = node;
                break;
            }
        }

        gridPane.getChildren().remove(label);
        gridPane.add(newLabel, y, x);
    }

    private CustomLabel makeLabel(int x, int y, String turn) {
        CustomLabel newLabel = null;
        if (turn == "x") {
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
