package com.example.demo;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Variables {

    static Stage setUp = new Stage();

    static Stage home = new Stage();

    static Stage rules = new Stage();

    static Stage game = new Stage();

    static Stage end = new Stage();

    static Color darkBlue = Color.web("#003844");
    static Color darkBlue2 = Color.web("#003845");
    static Color yellow = Color.web("#FFEBC6");

    static Color darkYellow = Color.web("#FFB100");

    static int numPlayers = 2;

    static ArrayList<Player> players = new ArrayList<>();

    static boolean innCathedral  = false;

    static boolean goldmines  = false;

    static SaveGame savedGame;

    public static SaveGame getSavedGame() {
        return savedGame;
    }

    public static void setSavedGame(SaveGame savedGame) {
        Variables.savedGame = savedGame;
    }

    public static boolean isGoldmines() {
        return goldmines;
    }

    public static void setGoldmines(boolean goldmines) {
        Variables.goldmines = goldmines;
    }

    public static boolean isInnCathedral() {
        return innCathedral;
    }

    public static void setInnCathedral(boolean innCathedral) {
        Variables.innCathedral = innCathedral;
    }

    public static ArrayList<Tile> getTilesNotPlayed() {
        return tilesNotPlayed;
    }

    public static void setTilesNotPlayed(ArrayList<Tile> tilesNotPlayed) {
        Variables.tilesNotPlayed = tilesNotPlayed;
    }

    static ArrayList<Tile> tilesNotPlayed = new ArrayList<>();

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Variables.players = players;
    }

    public static int getNumPlayers() {
        return numPlayers;
    }

    public static void setNumPlayers(int numPlayers) {
        Variables.numPlayers = numPlayers;
    }

    public static Color getDarkYellow() {
        return darkYellow;
    }

    public static Color getDarkBlue2() {
        return darkBlue2;
    }

    public static Color getYellow() {
        return yellow;
    }
    public static Color getDarkBlue() {
        return darkBlue;
    }

    public static void setDarkBlue(Color darkBlue) {
        Variables.darkBlue = darkBlue;
    }

    public static Stage getEndStage(){
        return end;
    }

    public static Stage getSetUpStage(){
        return setUp;
    }

    public static Stage getRulesStage(){
        return rules;
    }

    public static Stage getHomeStage(){
        return home;
    }

    public static Stage getGameStage(){
        return game;
    }

    public static void setHomeStage(Stage home2){
        home = home2;
    }
}
