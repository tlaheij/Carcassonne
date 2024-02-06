package com.example.demo;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class SaveTile {
    private String[] sides;
    private String[] fields;
    private boolean church;
    private boolean shield;
    private String imageLoc;
    private ArrayList<SaveProject> projects = new ArrayList<>();
    private int numProjects;
    private int rotation;
    private String shieldProjectID;
    private String ID;
    private int code;
    private int X;
    private int Y;


    public SaveTile(String[] sides, String[] fields, boolean church, boolean shield, String imageLoc, ArrayList<Project> projects, int numProjects, int rotation, String shieldProjectID, String ID, int code, int X, int Y) {
        this.sides = sides;
        this.fields = fields;
        this.church = church;
        this.shield = shield;
        this.imageLoc = imageLoc;

        for(int i = 0; i < projects.size(); i ++){
            SaveProject tempProj = new SaveProject(projects.get(i).getType(), projects.get(i).getID(), projects.get(i).getMeepleX(), projects.get(i).getMeepleY(), projects.get(i).getMeeple(), X, Y, projects.get(i).isShield());
            this.projects.add(tempProj);
        }

        this.numProjects = numProjects;
        this.rotation = rotation;
        this.shieldProjectID = shieldProjectID;
        this.ID = ID;
        this.code = code;
        this.X = X;
        this.Y = Y;
    }

    public SaveTile() {
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

    public ArrayList<SaveProject> getProjects() {
        return projects;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getNumProjects() {
        return numProjects;
    }

    public int getRotation() {
        return rotation;
    }

    public String getShieldProjectID() {
        return shieldProjectID;
    }

    public String getID() {
        return ID;
    }

    public int getCode() {
        return code;
    }

    public void setSides(String[] sides) {
        this.sides = sides;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public void setChurch(boolean church) {
        this.church = church;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public void setImageLoc(String imageLoc) {
        this.imageLoc = imageLoc;
    }

    public void setProjects(ArrayList<SaveProject> projects) {
        this.projects = projects;
    }

    public void setNumProjects(int numProjects) {
        this.numProjects = numProjects;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void setShieldProjectID(String shieldProjectID) {
        this.shieldProjectID = shieldProjectID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }
}
