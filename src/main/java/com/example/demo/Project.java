package com.example.demo;

public class Project {
    private String type;
    private String ID;
    private int meepleX;
    private int meepleY;
    private Meeple meeple;

    private Tile tile;

    boolean shield;

    public Project(String type, String ID, int meepleX, int meepleY, Meeple meeple, boolean shield, Tile tile){
        this.type = type;
        this.ID = ID;
        this.meepleX = meepleX;
        this.meepleY = meepleY;
        this.meeple = meeple;
        this.shield = shield;
        this.tile =tile;
        this.meeple = null;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isShield() {
        return shield;
    }

    public Meeple getMeeple() {
        return meeple;
    }

    public void setMeeple(Meeple meeple) {
        this.meeple = meeple;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getMeepleX() {
        return meepleX;
    }

    public void setMeepleX(int meepleX) {
        this.meepleX = meepleX;
    }

    public int getMeepleY() {
        return meepleY;
    }

    public void setMeepleY(int meepleY) {
        this.meepleY = meepleY;
    }
}
