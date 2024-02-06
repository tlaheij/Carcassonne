package com.example.demo;

public class SavePlayer {
    private int numMeeples;
    private String colour;
    private int points;
    private String name;
    private boolean hasBigMeeple;
    private int numGold;

    public SavePlayer(int numMeeples, String colour, int points, String name, boolean hasBigMeeple, int numGold) {
        this.numMeeples = numMeeples;
        this.colour = colour;
        this.points = points;
        this.name = name;
        this.hasBigMeeple = hasBigMeeple;
        this.numGold = numGold;
    }

    public SavePlayer() {
    }

    public int getNumMeeples() {
        return numMeeples;
    }

    public void setNumMeeples(int numMeeples) {
        this.numMeeples = numMeeples;
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

    public boolean isHasBigMeeple() {
        return hasBigMeeple;
    }

    public void setHasBigMeeple(boolean hasBigMeeple) {
        this.hasBigMeeple = hasBigMeeple;
    }

    public int getNumGold() {
        return numGold;
    }

    public void setNumGold(int numGold) {
        this.numGold = numGold;
    }


}
