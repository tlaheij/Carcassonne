package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayerPreviews implements Initializable {
    @FXML
    private VBox yellowTurnPlayerBox;
    @FXML
    private VBox redTurnPlayerBox;
    @FXML
    private VBox pinkTurnPlayerBox;
    @FXML
    private VBox greenTurnPlayerBox;
    @FXML
    private VBox blueTurnPlayerBox;
    @FXML
    private VBox blackTurnPlayerBox;
    @FXML
    private VBox redPlayerWaitingBox;
    @FXML
    private VBox pinkPlayerWaitingBox;
    @FXML
    private VBox greenPlayerWaitingBox;
    @FXML
    private VBox bluePlayerWaitingBox;
    @FXML
    private VBox blackPlayerWaitingBox;
    @FXML
    private VBox yellowPlayerWaitingBox;
    @FXML
    private Text yellowTurnPoints;
    @FXML
    private Text redTurnPoints;
    @FXML
    private Text pinkTurnPoints;
    @FXML
    private Text greenTurnPoints;
    @FXML
    private Text blueTurnPoints;
    @FXML
    private Text blackTurnPoints;
    @FXML
    private Text yellowWaitPoints;
    @FXML
    private Text redWaitPoints;
    @FXML
    private Text pinkWaitPoints;
    @FXML
    private Text greenWaitPoints;
    @FXML
    private Text blueWaitPoints;
    @FXML
    private Text blackWaitPoints;
    @FXML
    private Text yellowTurnName;
    @FXML
    private Text redTurnName;
    @FXML
    private Text pinkTurnName;
    @FXML
    private Text greenTurnName;
    @FXML
    private Text blueTurnName;
    @FXML
    private Text blackTurnName;
    @FXML
    private Text yellowWaitName;
    @FXML
    private Text redWaitName;
    @FXML
    private Text pinkWaitName;
    @FXML
    private Text greenWaitName;
    @FXML
    private Text blueWaitName;
    @FXML
    private Text blackWaitName;
    @FXML
    private HBox yellowTurnMeeplesBox1;
    @FXML
    private HBox redTurnMeeplesBox1;
    @FXML
    private HBox pinkTurnMeeplesBox1;
    @FXML
    private HBox greenTurnMeeplesBox1;
    @FXML
    private HBox blueTurnMeeplesBox1;
    @FXML
    private HBox blackTurnMeeplesBox1;
    @FXML
    private HBox yellowTurnMeeplesBox2;
    @FXML
    private HBox redTurnMeeplesBox2;
    @FXML
    private HBox pinkTurnMeeplesBox2;
    @FXML
    private HBox greenTurnMeeplesBox2;
    @FXML
    private HBox blueTurnMeeplesBox2;
    @FXML
    private HBox blackTurnMeeplesBox2;
    @FXML
    private HBox yellowWaitMeeplesBox;
    @FXML
    private HBox redWaitMeeplesBox;
    @FXML
    private HBox pinkWaitMeeplesBox;
    @FXML
    private HBox greenWaitMeeplesBox;
    @FXML
    private HBox blueWaitMeeplesBox;
    @FXML
    private HBox blackWaitMeeplesBox;
    @FXML
    private VBox redEndBox;
    @FXML
    private VBox yellowEndBox;

    @FXML
    private VBox pinkEndBox;

    @FXML
    private VBox greenEndBox;

    @FXML
    private VBox blueEndBox;

    @FXML
    private VBox blackEndBox;

    @FXML
    private Text redEndName;

    @FXML
    private Text yellowEndName;

    @FXML
    private Text pinkEndName;

    @FXML
    private Text greenEndName;

    @FXML
    private Text blueEndName;

    @FXML
    private Text blackEndName;

    @FXML
    private Text redEndPoints;

    @FXML
    private Text yellowEndPoints;

    @FXML
    private Text pinkEndPoints;

    @FXML
    private Text greenEndPoints;

    @FXML
    private Text blueEndPoints;

    @FXML
    private Text blackEndPoints;

    @FXML
    private Text redEndRank;

    @FXML
    private Text yellowEndRank;

    @FXML
    private Text pinkEndRank;

    @FXML
    private Text greenEndRank;

    @FXML
    private Text blueEndRank;

    @FXML
    private Text blackEndRank;

    @FXML
    private ImageView yellowBigMeepleWaiting;
    @FXML
    private ImageView redBigMeepleWaiting;
    @FXML
    private ImageView pinkBigMeepleWaiting;
    @FXML
    private ImageView greenBigMeepleWaiting;
    @FXML
    private ImageView blueBigMeepleWaiting;
    @FXML
    private ImageView blackBigMeepleWaiting;
    @FXML
    private ImageView yellowBigMeepleTurn;
    @FXML
    private ImageView redBigMeepleTurn;
    @FXML
    private ImageView pinkBigMeepleTurn;
    @FXML
    private ImageView greenBigMeepleTurn;
    @FXML
    private ImageView blueBigMeepleTurn;
    @FXML
    private ImageView blackBigMeepleTurn;
    @FXML
    private HBox yellowGoldBoxWaiting;
    @FXML
    private HBox redGoldBoxWaiting;
    @FXML
    private HBox pinkGoldBoxWaiting;
    @FXML
    private HBox greenGoldBoxWaiting;
    @FXML
    private HBox blueGoldBoxWaiting;
    @FXML
    private HBox blackGoldBoxWaiting;
    @FXML
    private Text yellowNumGoldTextWaiting;
    @FXML
    private Text redNumGoldTextWaiting;
    @FXML
    private Text pinkNumGoldTextWaiting;
    @FXML
    private Text greenNumGoldTextWaiting;
    @FXML
    private Text blueNumGoldTextWaiting;
    @FXML
    private Text blackNumGoldTextWaiting;
    @FXML
    private HBox yellowGoldBoxTurn;
    @FXML
    private HBox redGoldBoxTurn;
    @FXML
    private HBox pinkGoldBoxTurn;
    @FXML
    private HBox greenGoldBoxTurn;
    @FXML
    private HBox blueGoldBoxTurn;
    @FXML
    private HBox blackGoldBoxTurn;
    @FXML
    private Text yellowNumGoldTextTurn;
    @FXML
    private Text redNumGoldTextTurn;
    @FXML
    private Text pinkNumGoldTextTurn;
    @FXML
    private Text greenNumGoldTextTurn;
    @FXML
    private Text blueNumGoldTextTurn;
    @FXML
    private Text blackNumGoldTextTurn;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        VBox[] turnBoxs = {yellowTurnPlayerBox, redTurnPlayerBox, pinkTurnPlayerBox, greenTurnPlayerBox, blueTurnPlayerBox, blackTurnPlayerBox};
        VBox[] waitingBoxs = {yellowPlayerWaitingBox, redPlayerWaitingBox, pinkPlayerWaitingBox, greenPlayerWaitingBox, bluePlayerWaitingBox, blackPlayerWaitingBox};
        Text[] turnNames = {yellowTurnName, redTurnName, pinkTurnName, greenTurnName, blueTurnName, blackTurnName};
        Text[] waitingNames = {yellowWaitName, redWaitName, pinkWaitName, greenWaitName, blueWaitName, blackWaitName};
        Text[] turnPoints = {yellowTurnPoints, redTurnPoints, pinkTurnPoints, greenTurnPoints, blueTurnPoints, blackTurnPoints};
        Text[] waitingPoints = {yellowWaitPoints, redWaitPoints, pinkWaitPoints, greenWaitPoints, blueWaitPoints, blackWaitPoints};
        HBox[] turnMeeplesBox1 = {yellowTurnMeeplesBox1, redTurnMeeplesBox1, pinkTurnMeeplesBox1, greenTurnMeeplesBox1, blueTurnMeeplesBox1, blackTurnMeeplesBox1};
        HBox[] turnMeeplesBox2 = {yellowTurnMeeplesBox2, redTurnMeeplesBox2, pinkTurnMeeplesBox2, greenTurnMeeplesBox2, blueTurnMeeplesBox2, blackTurnMeeplesBox2};
        HBox[] waitingMeeplesBox = {yellowWaitMeeplesBox, redWaitMeeplesBox, pinkWaitMeeplesBox, greenWaitMeeplesBox, blueWaitMeeplesBox, blackWaitMeeplesBox};
        VBox[] endBoxs = {yellowEndBox, redEndBox, pinkEndBox, greenEndBox, blueEndBox, blackEndBox};
        Text[] endNames = {yellowEndName, redEndName, pinkEndName, greenEndName, blueEndName, blackEndName};
        Text[] endPoints = {yellowEndPoints, redEndPoints, pinkEndPoints, greenEndPoints, blueEndPoints, blackEndPoints};
        Text[] endRank = {yellowEndRank, redEndRank, pinkEndRank, greenEndRank, blueEndRank, blackEndRank};
        ImageView[] bigMeeplesWaiting = {yellowBigMeepleWaiting, redBigMeepleWaiting, pinkBigMeepleWaiting, greenBigMeepleWaiting, blueBigMeepleWaiting, blackBigMeepleWaiting};
        ImageView[] bigMeeplesTurn = {yellowBigMeepleTurn, redBigMeepleTurn, pinkBigMeepleTurn, greenBigMeepleTurn, blueBigMeepleTurn, blackBigMeepleTurn};
        HBox[] goldBoxWaiting = {yellowGoldBoxWaiting, redGoldBoxWaiting, pinkGoldBoxWaiting, greenGoldBoxWaiting, blueGoldBoxWaiting, blackGoldBoxWaiting};
        HBox[] goldBoxTurn = {yellowGoldBoxTurn, redGoldBoxTurn, pinkGoldBoxTurn, greenGoldBoxTurn, blueGoldBoxTurn, blackGoldBoxTurn};
        Text[] numGoldTextWaiting = {yellowNumGoldTextWaiting, redNumGoldTextWaiting, pinkNumGoldTextWaiting, greenNumGoldTextWaiting, blueNumGoldTextWaiting, blackNumGoldTextWaiting};
        Text[] numGoldTextTurn = {yellowNumGoldTextTurn, redNumGoldTextTurn, pinkNumGoldTextTurn, greenNumGoldTextTurn, blueNumGoldTextTurn, blackNumGoldTextTurn};
        ArrayList<Player> players = new ArrayList<>();
        String[] colours = {"yellow", "red", "pink", "green", "blue", "black"};
        for(int i = 0; i < 6; i ++){
            Player player = new Player(turnBoxs[i], waitingBoxs[i], turnNames[i], waitingNames[i], turnPoints[i], waitingPoints[i], turnMeeplesBox1[i], turnMeeplesBox2[i], waitingMeeplesBox[i], endBoxs[i], endNames[i], endPoints[i], endRank[i],6, colours[i],0, bigMeeplesTurn[i], bigMeeplesWaiting[i], goldBoxWaiting[i], goldBoxTurn[i], numGoldTextWaiting[i], numGoldTextTurn[i]);
            players.add(player);
        }
        Variables.setPlayers(players);
    }
}
