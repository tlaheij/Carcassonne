package com.example.demo;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SaveGame {
    private int currentRotationPreviewTile;
    private ArrayList<SaveTile> playedTiles = new ArrayList<>();
    private ArrayList<SaveTile> tiles = new ArrayList<>();
    private int numPlayers;
    private int meepleCounter;
    private boolean IC;
    private ArrayList<SaveICTile> ICTiles = new ArrayList<>();
    private boolean G;
    private ArrayList<SaveGTile> GTiles = new ArrayList<>();

    private ArrayList<SavePlayer> players = new ArrayList<>();

    private int cols;
    private int rows;

    public SaveGame(int currentRotationPreviewTile, ArrayList<ArrayList<Tile>> playedTile, ArrayList<Tile> playedTiles, ArrayList<Tile> tiles, int numPlayers, ArrayList<Player> players, int meepleCounter, boolean IC, ArrayList<ICTile> ICTiles, boolean G, ArrayList<GTile> GTiles) {
        this.currentRotationPreviewTile = currentRotationPreviewTile;

        for (int i = 0; i < playedTile.size(); i++) {
            for(int j = 0; j <  playedTile.get(i).size(); j++){
                for(int k = 0; k < playedTiles.size(); k ++){
                    if(playedTile.get(i).get(j) == playedTiles.get(k)){
                        SaveTile tempTile = new SaveTile(playedTiles.get(k).getSides(), playedTiles.get(k).getFields(), playedTiles.get(k).isChurch(), playedTiles.get(k).isShield(), playedTiles.get(k).getImageName(), playedTiles.get(k).getProjects(), playedTiles.get(k).getNumProjects(), playedTiles.get(k).getRotation(), playedTiles.get(k).getShieldProjectID(), playedTiles.get(k).getID(), playedTiles.get(k).getCode(), i, j);
                        this.playedTiles.add(tempTile);
                    }
                }
            }
        }

        for(int i = 0; i < tiles.size(); i ++){
            SaveTile tempTile = new SaveTile(tiles.get(i).getSides(), tiles.get(i).getFields(), tiles.get(i).isChurch(), tiles.get(i).isShield(), tiles.get(i).getImageName(), tiles.get(i).getProjects(), tiles.get(i).getNumProjects(), tiles.get(i).getRotation(), tiles.get(i).getShieldProjectID(), tiles.get(i).getID(), tiles.get(i).getCode(), -1, -1);
            this.tiles.add(tempTile);
        }

        this.numPlayers = numPlayers;
        this.meepleCounter = meepleCounter;
        this.IC = IC;

        if(IC){
            for(int i = 0; i < ICTiles.size(); i ++){
                SaveICTile tempICTile = new SaveICTile(ICTiles.get(i).isInn(), ICTiles.get(i).isCathedrale(), ICTiles.get(i).getICProjects(), ICTiles.get(i).getCode());
                this.ICTiles.add(tempICTile);
            }
        }
        else{
            this.ICTiles = null;
        }

        this.G = G;

        if(G){
            for(int i = 0; i < GTiles.size(); i ++){
                SaveGTile tempGTile = new SaveGTile(GTiles.get(i).getXGold(), GTiles.get(i).getYGold(), GTiles.get(i).isHasGold(), GTiles.get(i).getCode());
                this.GTiles.add(tempGTile);
            }
        }
        else{
            this.GTiles = null;
        }

        for(int i = 0; i < players.size(); i ++){
            SavePlayer tempPlayer = new SavePlayer(players.get(i).getNumMeeples(), players.get(i).getColour(), players.get(i).getPoints(), players.get(i).getName(), players.get(i).isHasBigMeeple(), players.get(i).getNumGold());
            this.players.add(tempPlayer);
        }

        this.cols = playedTile.size();
        this.rows = playedTile.get(0).size();
    }

    public SaveGame() {
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCurrentRotationPreviewTile() {
        return currentRotationPreviewTile;
    }

    public ArrayList<SaveTile> getPlayedTiles() {
        return playedTiles;
    }

    public ArrayList<SaveTile> getTiles() {
        return tiles;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getMeepleCounter() {
        return meepleCounter;
    }

    public boolean isIC() {
        return IC;
    }

    public ArrayList<SaveICTile> getICTiles() {
        return ICTiles;
    }

    public boolean isG() {
        return G;
    }

    public ArrayList<SaveGTile> getGTiles() {
        return GTiles;
    }

    public ArrayList<SavePlayer> getPlayers() {
        return players;
    }

    public void setCurrentRotationPreviewTile(int currentRotationPreviewTile) {
        this.currentRotationPreviewTile = currentRotationPreviewTile;
    }

    public void setPlayedTiles(ArrayList<SaveTile> playedTiles) {
        this.playedTiles = playedTiles;
    }

    public void setTiles(ArrayList<SaveTile> tiles) {
        this.tiles = tiles;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setMeepleCounter(int meepleCounter) {
        this.meepleCounter = meepleCounter;
    }

    public void setIC(boolean IC) {
        this.IC = IC;
    }

    public void setICTiles(ArrayList<SaveICTile> ICTiles) {
        this.ICTiles = ICTiles;
    }

    public void setG(boolean g) {
        G = g;
    }

    public void setGTiles(ArrayList<SaveGTile> GTiles) {
        this.GTiles = GTiles;
    }

    public void setPlayers(ArrayList<SavePlayer> players) {
        this.players = players;
    }
}
