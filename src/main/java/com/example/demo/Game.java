package com.example.demo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Game {
    // The board which is inside a scroll plane
    // colomuns and rows will be added because it starts blank
    private HBox board = new HBox();

    // is the VBox on the side where all the player data is kepted
    private VBox side = new VBox();

    // the ImageView where the tile currently being placed is shown to the user
    private ImageView previewTile = new ImageView();

    private StackPane preview;

    // the basis of the whole screen
    private AnchorPane screen = new AnchorPane();

    // shows the player how many tiles are left
    private Text tilesLeftText = new Text();

    // keeps all the colomuns so that they are easily accessable (this is only VBox's)
    private ArrayList<VBox> cols = new ArrayList<>();

    // the whole grid so that you can easily access it (this is only HBox's)
    private ArrayList<ArrayList<HBox>> grid = new ArrayList<>();

    //an array list of all the tiles in the game in order
    private ArrayList<Tile> tiles = new ArrayList<>();
    // an array list of all the tiles that have been played
    private ArrayList<Tile> playedTiles = new ArrayList<>();

    // a 2D array list of all the tiles that are played in a grid formate that matches where the tiles are placed by the users.
    private ArrayList<ArrayList<Tile>> playedTile = new ArrayList<>();

    // The rotation of the tile being placed
    private int currentRotationPreviewTile = 0;

    // the box where the user can choose where they want to place their meeple on the tiles
    private VBox selectionOption = new VBox();

    // this is a singular box with a project which the user has to click on place their meeple on that project
    private HBox selectedProjectBox;
    // the project that the user has selected to place their meeple on can be null
    private Project selectedProject;
    // this array list keeps track of all the projects of tiles which contribute to a bigger, complete project whcih the players can get points for
    private ArrayList<Project> projectsInProject = new ArrayList<>();
    // an array list the keeps hold of all the meeples that have been placed
    private ArrayList<Meeple> placedMeeples = new ArrayList<>();
    // every player as a player box which is a box that shows the players all the statistics of that player and when it is theri turn
    private HBox playersBox;
    // an integer whcih shows howmany players
    int numPlayers = 0;
    // the a box whcih contains and displays all statistics of the player that is currently playing their turn
    private VBox turnPlayerBox;

    private int meepleCounter = 0;
    // a button whcih opens a window which displayes all the tiles that are still left
    private HBox tilesLeftButton;
    // arraylist of all the completed cities in the game
    private ArrayList<ArrayList<Project>> completedCities = new ArrayList<>();
    // a unique code which will be used to generate for each tile
    private int uniqueCode = 0;
    // a variable that tells me if the user wants to play with the inns and cathedrales (IC) expansion - by default false
    private boolean IC = false;
    // all the inns and cathedrale tile in one arrayList
    private ArrayList<ICTile> ICTiles = new ArrayList<>();
    // all the inns and cathedrale projects in one arrayList
    private ArrayList<ICProject> ICProjects = new ArrayList<>();
    // a box that is where the user chooses which type of meeple to place (only for IC expansion)
    private VBox ICMeepleSelection;
    // the box to click when you want to place a big meeple (only for IC expansion)
    private VBox meepleselectionBigMeeple;
    // the box to click when you want to place a small meeple (only for IC expansion)
    private VBox meepleselectionSmallMeeple;
    // which meeple that is selected by the user (only usefull for IC expansion)
    private String meepleSelected;
    // a variable that tells me if the user wants to play with the goldmines (G) expansion - by default false
    private boolean G = false;
    // all goldmines tiles in one arrayList
    private ArrayList<GTile> GTiles = new ArrayList<>();
    // the button for the user to gain extra information about th epoints allocation of gold (only for G expansion)
    private HBox goldPointsButton;

    private SaveGame savedGame;

    public Game (HBox board, VBox side, ImageView previewTile, AnchorPane screen, Text tilesLeftText, VBox selectionOption, StackPane preview, HBox playersBox, VBox turnPlayerBox, HBox tilesLeftButton, VBox ICMeepleSelection, VBox meepleselectionBigMeeple, VBox meepleselectionSmallMeeple, HBox goldPointsButton){
        this.board = board;
        this.side = side;
        this.previewTile = previewTile;
        this.screen = screen;
        this.tilesLeftText = tilesLeftText;
        this.selectionOption = selectionOption;
        this.preview = preview;
        this.playersBox = playersBox;
        this.turnPlayerBox = turnPlayerBox;
        this.tilesLeftButton = tilesLeftButton;
        this.ICMeepleSelection = ICMeepleSelection;
        this.meepleselectionBigMeeple = meepleselectionBigMeeple;
        this.meepleselectionSmallMeeple = meepleselectionSmallMeeple;
        this.goldPointsButton = goldPointsButton;
    }

    /**
     * <p>
     *     This is the mehode which is the start of the game it sets up all the players the board
     *     by placing the starting tile, additionally it processes all the tile data and creates tiles
     *     and starts of the player turn, it also opens any game that was previously saved by the user.
     *     It does this based on all the information that was gained from the set up screen and therefore
     *     what the user wants to play with.
     * </p>
     * <p>
     *     The methode will only run once the play button is pressed, therefore the user must press the play
     *     button before being able to play. The button is disabled after this has run once therefore this
     *     methode will only be able to run once.
     * </p>
     *
     * Use the {@link #openSavedGame()}                         to load any saved game that was paused
     *                                                          previously.
     * Use the {@link #setUpPlayers()}                          to set up the players giving them meeples
     *                                                          and updating their waiting and turn boxs.
     * Use the {@link #addColBack()}                            to add the first 10 columns of the board.
     * Use the {@link #processData(ArrayList<String[]>)}        to process all the tile data.
     * Use the {@link #processICData(ArrayList<String[]>)}      to process all the inns and cathedral
     *                                                          expansion tile data.
     * Use the {@link #createICTiles(ArrayList<String[][]>)}    to create all the inns and cathedral
     *                                                          expansion tiles using the data that
     *                                                          was processed.
     * Use the {@link #processGData(ArrayList<String[]>)}       to process all the goldmines expansion
     *                                                          tile data.
     * Use the {@link #createGTiles(ArrayList<String[][]>)}     to create all the goldmines' expansion
     *                                                          tiles using the data that was processed.
     * Use the {@link #createTiles(ArrayList<String[][]>)}      to create all the tiles using the data
     *                                                          that was processed.
     * Use the {@link #showCanPlace(Tile tile)}                 to show all the places that the first
     *                                                          tile can be placed.
     *
     * @throws FileNotFoundException                            if the methode is unable to find any file,
     *                                                          these files could include the tile data
     *                                                          files, the fxml files, or the saved game file
     */
    public void play() throws FileNotFoundException {
        if (Variables.getSavedGame() != null) {
            savedGame = Variables.getSavedGame();
            Variables.setSavedGame(null);

            openSavedGame();
        }
        else {
            // if the user selected any expansions they will be set true or false
            IC = Variables.isInnCathedral();
            G = Variables.isGoldmines();

            // if they didn't select the IC expansion, the meeple selection can be deleted
            if (!IC) {
                ICMeepleSelection.getChildren().clear();
                ICMeepleSelection.setPrefHeight(0);
            }
            // if they did select the IC expansion the meeple selection can be hidden for now (not deleted)
            else {
                ICMeepleSelection.setOpacity(0);
                ICMeepleSelection.setDisable(true);
            }
            // if they didn't select the G expansion the gold poitns button can be deleted
            if (!G) {
                goldPointsButton.setOpacity(0);
                goldPointsButton.setDisable(true);
            }

            // retriving the number of players in the game
            numPlayers = Variables.getNumPlayers();
            // calling a function to create the player boxs so that they can be displayed to the players.
            setUpPlayers();

            // setting up the board so that there are columns and rows to start
            for (int i = 0; i < 10; i++) {
                // The method to add a colomum is called 10 times
                addColBack();
            }

            // setting up the data for the startin tile
            // C1 - north side is a city, R1 - east side is a road, F1, - south side is a field, R1 - west side is the same road as east side
            String[] sides = {"C1", "R1", "F1", "R1"};
            // c - north left is city, c - north right side is city, 1 - east left side is field 1, 2 - east right side is field 2
            // 2 - south left is field 2, 2 - south right side is field 2, 2 - west left side is field 2, 1 - west right side is field 1
            String[] fields = {"c", "c", "1", "2", "2", "2", "2", "1"};

            // an integer of how many projects the start tile has
            int numStartTileProjects = 4;

            // creating the start tile using the tile class and all previously created variables
            Tile start = new Tile(sides, fields, false, false, null, "start tile", numStartTileProjects, uniqueCode);
            uniqueCode++;

            // adding all the prejects of the start tile individually, using the addProject method of the tile class
            start.addProject("city1", "1", 38, 3, false);
            start.addProject("road1", "2", 34, 37, false);
            start.addProject("field1", "1", 34, 56, false);
            start.addProject("field2", "2", 24, 26, false);

            // adding the start tile to played tiles
            playedTiles.add(start);

            // creating the image view inorder to display is inside an HBox on the board
            ImageView startTile = new ImageView();

            // setting the image of the image view to the image connected with the start tile
            startTile.setImage(start.getImage());
            // resizing the image view to make sure it fits prefectly in the grid
            startTile.resize(80, 80);
            // resize the image within the image view
            startTile.setFitHeight(80);
            startTile.setFitWidth(80);

            // using the gird variable to get place the image view in the correct HBox
            grid.get(4).get(3).getChildren().add(startTile);
            // placing the same tile in the 2D array list playedTile
            playedTile.get(4).set(3, start);

            // needed to javaFX inorder to locate where to find the file
            String currentDir = System.getProperty("user.dir");
            // sending the tiles.txt file to the file reader inorder to get all the tile data and then processing that data and saving that in the data string[][]
            ArrayList<String[][]> data = processData(FileRead.tiles(currentDir + "/src/main/saved/tiles.txt"));
            // if the IC expansion was selected that data also needs to be proceesd
            if (IC) {
                // sending the tiles.txt file to the file reader inorder to get all the tile data for the IC expansion and then processing that data and saving that in the data string[][]
                ArrayList<String[][]> ICData = processICData(FileRead.tiles(currentDir + "/src/main/saved/tilesIC.txt"));
                // create all the IC expansion tiles
                createICTiles(ICData);

                // loop through all the IC expansion tiles and their porjects
                for (int i = 0; i < ICTiles.size(); i++) {
                    for (int j = 0; j < ICTiles.get(i).getNumProjects(); j++) {
                        // add the IC expansion tiles to the Ic projects arrayList
                        ICProjects.add(ICTiles.get(i).getICProjects().get(j));
                    }
                }
            }

            // if the G expansion was selected that data also needs to be proceesd
            if (G) {
                // sending the tiles.txt file to the file reader inorder to get all the tile data for the G expansion and then processing that data and saving that in the data string[][]
                ArrayList<String[][]> GData = processGData(FileRead.tiles(currentDir + "/src/main/saved/tilesG.txt"));
                // creating all the G expansion tiles
                createGTiles(GData);
            }

            // creating all the tiles for the game
            createTiles(data);

            // shuffling the tiles in a random order
            Collections.shuffle(tiles);

            // setting the prewview tile image view  to the top most tile
            previewTile.setImage(tiles.get(0).getImage());

            // checking if the G expansion was selected and if the current tile is a G expanion tile
            if (G && GTiles.contains(tiles.get(0))) {
                // a temporary value set to the gTile (this should be the current tile)
                GTile gTile = null;
                // looping through all the g expansion tiles
                for (int i = 0; i < GTiles.size(); i++) {
                    // if this is the current tile we set it to the gitle
                    if (tiles.get(0) == GTiles.get(i)) {
                        gTile = GTiles.get(i);
                    }
                }
                // creating a pane and hbox inorder to place the gold bar on the tile
                Pane width2 = new Pane();
                HBox ph2 = new HBox();

                // obtaining a image view and image for the gold bar
                ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
                // setting of the goldbar to the size wanted
                goldImage.setFitHeight(15);
                goldImage.setFitWidth(24);

                // setting the size of the pane to the X coordinate of the goldbar
                // the width is a place holder which should push the gold bar image view so many pixels to the right
                width2.setPrefSize(gTile.getXGold() * 2, 160 - gTile.getYGold());
                // the y coordinates are set as padding in a HBox pushing the gold bar down
                ph2.setPadding(new Insets(gTile.getYGold() * 2, 0, 0, 0));

                // putting the imageview and width pane in the HBox
                ph2.getChildren().addAll(width2, goldImage);
                // adding the HBox of the preview stackPane which is what the user sees (the width and ph2 shoudl be invisible)
                preview.getChildren().add(ph2);
            }

            // calling a function which will show where the tile currently being placed could be placed
            showCanPlace(tiles.get(0));

            // setting number of tiles left
            tilesLeftText.setText("Tiles Left: " + tiles.size());
        }
    }

    private void openSavedGame(){

        // if the user selected any expansions they will be set true or false
        IC = savedGame.isIC();
        G = savedGame.isG();

        Variables.setInnCathedral(savedGame.isIC());
        Variables.setGoldmines(savedGame.isG());

        for(int i = 0; i < savedGame.getCols(); i ++){
            addColBack();
        }

        if(savedGame.getRows() >= 8){
            for(int i = 0; i < savedGame.getRows() - 8; i ++){
                addRowBack();
            }
        }

        for(int i = 0; i < savedGame.getPlayedTiles().size(); i++){
            SaveTile tile = savedGame.getPlayedTiles().get(i);
            ICTile ICtempTile = null;

            if(IC){
                for(int j = 0; j < savedGame.getICTiles().size(); j ++){
                    if(savedGame.getICTiles().get(j).getCode() == tile.getCode()){
                        ICtempTile = new ICTile(tile.getSides(), tile.getFields(), tile.isChurch(), tile.isShield(), tile.getShieldProjectID(), tile.getImageLoc().substring(8), tile.getNumProjects(), tile.getCode());

                        if(savedGame.getICTiles().get(j).isCathedrale()){
                            ICtempTile.addCathedral();
                        }

                        if(savedGame.getICTiles().get(j).isInn()){
                            ICtempTile.addInn();
                        }

                        for(int k = 0; k < savedGame.getICTiles().get(j).getICprojects().size(); k ++) {
                            SaveICProject ICP = savedGame.getICTiles().get(j).getICprojects().get(k);
                            SaveProject P = tile.getProjects().get(k);
                            if (ICP.isInn()) {
                                ICtempTile.addICProject(P.getType(), P.getID(), P.getMeepleX(), P.getMeepleY(), P.isShield(), "i");
                            } else if (ICP.isCathedrale()){
                                ICtempTile.addICProject(P.getType(), P.getID(), P.getMeepleX(), P.getMeepleY(), P.isShield(), "c");
                            }
                        }
                        ICTiles.add(ICtempTile);
                    }
                }
            }

            GTile GtempTile = null;
            if(G){
                for(int j = 0; j < savedGame.getGTiles().size(); j ++) {
                    if (savedGame.getGTiles().get(j).getCode() == tile.getCode()) {
                        GtempTile = new GTile(tile.getSides(), tile.getFields(), tile.isChurch(), tile.isShield(), tile.getShieldProjectID(), tile.getImageLoc().substring(7), tile.getNumProjects(), tile.getCode(), savedGame.getGTiles().get(j).getXGold(), savedGame.getGTiles().get(j).getYGold());

                        if(savedGame.getGTiles().get(j).isHasGold()){
                            GtempTile.giveGold();
                        }
                        else{
                            GtempTile.takeGold();
                        }

                        GTiles.add(GtempTile);
                    }
                }
            }

            Tile tempTile;

            if(ICtempTile != null){
                tempTile = ICtempTile;
            }
            else if(GtempTile != null){
                tempTile = GtempTile;
            }
            else{
                tempTile = new Tile(tile.getSides(), tile.getFields(), tile.isChurch(), tile.isShield(), tile.getShieldProjectID(), tile.getImageLoc(), tile.getNumProjects(), tile.getCode());
            }
            tempTile.setRotation(tile.getRotation());

            playedTile.get(tile.getX()).set(tile.getY(), tempTile);
            playedTiles.add(tempTile);
            ImageView tileImage = new ImageView(tempTile.getImage());
            tileImage.setFitWidth(80);
            tileImage.setFitHeight(80);
            tileImage.setRotate(tempTile.getRotation());

            grid.get(tile.getX()).get(tile.getY()).getChildren().add(tileImage);

            for(int j = 0; j < tempTile.getNumProjects(); j ++){
                SaveProject project = tile.getProjects().get(j);
                tempTile.addProject(project.getType(), project.getID(), project.getMeepleX(), project.getMeepleY(), project.isShield());

                if(project.getMeeple() != null){
                    SaveMeeple meeple = project.getMeeple();
                    Meeple newMeeple = new Meeple(meeple.getX(), meeple.getY(), meeple.getXgrid(), meeple.getYgird(), meeple.getColour(), meeple.isBig());
                    newMeeple.setProject(tempTile.getProjects().get(j));
                    tempTile.getProjects().get(j).setMeeple(newMeeple);
                    placedMeeples.add(newMeeple);

                    selectedProject = tempTile.getProjects().get(j);

                    placeMeeple(tile.getX(), tile.getY(), tempTile, newMeeple, tile.getRotation());
                }
                else{
                    int rotation = tile.getRotation();
                    // checking if the user is playing with the golmines expansion and if the current tile is part of that expansion
                    if(G && GTiles.contains(tempTile)){
                        // if so, the variable gTile si created which will become the current tile but then as a gTile instead of a regular TIle
                        GTile gTile = null;
                        // loop through all the tiles part of the goldmines expansion
                        for(int k = 0; k < GTiles.size(); k ++){
                            // checking if the current tile is the current tile from the goldmines expansion
                            if(tempTile == GTiles.get(k)){
                                // if so, we set gTile equal to the that tiles because it is part of the GTile class
                                gTile = GTiles.get(k);
                            }
                        }
                        if(gTile.isHasGold()){
                            // creating a HBox for the gold ingot image
                            HBox goldBox = new HBox();
                            // setting the height to fit perfectly in a tile box
                            goldBox.setPrefWidth(80);
                            goldBox.setPrefHeight(80);

                            // creating a pane that will act as the x-axis (pusing the image to the right)
                            Pane width2 = new Pane();

                            // creating the image view of the gold ingot
                            ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
                            // setting the size
                            goldImage.setFitHeight(8);
                            goldImage.setFitWidth(12);

                            // finding the rotation of the tile
                            if (rotation == 0) {
                                // it isn't rotated
                                // set the width equal the gold X posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                                width2.setPrefSize(gTile.getXGold(), 80 - gTile.getYGold());
                                // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                                goldBox.setPadding(new Insets(gTile.getYGold(), 0, 0, 0));
                            }
                            else if (rotation == 90) {
                                // the tile is rotated (X and Y set swapped & Y is inversed)
                                // set the width equal the gold Y posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                                width2.setPrefSize(80 - gTile.getYGold() - 8, 80 - gTile.getXGold());
                                // setting the padding at the top of the HBox to the X posistion of the gold in order to "push" the image view down
                                goldBox.setPadding(new Insets(gTile.getXGold(), 0, 0, 0));
                            }
                            else if (rotation == 180) {
                                // the tile is rotated (X and Y set are inversed)
                                // set the width equal the gold X posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                                width2.setPrefSize(80 - gTile.getXGold() - 12, gTile.getYGold() - 8);
                                // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                                goldBox.setPadding(new Insets(80 - gTile.getYGold() - 8, 0, 0, 0));
                            }
                            else{
                                // the tile is rotated (X and Y set swapped & X is inversed)
                                // set the width equal the gold Y posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                                width2.setPrefSize(gTile.getYGold(), gTile.getXGold() - 12);
                                // setting the padding at the top of the HBox to the X posistion but inversed of the gold in order to "push" the image view down
                                goldBox.setPadding(new Insets(80 - gTile.getXGold() - 12, 0, 0, 0));
                            }
                            // add the width and the gold image to the HBox
                            goldBox.getChildren().addAll(width2, goldImage);

                            // save the gold box in the tile
                            gTile.setGoldBox(goldBox);

                            // the box where th user wants to place their meeple is retrived from the grid
                            HBox box = grid.get(tile.getX()).get(tile.getY());

                            // a StackPane is made because a meeple will now have to be place above the tile
                            StackPane addBox = new StackPane();
                            // the StackPane is set to the size of the box
                            addBox.setPrefWidth(80);
                            addBox.setPrefHeight(80);

                            // a temporary node is created to store the imageview of the tile
                            Node temp = box.getChildren().get(0);
                            // the box is cleared
                            box.getChildren().clear();
                            // the imageView of the tile is added to the StackPane
                            box.getChildren().add(addBox);
                            addBox.getChildren().add(temp);

                            // save the HBox of the grid in the tiles information so the place can be easily traced down
                            gTile.setGoldBoxParent(addBox);

                            // add the HBox with the image of the gold ingot to the stackpane which already has the image of the tiles
                            addBox.getChildren().add(goldBox);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < savedGame.getTiles().size(); i++){
            SaveTile tile = savedGame.getTiles().get(i);
            ICTile ICtempTile = null;

            if(IC){
                for(int j = 0; j < savedGame.getICTiles().size(); j ++){
                    if(savedGame.getICTiles().get(j).getCode() == tile.getCode()){
                        ICtempTile = new ICTile(tile.getSides(), tile.getFields(), tile.isChurch(), tile.isShield(), tile.getShieldProjectID(), tile.getImageLoc().substring(8), tile.getNumProjects(), tile.getCode());

                        if(savedGame.getICTiles().get(j).isCathedrale()){
                            ICtempTile.addCathedral();
                        }

                        if(savedGame.getICTiles().get(j).isInn()){
                            ICtempTile.addInn();
                        }

                        for(int k = 0; k < savedGame.getICTiles().get(j).getICprojects().size(); k ++) {
                            SaveICProject ICP = savedGame.getICTiles().get(j).getICprojects().get(k);
                            SaveProject P = tile.getProjects().get(k);
                            if (ICP.isInn()) {
                                ICtempTile.addICProject(P.getType(), P.getID(), P.getMeepleX(), P.getMeepleY(), P.isShield(), "i");
                            } else if (ICP.isCathedrale()){
                                ICtempTile.addICProject(P.getType(), P.getID(), P.getMeepleX(), P.getMeepleY(), P.isShield(), "c");
                            }
                        }

                        ICTiles.add(ICtempTile);
                    }
                }
            }

            GTile GtempTile = null;
            if(G){
                for(int j = 0; j < savedGame.getGTiles().size(); j ++) {
                    if (savedGame.getGTiles().get(j).getCode() == tile.getCode()) {
                        GtempTile = new GTile(tile.getSides(), tile.getFields(), tile.isChurch(), tile.isShield(), tile.getShieldProjectID(), tile.getImageLoc().substring(7), tile.getNumProjects(), tile.getCode(), savedGame.getGTiles().get(j).getXGold(), savedGame.getGTiles().get(j).getYGold());
                        if(savedGame.getGTiles().get(j).isHasGold()){
                            GtempTile.giveGold();
                        }
                        else{
                            GtempTile.takeGold();
                        }

                        GTiles.add(GtempTile);
                    }
                }
            }

            Tile tempTile;

            if(ICtempTile != null){
                tempTile = ICtempTile;
            }
            else if(GtempTile != null){
                tempTile = GtempTile;
            }
            else{
                tempTile = new Tile(tile.getSides(), tile.getFields(), tile.isChurch(), tile.isShield(), tile.getShieldProjectID(), tile.getImageLoc(), tile.getNumProjects(), tile.getCode());
            }
            tiles.add(tempTile);

            for(int j = 0; j < tempTile.getNumProjects(); j ++){
                SaveProject project = tile.getProjects().get(j);
                tempTile.addProject(project.getType(), project.getID(), project.getMeepleX(), project.getMeepleY(), project.isShield());
            }
        }

        // if they didn't select the IC expansion, the meeple selection can be deleted
        if (!IC) {
            ICMeepleSelection.getChildren().clear();
            ICMeepleSelection.setPrefHeight(0);
        }
        // if they did select the IC expansion the meeple selection can be hidden for now (not deleted)
        else {
            ICMeepleSelection.setOpacity(0);
            ICMeepleSelection.setDisable(true);
        }
        // if they didn't select the G expansion the gold poitns button can be deleted
        if (!G) {
            goldPointsButton.setOpacity(0);
            goldPointsButton.setDisable(true);
        }

        numPlayers = savedGame.getNumPlayers();
        Variables.setNumPlayers(savedGame.getNumPlayers());

        ArrayList<Player> players = new ArrayList<>();
        for(int j = 0; j < savedGame.getPlayers().size(); j ++){
            for(int i = 0; i < Variables.getPlayers().size(); i ++){
                SavePlayer savePlayer = savedGame.getPlayers().get(j);
                if(Variables.getPlayers().get(i).getColour().equals(savePlayer.getColour())){
                    Variables.getPlayers().get(i).setName(savePlayer.getName());
                    Variables.getPlayers().get(i).setPoints(savePlayer.getPoints());
                    Variables.getPlayers().get(i).setNumMeeples(savePlayer.getNumMeeples());
                    Variables.getPlayers().get(i).setNumGold(savePlayer.getNumGold());
                    Variables.getPlayers().get(i).setHasBigMeeple(savePlayer.isHasBigMeeple());

                    players.add(Variables.getPlayers().get(i));
                }
            }
        }
        Variables.setPlayers(players);


        for(int i = 0; i < Variables.getNumPlayers(); i ++){
            Variables.getPlayers().get(i).getTurnName().setText(Variables.getPlayers().get(i).getName());
            Variables.getPlayers().get(i).getTurnPoints().setText(String.valueOf(Variables.getPlayers().get(i).getPoints()));

            Variables.getPlayers().get(i).getWaitingName().setText(Variables.getPlayers().get(i).getName());
            Variables.getPlayers().get(i).getWaitingPoints().setText(String.valueOf(Variables.getPlayers().get(i).getPoints()));

            for(int j = 0; j < 6 - Variables.getPlayers().get(i).getNumMeeples(); j++){
                Variables.getPlayers().get(i).getWaitingMeeplesBox().getChildren().remove(0);
            }

            if(Variables.getPlayers().get(i).getNumMeeples() == 3){
                Variables.getPlayers().get(i).getTurnMeeplesBox2().getChildren().clear();
            }
            else if(Variables.getPlayers().get(i).getNumMeeples() < 3){
                Variables.getPlayers().get(i).getTurnMeeplesBox2().getChildren().clear();
                for(int j = 0; j < 3 - Variables.getPlayers().get(i).getNumMeeples(); j++){
                    Variables.getPlayers().get(i).getTurnMeeplesBox1().getChildren().remove(0);
                }
            }
            else{
                for(int j = 0; j < 6 - Variables.getPlayers().get(i).getNumMeeples(); j++){
                    Variables.getPlayers().get(i).getTurnMeeplesBox2().getChildren().remove(0);
                }
            }
        }

        turnPlayerBox.getChildren().add(Variables.getPlayers().get(0).getTurnBox());

        for(int i = 1; i < Variables.getNumPlayers(); i ++){
            playersBox.getChildren().add(Variables.getPlayers().get(i).getWaitingBox());
        }

        if(IC){
            for(int i = 0; i < Variables.getNumPlayers(); i ++){
                Variables.getPlayers().get(i).giveBigMeeple();
            }
        }
        else{
            for(int i = 0; i < Variables.getNumPlayers(); i ++){
                Variables.getPlayers().get(i).getBigMeepleWaitPreview().setOpacity(0);
                Variables.getPlayers().get(i).getBigMeepleTurnPreview().setOpacity(0);
            }
        }

        if(!G){
            for(int i = 0; i < Variables.getNumPlayers(); i ++){
                Variables.getPlayers().get(i).getGoldBoxTurn().setOpacity(0);
                Variables.getPlayers().get(i).getGoldBoxWaiting().setOpacity(0);
            }
        }

        previewTile.setImage(tiles.get(0).getImage());

        if (G && GTiles.contains(tiles.get(0))) {
            // a temporary value set to the gTile (this should be the current tile)
            GTile gTile = null;
            // looping through all the g expansion tiles
            for (int i = 0; i < GTiles.size(); i++) {
                // if this is the current tile we set it to the gitle
                if (tiles.get(0) == GTiles.get(i)) {
                    gTile = GTiles.get(i);
                }
            }
            // creating a pane and hbox inorder to place the gold bar on the tile
            Pane width2 = new Pane();
            HBox ph2 = new HBox();

            // obtaining a image view and image for the gold bar
            ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
            // setting of the goldbar to the size wanted
            goldImage.setFitHeight(15);
            goldImage.setFitWidth(24);

            // setting the size of the pane to the X coordinate of the goldbar
            // the width is a place holder which should push the gold bar image view so many pixels to the right
            width2.setPrefSize(gTile.getXGold() * 2, 160 - gTile.getYGold());
            // the y coordinates are set as padding in a HBox pushing the gold bar down
            ph2.setPadding(new Insets(gTile.getYGold() * 2, 0, 0, 0));

            // putting the imageview and width pane in the HBox
            ph2.getChildren().addAll(width2, goldImage);
            // adding the HBox of the preview stackPane which is what the user sees (the width and ph2 shoudl be invisible)
            preview.getChildren().add(ph2);
        }

        for(int i = 0; i < playedTile.size(); i ++){
            for(int j = 0; j < playedTile.get(i).size(); j++){
                Tile tile = playedTile.get(i).get(j);

                if(tile != null) {
                    for (int k = 0; k < tile.getSides().length; k++) {
                        String side = tile.getSides()[k];
                        if (side.charAt(0) == 'C') {
                            ArrayList<Tile> checkedTiles = new ArrayList<>();
                            projectsInProject.clear();
                            ArrayList<Tile> city = checkFinishProject(tile, i, j, side, checkedTiles);

                            if (!(city.contains(null))) {
                                completedCities.add(projectsInProject);
                            }
                        }
                    }
                }
            }
        }

        // calling a function which will show where the tile currently being placed could be placed
        showCanPlace(tiles.get(0));

        // setting number of tiles left
        tilesLeftText.setText("Tiles Left: " + tiles.size());
    }

    private void placeMeeple(int colIndex, int rowIndex, Tile tile, Meeple meeple, int rotation){

        // checking wether the user selected a project and wether the user has enough meeples to play or has a big meeple to play and if they selected a meeple type
        if (selectedProject != null) {
            // if all is true, the selected option is reset to empty
            selectionOption.getChildren().clear();

            // the box where th user wants to place their meeple is retrived from the grid
            HBox box = grid.get(colIndex).get(rowIndex);

            // a StackPane is made because a meeple will now have to be place above the tile
            StackPane addBox = new StackPane();
            // the StackPane is set to the size of the box
            addBox.setPrefWidth(80);
            addBox.setPrefHeight(80);

            // a temporary node is created to store the imageview of the tile
            Node temp = box.getChildren().get(0);
            // the box is cleared
            box.getChildren().clear();
            // the imageView of the tile is added to the StackPane
            box.getChildren().add(addBox);

            // a meeple box where the meepple imageview will be placed in is created
            HBox meepleBox = new HBox();
            // the meeple box is set to the size of the box
            meepleBox.setPrefWidth(80);
            meepleBox.setPrefHeight(80);

            // a pane is created that will push the meeple imageview to the side (acting as a x axis)
            Pane width = new Pane();

            // the meeple is added to the placed meeple
            placedMeeples.add(meeple);

            // finding the rotation of the tile
            if (rotation == 0) {
                // it isn't rotated
                // set the width equal the gold X posision  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(meeple.getX(), 80 - meeple.getY());
                // setting the padding at the top of the HBox to the Y psosion of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(meeple.getY(), 0, 0, 0));
            }
            else if (rotation == 90) {
                // the tile is rotated (X and Y set swapped & Y is inversed)
                // set the width equal the gold Y posision but inveresed  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(80 - meeple.getY() - 12, 80 - meeple.getX());
                // setting the padding at the top of the HBox to the X psosion of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(meeple.getX(), 0, 0, 0));
            }
            else if (rotation == 180) {
                // the tile is rotated (X and Y set are inversed)
                // set the width equal the gold X posision but inveresed  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(80 - meeple.getX() - 12, meeple.getY() - 12);
                // setting the padding at the top of the HBox to the Y psosion of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(80 - meeple.getY() - 12, 0, 0, 0));
            }
            else{
                // the tile is rotated (X and Y set swapped & X is inversed)
                // set the width equal the gold Y posision  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(meeple.getY(), meeple.getX()  - 12);
                // setting the padding at the top of the HBox to the X psosion but inversed of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(80 - meeple.getX() - 12, 0, 0, 0));
            }
            // add the meeple imageView aand the width pane to the meeple box
            meepleBox.getChildren().addAll(width, meeple.getDisplay());

            // adding the meeple box the box
            addBox.getChildren().addAll(temp, meepleBox);

            // checking if the user is playing with the golmines expansion and if the current tile is part of that expansion
            if(G && GTiles.contains(tile)){
                // if so, the variable gTile si created which will become the current tile but then as a gTile instead of a regular TIle
                GTile gTile = null;
                // loop through all the tiles part of the goldmines expansion
                for(int i = 0; i < GTiles.size(); i ++){
                    // checking if the current tile is the current tile from the goldmines expansion
                    if(tile == GTiles.get(i)){
                        // if so, we set gTile equal to the that tiles because it is part of the GTile class
                        gTile = GTiles.get(i);
                    }
                }

                // creating a HBox for the gold ingot image
                HBox goldBox = new HBox();
                // setting the height to fit perfectly in a tile box
                goldBox.setPrefWidth(80);
                goldBox.setPrefHeight(80);

                // creating a pane that will act as the x-axis (pusing the image to the right)
                Pane width2 = new Pane();

                // creating the image view of the gold ingot
                ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
                // setting the size
                goldImage.setFitHeight(8);
                goldImage.setFitWidth(12);

                // finding the rotation of the tile
                if (rotation == 0) {
                    // it isn't rotated
                    // set the width equal the gold X posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(gTile.getXGold(), 80 - gTile.getYGold());
                    // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(gTile.getYGold(), 0, 0, 0));
                }
                else if (rotation == 90) {
                    // the tile is rotated (X and Y set swapped & Y is inversed)
                    // set the width equal the gold Y posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(80 - gTile.getYGold() - 8, 80 - gTile.getXGold());
                    // setting the padding at the top of the HBox to the X posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(gTile.getXGold(), 0, 0, 0));
                }
                else if (rotation == 180) {
                    // the tile is rotated (X and Y set are inversed)
                    // set the width equal the gold X posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(80 - gTile.getXGold() - 12, gTile.getYGold() - 8);
                    // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(80 - gTile.getYGold() - 8, 0, 0, 0));
                }
                else{
                    // the tile is rotated (X and Y set swapped & X is inversed)
                    // set the width equal the gold Y posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(gTile.getYGold(), gTile.getXGold() - 12);
                    // setting the padding at the top of the HBox to the X posistion but inversed of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(80 - gTile.getXGold() - 12, 0, 0, 0));
                }
                // add the width and the gold image to the HBox
                goldBox.getChildren().addAll(width2, goldImage);

                // save the gold box in the tile
                gTile.setGoldBox(goldBox);
                // save the HBox of the grid in the tiles information so the place can be easily traced down
                gTile.setGoldBoxParent(addBox);

                // add the HBox with the image of the gold ingot to the stackpane which already has the image of the tiles
                addBox.getChildren().add(goldBox);

            }

            // create a temporary node to save the image of the tile in the preview box
            Node temp2 = preview.getChildren().get(0);
            // clear the preview box and get rid of anything in it
            preview.getChildren().clear();
            // add back the image of the tile
            preview.getChildren().add(temp2);
        }
    }

    /**
     * <p>
     *     It adds a column to the right side of the board, the number of box's it will
     *     add depends on the number of rows. It updates both the visual board for the user
     *     as well as the boards which are used to read the data for example the tilePlayed
     *     variable. It should only be run at the start to create the board or when the
     *     tiles reach the right side of the board.
     * </p>
     */
    public void addColBack(){
        // figuring out the number of rows using the height of the HBox in the scrollpane
        int numRow = (int) (board.getHeight() / 80);
        // creating the new VBox for the colomum
        VBox newCol = new VBox();
        // setting the width to 80 like all other colomuns
        newCol.setMinWidth(80);

        // creating a new arraylist of HBox (a HBox for every row)
        ArrayList<HBox> gridCol = new ArrayList<>();

        // creating a new arraylist which represents a colume in the playedTile
        ArrayList<Tile> tileCol = new ArrayList<>();

        // looping through the number of rows
        for(int i = 0; i < numRow; i++){
            // creating a new HBox or cell of the grid
            HBox row = new HBox();
            // setting the width and height to 80 like all other cells
            row.setMinWidth(80);
            row.setPrefHeight(80);

            // changing colour of HBox when the mouse is on so that the user can see which one they are clicking on
            row.setOnMouseEntered(event -> {
                // when the background colour is null and the mouse enters we want to change the colour to dark blue
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                // when the background colour is yellow and the mouse enters we want to change the colour to a slightly differnt dark blue
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                // if it is a null when the mouse exits it becomes null again
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                // if the colour is the slightly different dark blue than the usual we change it back to yellow
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
                // otherwise it is the normal dark blue and we set it back to null when the mouse exits the HBox
                else{
                    row.setBackground(null);
                }
            });
            // running place tile once the HBox is clicked on
            row.setOnMouseClicked(event -> placeTile(row));

            // adding the HBox to the new colomun
            newCol.getChildren().add(row);
            // adding the Hbox in the grid / arraylist
            gridCol.add(row);
            // adding a placeholder in the tileCol so once a tile has been placed there it can be changed
            tileCol.add(null);
        }

        // setting the board width so that there is room for the new colomun
        board.setPrefWidth(board.getWidth() + 80);
        // resizing the board so that there is room for the new colomun
        board.resize(board.getWidth() + 80, board.getHeight());
        // adding the new colomun to the board
        board.getChildren().add(newCol);

        // adding the coloum to the list of colomuns
        cols.add(newCol);
        // adding the list of HBox as a
        grid.add(gridCol);
        // adding the colume to the playedTile
        playedTile.add(tileCol);
    }

    /**
     * <p>
     *     It adds a column to the left side of the board, the number of box's it will
     *     add depends on the number of rows. It updates both the visual board for the user
     *     as well as the boards which are used to read the data for example the tilePlayed
     *     variable. It should only be run when the tiles reach the left side of the board.
     * </p>
     */
    public void addColFront(){
        // figuring out the number of rows using the height of the HBox in the scrollpane
        int numRow = (int) (board.getHeight() / 80);
        // creating the new VBox for the colomum
        VBox newCol = new VBox();
        // setting the width to 80 like all other colomuns
        newCol.setMinWidth(80);

        // creating a new arraylist of HBox (a HBox for every row)
        ArrayList<HBox> gridCol = new ArrayList<>();

        // creating a new arraylist which represents a column in the playedTile
        ArrayList<Tile> tileCol = new ArrayList<>();

        // looping through the number of rows
        for(int i = 0; i < numRow; i++){
            // creating a new HBox or cell of the grid
            HBox row = new HBox();
            // setting the width and height to 80 like all other cells
            row.setMinWidth(80);
            row.setPrefHeight(80);

            // changing colour of HBox when the mouse is on so that the user can see which one they are clicking on
            row.setOnMouseEntered(event -> {
                // when the background colour is null and the mouse enters we want to change the colour to dark blue
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                // when the background colour is yellow and the mouse enters we want to change the colour to a slightly differnt dark blue
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                // if it is a null when the mouse exits it becomes null again
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                // if the colour is the slightly different dark blue than the usual we change it back to yellow
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
                // otherwise it is the normal dark blue and we set it back to null when the mouse exits the HBox
                else{
                    row.setBackground(null);
                }
            });
            // running place tile once the HBox is clicked on
            row.setOnMouseClicked(event -> placeTile(row));

            // adding the HBox to the new colomun
            newCol.getChildren().add(row);
            // adding the Hbox in the grid / arraylist
            gridCol.add(row);
            // adding null to the tile colomn as a palce holder because no tile has been placed yet
            tileCol.add(null);
        }

        // setting the board width so that there is room for the new colomun
        board.setPrefWidth(board.getWidth() + 80);
        // resizing the board so that there is room for the new colomun
        board.resize(board.getWidth() + 80, board.getHeight());
        // adding the new colomun to the board to the front
        board.getChildren().add(0, newCol);

        // adding the coloum to the list of colomuns at the front
        cols.add(0, newCol);
        // adding the list of HBox as a at the front
        grid.add(0, gridCol);
        // adding the colume to the playedTile
        playedTile.add(0, tileCol);
        // looping through the played meeples inorder to change the x coordinates of each
        for (int i = 0; i < placedMeeples.size(); i ++){
            // changing the x coordinates of the placed meeple by 1 because a colume was added
            placedMeeples.get(i).setXgrid(placedMeeples.get(i).getXgrid() + 1);
        }
    }

    /**
     * <p>
     *     It adds a row to the bottom of the board, the number of box's it will
     *     add depends on the number of columns. It updates both the visual board for the user
     *     as well as the boards which are used to read the data for example the tilePlayed
     *     variable. It should only be run when the tiles reach the bottom of the board.
     * </p>
     */
    public void addRowBack(){
        // finding the number of coloumns by useing the width of the HBox or board
        int numCol = (int) (board.getWidth() / 80);
        // adjusting the height fo the board so that there is room at the bottom for a row
        board.setPrefHeight(board.getHeight() + 80);
        // adjusting the height fo the board so that there is room at the bottom for a row
        board.resize(board.getWidth(), board.getHeight() + 80);

        // looping through the number of colomuns
        for(int i = 0; i < numCol; i++){
            // creating a new HBox or cell
            HBox row = new HBox();
            // setting the width and height to 80 like every other cell
            row.setPrefWidth(80);
            row.setPrefHeight(80);

            // changing colour of HBox when the mouse is on so that the user can see which one they are clicking on
            row.setOnMouseEntered(event -> {
                // when the background colour is null and the mouse enters we want to change the colour to dark blue
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                // when the background colour is yellow and the mouse enters we want to change the colour to a slightly differnt dark blue
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                // if it is a null when the mouse exits it becomes null again
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                // if the colour is the slightly different dark blue than the usual we change it back to yellow
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
                // otherwise it is the normal dark blue and we set it back to null when the mouse exits the HBox
                else{
                    row.setBackground(null);
                }
            });
            // running place tile once the HBox is clicked on
            row.setOnMouseClicked(event -> placeTile(row));

            // getting the current colomun
            VBox col = cols.get(i);
            // adding the HBox or cell to the colomun
            col.getChildren().add(row);

            // adding the HBox or cell to the grid
            grid.get(i).add(row);
            // adding a null to every colume in the played tile as a placeholder because no tiles have been placed there yet
            playedTile.get(i).add(null);
        }
    }

    /**
     * <p>
     *     It adds a row to the top of the board, the number of box's it will
     *     add depends on the number of columns. It updates both the visual board for the user
     *     as well as the boards which are used to read the data for example the tilePlayed
     *     variable. It should only be run when the tiles reach the top of the board.
     * </p>
     */
    public void addRowFront(){
        // finding the number of coloumns by useing the width of the HBox or board
        int numCol = (int) (board.getWidth() / 80);
        // adjusting the height fo the board so that there is room at the top for a row
        board.setPrefHeight(board.getHeight() + 80);
        // adjusting the height fo the board so that there is room at the top for a row
        board.resize(board.getWidth(), board.getHeight() + 80);

        // looping through the number of colomuns
        for(int i = 0; i < numCol; i++){
            // creating a new HBox or cell
            HBox row = new HBox();
            // setting the width and heigh to 80 like every other cell
            row.setPrefWidth(80);
            row.setPrefHeight(80);

            // changing colour of HBox when the mouse is on so that the user can see which one they are clicking on
            row.setOnMouseEntered(event -> {
                // when the background colour is null and the mouse enters we want to change the colour to dark blue
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                // when the background colour is yellow and the mouse enters we want to change the colour to a slightly differnt dark blue
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                // if it is a null when the mouse exits it becomes null again
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                // if the colour is the slightly different dark blue than the usual we change it back to yellow
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
                // otherwise it is the normal dark blue and we set it back to null when the mouse exits the HBox
                else{
                    row.setBackground(null);
                }
            });
            // running place tile once the HBox is clicked on
            row.setOnMouseClicked(event -> placeTile(row));

            // getting the current colomun
            VBox col = cols.get(i);
            // adding the HBox or cell to the colomun to the front
            col.getChildren().add(0, row);

            // adding the HBox or cell to the grid to the front
            grid.get(i).add(0, row);
            // adding a null to every colume in the played tile as a placeholder because no tiles have been placed there yet
            playedTile.get(i).add(0, null);
        }

        // looping through all played meeples to chaneg the y coordinates
        for (int j = 0; j < placedMeeples.size(); j ++){
            // increase the y coordinates by 1 of the meeple because they all shifted
            placedMeeples.get(j).setYgird(placedMeeples.get(j).getYgird() + 1);
        }
    }

    /**
     *<p>
     *     The method process the tiles data which is saved in a text file called
     *     "tiles.txt". It process this by turning each line into an array, each
     *     index corresponds to a different instance variable of the Tile class.
     *</p>
     * <p>
     *     The methode does not create the tiles, all it does is read the file and
     *     save the information in a way that can be read by the program.
     * </p>
     *
     * @param data  this is an arraylist of string arrays, each
     *              string array is one line of the text file.
     * @return      An ArrayList of string arrays, called proData which
     *              is the processed data. Each index in the ArrayList
     *              is one tile, each index in the string arrays
     *              correspond to an instance variable of the
     *              Tile class
     */
    private ArrayList<String[][]> processData(ArrayList<String[]> data){
        // creating an arraylist which will contain the processed data
        ArrayList<String[][]> proData = new ArrayList<>();

        // looping through the data
        for(int i = 0; i < data.size(); i++){
            //  setting up the data for a tile
            String[][] tile = new String[((data.get(i).length-16)/4)+6][];

            // setting up new / processed  sides for the tile
            String[] sides = new String [4];
            // looping through all the value in sides of the data
            for(int j = 0; j < sides.length; j++) {
                // setting each value in the processed sides to the corresponding value in the raw data
                sides[j] = data.get(i)[j];
            }
            // putting in the array of sides in te first place of the data for this tile
            tile[0] = sides;

            // setting up new / processed fields for the tile
            String[] fields = new String [8];
            // looping through all the values of the fields, starting from where sides ended
            for(int j = 4; j < fields.length + 4; j++){
                // setting each value in the processed sides to the corresponding value in the raw data (shift of 4 since we left off of 4 in the sides)
                fields[j - 4] = data.get(i)[j];
            }
            // putting the array of fields in the second place of the data for this tile
            tile[1] = fields;

            // creating a new array with the data of the church
            String[] church = {data.get(i)[12]};
            // putting the array of church in the third place of the data for this tile
            tile[2] = church;

            // creating a new shield with the data of the shield
            String[] shield = {data.get(i)[13]};
            // putting the array of shield in the fourth place of the data for this tile
            tile[3] = shield;

            // creating a new location with the data of the file name
            String[] loc = {data.get(i)[14]};
            // putting the array of location in the fifth place of the data for this tile
            tile[4] = loc;
            // obtaining the nubmber of projects from the data
            int numProject = Integer.parseInt(data.get(i)[15]);
            // converting the numver of projects to the string array
            String[] numProject2 = {String.valueOf(numProject)};
            // putting the numPorject array in the 6th place fo the tile data array
            tile[5] = numProject2;
            // looping through the remaining data (the data containing the data for each individual project of the tile)
            for(int j = 16; j < (numProject * 4) + 16; j += 4){
                // obtaining the porject name, project ID, meeple posistion (x and y coordinates) of the porject
                String projectName = data.get(i)[j];
                String projectID = data.get(i)[j+1];
                String projectMeepleX = data.get(i)[j+2];
                String projectMeepleY = data.get(i)[j+3];

                // putting all the data of the porject in a string array
                String[] projects = {projectName, projectID, projectMeepleX, projectMeepleY};
                // putting this data at the end of the array of tile data
                tile[(j-16)/4 + 6] = projects;
            }

            // adding the completed tile to the processed data
            proData.add(tile);
        }

        return proData;

    }

    /**
     *<p>
     *     The method process the inns and cathedral expansion tiles data which
     *     is saved in a text file called "tilesIC.txt". It process this by turning
     *     each line into an array, each index corresponds to a different instance
     *     variable of the ICTile class.
     *</p>
     * <p>
     *     The methode does not create the tiles, all it does is read the file and
     *     save the information in a way that can be read by the program.
     * </p>
     *
     * @param data  this is an arraylist of string arrays, each
     *              string array is one line of the text file.
     * @return      An ArrayList of string arrays, called proData which
     *              is the processed data. Each index in the ArrayList
     *              is one tile, each index in the string arrays
     *              correspond to an instance variable of the
     *              ICTile class
     */
    private ArrayList<String[][]> processICData(ArrayList<String[]> data){
        // creating an arraylist which will contain the processed data
        ArrayList<String[][]> proData = new ArrayList<>();

        // looping through the data
        for(int i = 0; i < data.size(); i++){
            //  setting up the data for a tile
            String[][] tile = new String[((data.get(i).length-18)/5)+8][];

            // setting up new / processed  sides for the tile
            String[] sides = new String [4];
            // looping through all the value in sides of the data
            for(int j = 0; j < sides.length; j++) {
                // setting each value in the processed sides to the corresponding value in the raw data
                sides[j] = data.get(i)[j];
            }
            // putting in the array of sides in te first place of the data for this tile
            tile[0] = sides;

            // setting up new / processed fields for the tile
            String[] fields = new String [8];
            // looping through all the values of the fields, starting from where sides ended
            for(int j = 4; j < fields.length + 4; j++){
                // setting each value in the processed sides to the corresponding value in the raw data (shift of 4 since we left off of 4 in the sides)
                fields[j - 4] = data.get(i)[j];
            }
            // putting the array of fields in the second place of the data for this tile
            tile[1] = fields;

            // creating a new array with the data of the church
            String[] church = {data.get(i)[12]};
            // putting the array of church in the third place of the data for this tile
            tile[2] = church;

            // creating a new array with the data of the shield
            String[] shield = {data.get(i)[13]};
            // putting the array of shield in the fourth place of the data for this tile
            tile[3] = shield;

            // creating a new array with the data of the cathedrale
            String[] chathedrale = {data.get(i)[14]};
            // putting the array of cathedrale in the fifth place of the data for this tile
            tile[4] = chathedrale;

            // creating a new array with the data of the inn
            String[] inn = {data.get(i)[15]};
            // putting the array of inn in the sixth place of the data for this tile
            tile[5] = inn;

            // creating a new location with the data of the file name
            String[] loc = {data.get(i)[16]};
            // putting the array of location in the fifth place of the data for this tile
            tile[6] = loc;
            // obtaining the nubmber of projects from the data
            int numProject = Integer.parseInt(data.get(i)[17]);
            // converting the numver of projects to the string array
            String[] numProject2 = {String.valueOf(numProject)};
            // putting the numPorject array in the 6th place fo the tile data array
            tile[7] = numProject2;
            // looping through the remaining data (the data containing the data for each individual project of the tile)
            for(int j = 18; j < (numProject * 5) + 18; j += 5){
                // obtaining the porject name, project ID, meeple posistion (x and y coordinates) of the porject
                String projectName = data.get(i)[j];
                String projectID = data.get(i)[j+1];
                String projectMeepleX = data.get(i)[j+2];
                String projectMeepleY = data.get(i)[j+3];
                String innOrCath = data.get(i)[j+4];

                // putting all the data of the porject in a string array
                String[] projects = {projectName, projectID, projectMeepleX, projectMeepleY, innOrCath};
                // putting this data at the end of the array of tile data
                tile[(j-18)/5 + 8] = projects;
            }

            // adding the completed tile to the processed data
            proData.add(tile);
        }

        return proData;
    }

    /**
     *<p>
     *     The method process the goldmines expansion tiles data which is saved
     *     in a text file called "tilesG.txt". It process this by turning each
     *     line into an array, each index corresponds to a different instance
     *     variable of the GTile class.
     *</p>
     * <p>
     *     The methode does not create the tiles, all it does is read the file and
     *     save the information in a way that can be read by the program.
     * </p>
     *
     * @param data  this is an arraylist of string arrays, each
     *              string array is one line of the text file.
     * @return      An ArrayList of string arrays, called proData which
     *              is the processed data. Each index in the ArrayList
     *              is one tile, each index in the string arrays
     *              correspond to an instance variable of the
     *              GTile class
     */
    private ArrayList<String[][]> processGData(ArrayList<String[]> data){
        // creating an arraylist which will contain the processed data
        ArrayList<String[][]> proData = new ArrayList<>();

        // looping through the data
        for(int i = 0; i < data.size(); i++){
            //  setting up the data for a tile
            String[][] tile = new String[((data.get(i).length-18)/4)+8][];

            // setting up new / processed  sides for the tile
            String[] sides = new String [4];
            // looping through all the value in sides of the data
            for(int j = 0; j < sides.length; j++) {
                // setting each value in the processed sides to the corresponding value in the raw data
                sides[j] = data.get(i)[j];
            }
            // putting in the array of sides in te first place of the data for this tile
            tile[0] = sides;

            // setting up new / processed fields for the tile
            String[] fields = new String [8];
            // looping through all the values of the fields, starting from where sides ended
            for(int j = 4; j < fields.length + 4; j++){
                // setting each value in the processed sides to the corresponding value in the raw data (shift of 4 since we left off of 4 in the sides)
                fields[j - 4] = data.get(i)[j];
            }
            // putting the array of fields in the second place of the data for this tile
            tile[1] = fields;

            // creating a new array with the data of the church
            String[] church = {data.get(i)[12]};
            // putting the array of church in the third place of the data for this tile
            tile[2] = church;

            // creating a new shield with the data of the shield
            String[] shield = {data.get(i)[13]};
            // putting the array of shield in the fourth place of the data for this tile
            tile[3] = shield;

            String[] XGold = {data.get(i)[14]};
            tile[4] = XGold;

            String[] YGold = {data.get(i)[15]};
            tile[5] = YGold;

            // creating a new location with the data of the file name
            String[] loc = {data.get(i)[16]};
            // putting the array of location in the fifth place of the data for this tile
            tile[6] = loc;
            // obtaining the nubmber of projects from the data
            int numProject = Integer.parseInt(data.get(i)[17]);
            // converting the numver of projects to the string array
            String[] numProject2 = {String.valueOf(numProject)};
            // putting the numPorject array in the 6th place fo the tile data array
            tile[7] = numProject2;
            // looping through the remaining data (the data containing the data for each individual project of the tile)
            for(int j = 18; j < (numProject * 4) + 18; j += 4){
                // obtaining the porject name, project ID, meeple posistion (x and y coordinates) of the porject
                String projectName = data.get(i)[j];
                String projectID = data.get(i)[j+1];
                String projectMeepleX = data.get(i)[j+2];
                String projectMeepleY = data.get(i)[j+3];

                // putting all the data of the porject in a string array
                String[] projects = {projectName, projectID, projectMeepleX, projectMeepleY};
                // putting this data at the end of the array of tile data
                tile[(j-18)/4 + 8] = projects;
            }

            // adding the completed tile to the processed data
            proData.add(tile);
        }

        return proData;

    }

    /**
     * <p>
     *     The method create all the tiles using the Tiles class, it uses
     *     the processed data to create all the tiles. It saves the tiles
     *     in the tiles arrayList so that they can be accessed throughout
     *     the program.
     * </p>
     *
     * @param data this is an ArrayList of string arrays with more array inside
     *             therefore a 3D array. It is the processed data with each string
     *             being one tile and each index of the string array being one
     *             instance variable of the Tile class.
     */
    private void createTiles(ArrayList<String[][]> data){
        // looping through all the tiles
        for(int i = 0; i < data.size(); i++){
            // setting the shield boolean to false
            boolean shield = false;
            // creating a shieldProjectID inorder to track which projects have a shield
            String shieldProjectID = null;
            // checking wether the tile has a shield (trueC1 is 6 characters)
            if(data.get(i)[3][0].length() == 6){
                //then setting the shield boolean to true and obtaining the porject ID
                shield = true;
                shieldProjectID = data.get(i)[3][0].substring(data.get(i)[3][0].length() -2);
            }
            // creating the tiles from the data
            Tile tile = new Tile(data.get(i)[0], data.get(i)[1], Boolean.parseBoolean(data.get(i)[2][0]), shield, shieldProjectID, data.get(i)[4][0], Integer.parseInt(data.get(i)[5][0]), uniqueCode);
            // looping through the porject data
            for(int j = 6; j < Integer.parseInt(data.get(i)[5][0]) + 6; j++){
                // checking wether this is the porject with the shield
                if(tile.isShield() && data.get(i)[j][0].charAt(0) == 'c' && tile.getShieldProjectID().charAt(1) == data.get(i)[j][0].charAt(data.get(i)[j][0].length() - 1)){
                    // if so we send it the boolean true for shield and also all the other project data to the tile so the project can be created and added to the tile
                    tile.addProject(data.get(i)[j][0], data.get(i)[j][1], Integer.parseInt(data.get(i)[j][2]), Integer.parseInt(data.get(i)[j][3]), true);
                }
                else{
                    // if not we send the boolean false for shield and also all the other project data to the tile so the project can be created and added to the tile
                    tile.addProject(data.get(i)[j][0], data.get(i)[j][1], Integer.parseInt(data.get(i)[j][2]), Integer.parseInt(data.get(i)[j][3]), false);
                }
            }
            // adding this to the array of tiles
            tiles.add(tile);
            // updating the unique code so it is different for each tile
            uniqueCode ++;
        }
    }

    /**
     * <p>
     *     The method create all the inns and cathedral expansion tiles
     *     using the ICTiles class, it uses the processed data to create
     *     all the tiles. It saves the tiles in the tiles arrayList so
     *     that they can be accessed throughout the program and also in
     *     the ICtiles arraylist so that they can be accessed as ICTiles.
     * </p>
     *
     * @param data this is an ArrayList of string arrays with more array inside
     *             therefore a 3D array. It is the processed data with each string
     *             being one tile and each index of the string array being one
     *             instance variable of the ICTile class.
     */
    private void createICTiles(ArrayList<String[][]> data){
        // looping through all the tiles
        for(int i = 0; i < data.size(); i++){
            // setting the shield boolean to false
            boolean shield = false;
            // creating a shieldProjectID inorder to track which projects have a shield
            String shieldProjectID = null;
            // checking wether the tile has a shield (trueC1 is 6 characters)
            if(data.get(i)[3][0].length() == 6){
                //then setting the shield boolean to true and obtaining the porject ID
                shield = true;
                shieldProjectID = data.get(i)[3][0].substring(data.get(i)[3][0].length() -2);
            }
            // creating the tiles from the data
            ICTile tile = new ICTile(data.get(i)[0], data.get(i)[1], Boolean.parseBoolean(data.get(i)[2][0]), shield, shieldProjectID, data.get(i)[6][0], Integer.parseInt(data.get(i)[7][0]), uniqueCode);
            // checking whether the tile has a catherdral
            if(Boolean.parseBoolean(data.get(i)[4][0])){
                // if it does we add a cathedral
                tile.addCathedral();
            }
            // checking whether the tile has a inn
            else if(Boolean.parseBoolean(data.get(i)[5][0])){
                // if it does we add a inn to the tile
                tile.addInn();
            }
            // looping through the porject data
            for(int j = 8; j < Integer.parseInt(data.get(i)[7][0]) + 8; j++){
                // checking wether this is the project with the shield
                if(tile.isShield() && data.get(i)[j][0].charAt(0) == 'c' && tile.getShieldProjectID().charAt(1) == data.get(i)[j][0].charAt(data.get(i)[j][0].length() - 1)){
                    // if so we send it the boolean true for shield and also all the other project data to the tile so the project can be created and added to the tile
                    tile.addICProject(data.get(i)[j][0], data.get(i)[j][2], Integer.parseInt(data.get(i)[j][3]), Integer.parseInt(data.get(i)[j][4]), true, data.get(i)[j][1]);
                }
                else{
                    // if not we send the boolean false for shield and also all the other project data to the tile so the project can be created and added to the tile
                    tile.addICProject(data.get(i)[j][0], data.get(i)[j][2], Integer.parseInt(data.get(i)[j][3]), Integer.parseInt(data.get(i)[j][4]), false, data.get(i)[j][1]);
                }
            }
            // adding the tile to the arrayList of IC expansion tiles
            ICTiles.add(tile);
            // adding this to the array of tiles
            tiles.add(tile);
            // updating the unque code so its different for every tile
            uniqueCode ++;
        }
    }

    /**
     * <p>
     *     The method create all the inns and cathedral expansion tiles
     *     using the GTiles class, it uses the processed data to create
     *     all the tiles. It saves the tiles in the tiles arrayList so
     *     that they can be accessed throughout the program and also in
     *     the Gtiles arraylist so that they can be accessed as GTiles.
     * </p>
     *
     * @param data this is an ArrayList of string arrays with more array inside
     *             therefore a 3D array. It is the processed data with each string
     *             being one tile and each index of the string array being one
     *             instance variable of the GTile class.
     */
    private void createGTiles(ArrayList<String[][]> data){
        // looping through all the tiles
        for(int i = 0; i < data.size(); i++){
            // setting the shield boolean to false
            boolean shield = false;
            // creating a shieldProjectID inorder to track which projects have a shield
            String shieldProjectID = null;
            // checking wether the tile has a shield (trueC1 is 6 characters)
            if(data.get(i)[3][0].length() == 6){
                //then setting the shield boolean to true and obtaining the porject ID
                shield = true;
                shieldProjectID = data.get(i)[3][0].substring(data.get(i)[3][0].length() -2);
            }
            // creating the tiles from the data
            GTile tile = new GTile(data.get(i)[0], data.get(i)[1], Boolean.parseBoolean(data.get(i)[2][0]), shield, shieldProjectID, data.get(i)[6][0], Integer.parseInt(data.get(i)[7][0]), uniqueCode, Integer.parseInt(data.get(i)[4][0]), Integer.parseInt(data.get(i)[5][0]));
            // looping through the porject data
            for(int j = 8; j < Integer.parseInt(data.get(i)[7][0]) + 8; j++){
                // checking wether this is the porject with the shield
                if(tile.isShield() && data.get(i)[j][0].charAt(0) == 'c' && tile.getShieldProjectID().charAt(1) == data.get(i)[j][0].charAt(data.get(i)[j][0].length() - 1)){
                    // if so we send it the boolean true for shield and also all the other project data to the tile so the project can be created and added to the tile
                    tile.addProject(data.get(i)[j][0], data.get(i)[j][1], Integer.parseInt(data.get(i)[j][2]), Integer.parseInt(data.get(i)[j][3]), true);
                }
                else{
                    // if not we send the boolean false for shield and also all the other project data to the tile so the project can be created and added to the tile
                    tile.addProject(data.get(i)[j][0], data.get(i)[j][1], Integer.parseInt(data.get(i)[j][2]), Integer.parseInt(data.get(i)[j][3]), false);
                }
            }
            // adding the tile to the G expansion tiles
            GTiles.add(tile);
            // adding this to the array of tiles
            tiles.add(tile);
            // updating the unique code so that it is different for all tiles
            uniqueCode ++;
        }
    }

    /**
     * <p>
     *     This function places a tile in the HBox that the user clicked
     *     on the board if the player is allowed. The player isn't allowed
     *     to place the tile there it wont dot anything. This function
     *     updates both the UI and the variables in the program.
     * </p>
     *
     * Use the {@link #checkPosistion(Tile, int, int)}              to check weather the tile is allowed to be
     *                                                              placed in this position
     * Use the {@link #chooseMeeplePosistion(Tile, HBox, int, int)} to allow the player to do their next part of
     *                                                              their turn, placing the meeple on the tile.
     *
     * @param place                                                 This is the HBox which the user clicked on
     *                                                              therefore this is the place in the grid and
     *                                                              the box where the tile needs to go.
     */
    private void placeTile(HBox place) {
        // checking weather the previous tile has been fully placed (placed and the player has gotten the chance to place a meeple)
        if(selectionOption.getChildren().size() == 0){
            // we get the top tile from the deck / tiles
            Tile tile = tiles.get(0);

            // obtaining the column index of the place which the player wants to place the tile
            // creating a temporary column index
            int colIndex = -1;
            // looping through all the columns
            for(int i = 0; i < cols.size(); i ++){
                // the place where the tile is being placed parent is the same as the column then that is the column which the tile is being placed
                if(place.getParent() == cols.get(i)){
                    // setting the column index equal
                    colIndex = i;
                }
            }

            // obtaining the row index of the place which the player wants to place the tile
            // creating a temporary row index
            int rowIndex = -1;
            // looping through all the rows
            for(int i = 0; i < grid.get(colIndex).size(); i ++){
                // the place where the tile is being placed parent is the same as the row then that is the row which the tile is being placed
                if (grid.get(colIndex).get(i) == place){
                    // setting the row index equal
                    rowIndex = i;
                }
            }

            // checking weather the place already has a tiles in it
            if (checkPosistion(tile, rowIndex, colIndex)) {
                // updating the played tile variable by setting the variable in the row and index colume where the player wants to place their tile equl to their tile
                playedTile.get(colIndex).set(rowIndex, tile);

                // if not create a new image view for the tile so it can be displayed
                ImageView tileImg = new ImageView();

                // set the image of the tile being places to the image view
                tileImg.setImage(tile.getImage());
                // resizing the image view so that it fits in the HBox perfectly
                tileImg.resize(80,80);
                // adjusting hieght and width so that it fits perfectly in the HBox
                tileImg.setFitHeight(80);
                tileImg.setFitWidth(80);
                // adjusting the rotation to the rotation of the preview tile / the rotation the user choose
                tileImg.setRotate(currentRotationPreviewTile);

                // adding the image view to the HBox
                place.getChildren().add(tileImg);

                // add the tile to the played tiles
                playedTiles.add(tiles.get(0));

                // place meeple
                chooseMeeplePosistion(tiles.get(0), place, colIndex, rowIndex);
            }
        }
    }

    /**
     * <p>
     *
     * </p>
     *
     * Use the {@link #projectSelection(HBox, Project, Tile)}                   to change the project that is selected when
     *                                                                          the use clicks on that project button. This
     *                                                                          will change the colour to show the user and
     *                                                                          change the variable.
     * Use the {@link #checkFinishProject(Tile, int, int, String, ArrayList)}   to obtain all the tiles and projects that
     *                                                                          are part of a specific big project so that
     *                                                                          it can be checked whether there are any players
     *                                                                          already on the project
     * Use the {@link #getDeleteMeeples(ArrayList)}                             to get the meeples that are placed on the big
     *                                                                          project. This needs to return nothing to be able
     *                                                                          to place the meeple on this project.
     * Use the {@link #getTilesOfField(Tile, int, int, String, ArrayList)}      to obtain all the tiles and projects that
     *                                                                          are part of a specific field so that it can
     *                                                                          be checked whether there are any players
     *                                                                          already on the project.
     * Use the {@link #confirm(HBox, int, int, Tile)}                           in order to confirm the project that the user
     *                                                                          wants to place their meeple. The function runs
     *                                                                          once the user has selected a project
     * Use the {@link #clear(HBox, int, int, Tile)}
     *
     *
     * @param tile
     * @param place
     * @param colIndex
     * @param rowIndex
     */

    private void chooseMeeplePosistion(Tile tile, HBox place, int colIndex, int rowIndex){
        // obtaining all the projects of the tile that was placed
        ArrayList<Project> projects = tile.getProjects();

        // creating a place holder pane so that everything is clear and organized for the users
        Pane ph4 = new Pane();
        ph4.setPrefSize(198, 4);

        // creating a title or instruction for the user so they know what they have to do
        Text title = new Text("Select Project");
        // updating the style of the text to fit the rest of the style
        title.setFont(Font.font("Ubuntu", FontWeight.BOLD, 18));
        title.setFill(Paint.valueOf("#FFB100"));

        // creating a place holder pane so that everything is clear and organized for the users
        Pane ph5 = new Pane();
        ph5.setPrefSize(198, 10);

        // add this to the selection option box so that it is visible to the user
        selectionOption.getChildren().addAll(ph4, title, ph5);

        // setting up the default image location so that they can more easily accessed
        String imageLoc = "file:./src/main/resources/com/example/demo/images/";

        // looping through all the projects of the tile
        for(int i = 0; i < tile.getNumProjects(); i++){
            // obtaining the project for easy reference later
            Project project = projects.get(i);

            // creating a HBox for the user to be able to select this project
            HBox projectDisplay = new HBox();
            // updating the event when the user clicks this box so that it is selected but also shown as selected (deselecting the other project, updating colour)
            projectDisplay.setOnMouseClicked(mouseEvent -> projectSelection(projectDisplay, project, tile));
            projectDisplay.setAlignment(Pos.CENTER_LEFT);

            // setting the background to the yellow used everywhere else as well (visual looks)
            projectDisplay.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(5), new Insets(1))));

            // creating a pane to act as a placeholder of buffer
            Pane ph = new Pane();
            // setting the preferred size
            ph.setPrefHeight(36);
            ph.setPrefWidth(12);

            // adding tho the project display
            projectDisplay.getChildren().add(ph);

            // creating a image view to show a icon of wether it's a city church field or road
            ImageView imageV = new ImageView();
            // making the image and text variables beforehand (the text is the name of the project)
            Image image;
            String text;

            // checking wether the project is a city
            if(project.getType().substring(0,project.getType().length() - 1).equals("city")){
                // if so we find the correspoding icon using the image location
                image = new Image(imageLoc + "fortress.png");
                // we set the text to say city so the user knows and a number to differ each city from each other
                text = "City " + project.getType().charAt(project.getType().length() - 1);
            }
            // checking wether the project is a road
            else if(project.getType().substring(0,project.getType().length() - 1).equals("road")){
                // if so we find the correspoding icon using the image location
                image = new Image(imageLoc + "signPost.png");
                // we set the text to say road so the user knows and a number to differ each road from each other
                text = "Road " + project.getType().charAt(project.getType().length() - 1);
            }
            // checking wether the project is a church
            else if(project.getType().substring(0,project.getType().length() - 1).equals("church")){
                // if so we find the correspoding icon using the image location
                image = new Image(imageLoc + "church.png");
                // we set the text to say chruch so the user knows and a number to differ each chruch from each other
                text = "Church " + project.getType().charAt(project.getType().length() - 1);
            }
            // otherwise it has to be a field
            else{
                // we find the image correspoding to a field uding the image location
                image = new Image(imageLoc + "farmland.png");
                // we set the text to say field so the user knows and a number to differ each field from each other
                text = "Field " + project.getType().charAt(project.getType().length() - 1);
            }

            // we give the image a image view
            imageV.setImage(image);
            // set the size of the image view
            imageV.setFitHeight(30);
            imageV.setFitWidth(30);
            // add it to the project display
            projectDisplay.getChildren().add(imageV);

            // a pane that will pace the text and image
            Pane ph2 = new Pane();
            // set the size of the pane
            ph2.setPrefHeight(36);
            ph2.setPrefWidth(9);

            // add it to the project display
            projectDisplay.getChildren().add(ph2);

            // creating a text object
            Text lable = new Text();
            // setting the text to the project name
            lable.setText(text);
            // updating the style, so it matches the rest of the style
            lable.setFont(Font.font("Ubuntu", FontWeight.BOLD, 17));
            lable.setFill(Paint.valueOf("#003844"));

            // adding the text to the project display
            projectDisplay.getChildren().add(lable);

            // checking weather the project is not a field
            if(text.charAt(0) != 'F'){
                // obtaining the project ID (example R1 --> road 1)
                String projectID = String.valueOf(text.charAt(0)) + text.charAt(text.length() - 1);

                // check wether there is already a meeple on this project
                // creating arraylist of all the checked tiles
                ArrayList<Tile> checked = new ArrayList<>();
                // clearing the projects in project
                projectsInProject.clear();
                // obtaining all the tiles of the project  is connected to using the check finish project function
                ArrayList<Tile> entireProject = checkFinishProject(tile, colIndex, rowIndex, projectID, checked);
                // checking wether the arraylist of tiles contains a null
                if(entireProject.contains(null)) {
                    // looping through the tiles of the project
                    for (int j = 0; j < entireProject.size(); j++) {
                        // checking wether this tile is null
                        if (entireProject.get(j) == null) {
                            // if it is null we remove it
                            entireProject.remove((j));
                            // update j so no tile is missed when looping
                            j--;
                        }
                    }
                }

                // we send the arraylist of tiles of the project to the getDeleteMeeples fucntion to obtain all meeples that are placed on the project
                ArrayList<Meeple> meeplesInProject = getDeleteMeeples(entireProject);

                // checking wether the arraylist meeples isn't empty
                if(meeplesInProject != null && meeplesInProject.size() > 0){
                    // if it isn't empty there is a meeple on that project so the user can't place a meeple there
                    // we set disable the project and set opacity to 0.5 to show the user
                    projectDisplay.setDisable(true);
                    projectDisplay.setOpacity(0.5);
                }
            }
            // checking if the project is a field
            else if(text.charAt(0) == 'F'){
                // getting the ID of the project
                String projectID = String.valueOf(text.charAt(text.length() - 1));

                // check wether there is already a meeple on this project
                // creating arraylist of all the checked tiles
                ArrayList<Tile> checked = new ArrayList<>();
                // clearing the projects in project
                projectsInProject.clear();
                // obtaining all the tiles of the project is connected to using the getTilesOfField function
                ArrayList<Tile> entireProject = getTilesOfField(tile, colIndex, rowIndex, projectID, checked);
                // we send the arraylist of tiles of the project to the getDeleteMeeples fucntion to obtain all meeples that are placed on the project
                ArrayList<Meeple> meeplesInProject = getDeleteMeeples(entireProject);

                // checking wether the arraylist meeples isn't empty
                if(meeplesInProject != null && meeplesInProject.size() > 0){
                    // if it isn't empty there is a meeple on that project so the user can't place a meeple there
                    // we set disable the project and set opacity to 0.5 to show the user
                    projectDisplay.setDisable(true);
                    projectDisplay.setOpacity(0.5);
                }
            }

            // checking if the player has run out of meeples to place
            if(Variables.getPlayers().get(0).getNumMeeples() == 0){
                // if so we disable the project display and show the user this is done (this will be true for all projects)
                projectDisplay.setDisable(true);
                projectDisplay.setOpacity(0.5);
            }

            // add the project display to the selection option box so it is visible to the user
            selectionOption.getChildren().add(projectDisplay);

            // create a pane as a place holder this will be to keep a distance between each projects display
            Pane ph3 = new Pane();
            // set the size to the wanted size
            ph3.setPrefWidth(198);
            ph3.setPrefHeight(10);

            // add this to the selction option box
            selectionOption.getChildren().add(ph3);
        }
        // create a box for the user to press to confirm their choice
        HBox confirmBox = new HBox();

        // set the aligment for visual reasons
        confirmBox.setAlignment(Pos.CENTER_LEFT);

        // set the padding of the box for visual reasons
        confirmBox.setPadding(new Insets(0, 0, 0, 12));
        // create a image view for the check mark
        ImageView confirmButton = new ImageView();
        // find the check mark image using image loccation string
        Image confirm = new Image(imageLoc + "approve.png");

        // setting the image to the image veiw
        confirmButton.setImage(confirm);
        // adjusting size of the check mark image view
        confirmButton.setFitWidth(37);
        confirmButton.setFitHeight(37);

        // telling it to run the confrim fucntion when pressed by the user
        confirmButton.setOnMouseClicked(mouseEvent -> {
            // it could run a IO exception so we have to try
            try {
                confirm(place, colIndex, rowIndex, tile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // create a image view for a cross (this is what the user presss if they dont want to place a meeple)
        ImageView clearButton = new ImageView();
        // find the x mark image using the image location string
        Image clear = new Image(imageLoc + "clear.png");

        // setting the image to the corresponding imageview
        clearButton.setImage(clear);
        // adjust the size of the x mark
        clearButton.setFitWidth(41);
        clearButton.setFitHeight(41);

        // tell it to run the clear function when pressed
        clearButton.setOnMouseClicked(mouseEvent -> {
            // it could run a IO exception so we have to try
            try {
                clear(place, colIndex, rowIndex, tile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // create a pane which will be used as spacing between the confirm button and clear button
        Pane spacing = new Pane();
        // adjust to the wanted size
        spacing.setPrefWidth(15);

        // add the imageview of the confirm button, clear button and the spacing to the confirm box
        confirmBox.getChildren().addAll(confirmButton, spacing, clearButton);
        // add the confirm box to the selection option box to display it to the user
        selectionOption.getChildren().add(confirmBox);

        // checking wether the user is play with the IC expansion
        if(IC){
            // if so the user also has to choose what type of meeple
            // checking wether they still have a big meeple
            if(Variables.getPlayers().get(0).isHasBigMeeple()){
                // if they do have a big meeple then we show the meeple selection box so they can select which meeple they want
                ICMeepleSelection.setOpacity(100);
                ICMeepleSelection.setDisable(false);

                // we reset the background to the dark yellow colour (from the previuos time they selected it could have been bright yellow)
                meepleselectionBigMeeple.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(10), new Insets(1))));
                meepleselectionSmallMeeple.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(10), new Insets(1))));
                // we reset the meeple selected to null
                meepleSelected = null;
            }
            else{
                // if they don't have any big meeples left they must place a small one so the meeple selected is updated
                meepleSelected = "small";
            }
        }
    }

    private void showCanPlace(Tile tile){
        // loop through all the tiles placed (and also the empty spaces)
        for(int i = 0;  i < playedTile.size(); i++){
            for(int j = 0; j < playedTile.get(i).size(); j++){
                // checking wether the current tile can be place in this spot
                if(checkPosistion(tile, j, i)){
                    // if the tile can be place in this spot we update the background
                    grid.get(i).get(j).setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
                else{
                    // if it can be place here we update the background to null
                    grid.get(i).get(j).setBackground(null);
                }
            }
        }
    }

    private boolean checkPosistion(Tile tile, int rowIndex, int colIndex){
        // chekcing wether the posistion where the tile want to be palced if empty
        if(playedTile.get(colIndex).get(rowIndex) == null){
            // setting the tiles norht, south, west, and east of the tile to null
            Tile north = null;
            Tile south = null;
            Tile east = null;
            Tile west = null;

            // checking if we are on the border of the board (north side, top)
            if(rowIndex == 0){
                // if we are at the boarder of the board it will only have a tile below it, south of it
                // we obtain the tile south of the posistion we want to place the tile
                south = playedTile.get(colIndex).get(rowIndex + 1);
            }
            // checking wether it is on the border of the board (sout side, bottom)
            else if (rowIndex == playedTile.get(colIndex).size() - 1){
                // if it is at the bottom of the board will only have a tile north of it
                // obtaining the tile north of the posisition we want to place the tile
                north = playedTile.get(colIndex).get(rowIndex - 1);
            }
            else{
                // it isn't at any vertical border
                // obtaining the tiles north and south from the posisition the user wants to place the tile
                south = playedTile.get(colIndex).get(rowIndex + 1);
                north = playedTile.get(colIndex).get(rowIndex - 1);
            }

            // checking if we are on the border of the board (west side, left)
            if(colIndex == 0){
                // if we are at the boarder of the board it will only have a tile right of it, east of it
                // we obtain the tile east of the posistion we want to place the tile
                east = playedTile.get(colIndex + 1).get(rowIndex);
            }

            else if(colIndex == playedTile.size() - 1){
                // if it is at the right of the board will only have a tile west of it
                // obtaining the tile west of the posisition we want to place the tile
                west = playedTile.get(colIndex - 1).get(rowIndex);
            }
            else{
                // it isn't at any horizontal border
                // obtaining the tiles east and west from the posisition the user wants to place the tile
                east = playedTile.get(colIndex + 1).get(rowIndex);
                west = playedTile.get(colIndex - 1).get(rowIndex);
            }

            // setting up a boolean canPlace that will be returned at the end
            boolean canPlace = true;
            // a intger that counts the number of non null valued tiles around the posistion
            int numNull = 0;

            // checking if there is somthing north from the tile
            if(north != null){
                // if there is something we check wether the type (road, field, or city) at the bottom of that tile is equal to the top of the tile being placed
                if(north.getSides()[2].charAt(0) != tile.getSides()[0].charAt(0)){
                    // if not you can't place you tile there
                    canPlace = false;
                }
                // we increase the counter
                numNull ++;
            }

            // checking if there is somthing south from the tile
            if(south != null){
                // if there is something we check wether the type (road, field, or city) at the top of that tile is equal to the bottom of the tile being placed
                if(south.getSides()[0].charAt(0) != tile.getSides()[2].charAt(0)){
                    // if not you can't place you tile there
                    canPlace = false;
                }
                // we increase the counter
                numNull ++;
            }

            // checking if there is somthing east from the tile
            if(east != null){
                // if there is something we check wether the type (road, field, or city) at the west side of that tile is equal to the east side of the tile being placed
                if(east.getSides()[3].charAt(0) != tile.getSides()[1].charAt(0)){
                    // if not you can't place you tile there
                    canPlace = false;
                }
                // we increase the counter
                numNull ++;
            }

            // checking if there is somthing west from the tile
            if(west != null){
                // if there is something we check wether the type (road, field, or city) at the east side of that tile is equal to the west side of the tile being placed
                if(west.getSides()[1].charAt(0) != tile.getSides()[3].charAt(0)){
                    // if not you can't place you tile there
                    canPlace = false;
                }
                // we increase the counter
                numNull ++;
            }

            // checking that atleast on one side there is a tile it connects too
            if(numNull == 0){
                // if this isn't the case you can't place there
                canPlace = false;
            }

            // return the boolean canPlace
            return canPlace;
        }
        else{
            // if there is already a tile there you can't palce a tile ther
            return false;
        }
    }

    private void checkBoard(HBox recentTile){
        // checking wether the  colomum the current tile is placed in is the same as the last coloumn
        if(recentTile.getParent() == cols.get(cols.size()-1)){
            // if so we add an extra colomun at the ends
            addColBack();
        }
        // checking wether the  colomum the current tile is placed in is the same as the first coloum
        else if(recentTile.getParent() == cols.get(0)){
            // if so add a coloum to the front
            addColFront();
        }

        // looping through all coloumns
        for(int i = 0; i < grid.size(); i++){
            // checking wether the row the current tile is placed in the same as the bottom row
            if(recentTile == grid.get(i).get(grid.get(i).size()-1)){
                // if so add a row to the bottom
                addRowBack();
            }
            // checking wether the row the current tile is placed in the same as the top row
            else if(recentTile == grid.get(i).get(0)){
                // if so add a row to the top
                addRowFront();
            }
        }
    }

    /**
     * <p>
     *     This methode rotates the tile that is currently being placed
     *     when the user pressed the 'r' key on their keyboard or clicks
     *     the rotate button and they are not currently selecting where
     *     to place the meeple for a tile.
     * </p>
     */

    public void rotate(){
        if(selectionOption.getChildren().size() == 0){
            // getting the current tile that is being placed
            Tile currentTile = tiles.get(0);

            // get the sides of this tile
            String[] currentSides = currentTile.getSides();
            // create new sides
            String[] newSides  = new String[4];

            // rotating the sides
            // loop through all values in the sides except that last one
            for (int i = 0; i < currentSides.length - 1; i++){
                // set the value of the side to same index but plus one so that there is a shift on one
                newSides[i + 1] = currentSides[i];
            }
            // fill in the very first in the new side with the very last in the old side so that they have all shifted right by 1
            newSides[0] = currentSides[currentSides.length - 1];
            // update the sides with the new sides
            currentTile.setSides(newSides);

            // get the fields of this tile
            String[] currentField = currentTile.getFields();
            // create new fields
            String[] newFields = new String[8];

            // rotating the fields
            // loop through all values in the fields except that last two
            for (int i = 0; i < currentField.length - 2; i += 2){
                // setting the values of the old fields to same index plus 2 in the new fields so tha they shift by 2 each
                newFields[i + 2] = currentField[i];
                // doing the same thing but with the next one
                newFields[i + 3] = currentField[i + 1];
            }
            // filling in the very first of the new field with the second to last of the old field
            newFields[0] = currentField[currentField.length - 2];
            // filling in the secound of the new field with the last of the old field
            newFields[1] = currentField[currentField.length - 1];
            currentTile.setFields(newFields);

            // checking wether we don't go over the 360 when rotating
            if (currentRotationPreviewTile + 90 == 360){
                // if so we go down to -90 so that +90 would be 0
                currentRotationPreviewTile = -90;
            }

            // rotating the preview image view
            previewTile.setRotate(currentRotationPreviewTile + 90);
            // adjusting the current rotation
            currentRotationPreviewTile += 90;

            // checking if the tile is a tilef rom the goldmines expansion
            if(G && GTiles.contains(currentTile)){
                // if so we obtain that tile as a goldmines tile
                // a temporray null (it is sure it will change)
                GTile gTile = null;
                // looping through at goldmines tiles
                for(int i = 0; i < GTiles.size(); i ++){
                    // chekcing if the tile trying to be found is eqaul the goldmines tile
                    if(currentTile == GTiles.get(i)){
                        // if so we found the tile and save it as a goldmines tile
                        gTile = GTiles.get(i);
                    }
                }
                // this pane will act as the x axis of the tile
                Pane width2 = new Pane();
                // this HBox will act as a y axis of the tile
                HBox ph2 = new HBox();

                // making a imageview with the image of the gold bar
                ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
                // setting the size to the wanted size
                goldImage.setFitHeight(15);
                goldImage.setFitWidth(24);

                // checking how much the tile is rotated from its original posistion
                if (currentRotationPreviewTile == 0) {
                    // it isn't rotated
                    // set the width equal the gold X posision (double becasue it is the preview tile) we set the height equal to the expexted height of ph2 so that is fits perfectly
                    width2.setPrefSize(gTile.getXGold() * 2, 160 - gTile.getYGold());
                    // setting the padding at the top of the HBox to the Y psosion of the gold in order to "push" the image view down (double becasue it is the preview tile)
                    ph2.setPadding(new Insets(gTile.getYGold() * 2, 0, 0, 0));
                }
                else if (currentRotationPreviewTile == 90) {
                    // the tile is rotated (X and Y set swapped & Y is inversed)
                    // set the width equal the gold Y posision but inveresed (double becasue it is the preview tile) we set the height equal to the expexted height of ph2 so that is fits perfectly
                    width2.setPrefSize(160 - (gTile.getYGold() * 2) - 25, 160 - (gTile.getXGold() * 2));
                    // setting the padding at the top of the HBox to the X psosion of the gold in order to "push" the image view down (double becasue it is the preview tile)
                    ph2.setPadding(new Insets(gTile.getXGold() * 2, 0, 0, 0));
                }
                else if (currentRotationPreviewTile == 180) {
                    // the tile is rotated (X and Y set are inversed)
                    // set the width equal the gold X posision but inveresed (double becasue it is the preview tile) we set the height equal to the expexted height of ph2 so that is fits perfectly
                    width2.setPrefSize(160 - (gTile.getXGold() * 2) - 33, gTile.getYGold() * 2 - 25);
                    // setting the padding at the top of the HBox to the Y psosion of the gold in order to "push" the image view down (double becasue it is the preview tile)
                    ph2.setPadding(new Insets(160 - (gTile.getYGold() * 2) - 25, 0, 0, 0));
                }
                else{
                    // the tile is rotated (X and Y set swapped & X is inversed)
                    // set the width equal the gold Y posision (double becasue it is the preview tile) we set the height equal to the expexted height of ph2 so that is fits perfectly
                    width2.setPrefSize(gTile.getYGold() * 2, gTile.getXGold() * 2 - 33);
                    // setting the padding at the top of the HBox to the X psosion but inversed of the gold in order to "push" the image view down (double becasue it is the preview tile)
                    ph2.setPadding(new Insets(160 - (gTile.getXGold() * 2) - 33, 0, 0, 0));
                }

                // add the width and the image to the HBox
                ph2.getChildren().addAll(width2, goldImage);

                // obtain the image for the tile
                Node temp = preview.getChildren().get(0);
                // get rid of any existing things other than the tile
                preview.getChildren().clear();
                // add the tile image back
                preview.getChildren().add(temp);

                // add the gold bar image
                preview.getChildren().add(ph2);
            }

            showCanPlace(tiles.get(0));
        }
    }

    public void placeTile2(HBox place, int colIndex, int rowIndex) throws IOException {
        // we know the player is allowed the place the tile here and wants to place the tile here
        // we set save the rotation in the tiles information
        tiles.get(0).setRotation(currentRotationPreviewTile);

        // we create a arrayList to save all the sides that we checked
        ArrayList<String> checkedSides = new ArrayList<>();

        // loop through all the side (it will always be 4)
        for(int i = 0; i < 4; i ++){
            // checking if the side if a field
            if(tiles.get(0).getSides()[i].charAt(0) != 'F'){
                // if is isn't we create a isChecked boolean to change when the side checked
                boolean isChecked = false;
                // loop through all the checked sides
                for(int j = 0; j < checkedSides.size(); j ++){
                    // check wether the side is equal to the side currently being looked at
                    if(checkedSides.get(j).equals(tiles.get(0).getSides()[i])){
                        // if they are then it is already checked
                        isChecked = true;
                    }
                }
                // checking wether the side is checked
                if(!isChecked){
                    // if it isn't we create a arraylist to save the tiles that we already checked
                    ArrayList<Tile> checked = new ArrayList<>();
                    // we clear the projects in projects arrayList so that it can be reused to save teh already checked projects
                    projectsInProject.clear();
                    // run the entire project function which returns a array list of all tiles in the project of the side
                    ArrayList<Tile> entireProject = checkFinishProject(tiles.get(0), colIndex, rowIndex, tiles.get(0).getSides()[i], checked);

                    // check wether the entire projects contain any null
                    if(!entireProject.contains(null)) {
                        // if it doesn't it mean the project is closed and therefore done
                        // create a numGold variable which keeps track of all the gold bars on the tiles of the completed project
                        int numGold = 0;
                        // checking wether the user wanted to place with the goldmines expansion
                        if(G){
                            // run the deleteGold function which gets rid of all the gold bars and return the number of gold bars it got rid of
                            numGold = deleteGold(entireProject);
                        }

                        // checking whether the project is a city (we use the side we were looking at to do this quick and effeciently)
                        if(tiles.get(0).getSides()[i].charAt(0) == 'C'){
                            // creating a temporray arrayList of projects to duplicate the projects in project variable
                            ArrayList<Project> projectsTemp = new ArrayList<>();
                            // loop through all the project in projectsInProject
                            for(int j = 0; j < projectsInProject.size(); j ++){
                                // adding the projects to the temporary arrayList
                                projectsTemp.add(projectsInProject.get(j));
                            }
                            // add the temporay arrayList to the completed cities (a duplicate was made so that they are saved in other memory locations)
                            completedCities.add(projectsTemp);
                        }
                        // run a fucntion that egts rid of all the meeples inside the city and returns all of the deleted ones in a arrayList
                        ArrayList<Meeple> deleteMeeples = getDeleteMeeples(entireProject);
                        // checking wether there are actaully meeples were deleted
                        if(deleteMeeples != null && deleteMeeples.size() != 0){
                            // give back the meeples to the players using a function
                            giveBackMeeples(deleteMeeples);

                            // run the function calculatePoints to obtain the number of points won by this project
                            int points = calculatePoints(entireProject, tiles.get(0).getSides()[i], false);
                            // run the function calculateProjectWinner which figures out which player had the most amount of meeples on the projects and return those players
                            ArrayList<Player> winningPlayers = calculateProjectWinner(deleteMeeples);
                            // loop through all the winning players (should mostly only be 1, unless ther is a tie)
                            for(int j = 0; j < winningPlayers.size(); j ++){
                                // update the points for each winning player by adding the won points to the existing points
                                winningPlayers.get(j).setPoints(winningPlayers.get(j).getPoints() + points);
                                // update the text at both the turn box and the waiting box
                                winningPlayers.get(j).getTurnPoints().setText(String.valueOf(((Integer.parseInt(winningPlayers.get(j).getTurnPoints().getText()) + points))));
                                winningPlayers.get(j).getWaitingPoints().setText(String.valueOf(((Integer.parseInt(winningPlayers.get(j).getWaitingPoints().getText()) + points))));
                            }
                            // getting rid of all the meeples that were placed on that project and give the meeples back to the players
                            clearMeeples(entireProject, deleteMeeples);

                            // checking wether the user is playing the goldmines expansion
                            if(G){
                                // if so, fund the amount of gold ingots on the project and hand them out to the players
                                giveGoldPoints(numGold, winningPlayers);
                            }
                        }
                    }
                    // add this side to the checked sides so if there is another side that is part of the same project it doesn't get checked agin
                    checkedSides.add(tiles.get(0).getSides()[i]);
                }
            }
        }
        // setting up a project ID variable, which will be used to save which field was done last
        char projectID = '0';

        // looping through all the fields
        for(int i = 0; i < 8; i ++){
            // checking if it isn't a city ('c') and if it isn't the previous field again
            if(tiles.get(0).getFields()[i].charAt(0) != 'c' && tiles.get(0).getFields()[i].charAt(0) != projectID){
                // setting up a check arraylist of tiles to be used by the getTilesOfField function
                ArrayList<Tile> checked = new ArrayList<>();
                // clearing the porjectsInPorject variable so that it can be used again
                projectsInProject.clear();
                // using the getTilesOfField function to obtain all the Tiles that are part of that field in a ArrayList
                ArrayList<Tile> entireProject = getTilesOfField(tiles.get(0), colIndex, rowIndex, tiles.get(0).getFields()[i], checked);
                // setting the projectID to this field as it has been checked
                projectID = tiles.get(0).getFields()[i].charAt(0);
                for(int j = 0; j < entireProject.size(); j ++){
                    System.out.print(Arrays.toString(entireProject.get(j).getFields()) + ", ");
                }
                System.out.println();
            }
        }
        // checking wether the board needs to be adjusted in size
        checkBoard(place);

        //check church
        checkChurch(tiles.get(0), true);

        // removing the tile from the deck / tiles
        tiles.remove(0);

        // checking wether this was the last tile
        if(tiles.size() == 0){
            // if it was the last one we don't display a the next preview tiles
            previewTile.setImage(null);
            ending();
        }
        else{
            // if it isn't we display the next tile in the preview Image view for the user to see
            previewTile.setImage(tiles.get(0).getImage());
            // checking if the user is playing with the goldmines expansion and if the tile is part of the goldmines expansion
            if(G && GTiles.contains(tiles.get(0))){
                // if so, the variable gTile si created which will become the current tile but then as a gTile instead of a regular TIle
                GTile gTile = null;
                // loop through all the tiles part of the goldmines expansion
                for(int i = 0; i < GTiles.size(); i ++){
                    // checking if the current tile is the current tile from the goldmines expansion
                    if(tiles.get(0) == GTiles.get(i)){
                        // if so, we set gTile equal to the that tiles because it is part of the GTile class
                        gTile = GTiles.get(i);
                    }
                }
                // creating the x axis for the gold ingot image (which will be a pane which pushes the image to the side)
                Pane width2 = new Pane();
                // creating the y axis for the gold ingot image (which will be a HBox which pushes the image to the down)
                HBox ph2 = new HBox();

                // creating a imageView for the gold ingot image
                ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
                // setting the size so that it isn't too big
                goldImage.setFitHeight(15);
                goldImage.setFitWidth(24);

                // setting the width pane equal to the X coordinates of the gold ingot so that it is in the correct position
                width2.setPrefSize(gTile.getXGold() * 2, 160 - gTile.getYGold());
                // setting the HBox equal to the Y coordinates of the gold ingot so that it is in the correct position
                ph2.setPadding(new Insets(gTile.getYGold() * 2, 0, 0, 0));

                // adding the width pane and hte gold ingot image to the HBox
                ph2.getChildren().addAll(width2, goldImage);
                // adding the HBox to the preview StackPane so that it will be displayed above the tile image
                preview.getChildren().add(ph2);
            }
            // running the showCanPlace function for the current tile to show the user where they can place the current tiles
            showCanPlace(tiles.get(0));
        }

        // reset the rotation back to 0
        currentRotationPreviewTile = 0;
        // reseat the rotation of the preview tile
        previewTile.setRotate(0);

        // update the tilesLeftText on the screen to be one less (the tiles size)
        tilesLeftText.setText("Tiles Left: " + tiles.size());

        // reset the selected project and the selected project box
        selectedProjectBox = null;
        selectedProject = null;

        // rotate the players as one turn has passed
        rotatePlayers();
    }

    public void confirm (HBox place, int colIndex, int rowIndex, Tile tile) throws IOException {
        // creating a variable the will store wether the the user selected a meeple type of not (by default true, incase the user didn't select the IC expansion)
        boolean meepleIsSelected = true;

        // checking wether the user is playing with the IC expansion and if so, whether they choose a meeple
        if(IC && meepleSelected == null){
            // if they are playing with the IC expansion and have not selected a meeple the meepleIsSelected variable is false
            meepleIsSelected = false;
        }

        // checking wether the user selected a project and wether the user has enough meeples to play or has a big meeple to play and if they selected a meeple type
        if (selectedProject != null && (Variables.getPlayers().get(0).getNumMeeples() != 0 || Variables.getPlayers().get(0).isHasBigMeeple())&& meepleIsSelected) {
            // if all is true, the selected option is reset to empty
            selectionOption.getChildren().clear();

            // the box where th user wants to place their meeple is retrived from the grid
            HBox box = grid.get(colIndex).get(rowIndex);

            // a StackPane is made because a meeple will now have to be place above the tile
            StackPane addBox = new StackPane();
            // the StackPane is set to the size of the box
            addBox.setPrefWidth(80);
            addBox.setPrefHeight(80);

            // a temporary node is created to store the imageview of the tile
            Node temp = box.getChildren().get(0);
            // the box is cleared
            box.getChildren().clear();
            // the imageView of the tile is added to the StackPane
            box.getChildren().add(addBox);

            // a meeple box where the meepple imageview will be placed in is created
            HBox meepleBox = new HBox();
            // the meeple box is set to the size of the box
            meepleBox.setPrefWidth(80);
            meepleBox.setPrefHeight(80);

            // a pane is created that will push the meeple imageview to the side (acting as a x axis)
            Pane width = new Pane();

            // the meeple that is added will be made
            Meeple meeple;

            // checking wether the user is playing with the IC expansion and if the user selected the big meeple type
            if(IC && meepleSelected.equals("big")){
                // if so a meeple is created with the big variable as true
                meeple = new Meeple(selectedProject.getMeepleX(), selectedProject.getMeepleY(), colIndex, rowIndex, Variables.getPlayers().get(0).getColour(), true);
            }
            else{
                // if so a meeple is created with the big variable as false
                meeple = new Meeple(selectedProject.getMeepleX(), selectedProject.getMeepleY(), colIndex, rowIndex, Variables.getPlayers().get(0).getColour(), false);
            }
            // the project whcih the meeple is placed on is saved in the meeple
            meeple.setProject(selectedProject);
            // the meeple is set as the meeple that is on this project
            selectedProject.setMeeple(meeple);
            // the meeple is added to the placed meeple
            placedMeeples.add(meeple);

            // finding the rotation of the tile
            if (currentRotationPreviewTile == 0) {
                // it isn't rotated
                // set the width equal the gold X posision  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(meeple.getX(), 80 - meeple.getY());
                // setting the padding at the top of the HBox to the Y psosion of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(meeple.getY(), 0, 0, 0));
            }
            else if (currentRotationPreviewTile == 90) {
                // the tile is rotated (X and Y set swapped & Y is inversed)
                // set the width equal the gold Y posision but inveresed  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(80 - meeple.getY() - 12, 80 - meeple.getX());
                // setting the padding at the top of the HBox to the X psosion of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(meeple.getX(), 0, 0, 0));
            }
            else if (currentRotationPreviewTile == 180) {
                // the tile is rotated (X and Y set are inversed)
                // set the width equal the gold X posision but inveresed  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(80 - meeple.getX() - 12, meeple.getY() - 12);
                // setting the padding at the top of the HBox to the Y psosion of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(80 - meeple.getY() - 12, 0, 0, 0));
            }
            else{
                // the tile is rotated (X and Y set swapped & X is inversed)
                // set the width equal the gold Y posision  we set the height equal to the expexted height of meeple box so that is fits perfectly
                width.setPrefSize(meeple.getY(), meeple.getX()  - 12);
                // setting the padding at the top of the HBox to the X psosion but inversed of the gold in order to "push" the image view down
                meepleBox.setPadding(new Insets(80 - meeple.getX() - 12, 0, 0, 0));
            }
            // add the meeple imageView aand the width pane to the meeple box
            meepleBox.getChildren().addAll(width, meeple.getDisplay());

            // adding the meeple box the box
            addBox.getChildren().addAll(temp, meepleBox);

            // checking if the user is playing with the golmines expansion and if the current tile is part of that expansion
            if(G && GTiles.contains(tile)){
                // if so, the variable gTile si created which will become the current tile but then as a gTile instead of a regular TIle
                GTile gTile = null;
                // loop through all the tiles part of the goldmines expansion
                for(int i = 0; i < GTiles.size(); i ++){
                    // checking if the current tile is the current tile from the goldmines expansion
                    if(tiles.get(0) == GTiles.get(i)){
                        // if so, we set gTile equal to the that tiles because it is part of the GTile class
                        gTile = GTiles.get(i);
                    }
                }

                // creating a HBox for the gold ingot image
                HBox goldBox = new HBox();
                // setting the height to fit perfectly in a tile box
                goldBox.setPrefWidth(80);
                goldBox.setPrefHeight(80);

                // creating a pane that will act as the x-axis (pusing the image to the right)
                Pane width2 = new Pane();

                // creating the image view of the gold ingot
                ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
                // setting the size
                goldImage.setFitHeight(8);
                goldImage.setFitWidth(12);

                // finding the rotation of the tile
                if (currentRotationPreviewTile == 0) {
                    // it isn't rotated
                    // set the width equal the gold X posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(gTile.getXGold(), 80 - gTile.getYGold());
                    // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(gTile.getYGold(), 0, 0, 0));
                }
                else if (currentRotationPreviewTile == 90) {
                    // the tile is rotated (X and Y set swapped & Y is inversed)
                    // set the width equal the gold Y posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(80 - gTile.getYGold() - 8, 80 - gTile.getXGold());
                    // setting the padding at the top of the HBox to the X posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(gTile.getXGold(), 0, 0, 0));
                }
                else if (currentRotationPreviewTile == 180) {
                    // the tile is rotated (X and Y set are inversed)
                    // set the width equal the gold X posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(80 - gTile.getXGold() - 12, gTile.getYGold() - 8);
                    // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(80 - gTile.getYGold() - 8, 0, 0, 0));
                }
                else{
                    // the tile is rotated (X and Y set swapped & X is inversed)
                    // set the width equal the gold Y posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(gTile.getYGold(), gTile.getXGold() - 12);
                    // setting the padding at the top of the HBox to the X posistion but inversed of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(80 - gTile.getXGold() - 12, 0, 0, 0));
                }
                // add the width and the gold image to the HBox
                goldBox.getChildren().addAll(width2, goldImage);

                // save the gold box in the tile
                gTile.setGoldBox(goldBox);
                // save the HBox of the grid in the tiles information so the place can be easily traced down
                gTile.setGoldBoxParent(addBox);

                // add the HBox with the image of the gold ingot to the stackpane which already has the image of the tiles
                addBox.getChildren().add(goldBox);

            }

            // checking if the user is playing with the inns and catherdrale expansion and whether they wanted to play a big meeple
            if(IC && meepleSelected.equals("big")){
                // if so we get rid of that players big meeple
                decreaseBigMeeple();
            }
            else{
                // if not we take one of the players meeples
                decreaseMeeple();
            }

            // create a temporary node to save the image of the tile in the preview box
            Node temp2 = preview.getChildren().get(0);
            // clear the preview box and get rid of anything in it
            preview.getChildren().clear();
            // add back the image of the tile
            preview.getChildren().add(temp2);

            // checking wether the user is playing with the IC expansion
            if(IC){
                // if so we disable and make the meeple selection area inviseble
                ICMeepleSelection.setOpacity(0);
                ICMeepleSelection.setDisable(true);
            }

            // we run the next function of placing the tile (checking wether any porjects have been completed and some other things)
            placeTile2(place, colIndex, rowIndex);
        }
        // checking wether the player didn't select any project
        else if(selectedProject == null) {
            // if all is true, the selected option is reset to empty
            selectionOption.getChildren().clear();
            // if so the player doesn't want to place a meeple so only a gold ingot might need to be placed
            // checking if the user is playing with the golmines expansion and if the current tile is part of that expansion
            if (G && GTiles.contains(tile)) {
                // if so, the variable gTile si created which will become the current tile but then as a gTile instead of a regular TIle
                GTile gTile = null;
                // loop through all the tiles part of the goldmines expansion
                for (int i = 0; i < GTiles.size(); i++) {
                    // checking if the current tile is the current tile from the goldmines expansion
                    if (tiles.get(0) == GTiles.get(i)) {
                        // if so, we set gTile equal to the that tiles because it is part of the GTile class
                        gTile = GTiles.get(i);
                    }
                }

                // the box where th user wants to place their meeple is retrived from the grid
                HBox box = grid.get(colIndex).get(rowIndex);

                // a StackPane is made because a meeple will now have to be place above the tile
                StackPane addBox = new StackPane();
                // the StackPane is set to the size of the box
                addBox.setPrefWidth(80);
                addBox.setPrefHeight(80);

                // a temporary node is created to store the imageview of the tile
                Node temp = box.getChildren().get(0);
                // the box is cleared
                box.getChildren().clear();
                // the imageView of the tile is added to the StackPane
                box.getChildren().add(addBox);

                // creating a HBox for the gold ingot image
                HBox goldBox = new HBox();
                // setting the height to fit perfectly in a tile box
                goldBox.setPrefWidth(80);
                goldBox.setPrefHeight(80);

                // creating a pane that will act as the x-axis (pusing the image to the right)
                Pane width2 = new Pane();

                // creating the image view of the gold ingot
                ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
                // setting the size
                goldImage.setFitHeight(8);
                goldImage.setFitWidth(12);

                // finding the rotation of the tile
                if (currentRotationPreviewTile == 0) {
                    // it isn't rotated
                    // set the width equal the gold X posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(gTile.getXGold(), 80 - gTile.getYGold());
                    // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(gTile.getYGold(), 0, 0, 0));
                } else if (currentRotationPreviewTile == 90) {
                    // the tile is rotated (X and Y set swapped & Y is inversed)
                    // set the width equal the gold Y posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(80 - gTile.getYGold() - 8, 80 - gTile.getXGold());
                    // setting the padding at the top of the HBox to the X posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(gTile.getXGold(), 0, 0, 0));
                } else if (currentRotationPreviewTile == 180) {
                    // the tile is rotated (X and Y set are inversed)
                    // set the width equal the gold X posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(80 - gTile.getXGold() - 12, gTile.getYGold() - 8);
                    // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(80 - gTile.getYGold() - 8, 0, 0, 0));
                } else {
                    // the tile is rotated (X and Y set swapped & X is inversed)
                    // set the width equal the gold Y posistion  we set the height equal to the expexted height of gold box so that is fits perfectly
                    width2.setPrefSize(gTile.getYGold(), gTile.getXGold() - 12);
                    // setting the padding at the top of the HBox to the X posistion but inversed of the gold in order to "push" the image view down
                    goldBox.setPadding(new Insets(80 - gTile.getXGold() - 12, 0, 0, 0));
                }
                // add the width and the gold image to the HBox
                goldBox.getChildren().addAll(width2, goldImage);

                // save the gold box in the tile
                gTile.setGoldBox(goldBox);
                // save the HBox of the grid in the tiles information so the place can be easily traced down
                gTile.setGoldBoxParent(addBox);

                // add the HBox with the image of the gold ingot to the stackpane which already has the image of the tiles
                addBox.getChildren().addAll(temp, goldBox);

            }

            // create a temporary node to save the image of the tile in the preview box
            Node temp2 = preview.getChildren().get(0);
            // clear the preview box and get rid of anything in it
            preview.getChildren().clear();
            // add back the image of the tile
            preview.getChildren().add(temp2);

            // checking wether the user is playing with the IC expansion
            if (IC) {
                // if so we disable and make the meeple selection area inviseble
                ICMeepleSelection.setOpacity(0);
                ICMeepleSelection.setDisable(true);
            }

            // we run the next function of placing the tile (checking wether any porjects have been completed and some other things)
            placeTile2(place, colIndex, rowIndex);
        }

    }

    public void projectSelection(HBox projectBox, Project project, Tile tile){
        // we know that the user wants to select another project
        // checking wether the current porject that is supposdly selected is null
        if(selectedProjectBox == null){
            // if it is null we don't have to change back the background colour of the other
            // changing the background colour of the box that represents the project
            projectBox.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(5), new Insets(1))));
        }
        else{
            // changing back the background colour of the previously selected project
            selectedProjectBox.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(5), new Insets(1))));
            // changing the background colour of the box that represents the project
            projectBox.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(5), new Insets(1))));
        }

        // update selected project box to the new box the user selected
        selectedProjectBox = projectBox;
        // update selected project  to the new box the user selected
        selectedProject = project;

        // now updating the meeple position

        // creating a temporary node to save the imageview of tile
        Node temp = preview.getChildren().get(0);
        // clear everything in this preview box
        preview.getChildren().clear();
        // add back the image of the tile
        preview.getChildren().add(temp);

        // create a new meeple for the preview tile
        Meeple previewMeeple = new Meeple(project.getMeepleX(), project.getMeepleY(), 0, 0, Variables.getPlayers().get(0).getColour(), false);

        // set the size to the normal size
        previewMeeple.setSize();

        // create a pane which will push the meeple image right
        Pane width = new Pane();
        // create a HBox to put the image of the meeple in
        HBox ph = new HBox();

        // finding the rotation of the tile
        if (currentRotationPreviewTile == 0) {
            // it isn't rotated
            // set the width equal the gold X posision  we set the height equal to the expexted height of meeple box so that is fits perfectly
            width.setPrefSize(previewMeeple.getX() * 2, 160 - (previewMeeple.getY() * 2));
            // setting the padding at the top of the HBox to the Y psosion of the meeple in order to "push" the image view down
            ph.setPadding(new Insets(previewMeeple.getY() * 2, 0, 0, 0));
        }
        else if (currentRotationPreviewTile == 90) {
            // the tile is rotated (X and Y set swapped & Y is inversed)
            // set the width equal the gold Y posision but inveresed  we set the height equal to the expexted height of meeple box so that is fits perfectly
            width.setPrefSize(160 - (previewMeeple.getY() * 2) - 24, 160 - (previewMeeple.getX() * 2));
            // setting the padding at the top of the HBox to the X psosion of the meeple in order to "push" the image view down
            ph.setPadding(new Insets(previewMeeple.getX() * 2, 0, 0, 0));
        }
        else if (currentRotationPreviewTile == 180) {
            // the tile is rotated (X and Y set are inversed)
            // set the width equal the gold X posision but inveresed  we set the height equal to the expexted height of meeple box so that is fits perfectly
            width.setPrefSize(160 - (previewMeeple.getX() * 2) - 24, (previewMeeple.getY() * 2) - 24);
            // setting the padding at the top of the HBox to the Y psosion of the meeple in order to "push" the image view down
            ph.setPadding(new Insets(160 - (previewMeeple.getY() * 2) - 24, 0, 0, 0));
        }
        else{
            // the tile is rotated (X and Y set swapped & X is inversed)
            // set the width equal the gold Y posision  we set the height equal to the expexted height of meeple box so that is fits perfectly
            width.setPrefSize(previewMeeple.getY() * 2, (previewMeeple.getX() * 2)  - 24);
            // setting the padding at the top of the HBox to the X psosion but inversed of the meeple in order to "push" the image view down
            ph.setPadding(new Insets(160 - (previewMeeple.getX() * 2) - 24, 0, 0, 0));
        }

        // add the width and the image of the meeple to the Hbox
        ph.getChildren().addAll(width, previewMeeple.getDisplay());
        // add the HBox to the stackpane of the preview
        preview.getChildren().add(ph);

        if(G && GTiles.contains(tile)){
            // if so, the variable gTile si created which will become the current tile but then as a gTile instead of a regular TIle
            GTile gTile = null;
            // loop through all the tiles part of the goldmines expansion
            for (int i = 0; i < GTiles.size(); i++) {
                // checking if the current tile is the current tile from the goldmines expansion
                if (tiles.get(0) == GTiles.get(i)) {
                    // if so, we set gTile equal to the that tiles because it is part of the GTile class
                    gTile = GTiles.get(i);
                }
            }

            // creating a pane that will act as the x-axis (pusing the image to the right)
            Pane width2 = new Pane();
            // creating a HBox for the gold ingot image
            HBox ph2 = new HBox();

            // creating the image view of the gold ingot
            ImageView goldImage = new ImageView(new Image("file:./src/main/resources/com/example/demo/images/gold.png"));
            // setting the size
            goldImage.setFitHeight(15);
            goldImage.setFitWidth(24);

            // finding the rotation of the tile
            if (currentRotationPreviewTile == 0) {
                // it isn't rotated
                // set the width equal the gold X posistion  we set the height equal to the expexted height of gold box so that is fits perfectly (double because it is the preview)
                width2.setPrefSize(gTile.getXGold() * 2, 160 - gTile.getYGold());
                // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down (double because it is the preview)
                ph2.setPadding(new Insets(gTile.getYGold() * 2, 0, 0, 0));
            }
            else if (currentRotationPreviewTile == 90) {
                // the tile is rotated (X and Y set swapped & Y is inversed)
                // set the width equal the gold Y posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly (double because it is the preview)
                width2.setPrefSize(160 - (gTile.getYGold() * 2) - 25, 160 - (gTile.getXGold() * 2));
                // setting the padding at the top of the HBox to the X posistion of the gold in order to "push" the image view down (double because it is the preview)
                ph2.setPadding(new Insets(gTile.getXGold() * 2, 0, 0, 0));
            }
            else if (currentRotationPreviewTile == 180) {
                // the tile is rotated (X and Y set are inversed)
                // set the width equal the gold X posistion but inveresed  we set the height equal to the expexted height of gold box so that is fits perfectly (double because it is the preview)
                width2.setPrefSize(160 - (gTile.getXGold() * 2) - 33, gTile.getYGold() * 2 - 25);
                // setting the padding at the top of the HBox to the Y posistion of the gold in order to "push" the image view down (double because it is the preview)
                ph2.setPadding(new Insets(160 - (gTile.getYGold() * 2) - 25, 0, 0, 0));
            }
            else{
                // the tile is rotated (X and Y set swapped & X is inversed)
                // set the width equal the gold Y posistion  we set the height equal to the expexted height of gold box so that is fits perfectly (double because it is the preview)
                width2.setPrefSize(gTile.getYGold() * 2, gTile.getXGold() * 2 - 33);
                // setting the padding at the top of the HBox to the X posistion but inversed of the gold in order to "push" the image view down (double because it is the preview)
                ph2.setPadding(new Insets(160 - (gTile.getXGold() * 2) - 33, 0, 0, 0));
            }

            // adding the "width" and the image of the gold ingot to the Hbox
            ph2.getChildren().addAll(width2, goldImage);
            // add this HBox to the preview StackPane so that it is visble to the user
            preview.getChildren().add(ph2);
        }
    }

    public void clear(HBox place, int colIndex, int rowIndex, Tile tile) throws IOException {
        // the user doesn't want to place a meeple
        // reseting the selected project
        selectedProject = null;
        // running the confirm function but with out any project selected
        confirm(place, colIndex, rowIndex, tile);
    }

    /**
     * <p>
     *     This method takes a tile and a project on that tile and figures out all the
     *     tiles that belong to the big project, it does this by checking all neighbouring
     *     tiles that connect via that project that is being checked and making use of
     *     recursion. It returns the tiles of the big project in an arraylist. A null value
     *     is added to the array list if he project is not closed at one end and therefore
     *     incomplete.
     * </p>
     *
     * @param tile              this parameter is the tile that is currently
     *                          being checked. This should be a tile that is
     *                          already played by the user. This is a object
     *                          of the Tile class.
     *
     * @param colIndex          this parameter is the column index of the
     *                          tile that is currently being checked. It ranges
     *                          from 0 to the length of the board. This is an
     *                          integer.
     *
     * @param rowIndex          this parameter is the row index of the
     *                          tile that is currently being checked.
     *                          It ranges from 0 to the length of the
     *                          board. This is an integer
     *
     * @param projectID         this parameter is a string which contrains
     *                          the project or feature of the tile that needs
     *                          to be checked. It is in the form of C1 or R2
     *
     * @param alreadyChecked    this parameter is all the tiles which have
     *                          previously been checked, they are all a part
     *                          of the big project
     *
     * @return                  returns a arraylist of objects which are all
     *                          a part of the Tile class. There are all the Tiles
     *                          that were checked and therefore all tiles part
     *                          of the big project.
     */
    private ArrayList<Tile> checkFinishProject(Tile tile, int colIndex, int rowIndex, String projectID, ArrayList<Tile> alreadyChecked){
        // creating an array of the checked tiles
        ArrayList<Tile> checked = new ArrayList<>();

        // adding the current tile to the checked tiles
        checked.add(tile);
        // add all the other tiles that were checked from previous recursion calls to this list
        checked.addAll(alreadyChecked);

        // obtain the sides of the current tile
        String[] sides = tile.getSides();

        // loop through the side of the current tile
        for(int i = 0; i < 4; i++){
            // checking wether this sides project is the same as the side withthe project connected to the bigger project
            if(sides[i].equals(projectID)){
                // creating an array list of all the projects in the current tile
                ArrayList<Project> projects = tile.getProjects();

                // looping through all the projects of the current tile
                for (Project project : projects) {
                    // checking if the current project is equal to the project that was give by the side
                    if ((int) project.getType().charAt(0) == (int) projectID.charAt(0) + 32 &&
                            (int) project.getType().charAt(project.getType().length() - 1) == (int) projectID.charAt(1)) {
                        // if so we add the project to the list of projects with all the individual projects of the big project
                        projectsInProject.add(project);
                    }
                }

                // checking if the side which is part of the project is at the top or bottom of the tile
                if(i == 0 || i == 2){
                    // if so we create a new tile
                    Tile newTile;
                    try{
                        // try to get the tile that is directly above it or below it
                        // if the side is the north side, we want the tile above it and vice versa
                        newTile = playedTile.get(colIndex).get(rowIndex - 1 + i);
                    }
                    catch(Exception e){
                        // if we get a error we make the tile null
                        newTile = null;
                    }

                    // set up a flag, which will be true if the tile is checked
                    boolean flag1 = false;

                    // loop through all the checked tiles
                    for (Tile value : checked) {
                        // checking if the current tile is a checked tile
                        if (value == newTile) {
                            // if so set the flag to true
                            flag1 = true;
                            break;
                        }
                    }

                    // checking if the flag is false
                    if(!flag1){
                        // if so we check if the tile which is connected to the side is not null
                        if(newTile != null){
                            // if this is the case we run the same function on the tile connected to that side and thus part of the big project
                            // sending it all the tiles already check includinng this one
                            checked.addAll(checkFinishProject(newTile, colIndex, rowIndex - 1 + i, newTile.getSides()[2 - i], checked));
                        }
                        else{
                            // if not the project is not finished as it is open on one end, therefore a null is added
                            checked.add(null);
                        }
                    }
                }
                else {
                    // if it isn't the top or bottom side then it is the east and west side
                    // we create a new tile
                    Tile newTile;
                    try{
                        // try to obtain the tile left or right of it
                        // if the side is the east side, we want the tile right of it and vice versa
                        newTile = playedTile.get(colIndex + 2 - i).get(rowIndex);
                    }
                    catch(Exception e){
                        // if this isn't possible the tile will be null
                        newTile = null;
                    }

                    // set up a flag, which will be true if the tile is checked
                    boolean flag1 = false;

                    // loop through all the checked tiles
                    for (Tile value : checked) {
                        // checking if the current tile is a checked tile
                        if (value == newTile) {
                            // if so set the flag to true
                            flag1 = true;
                            break;
                        }
                    }

                    // chekcing if the flag is false and thus the tile hasn't been checked
                    if(!flag1){
                        // checking if the tile above or below it actaully exits
                        if(newTile != null){
                            // if so we run the function on that tile, since this tile is also part of the overall project
                            // sending it all the tiles already check includinng this one
                            checked.addAll(checkFinishProject(newTile, colIndex + 2 - i, rowIndex, newTile.getSides()[4 - i], checked));
                        }
                        else{
                            // if the tile above or below it does exist then the project is still open somewhere so a null is added
                            checked.add(null);
                        }
                    }
                }
            }
        }

        // creating a secound arraylist of tiles that are checked
        ArrayList<Tile> checked2 = new ArrayList<>();

        // looping through the orignal arraylist of checked tiles
        for(int i = 0; i < checked.size(); i++){
            // checking is the current tile has already been added
            if(!checked2.contains(checked.get(i))){
                // if not we add the tile to the new arraylist
                checked2.add(checked.get(i));
            }
        }
        // retrun the whole arraylist of the checked tiles
        return checked2;
    }

    private void clearMeeples (ArrayList<Tile> entireProject, ArrayList<Meeple> deleteMeeples){
        // looping through the arraylist of tiles that is the whole project
        for(int j = 0; j < entireProject.size(); j ++){
            // looping through all the colums of the played tiles
            for(int k = 0; k < playedTile.size(); k ++) {
                // looping through every tile in this colume of tiles
                for (int l = 0; l < playedTile.get(k).size(); l++) {
                    // checking if the tile is equal a tile that is part of the porject
                    if (entireProject.get(j).equals(playedTile.get(k).get(l))) {
                        // if it is we loop through all the meeples that need to be deleted
                        for (int h = 0; h < deleteMeeples.size(); h++) {
                            // checking if the meeples x coordinated on the gird and the y coordinated match the x and y of the tiles
                            if (k == deleteMeeples.get(h).getXgrid() && deleteMeeples.get(h).getYgird() == l) {
                                // if so we obtain the tile that has a meeple that needs to be cleared
                                Tile tile = entireProject.get(j);
                                // we find the HBoc of that tile (this also gets rid of the gold ingots there)
                                HBox box = grid.get(k).get(l);
                                // clear the box getting rid of the Stackpane there
                                box.getChildren().clear();

                                // create a new image view for the tile so it can be displayed
                                ImageView tileImg = new ImageView();

                                // set the image of the tile being places to the image view
                                tileImg.setImage(tile.getImage());
                                // resizing the image view so that it fits in the HBox perfectly
                                tileImg.resize(80, 80);
                                // adjusting hieght and width so that it fits perfectly in the HBox
                                tileImg.setFitHeight(80);
                                tileImg.setFitWidth(80);
                                // adjusting the rotation to the rotation of the preview tile / the rotation the user choose
                                tileImg.setRotate(tile.getRotation());

                                // add the tile image to the tile
                                box.getChildren().add(tileImg);

                                deleteMeeples.get(h).getProject().setMeeple(null);
                                deleteMeeples.get(h).setProject(null);
                            }
                        }
                    }
                }
            }
        }
    }

    private int deleteGold(ArrayList<Tile> entireProject){
        // create a variable to track the amount of gold deleted
        int numGold = 0;

        // loop through all the tiles in the project
        for(int i = 0; i < entireProject.size(); i ++){
            // looping through all the goldmines expansion tiles
            for(int j = 0; j < GTiles.size(); j ++){
                // chekcing wether the current tile is part of the goldmiens expansion
                if(entireProject.get(i).getCode() == GTiles.get(j).getCode()){
                    // if so we obtain that tile as a GTile
                    GTile gTile = GTiles.get(j);

                    // checking if the tile still has a gold ingot
                    if(gTile.isHasGold()){
                        // we get rid of the gold box which included the image fo the gold ingot
                        gTile.getGoldBoxParent().getChildren().remove(gTile.getGoldBox());

                        // we take the gold of the tile
                        gTile.takeGold();

                        // add to the number of gold ingot deleted
                        numGold ++;
                    }
                }
            }
        }
        // return the number of gold that was deleted so that it can be distributed tot he players
        return numGold;
    }

    private void giveGoldPoints(int numGold, ArrayList<Player> winningPlayers){
        // obtain all the players
        ArrayList<Player> players = Variables.getPlayers();

        // sort the players in the order of which they have their turn
        winningPlayers.sort(Comparator.comparingInt(players::indexOf));

        // create a variable that keeps track of the player index
        int playersIndex = 0;

        // loop through the number of gold that got deleted (thus should be given to the players)
        for(int i = 0; i < numGold; i ++){
            // chekcing if the index is not bigger than the size of the winning players
            if(playersIndex >= winningPlayers.size()){
                // if it is we rest to the first player
                playersIndex = 0;
            }

            // give the player gold
            winningPlayers.get(playersIndex).giveGold();
            // update the number of gold they have on their waiting and turn box
            winningPlayers.get(playersIndex).getNumGoldTextTurn().setText(String.valueOf(Integer.parseInt(winningPlayers.get(playersIndex).getNumGoldTextTurn().getText()) + 1));
            winningPlayers.get(playersIndex).getNumGoldTextWaiting().setText(String.valueOf(Integer.parseInt(winningPlayers.get(playersIndex).getNumGoldTextWaiting().getText()) + 1));
            // increase the player index accoridngly
            playersIndex ++;
        }
    }

    private ArrayList<Meeple> getDeleteMeeples (ArrayList<Tile> entireProject){
        // create a arraylist of projects inorder to create a copy of the projectsInProject arraylist but without duplicates
        ArrayList<Project> projectsInProject2 = new ArrayList<>();
        // loop through the projectsInProject arraylist, to check each project
        for(int j = 0; j < projectsInProject.size(); j++){
            // checking if the new arraylist already contains the current porject
            if(!projectsInProject2.contains(projectsInProject.get(j))){
                // if it doesn't add the current project to the new array list
                projectsInProject2.add(projectsInProject.get(j));
            }
        }

        // creating an arraylist of meeples
        ArrayList<Meeple> allMeeples = new ArrayList<>();
        // loop through all the tiles included in the project
        for (int j = 0; j < entireProject.size(); j ++){
            // obtian all the projects of the current tile
            ArrayList<Project> tileProjects = entireProject.get(j).getProjects();
            // loop through all the projects
            for (int k = 0; k < tileProjects.size(); k ++){
                // add any meeple that is placed on that porject
                allMeeples.add(tileProjects.get(k).getMeeple());
            }
        }

        // looping through all the meeples collected
        for(int j = 0; j < allMeeples.size(); j ++){
            // checking if they are null
            if(allMeeples.get(j) == null){
                // if so we remove them
                allMeeples.remove(j);
                // update j becasue the size of the list has been decreased by 1
                j --;
            }
        }

        // checking if the arraylist of meeples is not null now
        if(!(allMeeples.size() == 0)) {
            // if it isn't create an arraylist of meeples that will include all the meeple that will have to be deleted
            ArrayList<Meeple> deleteMeeples = new ArrayList<>();
            // loop through all the meeples on the tiles which are a part of the project
            for (int j = 0; j < allMeeples.size(); j++) {
                // loop through all the porjects that are a part of the big project
                for (int k = 0; k < projectsInProject2.size(); k++) {
                    // checking if the project the meepl is on is equal the project that is part of the big project
                    if (allMeeples.get(j).getProject() == projectsInProject2.get(k)) {
                        // if it is that meeple needs to be deleted
                        deleteMeeples.add(allMeeples.get(j));
                    }
                }
            }
            // returning all meeples that need to be deleted
            return deleteMeeples;
        }
        return null;
    }

    private void checkChurch (Tile tile, boolean complete){
        // an x and y vairable are created these will become the x and y coordinated of the tile being checked on the grid
        int x = 0;
        int y = 0;
        // loop through the grid of tiles that have been placed (all tiles in their location)
        for(int i = 0; i < playedTile.size(); i++){
            // looping through the rows to access each tile individually
            for(int j = 0; j < playedTile.get(i).size(); j ++){
                // checking if the tile what we want to check is the tile in the grid (== is used becasue we want the exact same tile)
                if(playedTile.get(i).get(j) == tile){
                    // if so we save the x and y coordinates
                    x = i;
                    y = j;
                }
            }
        }

        // to check the church we want to start with the tile that is that north-west of  the tile checked
        int xStart = x - 1;
        int yStart = y - 1;

        // creating an array list of all the tiles that are around the church tile
        ArrayList<Tile> tilesAround = new ArrayList<>();

        // looping 3 times (the tile north-west, north and north-east of the church tile)
        for(int i = 0; i < 3; i ++){
            // looping 3 times for the 3 rows (top, middle, bottom)
            for(int j = 0; j < 3; j ++){
                // checking if the tile in this location is null
                if(playedTile.get(xStart + i).get(yStart + j) != null){
                    // if is isn't we check if this tile has a church
                    if(playedTile.get(xStart + i).get(yStart + j).isChurch()){
                        // we check if we have already checked a secound church in this recursion
                        if(complete){
                            // if not we have to check if this churhc is not complete by placing this tile
                            checkChurch(playedTile.get(xStart + i).get(yStart + j), false);
                        }
                    }
                    // adding the tile to the tiles that are around the chruch tile
                    tilesAround.add(playedTile.get(xStart + i).get(yStart + j));
                }
            }
        }

        //checking if we are done collecting the tiles around and if there are 9 tile around
        if(!complete && tilesAround.size() == 9){
            // creating an arraylist so that function can be reused
            ArrayList<Tile> project = new ArrayList<>();
            // adding the chruch tile which is complete to the arraylist
            project.add(tile);
            // creating an array list for the meeple (so that old fucntions can be reused)
            ArrayList<Meeple> meeple = new ArrayList<>();
            // saving all the projects on the tile
            ArrayList<Project> projects = tile.getProjects();
            // looping through all the projects of the tile
            for(int i = 0; i < projects.size(); i ++){
                // checking wether the project is a church
                if(projects.get(i).getType().charAt(0) == 'c'){
                    // if the project is a church, check wether it has a meeple on it
                    if(projects.get(i).getMeeple() != null){
                        // if it does add the meeple to the meeple arrayList
                        meeple.add((projects.get(i).getMeeple()));
                    }
                }
            }

            // checking wether there is a meeple on the church
            if(meeple.size() != 0){
                // runt he give back meeple function so the player gets their meeple back
                giveBackMeeples(meeple);
                // create a player variable to save the winning player
                Player player = null;
                // loop through all the players
                for(int i = 0; i < numPlayers; i ++){
                    // check if the colour of the plaeyr matches the colour of the meeple
                    if(Variables.getPlayers().get(i).getColour().equals(meeple.get(0).getColour())){
                        // if it does we save that player
                        player = Variables.getPlayers().get(i);
                    }
                }

                // set the variable with point equal to 9 (beacsue a completed chruch gives you 9 points)
                int points = 9;
                // make sure there is a winning player
                assert player != null;
                // give the winning player their points
                player.setPoints(player.getPoints() + points);
                // update the points on the player's turn and waiting box
                player.getTurnPoints().setText(String.valueOf(((Integer.parseInt(player.getTurnPoints().getText()) + points))));
                player.getWaitingPoints().setText(String.valueOf(((Integer.parseInt(player.getWaitingPoints().getText()) + points))));

                // get rid of the meeple that was placed on the church (it was already given back earlier)
                clearMeeples(project, meeple);

                // checking wether the user is playing with the goldmines expansion
                if(G){
                    // obtaint he number of gold that was around the church (the gold that will be distributed to the players)
                    int numGold = deleteGold(tilesAround);
                    // creating an arraylist of players (so that old functions can be used)
                    ArrayList<Player> player2 = new ArrayList<>();
                    // add the winning player to the array list
                    player2.add(player);
                    // use the give gold points function to distribute the gold ingots
                    giveGoldPoints(numGold, player2);
                }
            }
        }
    }

    private ArrayList<Tile> getTilesOfField(Tile tile, int colIndex, int rowIndex, String projectID, ArrayList<Tile> alreadyChecked){
        // create an arrayList for all the tiles that have already been checked
        ArrayList<Tile> checked = new ArrayList<>();

        // add the current tile to this arrayList
        checked.add(tile);
        // add all the other tiles from the previous arrayList
        checked.addAll(alreadyChecked);

        // obtain all the fields of the current tile
        String[] fields = tile.getFields();

        // loop through all the fields
        for(int i = 0; i < fields.length; i++){
            // checking wether the current field is part of the field that is being checked
            if(fields[i].charAt(0) == projectID.charAt(0)){
                // create an arraylist to save all the projects of the tile
                ArrayList<Project> projects = tile.getProjects();

                // loop through all the projects of the tile
                for(int j = 0; j < projects.size(); j++){
                    // checking if it is the field that we are looking for
                    if((int) projects.get(j).getType().charAt(projects.get(j).getType().length() - 1) == (int) projectID.charAt(0) && projects.get(j).getType().charAt(0) == 'f'){
                        // if it is then we add it to the arraylist tha contains all the smaller porjects of the big project
                        projectsInProject.add(projects.get(j));
                    }
                }

                // checking if the field is in the 0th or 5th index
                if(i == 0 || i == 5){
                    // creating a tile variable to save the tile that is below or under it
                    Tile newTile;
                    try{
                        // trying to obtain the tile that borders this tile above or below it (depends on what the index is)
                        newTile = playedTile.get(colIndex).get(rowIndex + ((2 * i - 5) / 5));
                    }
                    catch(Exception e){
                        // if the that isn't possible, save the tile as null
                        newTile = null;
                    }

                    // creating a flag boolean that will tell us  wether this new tile has been checked or not
                    boolean flag1 = false;
                    // loop through all the checked tiles
                    for(int j = 0; j < checked.size(); j++) {
                        // checking wether the checked tile is the same as the new tile
                        if(checked.get(j) == newTile) {
                            // if so setting the flag to true to indicate this
                            flag1 = true;
                        }
                    }

                    // checking if the flag is false (if it is false the tile hasn't been checked yet)
                    if(!flag1){
                        // checking if the new tile is not null (meaning there is no tile to be checked)
                        if(newTile != null){
                            // if all that is true we run this function again but on that tile (giving it all the tiles that have already been checked)
                            checked.addAll(getTilesOfField(newTile, colIndex, rowIndex + ((2 * i - 5) / 5), newTile.getFields()[i + (-2 * i + 5)], checked));
                        }
                    }
                }
                // checking if the field is in the 3rd or 6th index
                else if(i == 3 || i == 6){
                    // creating a tile variable to save the tile that is left or right of it
                    Tile newTile;
                    try{
                        // trying to obtain the tile that borders this tile left or right of it (depends on what the index is)
                        newTile = playedTile.get(colIndex + (((-2) * i + 9)/3)).get(rowIndex);
                    }
                    catch(Exception e){
                        // if the that isn't possible, save the tile as null
                        newTile = null;
                    }

                    // creating a flag boolean that will tell us  wether this new tile has been checked or not
                    boolean flag1 = false;
                    // loop through all the checked tiles
                    for(int j = 0; j < checked.size(); j++) {
                        // checking wether the checked tile is the same as the new tile
                        if(checked.get(j) == newTile) {
                            // if so setting the flag to true to indicate this
                            flag1 = true;
                        }
                    }

                    // checking if the flag is false (if it is false the tile hasn't been checked yet)
                    if(!flag1){
                        // checking if the new tile is not null (meaning there is no tile to be checked)
                        if(newTile != null){
                            // if all that is true we run this function again but on that tile (giving it all the tiles that have already been checked)
                            checked.addAll(getTilesOfField(newTile, colIndex + (((-2) * i + 9)/3), rowIndex, newTile.getFields()[i + ((-2) * i + 9)], checked));
                        }
                    }
                }
                // checking if the field is in the 2nd or 7th index
                else if(i == 2 || i == 7){
                    // creating a tile variable to save the tile that is left or right of it
                    Tile newTile;
                    try{
                        // trying to obtain the tile that borders this tile left or right below it (depends on what the index is)
                        newTile = playedTile.get(colIndex + (((-2) * i + 9)/5)).get(rowIndex);
                    }
                    catch(Exception e){
                        // if the that isn't possible, save the tile as null
                        newTile = null;
                    }

                    // creating a flag boolean that will tell us  wether this new tile has been checked or not
                    boolean flag1 = false;
                    // loop through all the checked tiles
                    for(int j = 0; j < checked.size(); j++) {
                        // checking wether the checked tile is the same as the new tile
                        if(checked.get(j) == newTile) {
                            // if so setting the flag to true to indicate this
                            flag1 = true;
                        }
                    }

                    // checking if the flag is false (if it is false the tile hasn't been checked yet)
                    if(!flag1){
                        // checking if the new tile is not null (meaning there is no tile to be checked)
                        if(newTile != null){
                            // if all that is true we run this function again but on that tile (giving it all the tiles that have already been checked)
                            checked.addAll(getTilesOfField(newTile, colIndex + (((-2) * i + 9)/5), rowIndex, newTile.getFields()[i + ((-2) * i + 9)], checked));
                        }
                    }
                }
                else {
                    // the field must be in the 1th or 4th index
                    // creating a tile variable to save the tile that is below or under it
                    Tile newTile;
                    try{
                        // trying to obtain the tile that borders this tile above or below it
                        // depends on what the index is
                        newTile = playedTile.get(colIndex).get(rowIndex + ((2 * i - 5)/3));
                    }
                    catch(Exception e){
                        // if the that isn't possible, save the tile as null
                        newTile = null;
                    }

                    // creating a flag boolean that will tell us  wether this new tile has been checked or not
                    boolean flag1 = false;
                    // loop through all the checked tiles
                    for(int j = 0; j < checked.size(); j++) {
                        // checking wether the checked tile is the same as the new tile
                        if(checked.get(j) == newTile) {
                            // if so setting the flag to true to indicate this
                            flag1 = true;
                        }
                    }

                    // checking if the flag is false (if it is false the tile hasn't been checked yet)
                    if(!flag1){
                        // checking if the new tile is not null (meaning there is no tile to be checked)
                        if(newTile != null){
                            // if all that is true we run this function again but on that tile (giving it all the tiles that have already been checked)
                            checked.addAll(getTilesOfField(newTile, colIndex, rowIndex + ((2 * i - 5)/3), newTile.getFields()[i + ((-2) * i + 5)], checked));
                        }
                    }
                }
            }
        }

        // creating an arrayList to save the checked tiles however this time no duplicates
        ArrayList<Tile> checked2 = new ArrayList<>();
        // looping through all the checked tiles
        for(int i = 0; i < checked.size(); i++){
            // checking if this tile has already been added to the secound check arrayList
            if(!checked2.contains(checked.get(i))){
                // if it hasn't then we add it tile
                checked2.add(checked.get(i));
            }
        }

        // returning all the tiles that have been checked because those are the tiles that are part of the project
        return checked2;
    }

    private void setUpPlayers(){
        // creating an arrayList to save all the player in
        ArrayList<Player> players = new ArrayList<>();
        // looping for the number of players (not the length of the players variable in the Variables class)
        for(int i = 0; i < Variables.getNumPlayers(); i ++){
            // add the players to the arrayList
            players.add(Variables.getPlayers().get(i));
        }
        // update variable of player in the Variables class
        Variables.setPlayers(players);

        // loop through all the players
        for(int i = 0; i < Variables.getNumPlayers(); i ++){
            // update the players turn box, updating the name and the points
            Variables.getPlayers().get(i).getTurnName().setText(Variables.getPlayers().get(i).getName());
            Variables.getPlayers().get(i).getTurnPoints().setText(String.valueOf(Variables.getPlayers().get(i).getPoints()));

            // update the players waiting box, updating the name and the points
            Variables.getPlayers().get(i).getWaitingName().setText(Variables.getPlayers().get(i).getName());
            Variables.getPlayers().get(i).getWaitingPoints().setText(String.valueOf(Variables.getPlayers().get(i).getPoints()));
        }

        // in the turn player box add the player which currently has their turn
        turnPlayerBox.getChildren().add(Variables.getPlayers().get(0).getTurnBox());

        // loop through all the remaining players (the players that dont have their turn yet)
        for(int i = 1; i < Variables.getNumPlayers(); i ++){
            // add the players whos turn it isnt to the waiting boxs
            playersBox.getChildren().add(Variables.getPlayers().get(i).getWaitingBox());
        }

        // checking if the user wants to play with the IC expansion
        if(IC){
            // if they do loop through all the players
            for(int i = 0; i < Variables.getNumPlayers(); i ++){
                // give each player a big meeple that they can use
                Variables.getPlayers().get(i).giveBigMeeple();
            }
        }
        else{
            // if the user isn't playing with the IC expansion loop through all players
            for(int i = 0; i < Variables.getNumPlayers(); i ++){
                // getting rid of the big meeple image in both the turn and waiting box
                Variables.getPlayers().get(i).getBigMeepleWaitPreview().setOpacity(0);
                Variables.getPlayers().get(i).getBigMeepleTurnPreview().setOpacity(0);
            }
        }

        // checking if the user is playing with the goldmines expansion
        if(!G){
            // if they don't loop through all the players
            for(int i = 0; i < Variables.getNumPlayers(); i ++){
                // get rid of the gold ingot counters for each player in the turn and waiting box
                Variables.getPlayers().get(i).getGoldBoxTurn().setOpacity(0);
                Variables.getPlayers().get(i).getGoldBoxWaiting().setOpacity(0);
            }
        }
    }

    private void rotatePlayers(){
        // obtain the players arraylist
        ArrayList<Player> players = Variables.getPlayers();

        // get the player which whos turn it currently is
        Player first = players.get(0);
        // loop through the players expcept for the last one
        for(int i = 0; i < players.size() - 1; i ++){
            // move all players up by 1, closer to the head of the arrayList
            players.set(i, players.get(i + 1));
        }
        // move the one previouly first to the back of the arrayList
        players.set(players.size() - 1, first);

        // update the players variable in teh Variables class
        Variables.setPlayers(players);

        // remove the players waiting box that now has their turn
        playersBox.getChildren().remove(0);
        // add the players waiting box who just finished their turn to the back
        playersBox.getChildren().add(players.get(numPlayers - 1).getWaitingBox());

        // get rid of the turn box
        turnPlayerBox.getChildren().clear();
        // add the player who currently has their turn in the turn box
        turnPlayerBox.getChildren().add(players.get(0).getTurnBox());
    }

    private void decreaseMeeple(){
        // obtain the player who currently has their turn
        Player player = Variables.getPlayers().get(0);

        // decrease their meeple counter by 1
        player.setNumMeeples(player.getNumMeeples() - 1);

        // checking if the secound row of meeple images in the turn box is already empty
        if(player.getTurnMeeplesBox2().getChildren().size() == 0){
            // if it is then remove a meeple from the first row of mmeeple images
            player.getTurnMeeplesBox1().getChildren().remove(0);
        }
        else{
            // if not there are still meeple images in the secound so we remove one
            player.getTurnMeeplesBox2().getChildren().remove(0);
        }

        // remove a meeple image from the waiting box
        player.getWaitingMeeplesBox().getChildren().remove(0);
    }

    private void decreaseBigMeeple(){
        // obtain the player who currently has their turn
        Player player = Variables.getPlayers().get(0);

        // get rid of their big meeple
        player.takeBigMeeple();

        // make the big meeple image invisble in both the turn and waiting box
        player.getBigMeepleWaitPreview().setOpacity(0);
        player.getBigMeepleTurnPreview().setOpacity(0);
    }

    private void giveBackMeeples(ArrayList<Meeple> meeples){
        // loop through all the meeples that need to be given back
        for(int i = 0; i < meeples.size(); i ++){
            // get one meeple individually
            Meeple meeple = meeples.get(i);

            // create a player variable which should be the player which the meeple is from
            Player player = null;

            // loop through the number of players (looping through the players
            for(int j = 0; j < numPlayers; j ++){
                // checking if the players colour matches the meeple colour
                if(Variables.getPlayers().get(j).getColour().equals(meeple.getColour())){
                    // if they match save the player and break the loop
                    player = Variables.getPlayers().get(j);
                    break;
                }
            }

            // checking if the player isn't null (this should never be the case)
            if(player != null){
                // checking if the user is playing with the IC expansion and the meeple is a big meeple
                if(IC && meeple.isBig()){
                    //if it is give back the players big meeple
                    player.giveBigMeeple();

                    // show the big meeple image once again in the turn and wait box
                    player.getBigMeepleWaitPreview().setOpacity(100);
                    player.getBigMeepleTurnPreview().setOpacity(100);
                }
                else {
                    // if it isn't a big meeple, increase the meeple counter of the player
                    player.setNumMeeples(player.getNumMeeples() + 1);

                    // create a new imageview for a meeple image for the waiting box (slightly smaller than the turn box)
                    ImageView waitingMeeple = new ImageView();
                    // update the size
                    waitingMeeple.setFitWidth(16);
                    waitingMeeple.setFitHeight(16);
                    // add the image of the meeple
                    waitingMeeple.setImage(player.getWaitingMeepleDisplay().getImage());

                    // add the imageview to their waiting box
                    player.getWaitingMeeplesBox().getChildren().add(waitingMeeple);

                    // create a new imageview for a meeple image for the turn box (slightly bigger than the waiting box)
                    ImageView turnMeeple = new ImageView();
                    // update size
                    turnMeeple.setFitHeight(37);
                    turnMeeple.setFitWidth(37);
                    // add the image of the meeple
                    turnMeeple.setImage(player.getTurnMeepleDisplay().getImage());

                    // checking if the first row of meeple images is already full
                    if (player.getTurnMeeplesBox1().getChildren().size() < 3) {
                        // if it isn't we add the meeple image to the first row
                        player.getTurnMeeplesBox1().getChildren().add(turnMeeple);
                    }
                    else {
                        //if it is add the meeple image to the secound row
                        player.getTurnMeeplesBox2().getChildren().add(turnMeeple);
                    }
                }
            }
        }
    }

    private ArrayList<Player> calculateProjectWinner(ArrayList<Meeple> meeples){
        // create an arraylist for the winning players
        ArrayList<Player> winningPlayers = new ArrayList<>();

        // create an arraylist to save all the meeple colours of the meeples
        ArrayList<String> meepleColours = new ArrayList<>();
        // loop through all the meeples
        for (int i = 0; i < meeples.size(); i ++){
            // checking if the meeple is big and if the user is playing with the IC expansion
            if(IC && meeples.get(i).isBig()){
                // if this is the case add the meeple colour to the arraylist (should get added twice now)
                meepleColours.add(meeples.get(i).getColour());
            }
            // add the meeple colour to the arraylist
            meepleColours.add(meeples.get(i).getColour());
        }

        // sort the meeple colours arrayList
        Collections.sort(meepleColours);

        // create a counter variable
        int counter = 0;
        // make a variable to same the number the most common colour is in the arraylist
        int occurancesOfMostCommonColour = 0;
        // create an string to save the colour of the meeple
        String colourChecking = "";

        // loop through all the colours
        for(int i = 0; i < meepleColours.size(); i ++){
            // checking if the colour is equal to the current oclour being checked
            if(meepleColours.get(i).equals(colourChecking)){
                // if is it, increase the counter
                counter ++;
            }
            else{
                // if it isn't we must be on the next colour
                // checking if the counter is bigger than the current most common colour
                if(counter > occurancesOfMostCommonColour) {
                    // if it is that is the new most common colour
                    occurancesOfMostCommonColour = counter;
                }
                // reset the counter to 1 (not 0, otherwise we wouldn't count the current one)
                counter = 1;
                // update colour checking currently
                colourChecking = meepleColours.get(i);
            }
        }

        // last check if the counter is larger than the most common colour
        if(counter > occurancesOfMostCommonColour) {
            // if so update the most common occurance
            occurancesOfMostCommonColour = counter;
        }
        // update the counter back to 0 so it can be reused
        counter = 0;

        // create a array list of the most common colours
        ArrayList<String> mostCommonColours = new ArrayList<>();

        // loop through all the meeple colours
        for(int i = 0; i < meepleColours.size(); i ++){
            // checking if the meeple colour is equal to the colour currently checking
            if(meepleColours.get(i).equals(colourChecking)){
                // if so increase the counter
                counter ++;
            }
            else{
                // if not checking if the counter is equal to the most common occurance of a colour
                if(counter == occurancesOfMostCommonColour) {
                    // if it is add the colour to the most common colours arraylist
                    mostCommonColours.add(meepleColours.get(i - 1));
                }
                // reset the counter to 1 (not 0, otherwise we wouldn't count the current one)
                counter = 1;
                // update colour checking currently
                colourChecking = meepleColours.get(i);
            }
        }

        // last check if the counter is equal to the most common occurance of a colour
        if(counter == occurancesOfMostCommonColour && counter != 0) {
            // if it is add the colour to the most common colours arraylist
            mostCommonColours.add(meepleColours.get(meepleColours.size() - 1));
        }

        // loop through the most common colours
        for(int i = 0; i < mostCommonColours.size(); i ++){
            // loop through the players
            for(int j = 0; j < numPlayers; j ++){
                //checking if the player colour is equal to the most common colour
                if(Variables.getPlayers().get(j).getColour().equals(mostCommonColours.get(i))){
                    // if so then we add the player to the winning players arraylist
                    winningPlayers.add(Variables.getPlayers().get(j));
                }
            }
        }
        // return the winning players
        return winningPlayers;
    }

    private int calculatePoints (ArrayList<Tile> entireProject, String type, boolean end){
        // check if the project is a city
        if(type.charAt(0) == 'C'){
            // if it is every tiles is 2 points so the size of the project times 2
            int points  = entireProject.size() * 2;
            // create a variable which will tell us if the city has a cathedral
            boolean catherdrale = false;

            // create another arraylist to contain all the projects within the big project (this time without duplicats)
            ArrayList<Project> projectsInProject2 = new ArrayList<>();
            // loop through the orignal arraylist
            for(int j = 0; j < projectsInProject.size(); j++){
                // check if it has already been added
                if(!projectsInProject2.contains(projectsInProject.get(j))){
                    // if it hasnt add the project
                    projectsInProject2.add(projectsInProject.get(j));
                }
            }

            //  loop through all the smaller projects of the big project
            for(int i = 0; i < projectsInProject2.size(); i ++){
                // checking if the project has a shield
                if(projectsInProject2.get(i).isShield()){
                    // if it does 2 extra points for each shield
                    points += 2;
                }
                // looping through all the IC project inorder to check if the project is a IC project
                for(int j = 0; j < ICProjects.size(); j ++){
                    // checking if the current project is the same as the IC project
                    if(projectsInProject2.get(i).equals(ICProjects.get(j))){
                        // if they are equal check if the IC project has a cathedrale
                        if(ICProjects.get(j).isCathedrale()){
                            // if it does have a cathedral set the cathedral value to true
                            catherdrale = true;
                        }
                    }
                }
            }

            // checking if the project has a cathedral and checking if it isn't the end of the game
            if(catherdrale && !end){
                // if it isn't the end and has a cathedral each tile and shield is 3 points instead of 2
                points = (points/2) * 3;
            }
            // checking if it is the end and if there is a catherdral
            else if(end && catherdrale){
                // if it is the end and it has a cathedrale the whole project is 0 points
                points = 0;
            }

            // returning the points
            return points;
        }
        // checking if the project is a road
        else if(type.charAt(0) == 'R'){
            // checking if the user is playing with the IC expansion
            if(IC){
                // creating a inn boolean which will tell us if the road has a Inn
                boolean inn = false;

                // create another arraylist to contain all the projects within the big project (this time without duplicats)
                ArrayList<Project> projectsInProject2 = new ArrayList<>();
                // loop through the orignal arraylist
                for(int j = 0; j < projectsInProject.size(); j++){
                    // check if it has already been added
                    if(!projectsInProject2.contains(projectsInProject.get(j))){
                        // if it hasnt add the project
                        projectsInProject2.add(projectsInProject.get(j));
                    }
                }

                //  loop through all the smaller projects of the big project
                for(int i = 0; i < projectsInProject2.size(); i ++){
                    // looping through all the IC projects to see if the project is a IC project
                    for(int j = 0; j < ICProjects.size(); j ++){
                        // checking to see if the current proejct matchs the IC project
                        if(projectsInProject2.get(i).equals(ICProjects.get(j))){
                            // if it is a IC project, check if the IC project has a inn
                            if(ICProjects.get(j).isInn()){
                                // if the project does have a inn set the Inn variable to the true
                                inn = true;
                            }
                        }
                    }
                }

                // chekcing if the project has a inn and if it is the end of the game
                if(inn && !end){
                    //  if it isn't the end of the game and has a inn each tiles is 2 points instead of 1
                    return entireProject.size() * 2;
                }
                // checking if it is the end of the game and has a inn
                else if(end && inn){
                    // if it is the end and the road has a inn the whole project is 0 points
                    return 0;
                }
                else{
                    // otherwise it is just worth 1 point per tile
                    return entireProject.size();
                }
            }
            else{
                // no special rules so just 1 point per tile
                return entireProject.size();
            }
        }
        // this it should never reach but just in case it is there
        return 0;
    }

    public void openTilesLeftScreen() throws IOException {
        // update the tilesNotPLayed variable in the variables class
        Variables.setTilesNotPlayed(new ArrayList<>(tiles));

        // make a fxml loader with he fxml file of the tiles left screen
        FXMLLoader fxmlLoader5 = new FXMLLoader(CarcassonneApplication.class.getResource("tileLeftScreen.fxml"));
        // set the scene
        Scene tileLeftScene = new Scene(fxmlLoader5.load());
        // create a stage
        Stage tilesLeftStage = new Stage();

        // add a title to the screen
        tilesLeftStage.setTitle("Tile Left");
        // set the stage to the scene
        tilesLeftStage.setScene(tileLeftScene);
        // show the stage to the user
        tilesLeftStage.show();
    }

    private void ending() throws IOException {
        // create an arraylist to save all the field projects in the game with their tiles
        ArrayList<ArrayList<Tile>> allFields = new ArrayList<>();
        // create an arraylist to save all the field projects in the game with their projects
        ArrayList<ArrayList<Project>> allFieldsProjects = new ArrayList<>();

        // create an arraylist to save all the city projects in the game with their tiles
        ArrayList<ArrayList<Tile>> allCities = new ArrayList<>();
        // create an arraylist to save all the city projects in the game with their projects
        ArrayList<ArrayList<Project>> allCitiesProjects = new ArrayList<>();

        // create an arraylist to save all the road projects in the game with their tiles
        ArrayList<ArrayList<Tile>> allRoads = new ArrayList<>();
        // create an arraylist to save all the road projects in the game with their projects
        ArrayList<ArrayList<Project>> allRoadsProjects = new ArrayList<>();

        // loop through all the colums of the played tiles
        for(int i = 0; i < playedTile.size(); i ++) {
            // loop through all the row of the played tiles so each tile is accessed individually
            for (int j = 0; j < playedTile.get(0).size(); j++) {
                // obtain the current tile through the row and column index
                Tile currentTile = playedTile.get(i).get(j);

                // checking if the current tile is null
                if (currentTile != null) {
                    // if it isn't we retrieve their fields
                    String[] fields = currentTile.getFields();

                    // looping through all the fields
                    for (int k = 0; k < fields.length; k ++) {
                        // clearing the projects in project variable so that it can be reused
                        projectsInProject.clear();
                        // clearing the projects in project variable so that it can be reused
                        ArrayList<Tile> checked = new ArrayList<>();
                        // running the get tiles of field function on this tile so that the field is obtained
                        ArrayList<Tile> entireProject = getTilesOfField(currentTile, i, j, currentTile.getFields()[k], checked);

                        // add the tiles to the all field arraylist
                        allFields.add(entireProject);

                        // creating a copy of the projects in project so that it doesnt get altered
                        ArrayList<Project> copyProjectsInProject = new ArrayList<>(projectsInProject);
                        // saving the copy in the all field projects array
                        allFieldsProjects.add(copyProjectsInProject);
                    }

                    // retrieving all the sides of the current tile
                    String[] sides = currentTile.getSides();

                    // looping through all the sides
                    for (int k = 0; k < sides.length; k++) {
                        // clearing the projects in project variable so that it can be reused
                        projectsInProject.clear();
                        // clearing the projects in project variable so that it can be reused
                        ArrayList<Tile> checked = new ArrayList<>();
                        // creating an arraylist to put the project tiles in
                        ArrayList<Tile> entireProject;
                        // checking if the project is a city
                        if (sides[k].charAt(0) == 'C') {
                            // if it is then we run the check finished project, which return all the tiles that are part of the city
                            entireProject = checkFinishProject(currentTile, i, j, sides[k], checked);

                            // checking if it contains null because if it doesn't the project is complete and therefore the points will already be handed out points
                            if (entireProject.contains(null)) {
                                // set up an array to make a copy of the entire project varible (this is to make sure there aren't any nulls in the final list)
                                ArrayList<Tile> copyEntireProject = new ArrayList<>();
                                // loop through the projects variable
                                for (int l = 0; l < entireProject.size(); l++) {
                                    // checking if it is a null
                                    if (entireProject.get(l) != null) {
                                        // if it isn't then we add the tile
                                        copyEntireProject.add(entireProject.get(l));
                                    }
                                }

                                // add the copy of the project tiles to the all cities arraylist
                                allCities.add(copyEntireProject);
                                // making a copy of the project in porject array so that it doesn't alter if one is altered
                                ArrayList<Project> copyProjectsInProject = new ArrayList<>(projectsInProject);
                                // add the copy to the city porjects arraylist
                                allCitiesProjects.add(copyProjectsInProject);
                            }
                        }
                        // checking if the project is a road
                        else if (sides[k].charAt(0) == 'R') {
                            // if it is then we run the check finished project, which returns all the tiles that are part of the road
                            entireProject = checkFinishProject(currentTile, i, j, sides[k], checked);

                            // checking if it contains null because if it doesn't the project is complete and therefore the points will already be handed out points
                            if (entireProject.contains(null)) {
                                // set up an array to make a copy of the entire project variable (this is to make sure there aren't any nulls in the final list)
                                ArrayList<Tile> copyEntireProject = new ArrayList<>();
                                // loop through the projects variable
                                for (int l = 0; l < entireProject.size(); l++) {
                                    // checking if it is a null
                                    if (entireProject.get(l) != null) {
                                        // if it isn't then we add the tile
                                        copyEntireProject.add(entireProject.get(l));
                                    }
                                }

                                // add the copy of the project tiles to the all roads arraylist
                                allRoads.add(copyEntireProject);
                                // making a copy of the project in porject array so that it doesn't alter if one is altered
                                ArrayList<Project> copyProjectsInProject = new ArrayList<>(projectsInProject);
                                // add the copy to the road porjects arraylist
                                allRoadsProjects.add(copyProjectsInProject);
                            }
                        }
                    }

                    // checking if the current tile has a church
                    if(currentTile.isChurch()){
                        // if it does have a church loop through the projects of the current tile
                        for(int k = 0; k < currentTile.getProjects().size(); k ++){
                            // checking if the project is a church and if it has a meeple on it
                            if(currentTile.getProjects().get(k).getType().equals("church1") && currentTile.getProjects().get(k).getMeeple() != null){
                                // if it does points must be rewarded

                                // creating a points variable starting at 9 and should slowly decrease to the right amount of points
                                int points = 9;
                                // creating 2 variables that will tell us if the tile is at a border of not
                                boolean iBorder = false;
                                boolean jBorder = false;

                                // checking if i which is the colonm index is 0 or if it is the last index
                                if(i == 0 || i == playedTile.size() - 1){
                                    // if one is true then one whole side there are no tiles so 3 points are subtracted
                                    points -= 3;
                                    // the i border is set to true because the tile is at a colume border
                                    iBorder = true;
                                }
                                // checking if j is 0, j is the row index, so if it is 0 it is at the top row *no rows above it), or of j is the last index *no more rows under it)
                                if(j == 0 || j == playedTile.get(i).size() - 1){
                                    // checking if the tile is already at a west or south border
                                    if(iBorder){
                                        // if it is one point is already subtracted so only 2 points need to be subtracted
                                        points -= 2;
                                    }
                                    else{
                                        // if is isn't 3 whole tiles are unable to be above or below it so 3 points are not gained
                                        points -= 3;
                                    }
                                    // setting the jBorder value to true to tell that it is on a row border
                                    jBorder = true;
                                }

                                // checking if it is not at any border and if the tile directly north west from it is null
                                if(!iBorder && !jBorder && playedTile.get(i - 1).get(j - 1) == null){
                                    // if this is the case they are missing a tile north west of it so one points subtracted
                                    points -= 1;
                                }
                                // checking if it is not at any border and if the tile directly north east from it is null
                                if(!iBorder && !jBorder && playedTile.get(i + 1).get(j - 1) == null){
                                    // if this is the case they are missing a tile north east of it so one points subtracted
                                    points -= 1;
                                }
                                // checking if it is not at any border and if the tile directly south east from it is null
                                if(!iBorder && !jBorder && playedTile.get(i - 1).get(j + 1) == null){
                                    // if this is the case they are missing a tile south west of it so one points subtracted
                                    points -= 1;
                                }
                                // checking if it is not at any border and if the tile directly south west from it is null
                                if(!iBorder && !jBorder && playedTile.get(i + 1).get(j + 1) == null){
                                    // if this is the case they are missing a tile south east of it so one points subtracted
                                    points -= 1;
                                }

                                // checking if it isn't on a west or east border, and if the tiles directly west of it is null
                                if(!iBorder && playedTile.get(i - 1).get(j) == null){
                                    // if this is the case then there is no tile west of it even though there could be one so that is one less points
                                    points -= 1;
                                }
                                // checking if it isn't on a west or east border, and if the tiles directly east of it is null
                                if(!iBorder && playedTile.get(i + 1).get(j) == null){
                                    // if this is the case then there is no tile weast of it even though there could be one so that is one less points
                                    points -= 1;
                                }
                                // checking if it isn't on a north or south border, and if the tiles directly north of it is null
                                if(!jBorder && playedTile.get(i).get(j - 1) == null){
                                    // if this is the case then there is no tile north of it even though there could be one so that is one less points
                                    points -= 1;
                                }
                                // checking if it isn't on a north or south border, and if the tiles directly south of it is null
                                if(!jBorder && playedTile.get(i).get(j + 1) == null){
                                    // if this is the case then there is no tile south of it even though there could be one so that is one less points
                                    points -= 1;
                                }

                                // creating an arraylist to store the meeple that is one the chruch (an arraylist is used so old functions can be reused)
                                ArrayList<Meeple> meeples = new ArrayList<>();
                                // add the meeple that is on the chruch
                                meeples.add(currentTile.getProjects().get(k).getMeeple());

                                // create an arraylist of the winning player, using the calculate winner function to figure out which player the meeple belongs to
                                ArrayList<Player> winningPlayers = calculateProjectWinner(meeples);

                                // looping through all the winning players (in this case it should only be one)
                                for(int l = 0; l < winningPlayers.size(); l ++){
                                    // updating the points in the player class
                                    winningPlayers.get(l).setPoints(winningPlayers.get(l).getPoints() + points);
                                }
                            }
                        }
                    }
                }
            }
        }

        // create another array list to contain all the field tiles in (a copy is made to get rid of any duplicated)
        ArrayList<ArrayList<Tile>> allFields2 = new ArrayList<>();
        // create another array list to contain all the field projects in (a copy is made to get rid of any duplicated)
        ArrayList<ArrayList<Project>> allFieldsProject2 = new ArrayList<>();

        // loop through all the fields (the project instance of this variable should be the same length because it has the same amount of fields)
        for(int i = 0; i < allFields.size(); i ++){
            // sorting the tiles in the tile bundle that makes up the big project, sorting them by their unique code
            allFields.get(i).sort(Comparator.comparing(Tile::getCode));

            // checking if the copy already has this field
            if(!allFields2.contains(allFields.get(i))){
                // if it doesn't then add both the tiles bundle and the projects bundle to their arraylist copies
                allFields2.add(allFields.get(i));
                allFieldsProject2.add(allFieldsProjects.get(i));
            }
        }

        // create another array list to contain all the city tiles in (a copy is made to get rid of any duplicated)
        ArrayList<ArrayList<Tile>> allCities2 = new ArrayList<>();
        // create another array list to contain all the city projects in (a copy is made to get rid of any duplicated)
        ArrayList<ArrayList<Project>> allCitiesProject2 = new ArrayList<>();

        // loop through all the cities (the project instance of this variable should be the same length because it has the same amount of cities)
        for(int i = 0; i < allCities.size(); i ++) {
            // sorting the tiles in the tile bundle that makes up the big project, sorting them by their unique code
            allCities.get(i).sort(Comparator.comparing(Tile::getCode));

            // checking if the copy already has this city
            if (!allCities2.contains(allCities.get(i))) {
                // if it doesn't then add both the tiles bundle and the projects bundle to their arraylist copies
                allCities2.add(allCities.get(i));
                allCitiesProject2.add(allCitiesProjects.get(i));
            }
        }

        // create another array list to contain all the road tiles in (a copy is made to get rid of any duplicated)
        ArrayList<ArrayList<Tile>> allRoads2 = new ArrayList<>();
        // create another array list to contain all the road projects in (a copy is made to get rid of any duplicated)
        ArrayList<ArrayList<Project>> allRoadsProject2 = new ArrayList<>();

        // loop through all the roads (the project instance of this variable should be the same length because it has the same amount of roads)
        for(int i = 0; i < allRoads.size(); i ++){
            // sorting the tiles in the tile bundle that makes up the big project, sorting them by their unique code
            allRoads.get(i).sort(Comparator.comparing(Tile::getCode));

            // checking if the copy already has this road
            if(!allRoads2.contains(allRoads.get(i))){
                // if it doesn't then add both the tiles bundle and the projects bundle to their arraylist copies
                allRoads2.add(allRoads.get(i));
                allRoadsProject2.add(allRoadsProjects.get(i));
            }
        }

        // looping through all the fields with out duplicates
        for(int i = 0; i < allFields2.size(); i ++){
            // using the calculate field points function to calculate the number points and save the
            int points = caclculateFieldPoints(allFieldsProject2.get(i), allFields2.get(i));

            // get all the projects of the current field
            projectsInProject = allFieldsProject2.get(i);

            // create an arraylist to save all the meeples that are placed on the field, using the get delete meeples function to obtain those meeples
            ArrayList<Meeple> meeples = getDeleteMeeples(allFields2.get(i));

            // checking if there are any meeples on the field
            if(meeples != null && meeples.size() != 0){
                // if there are create a winning players arraylist and use teh calculate project winner function to obtain the players that won
                ArrayList<Player> winningPlayers = calculateProjectWinner(meeples);

                // loop through all the players that won
                for(int j = 0; j < winningPlayers.size(); j ++){
                    // updating their points in their class
                    winningPlayers.get(j).setPoints(winningPlayers.get(j).getPoints() + points);
                }
            }
        }

        // looping through all the cities with out duplicates
        for(int i = 0; i < allCities2.size(); i ++){
            // get all the projects of the current city
            projectsInProject = allCitiesProject2.get(i);

            // using the calculate points function to calculate the number points and save the
            int points = calculatePoints(allCities2.get(i), "C1", true);
            // dividing by 2 because at the end of the game cties are worth half their points
            points = points / 2;

            // create an arraylist to save all the meeples that are placed on the city, using the get delete meeples function to obtain those meeples
            ArrayList<Meeple> meeples = getDeleteMeeples(allCities2.get(i));

            // checking if there are any meeples on the city
            if(meeples != null && meeples.size() != 0){
                // if there are create a winning players arraylist and use teh calculate project winner function to obtain the players that won
                ArrayList<Player> winningPlayers = calculateProjectWinner(meeples);

                // loop through all the players that won
                for(int j = 0; j < winningPlayers.size(); j ++){
                    // updating their points in their class
                    winningPlayers.get(j).setPoints(winningPlayers.get(j).getPoints() + points);
                }
            }
        }

        // looping through all the cities with out duplicates
        for(int i = 0; i < allRoads2.size(); i ++){
            // get all the projects of the current city
            projectsInProject = allRoadsProject2.get(i);

            // using the calculate points function to calculate the number points and save the
            int points = calculatePoints(allRoads2.get(i), "R1", true);

            // create an arraylist to save all the meeples that are placed on the city, using the get delete meeples function to obtain those meeples
            ArrayList<Meeple> meeples = getDeleteMeeples(allRoads2.get(i));

            // checking if there are any meeples on the city
            if(meeples != null && meeples.size() != 0){
                // if there are create a winning players arraylist and use teh calculate project winner function to obtain the players that won
                ArrayList<Player> winningPlayers = calculateProjectWinner(meeples);

                // loop through all the players that won
                for(int j = 0; j < winningPlayers.size(); j ++){
                    // updating their points in their class
                    winningPlayers.get(j).setPoints(winningPlayers.get(j).getPoints() + points);
                }
            }
        }

        // checking if the user is playing with the goldmines expansion
        if(G){
            // if they are then the gold points have to be calculated

            // loop through all the players
            for(int i = 0; i < Variables.players.size(); i ++){
                // retreive the current player
                Player player = Variables.players.get(i);

                // check if they have between the 1 and 3 gold ingots
                if(player.getNumGold() >= 1 && player.getNumGold() <= 3){
                    // if they do each ingot is worth 1 point so that is updated
                    player.setPoints(player.getPoints() + player.getNumGold());
                }
                // checki if they have between 4 and 6 gold ingots
                else if(player.getNumGold() >= 4 && player.getNumGold() <= 6){
                    // if they do each ingot is worth 2 points, the points are calculated and added
                    player.setPoints(player.getPoints() + (player.getNumGold() * 2));
                }
                // checking if the player has between the 7 and 9 gold ingots
                else if(player.getNumGold() >= 7 && player.getNumGold() <= 9){
                    // if they do each ingot is worth 3 points, the points are calculated and added
                    player.setPoints(player.getPoints() + (player.getNumGold() * 3));
                }
                // checking if the player has above 10 gold ingots
                else if(player.getNumGold() >= 10){
                    // if the player does each ingot is worth 4 points, the points are calculated and added
                    player.setPoints(player.getPoints() + (player.getNumGold() * 4));
                }
            }
        }

        // disabling the screen, so the user can't do anything any more
        screen.setDisable(true);

        // running the fucntion that will open the ending screen and close the game screen
        openEndingScreen();
    }

    private int caclculateFieldPoints(ArrayList<Project> projects, ArrayList<Tile> field) {
        // set up a points variable which will keep track of the poitns the field is worth
        int points = 0;

        // creating an duplicate of the coleded cities arraylist (this is to get rid of all the duplicates)
        ArrayList<ArrayList<Project>> completedCitiesCopy = new ArrayList<>();

        // loop through the completed cities
        for (int i = 0; i < completedCities.size(); i++) {
            // check if the city has been added already
            if (!completedCitiesCopy.contains(completedCities.get(i))) {
                // if it hasn't, it is added to the list
                completedCitiesCopy.add(completedCities.get(i));
            }
        }

        // looping through all small projects that are part of the big field porject
        for (int i = 0; i < projects.size(); i++) {
            // checking if the field arraylist has the tile of the project
            if (field.contains(projects.get(i).getTile())) {
                // if it does we retrieve that tile
                Tile tile = field.get(field.indexOf(projects.get(i).getTile()));

                // loop through all the fields of the tile
                for (int j = 0; j < tile.getFields().length; j++) {
                    // checking if the field side is part of the small project (the same project)
                    if(tile.getFields()[j].charAt(0) == projects.get(i).getType().charAt(projects.get(i).getType().length() - 1)) {
                        // if it is checking if we aren't checking the last field
                        if (j != 7) {
                            // if it isn't the last the checking if the field to the left of it is a city instead of a field
                            if (tile.getFields()[j + 1].equals("c")) {
                                // if it is then obtain the index of the side instead of the field side (the difference being the side is the 4 sides of the tiles, however the field side splits those side in half)
                                String cityID = tile.getSides()[(j + 1) / 2];

                                // checking if the there are actaully completed cities
                                if (completedCitiesCopy.size() != 0) {
                                    // if there are loop through the completed cities
                                    for (int l = 0; l < completedCitiesCopy.size(); l++) {
                                        // loop through the individual projects of the completed cities
                                        for (int o = 0; o < completedCitiesCopy.get(l).size(); o++) {
                                            // retrieve the current project of the city
                                            Project currentProject = completedCitiesCopy.get(l).get(o);

                                            // also retrieve the current of the project
                                            Tile currentTile = currentProject.getTile();

                                            // checking if the current tile is equal the tile that the field project was on, and if the city ID matches the one of the side which borders the field
                                            if (currentTile == tile && cityID.charAt(1) == currentProject.getType().charAt(currentProject.getType().length() - 1)) {
                                                // if this is the case 3 points are rewarded
                                                points += 3;
                                                // removing the city so it isn't counted twice
                                                completedCitiesCopy.remove(l);
                                            }
                                            // checking if there are any completed cities left
                                            if (completedCitiesCopy.size() <= l) {
                                                // if there aren't break out of the loop tio prevent an error
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            // if it is the 7th field side, check if the field side in the first posistion is a city (this woudl therefore still border it)
                            if (tile.getFields()[0].equals("c")) {
                                // if it isn't the last the checking if the field to the left of it is a city instead of a field
                                String cityID = tile.getSides()[0];

                                // checking if the there are actaully completed cities
                                if (completedCitiesCopy.size() != 0) {
                                    // if there are loop through the completed cities
                                    for (int l = 0; l < completedCitiesCopy.size(); l++) {
                                        // loop through the individual projects of the completed cities
                                        for (int o = 0; o < completedCitiesCopy.get(l).size(); o++) {
                                            // retrieve the current project of the city
                                            Project currentProject = completedCitiesCopy.get(l).get(o);

                                            // also retrieve the current of the project
                                            Tile currentTile = currentProject.getTile();

                                            // checking if the current tile is equal the tile that the field project was on, and if the city ID matches the one of the side which borders the field
                                            if (currentTile == tile && cityID.charAt(1) == currentProject.getType().charAt(currentProject.getType().length() - 1)) {
                                                // if this is the case 3 points are rewarded
                                                points += 3;
                                                // removing the city so it isn't counted twice
                                                completedCitiesCopy.remove(l);
                                            }
                                            // checking if there are any completed cities left
                                            if (completedCitiesCopy.size() <= l) {
                                                // if there aren't break out of the loop tio prevent an error
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // checking if we aren't checking the first field
                        if (j != 0) {
                            // if it isn't the last the checking if the field to the right of it is a city instead of a field
                            if (tile.getFields()[j - 1].equals("c")) {
                                // if it is then obtain the index of the side instead of the field side (the difference being the side is the 4 sides of the tiles, however the field side splits those side in half)
                                String cityID = tile.getSides()[(j - 1) / 2];

                                // checking if the there are actaully completed cities
                                if (completedCitiesCopy.size() != 0) {
                                    // if there are loop through the completed cities
                                    for (int l = 0; l < completedCitiesCopy.size(); l++) {
                                        // loop through the individual projects of the completed cities
                                        for (int o = 0; o < completedCitiesCopy.get(l).size(); o++) {
                                            // retrieve the current project of the city
                                            Project currentProject = completedCitiesCopy.get(l).get(o);

                                            // also retrieve the current of the project
                                            Tile currentTile = currentProject.getTile();

                                            // checking if the current tile is equal the tile that the field project was on, and if the city ID matches the one of the side which borders the field
                                            if (currentTile == tile && cityID.charAt(1) == currentProject.getType().charAt(currentProject.getType().length() - 1)) {
                                                // if this is the case 3 points are rewarded
                                                points += 3;
                                                // removing the city so it isn't counted twice
                                                completedCitiesCopy.remove(l);
                                            }
                                            // checking if there are any completed cities left
                                            if (completedCitiesCopy.size() <= l) {
                                                // if there aren't break out of the loop tio prevent an error
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            // if it is the 0th field side, check if the field side in the last posistion is a city (this would therefore still border it)
                            if (tile.getFields()[7].equals("c")) {
                                // if it is then obtain the last side which is the side which the 7th field side if on
                                String cityID = tile.getSides()[3];

                                // checking if the there are actaully completed cities
                                if (completedCitiesCopy.size() != 0) {
                                    // if there are loop through the completed cities
                                    for (int l = 0; l < completedCitiesCopy.size(); l++) {
                                        // loop through the individual projects of the completed cities
                                        for (int o = 0; o < completedCitiesCopy.get(l).size(); o++) {
                                            // retrieve the current project of the city
                                            Project currentProject = completedCitiesCopy.get(l).get(o);

                                            // also retrieve the current of the project
                                            Tile currentTile = currentProject.getTile();

                                            // checking if the current tile is equal the tile that the field project was on, and if the city ID matches the one of the side which borders the field
                                            if (currentTile == tile && cityID.charAt(1) == currentProject.getType().charAt(currentProject.getType().length() - 1)) {
                                                // if this is the case 3 points are rewarded
                                                points += 3;
                                                // removing the city so it isn't counted twice
                                                completedCitiesCopy.remove(l);
                                            }
                                            // checking if there are any completed cities left
                                            if (completedCitiesCopy.size() <= l) {
                                                // if there aren't break out of the loop tio prevent an error
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // returning the total number of points
        return points;
    }

    /**
     * <p>
     *     This method opens the ending screen which will display
     *     the results of the game.
     * </p>
     *
     * @throws IOException  This error happens when the FXML file
     *                      cannot be loaded because it is empty.
     */
    public void openEndingScreen() throws IOException {
        // creating a new FXML loader inorder to load the end screen fxml
        FXMLLoader fxmlLoader6 = new FXMLLoader(EndingScreenController.class.getResource("endingScreen.fxml"));
        // loop the FXML file in a new scene
        Scene scene6 = new Scene(fxmlLoader6.load());
        // get the stage that has already been set up for the end screen
        Stage stage6 = Variables.getEndStage();
        // give the window a title
        stage6.setTitle("Results");
        // set the scene for the stage
        stage6.setScene(scene6);
        // display the window so the user is able to see it
        stage6.show();

        // get rid of the game screen
        Variables.getGameStage().hide();
    }

    public void selectSmallMeeples(){
        // checking if the big meeple was sleected when the small meeple we pressed
        if(meepleselectionBigMeeple.getBackground().equals(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(10), new Insets(1))))){
            // if it was the big meeple background needs to be reset
            meepleselectionBigMeeple.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(10), new Insets(1))));
        }
        // change the background of the small meeple button to show the user it is selected
        meepleselectionSmallMeeple.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(10), new Insets(1))));

        // update the variable so the program knows it is selected
        meepleSelected = "small";
    }

    public void selectBigMeeples(){
        // checking if the small meeple was selected when the big meeple is selected
        if(meepleselectionSmallMeeple.getBackground().equals(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(10), new Insets(1))))){
            // if it is the small meeple box's background needs to be reset to its orignal colour
            meepleselectionSmallMeeple.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(10), new Insets(1))));
        }
        // update the big meeple background so the user is able to see that the big meeple is selected
        meepleselectionBigMeeple.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(10), new Insets(1))));

        // update the variable so that the program knows which one is selected
        meepleSelected = "big";
    }

    public void openGoldPointsScreen() throws IOException {
        // creating a FXML loader to load the gold points allocation screen fxml file
        FXMLLoader fxmlLoader5 = new FXMLLoader(CarcassonneApplication.class.getResource("goldPointsAllocationScreen.fxml"));
        // create a new stage and load the gold allocations screen fxml file
        Scene goldPointsScene = new Scene(fxmlLoader5.load());
        // create a new stage for the screen
        Stage goldPointsStage = new Stage();

        // give the window a name
        goldPointsStage.setTitle("Gold Points");
        // set the stage for the scene
        goldPointsStage.setScene(goldPointsScene);
        // show the stage so that user is abel to see the window
        goldPointsStage.show();
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Tile> getPlayedTiles() {
        return playedTiles;
    }

    public ArrayList<ArrayList<Tile>> getPlayedTile() {
        return playedTile;
    }

    public int getCurrentRotationPreviewTile() {
        return currentRotationPreviewTile;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public ArrayList<Project> getProjectsInProject() {
        return projectsInProject;
    }

    public ArrayList<Meeple> getPlacedMeeples() {
        return placedMeeples;
    }

    public int getMeepleCounter() {
        return meepleCounter;
    }

    public ArrayList<ArrayList<Project>> getCompletedCities() {
        return completedCities;
    }

    public ArrayList<ICTile> getICTiles() {
        return ICTiles;
    }

    public ArrayList<ICProject> getICProjects() {
        return ICProjects;
    }

    public String getMeepleSelected() {
        return meepleSelected;
    }

    public ArrayList<GTile> getGTiles() {
        return GTiles;
    }
}
