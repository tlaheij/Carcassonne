package com.example.demo;

public class SaveICProject {
    private boolean inn;
    private boolean cathedrale;
    private String type;
    private String ID;

    public SaveICProject(boolean inn, boolean cathedrale, String type, String ID) {
        this.inn = inn;
        this.cathedrale = cathedrale;
        this.type = type;
        this.ID = ID;
    }

    public SaveICProject() {
    }

    public boolean isInn() {
        return inn;
    }

    public boolean isCathedrale() {
        return cathedrale;
    }

    public String getType() {
        return type;
    }

    public String getID() {
        return ID;
    }

    public void setInn(boolean inn) {
        this.inn = inn;
    }

    public void setCathedrale(boolean cathedrale) {
        this.cathedrale = cathedrale;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
