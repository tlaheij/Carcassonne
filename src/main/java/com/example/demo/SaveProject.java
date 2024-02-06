package com.example.demo;

public class SaveProject {
    private String type;
    private String ID;
    private int meepleX;
    private int meepleY;
    private SaveMeeple meeple;
    private int xTile;
    private int yTile;
    boolean shield;

    public SaveProject(String type, String ID, int meepleX, int meepleY, Meeple meeple, int X, int Y, boolean shield) {
        this.type = type;
        this.ID = ID;
        this.meepleX = meepleX;
        this.meepleY = meepleY;

        if(meeple == null){
            this.meeple = null;
        }
        else{
            SaveMeeple tempMeeple = new SaveMeeple(meeple.getX(), meeple.getY(), meeple.getColour(), meeple.getXgrid(), meeple.getYgird(), meeple.isBig());
            this.meeple = tempMeeple;
        }

        this.xTile = X;
        this.yTile = Y;
        this.shield = shield;
    }

    public SaveProject() {
    }

    public String getType() {
        return type;
    }

    public String getID() {
        return ID;
    }

    public int getMeepleX() {
        return meepleX;
    }

    public int getMeepleY() {
        return meepleY;
    }

    public SaveMeeple getMeeple() {
        return meeple;
    }

    public int getxTile() {
        return xTile;
    }

    public int getyTile() {
        return yTile;
    }

    public boolean isShield() {
        return shield;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMeepleX(int meepleX) {
        this.meepleX = meepleX;
    }

    public void setMeepleY(int meepleY) {
        this.meepleY = meepleY;
    }

    public void setMeeple(SaveMeeple meeple) {
        this.meeple = meeple;
    }

    public void setxTile(int xTile) {
        this.xTile = xTile;
    }

    public void setyTile(int yTile) {
        this.yTile = yTile;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }
}
