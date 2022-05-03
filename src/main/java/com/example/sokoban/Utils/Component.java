package com.example.sokoban.Utils;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Component extends Canvas {

    public Transform transform;
    protected GraphicsContext graphicsContext;
    protected ArrayList<Timeline> timelines;
    protected Group group;

    public Component(double width, double height, double x, double y, Group group) {
        super(width, height);
        this.transform = new Transform(new Vector2d(x, y));
        this.setLayoutX(transform.position.x);
        this.setLayoutY(transform.position.y);
        this.graphicsContext = this.getGraphicsContext2D();
        this.timelines = new ArrayList<>();
        this.group = group;
        draw();
    }

    public void draw() {
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
    }

    public void destroy() {
        group.getChildren().remove(this);
        for (Timeline t : timelines) {
            t.stop();
        }
    }
}
