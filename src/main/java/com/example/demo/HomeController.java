package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private HBox savedButton;

    @FXML
    protected void onPlayButtonClick() throws IOException {
        Stage newStage = Variables.getSetUpStage();
        newStage.show();

        Stage oldStage = Variables.getHomeStage();
        oldStage.close();
    }

    @FXML
    protected void onRulesButtonClick() throws IOException {
        Stage newStage = Variables.getRulesStage();
        newStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File file = new File("save.json");

        if(!file.exists() || file.length() == 0){
            savedButton.setOpacity(0);
            savedButton.setDisable(true);
        }
    }

    @FXML
    public void openSavedGame(){
        SaveGame game = FileRead.readSavedGame();

        Variables.setSavedGame(game);



        Stage newStage = Variables.getGameStage();
        newStage.show();

        Stage oldStage = Variables.getHomeStage();
        oldStage.close();
    }

}
