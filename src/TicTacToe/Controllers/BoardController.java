package TicTacToe.Controllers;

import Framework.Config;
import Framework.GUI.Board;
import TicTacToe.Views.CustomLabel;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Created by femkeh on 06/04/17.
 */
public class BoardController extends Board {
    private static final int BOARDSIZE = 3;
    private Label[] listOfLabels;

    private static final String gridCellStyle = "-fx-border-color: black; -fx-border-width:1;";
    private static final String preGameGridStyle = "-fx-border-color: orange;-fx-border-width:3;-fx-padding: 10 10 10 10;-fx-border-insets: 10 10 10 10;";
    private static final String ourTurnGridStyle = "-fx-border-color: green;-fx-border-width:3;-fx-padding: 10 10 10 10;-fx-border-insets: 10 10 10 10;";
    private static double cellWidth;
    private static double cellHeight;

    public void initialize() {
        cellWidth = (gridPane.getPrefWidth() / BOARDSIZE) - 2;
        cellHeight = (gridPane.getPrefWidth() / BOARDSIZE) - 2;
        drawGrid(BOARDSIZE);
        loadGrid();
    }

    private void loadGrid() {
        int i;
        int j;
        for (i = 0; i < BOARDSIZE; i++) {
            for (j = 0; j < BOARDSIZE; j++) {
                Image image = new Image(BoardController.class.getClassLoader().getResourceAsStream("./Empty.png"));
                ImageView imageView = new ImageView();
                imageView.setFitHeight(50.0);
                imageView.setFitWidth(50.0);
                imageView.setImage(image);
                CustomLabel label = new CustomLabel();
                label.setGraphic(imageView);
                label.setX(i);
                label.setY(j);
                label.setOnMouseClicked(this::clickToDoMove);
                gridPane.setHalignment(label, HPos.CENTER);
                label.setMinSize(cellWidth, cellHeight);
                label.setStyle(gridCellStyle);
                gridPane.add(label, j, i);
            }
        }
        gridPane.setStyle(preGameGridStyle);
    }

    // Move received from within game
    public void clickToDoMove(MouseEvent mouseEvent) {
        CustomLabel label = (CustomLabel) mouseEvent.getSource();
        int x = label.getX();
        int y = label.getY();
        System.out.println("x: " + x + " y:" + y);
        String turn = null;
        try {
            turn = Config.get("game", "useCharacterForPlayer");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if (gridPane.getRowIndex(node) == y && gridPane.getColumnIndex(node) == x) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
        gridPane.add(newLabel, y, x);
    }

    private CustomLabel makeLabel(int x, int y, String turn) {
        CustomLabel newLabel = new CustomLabel();
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50.0);
        imageView.setFitWidth(50.0);
        if (turn.equals("X")) {
            Image image = new Image(BoardController.class.getClassLoader().getResourceAsStream("./X.png"));
            imageView.setImage(image);
            newLabel.setGraphic(imageView);
            newLabel.setX(x);
            newLabel.setY(y);
            gridPane.setHalignment(newLabel, HPos.CENTER);
        } else {
            Image image = new Image(BoardController.class.getClassLoader().getResourceAsStream("./O.png"));
            imageView.setImage(image);
            newLabel.setGraphic(imageView);
            newLabel.setX(x);
            newLabel.setY(y);
            gridPane.setHalignment(newLabel, HPos.CENTER);
        }
        return newLabel;
    }

    public void loadPreGameBoardState() {
        // gameLogic = null; || gameLogic = new Game();

        gridPane.getChildren().removeAll();
        loadGrid();
        gridPane.setStyle(preGameGridStyle);
    }

    public void setOurTurn() {
        gridPane.setStyle(ourTurnGridStyle);
    }
}
