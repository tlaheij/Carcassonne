package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameSetUpController {
    @FXML
    private Text numPlayers;

    @FXML
    private ImageView forwardButton;

    @FXML
    private ImageView backwardButton;

    @FXML
    private HBox playerInp1;
    @FXML
    private HBox playerInp2;
    @FXML
    private HBox playerInp3;
    @FXML
    private HBox playerInp4;
    @FXML
    private HBox playerInp5;
    @FXML
    private HBox playerInp6;

    private final ArrayList<HBox> playerInputNames= new ArrayList<>();

    @FXML
    private VBox setUp;

    @FXML
    private AnchorPane stage;

    public void onBackButtom(){
        Stage newStage = Variables.getHomeStage();
        Stage oldStage = Variables.getSetUpStage();

        oldStage.close();
        newStage.show();
    }

    public void onForwardButton(){

        playerInputNames.addAll(Arrays.asList(playerInp1, playerInp2, playerInp3, playerInp4, playerInp5, playerInp6));

        int newNumPlayers = Integer.parseInt(numPlayers.getText()) + 1;

        if (newNumPlayers > 6){
            forwardButton.setOpacity(0.5);
            forwardButton.setDisable(true);
        }
        else if(newNumPlayers == 6){
            forwardButton.setOpacity(0.5);
            forwardButton.setDisable(true);
            numPlayers.setText(newNumPlayers + "");

        }
        else{
            numPlayers.setText(newNumPlayers + "");
            backwardButton.setOpacity(1);
            backwardButton.setDisable(false);
        }

        setUp.getChildren().add(playerInputNames.get(newNumPlayers-1));

        playerInputNames.clear();
    }

    public void onBackwardButton(){

        playerInputNames.addAll(Arrays.asList(playerInp1, playerInp2, playerInp3, playerInp4, playerInp5, playerInp6));

        int newNumPlayers = Integer.parseInt(numPlayers.getText()) - 1;

        if (newNumPlayers < 2){
            backwardButton.setOpacity(0.5);
            backwardButton.setDisable(true);
        }
        else if(newNumPlayers == 2){
            backwardButton.setOpacity(0.5);
            numPlayers.setText(newNumPlayers + "");
            backwardButton.setDisable(true);
        }
        else{
            numPlayers.setText(newNumPlayers + "");
            forwardButton.setOpacity(1);
            forwardButton.setDisable(false);
        }

        setUp.getChildren().remove(playerInputNames.get(newNumPlayers));

        playerInputNames.clear();
    }

    @FXML
    protected void onRulesButtonClick() throws IOException {
        Stage newStage = Variables.getRulesStage();
        newStage.show();
    }

    @FXML
    protected void onPlayButtonClick() throws IOException {

        Stage newStage = Variables.getGameStage();
        newStage.show();

        Stage oldStage = Variables.getSetUpStage();
        oldStage.close();
    }
}