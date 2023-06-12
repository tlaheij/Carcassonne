package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CarcassonneApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarcassonneApplication.class.getResource("home-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("Carcassonne");
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoader1 = new FXMLLoader(GameController.class.getResource("game-screen.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load(), 1290, 787);
        Stage gameStage = Variables.getGameStage();
        gameStage.setTitle("com.example.demo.Game");
        gameStage.setScene(scene1);

        FXMLLoader fxmlLoader2 = new FXMLLoader(GameSetUpController.class.getResource("gamesetup-screen.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 600, 400);
        Stage setUpStage = Variables.getSetUpStage();
        setUpStage.setTitle("com.example.demo.Game Set Up");
        setUpStage.setScene(scene2);

        FXMLLoader fxmlLoader3 = new FXMLLoader(RulesScreenController.class.getResource("rules-screen.fxml"));
        Scene scene3 = new Scene(fxmlLoader3.load(), 580, 780);
        Stage rulesStage = Variables.getRulesStage();
        rulesStage.setTitle("Rules");
        rulesStage.setScene(scene3);

        Variables.setHomeStage(stage);
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}