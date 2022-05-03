package com.example.sokoban.Components;

import com.example.sokoban.Utils.Component;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class Brick extends Component {

    public static final double size = 60;
    private static Image brickSprite = new Image("brick.png");

    public Brick(double x, double y, Group group) {
        super(size, size, x, y, group);
    }

    public void draw() {
        graphicsContext.drawImage(brickSprite, 0, 0, getWidth(), getHeight());
    }

}
