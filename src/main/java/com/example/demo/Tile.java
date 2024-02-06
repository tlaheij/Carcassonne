package com.example.demo;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Tile {
    // [north, east, south, west] city or road or field with number eg [R1, R1, C1, C2], length is 4
    private String[] sides;
    // [north, east, south, west] each side is spilt in 2 sections, each field is a number eg [1,1,1,2,2,2,2,1] (this is 2 fields) length is 8
    // C means no field but a city (so can be ignored)
    private String[] fields;

    private boolean church;
    private boolean shield;

    private String imageLoc;

    private String imageName;

    private Image image;

    private ArrayList<Project> projects = new ArrayList<>();

    private int numProjects;

    private int rotation;

    private String shieldProjectID;

    private String ID;
    private int code;


    public Tile(String[] sides, String[] fields, boolean church, boolean shield, String shieldProjectID, String imageName, int numProjects, int code){
        this.church = church;
        this.fields = fields;
        this.shield = shield;
        this.sides = sides;
        this.imageLoc = "file:./src/main/resources/com/example/demo/tiles/";
        this.imageLoc += imageName;
        this.code = code;
        this.imageLoc += ".png";
        this.numProjects = numProjects;
        this.shieldProjectID = shieldProjectID;
        this.imageName = imageName;

        image = new Image(imageLoc);

        ID = "";
        for(int i = 0; i < 4; i ++){
            ID += sides[i].charAt(0);
        }
    }

    public String getImageName() {
        return imageName;
    }

    public int getCode() {
        return code;
    }

    public String getID() {
        return ID;
    }

    public String getShieldProjectID() {
        return shieldProjectID;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void addProject(String projectName, String projectID, int meepleX, int meepleY, boolean shield){
        Project project = new Project(projectName, projectID, meepleX, meepleY, null, shield, this);
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
