package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class EndingScreenController implements Initializable {
    @FXML
    private VBox winnerBox;
    @FXML
    private ImageView  winnerIcon;
    @FXML
    private Text winnerName;
    @FXML
    private Text winnerPoints;
    @FXML
    private VBox ranking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ArrayList<Player> players = Variables.getPlayers();
        players.sort(Comparator.comparing(Player::getPoints));
        Collections.reverse(players);
        String winningColour = players.get(0).getColour();

        Image temp = new Image("file:./src/main/resources/com/example/demo/meeples/meeple" + winningColour + ".png") ;
        winnerIcon.setImage(temp);

        winnerName.setText(players.get(0).getName());
        winnerPoints.setText(String.valueOf(players.get(0).getPoints()));

        for(int i = 1; i < players.size(); i ++){
            ranking.getChildren().add(players.get(i).getEndBox());
            players.get(i).getEndName().setText(players.get(i).getName());
            players.get(i).getEndPoints().setText(String.valueOf(players.get(i).getPoints()));
            players.get(i).getEndRank().setText(String.valueOf(i + 1));
        }
    }

    public void onHomeButton(){
        Variables.getEndStage().hide();
        Variables.getHomeStage().show();
    }
}
