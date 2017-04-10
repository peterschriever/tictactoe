package TicTacToe.Controllers;

import Framework.Config;
import Framework.GUI.Board;
import TicTacToe.Views.CustomLabel;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by femkeh on 06/04/17.
 */
public class BoardController extends Board {
    private static final int BOARDSIZE = 3;

    public void initialize() {
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
                // @TODO borders voor binnen lijnen
                gridPane.setGridLinesVisible(true);
                gridPane.add(label, j, i);
            }
        }
    }

    // Move received from within game
    public void clickToDoMove(MouseEvent mouseEvent) {
        CustomLabel label = (CustomLabel) mouseEvent.getSource();
        int x = label.getX();
        int y = label.getY();
        String turn = null;
        try {
            turn = Config.get("game", "useCharacterForPlayer");
        } catch (IOException e) {
            e.printStackTrace();
        }
        CustomLabel newLabel = makeLabel(x, y, turn);
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
        TicTacToe.doTurn();
        TicTacToe.getBoard();
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

    // List of coordinates
    public Map<Integer, int[]> getListOfCoordinates() {
        Map<Integer, int[]> listOfCoordinates = new HashMap<>();
        int key = 0;
        int[] values = new int[2];
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                key = i * BOARDSIZE + j;
                values[0] = i;
                values[1] = j;
                listOfCoordinates.put(key, values);
            }
        }
        return listOfCoordinates;
    }
}
