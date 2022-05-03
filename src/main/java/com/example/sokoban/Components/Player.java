package com.example.sokoban.Components;

import com.example.sokoban.Utils.Component;
import com.example.sokoban.Utils.Vector2d;
import javafx.scene.Group;
import javafx.scene.image.Image;

import static com.example.sokoban.Maze.maze;

public class Player extends Component {

    public static final double size = 60;
    private static Image player = new Image("player.png");

    public Player(double x, double y, Group group) {
        super(size, size, x, y, group);
    }

    public void draw() {
        graphicsContext.drawImage(player, 0, 0, getWidth(), getHeight());
    }

    public void move(Vector2d direction) {
        transform.translate(new Vector2d(60 * direction.x, 60 * direction.y));
        setLayoutX(transform.position.x);
        setLayoutY(transform.position.y);
    }

    public boolean canMove(Vector2d direction) {
        Component component = maze[(int) transform.position.x / 60 + (int) direction.x][(int) transform.position.y / 60 + (int) direction.y];
        return component == null;
    }

    public boolean canMoveCrate(Vector2d direction) {
        Component component = maze[(int) transform.position.x / 60 + (int) direction.x][(int) transform.position.y / 60 + (int) direction.y];
        return component instanceof Crate;
    }
}
