package Game.Views;

import javafx.scene.control.Label;

/**
 * Created by femkeh on 07/04/17.
 */
public class CustomLabel extends Label {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
