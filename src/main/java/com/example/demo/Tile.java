package com.example.demo;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Tile {
    // [north, east, south, west] city or road or field with number eg [R1, R1, C1, C2], length is 4
    String[] sides;
    // [north, east, south, west] each side is spilt in 2 sections, each field is a number eg [1,1,1,2,2,2,2,1] (this is 2 fields) length is 8
    // C means no field but a city (so can be ignored)
    String[] fields;

    boolean church;
    boolean shield;

    String imageLoc;

    Image image;

    ArrayList<Project> projects = new ArrayList<>();

    int numProjects;

    int rotation;

    public Tile(String[] sides, String[] fields, boolean church, boolean shield, String imageName, int numProjects){
        this.church = church;
        this.fields = fields;
        this.shield = shield;
        this.sides = sides;
        this.imageLoc = "file:./src/main/resources/com/example/demo/tiles/";
        this.imageLoc += imageName;
        this.imageLoc += ".png";
        this.numProjects = numProjects;

        image = new Image(imageLoc);
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void addProject(String projectName, String projectID, int meepleX, int meepleY){
        Project project = new Project(projectName, projectID, meepleX, meepleY, null);
        projects.add(project);
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public int getNumProjects() {
        return numProjects;
    }

    public void setSides(String[] sides) {
        this.sides = sides;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public String[] getSides() {
        return sides;
    }

    public String[] getFields() {
        return fields;
    }

    public boolean isChurch() {
        return church;
    }

    public boolean isShield() {
        return shield;
    }

    public String getImageLoc() {
        return imageLoc;
    }
}
