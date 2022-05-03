package com.example.sokoban;

import com.example.sokoban.Components.*;
import com.example.sokoban.Components.Error;
import com.example.sokoban.Utils.Component;
import com.example.sokoban.Utils.Vector2d;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Maze {

    private Group group;
    private Scene scene;
    private Player player;
    public static Component[][] maze = new Component[10][10];
    private ArrayList<Endpoint> endpoints = new ArrayList<>();
    private ArrayList<Crate> crates = new ArrayList<>();
    private Timeline t;
    private boolean gameEnded;


    public Maze(Group group, Scene scene) {
        this.group = group;
        this.scene = scene;
        this.gameEnded = false;

        //Every 100ms check if player won
        t = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (checkIfWin()) {
                gameEnded = true;
                group.getChildren().clear();
                group.getChildren().add(new Win(0, 0, group));
                t.stop();
            }
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
        loadMazeFromFile();
        //Keyboard input
        this.scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                handlePlayerMovement(player.transform.position, Vector2d.up);
            }
            if (event.getCode() == KeyCode.S) {
                handlePlayerMovement(player.transform.position, Vector2d.down);
            }
            if (event.getCode() == KeyCode.A) {
                handlePlayerMovement(player.transform.position, Vector2d.left);
            }
            if (event.getCode() == KeyCode.D) {
                handlePlayerMovement(player.transform.position, Vector2d.right);
            }
            if (event.getCode() == KeyCode.R && !gameEnded) {
                clearMaze();
                loadMazeFromFile();
            }
        });
    }

    //Reads file maze.map and generates maze
    private void loadMazeFromFile() {
        int y = 0;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/maze.map"));
            String row;
            while (!(row = br.readLine()).equals("##end##")) {
                if (y >= 10) {
                    throw new RuntimeException("Map must be 10 units in height");
                }
                if (row.length() != 10) {
                    throw new RuntimeException("Map must be 10 units in width");
                }
                for (int x = 0; x < row.length(); x++) {
                    if (row.substring(x, x + 1).equals("w")) {
                            createBrick(x, y);
                        continue;
                    }
                    if (row.substring(x, x + 1).equals("p")) {
                        createPlayer(x, y);
                        continue;
                    }
                    if (row.substring(x, x + 1).equals("c")) {
                        createCrate(x, y);
                        continue;
                    }
                    if (row.substring(x, x + 1).equals("e")) {
                        createEndpoint(x, y);
                        continue;
                    }
                }
                y++;
            }
            if (y != 10) {
                throw new RuntimeException("Map must be 10 in height");
            }
            if (crates.size() != endpoints.size()) {
                throw new RuntimeException("Number of crates does not equal the number of endpoints");
            }
        } catch (Exception e) {
            group.getChildren().clear();
            group.getChildren().add(new Error(0, 0, e.toString(), group));
            t.stop();
            System.out.println(e);
        }
    }

    private void createBrick(int x, int y) {
        Brick brick = new Brick(x * Brick.size, y * Brick.size, group);
        maze[x][y] = brick;
        group.getChildren().add(brick);
    }

    private void createEndpoint(int x, int y) {
        Endpoint endpoint = new Endpoint(x * Endpoint.size, y * Endpoint.size, group);
        endpoints.add(endpoint);
        group.getChildren().add(endpoint);
        endpoint.toBack();
    }

    private void createCrate(int x, int y) {
        Crate crate = new Crate(x * Crate.size, y * Crate.size, group);
        maze[x][y] = crate;
        crates.add(crate);
        group.getChildren().add(crate);
    }

    private void createPlayer(int x, int y) {
        if (this.player != null) {
            throw new RuntimeException("You can't have more than one player");
        }
        Player player = new Player(x * Player.size, y * Player.size, group);
        this.player = player;
        maze[x][y] = player;
        group.getChildren().add(player);
    }

    private void handlePlayerMovement(Vector2d position, Vector2d direction) {
        if (canMove(position, direction)) {
            Component component = maze[(int) position.x / 60 + (int) direction.x][(int) position.y / 60 + (int) direction.y];
            maze[(int) position.x / 60 + (int) direction.x][(int) position.y / 60 + (int) direction.y] = maze[(int) position.x / 60][(int) position.y / 60];
            if (!(component instanceof Crate)) {
                maze[(int) position.x / 60][(int) position.y / 60] = component;
                player.move(direction);
            } else {
                Component component1 = maze[(int) position.x / 60 + (int) direction.x * 2][(int) position.y / 60 + (int) direction.y * 2];
                maze[(int) position.x / 60][(int) position.y / 60] = component1;
                maze[(int) position.x / 60 + (int) direction.x * 2][(int) position.y / 60 + (int) direction.y * 2] = component;
                player.move(direction);
                ((Crate) component).move(direction);
            }
        }
    }

    //Checks if player can move
    private boolean canMove(Vector2d position, Vector2d direction) {
        if (player.canMove(direction)) {
            return true;
        } else if (player.canMoveCrate(direction)) {
            Crate component = (Crate) maze[(int) position.x / 60 + (int) direction.x][(int) position.y / 60 + (int) direction.y];
            return component.canMove(direction);
        }
        return false;
    }

    //Check if all crates are on endpoints
    private boolean checkIfWin() {
        int endpointsToWin = 0;
        for (Endpoint endpoint: endpoints) {
            for (Crate crate: crates) {
                if (endpoint.transform.position.equals(crate.transform.position)) {
                    endpointsToWin++;
                }
            }
        }
        return endpointsToWin == endpoints.size();
    }

    private void clearMaze() {
        endpoints.clear();
        crates.clear();
        player = null;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = null;
            }
        }
        group.getChildren().clear();
    }

}
