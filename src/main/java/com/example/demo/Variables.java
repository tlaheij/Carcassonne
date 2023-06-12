package com.example.demo;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Variables {

    static Stage setUp = new Stage();

    static Stage home = new Stage();

    static Stage rules = new Stage();

    static Stage game = new Stage();

    static Color darkBlue = Color.web("#003844");
    static Color darkBlue2 = Color.web("#003845");
    static Color yellow = Color.web("#FFEBC6");

    static Color darkYellow = Color.web("#FFB100");

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
