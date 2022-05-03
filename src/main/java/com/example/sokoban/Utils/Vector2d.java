package com.example.sokoban.Utils;

public class Vector2d {

    public static final Vector2d zero = new Vector2d(0, 0);
    public static final Vector2d one = new Vector2d(1, 1);
    public static final Vector2d up = new Vector2d(0, -1);
    public static final Vector2d down  = new Vector2d(0, 1);
    public static final Vector2d left = new Vector2d(-1, 0);
    public static final Vector2d right = new Vector2d(1, 0);

    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2d add(Vector2d vectorA, Vector2d vectorB) {
        return new Vector2d(vectorA.x + vectorB.x, vectorA.y + vectorB.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector2d vector2d = (Vector2d) o;
        return Double.compare(vector2d.x, x) == 0
            && Double.compare(vector2d.y, y) == 0;
    }
}
