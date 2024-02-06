package com.example.demo;

import java.util.ArrayList;

public class SaveICTile {
    private boolean inn;
    private boolean cathedrale;

    ArrayList<SaveICProject> ICprojects = new ArrayList<>();

    private int code;

    public SaveICTile(boolean inn, boolean cathedrale, ArrayList<ICProject> ICprojects, int code) {
        this.inn = inn;
        this.cathedrale = cathedrale;

        for(int i  = 0; i < ICprojects.size(); i ++){
            SaveICProject temp = new SaveICProject(ICprojects.get(i).isInn(), ICprojects.get(i).isCathedrale(), ICprojects.get(i).getType(), ICprojects.get(i).getID());
            this.ICprojects.add(temp);
        }

        this.code = code;
    }

    public SaveICTile() {
    }

    public boolean isInn() {
        return inn;
    }

    public boolean isCathedrale() {
        return cathedrale;
    }

    public ArrayList<SaveICProject> getICprojects() {
        return ICprojects;
    }

    public int getCode() {
        return code;
    }

    public void setInn(boolean inn) {
        this.inn = inn;
    }

    public void setCathedrale(boolean cathedrale) {
        this.cathedrale = cathedrale;
    }

    public void setICprojects(ArrayList<SaveICProject> ICprojects) {
        this.ICprojects = ICprojects;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
