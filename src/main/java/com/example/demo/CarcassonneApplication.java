package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p>
 *     This class is that staring code for the application. It contains
 *     the code which creates the first screen and also the main() method
 *     which is the first method that will be run
 * </p>
 */

public class CarcassonneApplication extends Application {

    /**
     * <p>
     *     This is the method which will run once the launch method runs
     *     in the main methode. The method creates all the screens for
     *     the application and saves them in the variables class.
     * </p>
     *
     * @param stage         This is the first stage of the application and
     *                      is the stage which the first screen will be
     *                      places in.
     *
     * @throws IOException  This exception should only occur is one of
     *                      the fxml files cannot be found
     */
    @Override
    public void start(Stage stage) throws IOException {
        // creating an fxml loader to read the fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(CarcassonneApplication.class.getResource("home-screen.fxml"));
        // creating a new scene in which the fxml file gets loaded
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // giving the stage a title
        stage.setTitle("Carcassonne");
        // loading the scene in the stage so its visible to the user
        stage.setScene(scene);
        // display the stage to the user
        stage.show();

        // creating an fxml loader to read the fxml file
        FXMLLoader fxmlLoader1 = new FXMLLoader(GameController.class.getResource("game-screen.fxml"));
        // creating a new scene in which the fxml file gets loaded
        Scene scene1 = new Scene(fxmlLoader1.load(), 1342, 787);
        // retrieving the stage which is saved in the Variables class for this scene
        Stage gameStage = Variables.getGameStage();
        // giving the stage a title
        gameStage.setTitle("Game");
        // loading the scene in the stage
        gameStage.setScene(scene1);

        // creating an fxml loader to read the fxml file
        FXMLLoader fxmlLoader2 = new FXMLLoader(GameSetUpController.class.getResource("gamesetup-screen.fxml"));
        // creating a new scene in which the fxml file gets loaded
        Scene scene2 = new Scene(fxmlLoader2.load(), 600, 400);
        // retrieving the stage which is saved in the Variables class for this scene
        Stage setUpStage = Variables.getSetUpStage();
        // giving the stage a title
        setUpStage.setTitle("Game Set Up");
        // loading the scene in the stage
        setUpStage.setScene(scene2);

        FXMLLoader fxmlLoader3 = new FXMLLoader(RulesScreenController.class.getResource("rules-screen.fxml"));
        Scene scene3 = new Scene(fxmlLoader3.load());
        Stage rulesStage = Variables.getRulesStage();
        rulesStage.setTitle("Rules");
        rulesStage.setScene(scene3);

        FXMLLoader fxmlLoader4 = new FXMLLoader(RulesScreenController.class.getResource("playerPreviews.fxml"));
        Scene scene4 = new Scene(fxmlLoader4.load());
        Stage tempStage = new Stage();
        tempStage.setTitle("temp");
        tempStage.setScene(scene4);

        Variables.setHomeStage(stage);
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}