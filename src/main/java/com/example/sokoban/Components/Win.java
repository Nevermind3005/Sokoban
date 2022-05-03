package com.example.sokoban.Components;

import com.example.sokoban.Utils.Component;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Win extends Component {

    //Shown when player won
    public Win(double x, double y, Group group) {
        super(600, 600, x, y, group);
    }

    public void draw() {
        super.draw();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.loadFont("file:src/main/resources/Retro Gaming.ttf", 19));
        graphicsContext.fillText("You won", 300 - "You won".length() * 6.25, 300);
    }
}

