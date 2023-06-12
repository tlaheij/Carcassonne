package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Meeple {
    private ImageView display = new ImageView();
    private int x;
    private int y;
    private String colour;

    private Project project;

    private int xgrid;
    private int ygird;

    public Meeple(int x, int y, int xgrid, int ygird){
        this.x = x;
        this.y = y;
        // colindex
        this.xgrid = xgrid;
        // rowindex
        this.ygird= ygird;
        Image image = new Image("file:./src/main/resources/com/example/demo/meeples/meeplepink.png");
        display.setImage(image);
        display.setFitHeight(12);
        display.setFitWidth(12);
    }

    public int getXgrid() {
        return xgrid;
    }

    public int getYgird() {
        return ygird;
    }

    public void setXgrid(int xgrid) {
        this.xgrid = xgrid;
    }

    public void setYgird(int ygird) {
        this.ygird = ygird;
    }

    public void setProject(Project project){
        this.project = project;
    }

    public Project getProject(){
        return project;
    }

    public ImageView getDisplay() {
        return display;
    }

    public void setDisplay(ImageView display) {
        this.display = display;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setSize(){
        display.setFitHeight(24);
        display.setFitWidth(24);
    }
}
