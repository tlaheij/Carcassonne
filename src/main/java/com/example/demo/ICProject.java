package com.example.demo;

public class ICProject extends Project{

    private boolean inn;
    private boolean cathedrale;

    public ICProject(String type, String ID, int meepleX, int meepleY, Meeple meeple, boolean shield, Tile tile) {
        super(type, ID, meepleX, meepleY, meeple, shield, tile);
        inn = false;
        cathedrale = false;
    }

    public void addInn(){
        inn = true;
    }

    public void addCathedral(){
        cathedrale = true;
    }

    public boolean isInn() {
        return inn;
    }

    public boolean isCathedrale() {
        return cathedrale;
    }
}
