package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
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

}
