package com.example.sokoban.Utils;

public class Transform {

    public Vector2d position;

    public Transform(Vector2d position) {
        this.position = position;
    }

    public void translate(Vector2d translation) {
        this.position = Vector2d.add(this.position, translation);
    }
}
