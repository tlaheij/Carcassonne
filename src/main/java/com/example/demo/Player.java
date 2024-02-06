package com.example.demo;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Player {
    private int numMeeples;

    private String colour;

    private int points;

    private String name;
    private VBox turnBox;
    private VBox waitingBox;
    private HBox turnMeeplesBox1;
    private HBox turnMeeplesBox2;
    private HBox waitingMeeplesBox;
    private Text turnName;
    private Text waitingName;
    private Text turnPoints;
    private Text waitingPoints;

    private VBox endBox;

    private Text endName;

    private Text endPoints;

    private Text endRank;

    private ImageView waitingMeepleDisplay;
    private ImageView turnMeepleDisplay;

    private boolean hasBigMeeple;

    private ImageView bigMeepleTurnPreview;

    private ImageView bigMeepleWaitPreview;

    private int numGold;

    private HBox goldBoxWaiting;
    private HBox goldBoxTurn;

    private Text numGoldTextWaiting;
    private Text numGoldTextTurn;

    public Player(VBox turnBox, VBox waitingBox, Text turnName, Text waitingName, Text turnPoints, Text waitingPoints, HBox turnMeeplesBox1, HBox turnMeeplesBox2, HBox waitingMeeplesBox, VBox endBox, Text endName, Text endPoints, Text endRank, int numMeeples, String colour, int points, ImageView bigMeepleTurnPreview, ImageView bigMeepleWaitPreview, HBox goldBoxWaiting, HBox goldBoxTurn, Text numGoldTextWaiting, Text numGoldTextTurn){
        this.turnBox = turnBox;
        this.waitingBox = waitingBox;
        this.turnMeeplesBox1 = turnMeeplesBox1;
        this.turnMeeplesBox2 = turnMeeplesBox2;
        this.turnName = turnName;
        this.waitingName = waitingName;
        this.turnPoints = turnPoints;
        this.waitingPoints = waitingPoints;
        this.waitingMeeplesBox = waitingMeeplesBox;
        this.endBox = endBox;
        this.endName = endName;
        this.endPoints = endPoints;
        this.endRank = endRank;
        this.numMeeples = numMeeples;
        this.colour = colour;
        this.points = points;
        this.bigMeepleTurnPreview = bigMeepleTurnPreview;
        this.bigMeepleWaitPreview = bigMeepleWaitPreview;
        this.goldBoxTurn = goldBoxTurn;
        this.goldBoxWaiting = goldBoxWaiting;
        this.numGoldTextTurn = numGoldTextTurn;
        this.numGoldTextWaiting = numGoldTextWaiting;
        name = null;
        numGold = 0;

        waitingMeepleDisplay = (ImageView) waitingMeeplesBox.getChildren().get(0);
        turnMeepleDisplay = (ImageView) turnMeeplesBox1.getChildren().get(0);

    }

    public int getNumGold() {
        return numGold;
    }

    public void setNumGold(int numGold) {
        this.numGold = numGold;
    }

    public void setHasBigMeeple(boolean hasBigMeeple) {
        this.hasBigMeeple = hasBigMeeple;
    }

    public HBox getGoldBoxWaiting() {
        return goldBoxWaiting;
    }


    public HBox getGoldBoxTurn() {
        return goldBoxTurn;
    }


    public Text getNumGoldTextWaiting() {
        return numGoldTextWaiting;
    }

    public Text getNumGoldTextTurn() {
        return numGoldTextTurn;
    }

    public void giveGold(){
        numGold ++;
    }

    public ImageView getBigMeepleTurnPreview() {
        return bigMeepleTurnPreview;
    }

    public ImageView getBigMeepleWaitPreview() {
        return bigMeepleWaitPreview;
    }

    public void giveBigMeeple(){
        hasBigMeeple = true;
    }

    public void takeBigMeeple(){
        hasBigMeeple = false;
    }

    public boolean isHasBigMeeple(){
        return hasBigMeeple;
    }
    public VBox getEndBox() {
        return endBox;
    }

    public Text getEndName() {
        return endName;
    }

    public Text getEndPoints() {
        return endPoints;
    }

    public Text getEndRank() {
        return endRank;
    }

    public ImageView getWaitingMeepleDisplay() {
        return waitingMeepleDisplay;
    }

    public ImageView getTurnMeepleDisplay() {
        return turnMeepleDisplay;
    }

    public HBox getWaitingMeeplesBox() {
        return waitingMeeplesBox;
    }

    public void setWaitingMeeplesBox(HBox waitingMeeplesBox) {
        this.waitingMeeplesBox = waitingMeeplesBox;
    }

    public int getNumMeeples() {
        return numMeeples;
    }

    public void setNumMeeples(int numMeeples) {
        this.numMeeples = numMeeples;
    }

    public VBox getTurnBox() {
        return turnBox;
    }

    public void setTurnBox(VBox turnBox) {
        this.turnBox = turnBox;
    }

    public VBox getWaitingBox() {
        return waitingBox;
    }

    public void setWaitingBox(VBox waitingBox) {
        this.waitingBox = waitingBox;
    }

    public HBox getTurnMeeplesBox1() {
        return turnMeeplesBox1;
    }

    public void setTurnMeeplesBox1(HBox turnMeeplesBox1) {
        this.turnMeeplesBox1 = turnMeeplesBox1;
    }

    public HBox getTurnMeeplesBox2() {
        return turnMeeplesBox2;
    }

    public void setTurnMeeplesBox2(HBox turnMeeplesBox2) {
        this.turnMeeplesBox2 = turnMeeplesBox2;
    }

    public Text getTurnName() {
        return turnName;
    }

    public void setTurnName(Text turnName) {
        this.turnName = turnName;
    }

    public Text getWaitingName() {
        return waitingName;
    }

    public void setWaitingName(Text waitingName) {
        this.waitingName = waitingName;
    }

    public Text getTurnPoints() {
        return turnPoints;
    }

    public void setTurnPoints(Text turnPoints) {
        this.turnPoints = turnPoints;
    }

    public Text getWaitingPoints() {
        return waitingPoints;
    }

    public void setWaitingPoints(Text waitingPoints) {
        this.waitingPoints = waitingPoints;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
