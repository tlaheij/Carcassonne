package com.example.demo;

public class SaveMeeple {
    private int x;
    private int y;
    private String colour;
    private int xgrid;
    private int ygird;
    private boolean big;

    public SaveMeeple(int x, int y, String colour,  int xgrid, int ygird, boolean big) {
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.xgrid = xgrid;
        this.ygird = ygird;
        this.big = big;
    }

    public SaveMeeple() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColour() {
        return colour;
    }

    public int getXgrid() {
        return xgrid;
    }

    public int getYgird() {
        return ygird;
    }

    public boolean isBig() {
        return big;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setXgrid(int xgrid) {
        this.xgrid = xgrid;
    }

    public void setYgird(int ygird) {
        this.ygird = ygird;
    }

    public void setBig(boolean big) {
        this.big = big;
    }
}
