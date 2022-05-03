package com.example.sokoban.Components;

import com.example.sokoban.Utils.Component;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Error extends Component {

    private String errorMessage;

    //Shown when there is error in maze.map
    public Error(double x, double y, String errorMessage, Group group) {
        super(600, 600, x, y, group);
        this.errorMessage = errorMessage.substring(27);
        draw();
    }

    @Override
    public void draw() {
        if (errorMessage != null) {
            super.draw();
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, getWidth(), getHeight());
            graphicsContext.setFill(Color.WHITE);
            if (errorMessage.length() > 40) {
                graphicsContext.setFont(Font.loadFont("file:src/main/resources/Retro Gaming.ttf", 15));
                graphicsContext.fillText(errorMessage, 300 - errorMessage.length() * 5, 300);
            } else {
                graphicsContext.setFont(Font.loadFont("file:src/main/resources/Retro Gaming.ttf", 19));
                graphicsContext.fillText(errorMessage, 300 - errorMessage.length() * 6.25, 300);
            }
        }
    }
}
