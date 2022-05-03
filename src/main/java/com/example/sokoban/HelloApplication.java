package com.example.sokoban;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    //Player sprite source https://floppapedia-revamped.fandom.com/wiki/Fanter_Fanteriounson_the_IV
    //Crate sprite source https://freepikpsd.com/crate-texture-png-transparent-images/877744/
    //CrateEndpoint source https://www.iconfinder.com/icons/242925/treasure_spot_cross_mark_x_pirate_pirates_icon
    //Brick source https://br.pinterest.com/freyalane1/blocks/

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Font font19 = Font.loadFont("file:src/main/resources/Retro Gaming.ttf", 19);
        Font font80 = Font.loadFont("file:src/main/resources/Retro Gaming.ttf", 80);
        Group group = new Group();
        Scene scene = new Scene(group, 600, 600);
        //Game name
        Label gameName = new Label("SOKOBAN");
        gameName.setFont(font80);
        gameName.setLayoutX(80);
        gameName.setLayoutY(100);
        //Play
        Button startButton = new Button("PLAY");
        startButton.setFont(font19);
        startButton.setOnAction(event -> {
            group.getChildren().clear();
            Maze maze = new Maze(group, scene);
        });
        startButton.setLayoutX(260);
        startButton.setLayoutY(250);
        //Exit
        Button exitButton = new Button("EXIT");
        exitButton.setFont(font19);
        exitButton.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        exitButton.setLayoutX(260);
        exitButton.setLayoutY(350);

        group.getChildren().addAll(startButton, exitButton, gameName);
        stage.setTitle("SOKOBAN");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}