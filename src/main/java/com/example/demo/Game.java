package com.example.demo;

import com.example.demo.FileRead;
import com.example.demo.Tile;
import com.example.demo.Variables;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    private Text tilesLeftText = new Text();

    private ImageView playButton = new ImageView();

    // keeps all the colomuns so that they are easily accessable (this is only VBox's)
    private ArrayList<VBox> cols = new ArrayList<>();

    // the whole grid so that you can easily access it (this is only HBox's)
    private ArrayList<ArrayList<HBox>> grid = new ArrayList<>();

    //an array list of all the tiles in the game in order
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Tile> playedTiles = new ArrayList<>();

    private ArrayList<ArrayList<Tile>> playedTile = new ArrayList<>();

    // The rotation of the tile being placed
    private int currentRotationPreviewTile = 0;

    private VBox selectionOption = new VBox();

    private HBox selectedProjectBox;

    private Project selectedProject;

    private ArrayList<Project> projectsInProject = new ArrayList<>();

    private ArrayList<Meeple> placedMeeples = new ArrayList<>();

    public Game (HBox board, VBox side, ImageView previewTile, AnchorPane screen, Text tilesLeftText, ImageView playButton, VBox selectionOption, StackPane preview){
        this.board = board;
        this.side = side;
        this.previewTile = previewTile;
        this.screen = screen;
        this.tilesLeftText = tilesLeftText;
        this.playButton = playButton;
        this.selectionOption = selectionOption;
        this.preview = preview;
    }

    /**
     *
     * @throws FileNotFoundException
     */
    public void play() throws FileNotFoundException {
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

        int numStartTileProjects = 4;

        // creating the start tile using the tile class
        Tile start = new Tile(sides, fields, false, false, "start tile", numStartTileProjects);

        start.addProject("city1", "1", 38, 3);
        start.addProject("road1", "2", 34, 37);
        start.addProject("field1", "1", 34, 56);
        start.addProject("field2", "2", 24, 26);

        // adding the start tile to played tiles
        playedTiles.add(start);

        // creating the image view inorder to display is inside an HBox on the board
        ImageView startTile = new ImageView();

        // setting the image of the image view to the image connected with the start tile
        startTile.setImage(start.getImage());
        // resizing the image view to make sure it fits prefectly in the grid
        startTile.resize(80,80);
        // resize the image within the image view
        startTile.setFitHeight(80);
        startTile.setFitWidth(80);

        // using the gird variable to get place the image view in the correct HBox
        grid.get(4).get(3).getChildren().add(startTile);

        playedTile.get(4).set(3, start);

        // needed to javaFX inorder to locate where to find the file
        String currentDir = System.getProperty("user.dir");
        // sending the tiles.txt file to the file reader inorder to get all the tile data and then processing that data and saving that in the data string[][]
        ArrayList<String[][]> data = processData(FileRead.tiles(currentDir + "/src/main/saved/tiles.txt"));

        // creating all the tiles for the game
        createTiles(data);

        // shuffling the tiles in a random order
        // Collections.shuffle(tiles);

        // setting the prewview tile image view  to the top most tile
        previewTile.setImage(tiles.get(0).getImage());

        showCanPlace(tiles.get(0));

        tilesLeftText.setText("Tiles Left: " + tiles.size());

        playButton.setDisable(true);
    }

    public void addColBack(){
        // figuring out the number of rows using the height of the HBox in the scrollpane
        int numRow = (int) (board.getHeight() / 80);
        // creating the new VBox for the colomum
        VBox newCol = new VBox();
        // setting the width to 80 like all other colomuns
        newCol.setMinWidth(80);

        // creating a new arraylist of HBox (a HBox for every row)
        ArrayList<HBox> gridCol = new ArrayList<>();

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
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
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

        playedTile.add(tileCol);
    }

    public void addColFront(){
        // figuring out the number of rows using the height of the HBox in the scrollpane
        int numRow = (int) (board.getHeight() / 80);
        // creating the new VBox for the colomum
        VBox newCol = new VBox();
        // setting the width to 80 like all other colomuns
        newCol.setMinWidth(80);

        // creating a new arraylist of HBox (a HBox for every row)
        ArrayList<HBox> gridCol = new ArrayList<>();

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
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
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

        playedTile.add(0, tileCol);

        for (int i = 0; i < placedMeeples.size(); i ++){
            placedMeeples.get(i).setXgrid(placedMeeples.get(i).getXgrid() + 1);
        }
    }

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
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
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

            playedTile.get(i).add(null);
        }
    }

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
                if(row.getBackground() == null){
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(1), new Insets(1))));
                }
                else{
                    row.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))));
                }
            });
            // changing the colour back to normal once the mouse has exited the HBox
            row.setOnMouseExited(event -> {
                if(row.getBackground() == null){
                    row.setBackground(null);
                }
                else if(row.getBackground().equals(new Background(new BackgroundFill(Variables.getDarkBlue2(), new CornerRadii(1), new Insets(1))))){
                    row.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
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

            playedTile.get(i).add(0, null);
        }

        for (int j = 0; j < placedMeeples.size(); j ++){
            placedMeeples.get(j).setYgird(placedMeeples.get(j).getYgird() + 1);
        }
    }

    /**
     *
     * @param data
     * @return
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

            int numProject = Integer.parseInt(data.get(i)[15]);

            String[] numProject2 = {String.valueOf(numProject)};
            tile[5] = numProject2;

            for(int j = 16; j < (numProject * 4) + 16; j += 4){
                String projectName = data.get(i)[j];
                String projectID = data.get(i)[j+1];
                String projectMeepleX = data.get(i)[j+2];
                String projectMeepleY = data.get(i)[j+3];

                String[] projects = {projectName, projectID, projectMeepleX, projectMeepleY};
                tile[(j-16)/4 + 6] = projects;
            }

            // adding the completed tile to the processed data
            proData.add(tile);
        }

        return proData;

    }

    private void createTiles(ArrayList<String[][]> data){
        // looping through all the tiles
        for(int i = 0; i < data.size(); i++){
            // creating the tiles from the data
            Tile tile = new Tile(data.get(i)[0], data.get(i)[1], Boolean.parseBoolean(data.get(i)[2][0]), Boolean.parseBoolean(data.get(i)[2][0]), data.get(i)[4][0], Integer.parseInt(data.get(i)[5][0]));

            for(int j = 6; j < Integer.parseInt(data.get(i)[5][0]) + 6; j++){
                tile.addProject(data.get(i)[j][0], data.get(i)[j][1], Integer.parseInt(data.get(i)[j][2]), Integer.parseInt(data.get(i)[j][3]));
            }

            // adding this to the array of tiles
            tiles.add(tile);
        }
    }

    private void placeTile(HBox place) {
        if(selectionOption.getChildren().size() == 0){
            // we get the top tile from the deck / tiles
            Tile tile = tiles.get(0);

            int colIndex = -1;
            for(int i = 0; i < cols.size(); i ++){
                if(place.getParent() == cols.get(i)){
                    colIndex = i;
                }
            }

            int rowIndex = -1;
            for(int i = 0; i < grid.get(colIndex).size(); i ++){
                if (grid.get(colIndex).get(i) == place){
                    rowIndex = i;
                }
            }

            // checking wether the place already has a tiles in it
            if (checkPosistion(tile, rowIndex, colIndex)) {

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

                // place Meeple
                chooseMeeplePosistion(tiles.get(0), place, colIndex, rowIndex);
            }
        }
    }

    private void chooseMeeplePosistion(Tile tile, HBox place, int colIndex, int rowIndex){
        ArrayList<Project> projects = tile.getProjects();

        Pane ph4 = new Pane();
        ph4.setPrefSize(198, 4);

        Text title = new Text("Select Project");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setFill(Paint.valueOf("#FFB100"));

        Pane ph5 = new Pane();
        ph5.setPrefSize(198, 10);

        selectionOption.getChildren().addAll(ph4, title, ph5);

        String imageLoc = "file:./src/main/resources/com/example/demo/images/";

        for(int i = 0; i < tile.getNumProjects(); i++){
            Project project = projects.get(i);

            HBox projectDisplay = new HBox();
            projectDisplay.setOnMouseClicked(mouseEvent -> projectSelection(projectDisplay, project));
            projectDisplay.setAlignment(Pos.CENTER_LEFT);

            projectDisplay.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(5), new Insets(1))));

            Pane ph = new Pane();
            ph.setPrefHeight(36);
            ph.setPrefWidth(12);

            projectDisplay.getChildren().add(ph);

            ImageView imageV = new ImageView();
            Image image;
            String text;


            if(project.getType().substring(0,project.getType().length() - 1).equals("city")){
                image = new Image(imageLoc + "fortress.png");
                text = "City " + project.getType().charAt(project.getType().length() - 1);
            }
            else if(project.getType().substring(0,project.getType().length() - 1).equals("road")){
                image = new Image(imageLoc + "signPost.png");
                text = "Road " + project.getType().charAt(project.getType().length() - 1);
            }
            else if(project.getType().substring(0,project.getType().length() - 1).equals("church")){
                image = new Image(imageLoc + "church.png");
                text = "Church " + project.getType().charAt(project.getType().length() - 1);
            }
            else{
                image = new Image(imageLoc + "farmland.png");
                text = "Field " + project.getType().charAt(project.getType().length() - 1);
            }

            imageV.setImage(image);
            imageV.setFitHeight(30);
            imageV.setFitWidth(30);
            projectDisplay.getChildren().add(imageV);

            Pane ph2 = new Pane();
            ph2.setPrefHeight(36);
            ph2.setPrefWidth(9);

            projectDisplay.getChildren().add(ph2);

            Text lable = new Text();
            lable.setText(text);
            lable.setFont(Font.font("Arial", FontWeight.BOLD, 17));
            lable.setFill(Paint.valueOf("#003844"));

            projectDisplay.getChildren().add(lable);

            if(text.charAt(0) != 'F' || text.charAt(0) != 'C'){
                String projectID = String.valueOf(text.charAt(0)) + text.charAt(text.length() - 1);

                ArrayList<Tile> checked = new ArrayList<>();
                projectsInProject.clear();
                ArrayList<Tile> entireProject = checkFinishProject(tile, colIndex, rowIndex, projectID, checked);
                if(entireProject.contains(null)) {
                    for (int j = 0; j < entireProject.size(); j++) {
                        if (entireProject.get(j) == null) {
                            entireProject.remove((j));
                            j--;
                        }
                    }
                }

                ArrayList<Meeple> meeplesInProject = getDeleteMeeples(entireProject);


                if(meeplesInProject != null && meeplesInProject.size() > 0){
                    projectDisplay.setDisable(true);
                    projectDisplay.setOpacity(0.5);
                }

            }

            selectionOption.getChildren().add(projectDisplay);

            Pane ph3 = new Pane();
            ph3.setPrefWidth(198);
            ph3.setPrefHeight(10);

            selectionOption.getChildren().add(ph3);
        }
        HBox confirmBox = new HBox();

        confirmBox.setAlignment(Pos.CENTER_LEFT);

        confirmBox.setPadding(new Insets(0, 0, 0, 12));
        ImageView confirmButton = new ImageView();
        Image confirm = new Image(imageLoc + "approve.png");
        confirmButton.setImage(confirm);
        confirmButton.setFitWidth(37);
        confirmButton.setFitHeight(37);
        confirmButton.setOnMouseClicked(mouseEvent -> confirm(place, colIndex, rowIndex));

        ImageView clearButton = new ImageView();
        Image clear = new Image(imageLoc + "clear.png");
        clearButton.setImage(clear);
        clearButton.setFitWidth(41);
        clearButton.setFitHeight(41);
        clearButton.setOnMouseClicked(mouseEvent -> clear(place, colIndex, rowIndex));

        Pane spacing = new Pane();
        spacing.setPrefWidth(15);

        confirmBox.getChildren().addAll(confirmButton, spacing, clearButton);
        selectionOption.getChildren().add(confirmBox);
    }

    private void showCanPlace(Tile tile){
        for(int i = 0;  i < playedTile.size(); i++){
            for(int j = 0; j < playedTile.get(i).size(); j++){
                if(checkPosistion(tile, j, i)){
                    grid.get(i).get(j).setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(1), new Insets(1))));
                }
                else{
                    grid.get(i).get(j).setBackground(null);
                }
            }
        }
    }

    private boolean checkPosistion(Tile tile, int rowIndex, int colIndex){
        if(playedTile.get(colIndex).get(rowIndex) == null){
            Tile north = null;
            Tile south = null;
            Tile east = null;
            Tile west = null;

            if(rowIndex == 0){
                south = playedTile.get(colIndex).get(rowIndex + 1);
            }

            else if (rowIndex == playedTile.get(colIndex).size() - 1){
                north = playedTile.get(colIndex).get(rowIndex - 1);
            }
            else{
                south = playedTile.get(colIndex).get(rowIndex + 1);
                north = playedTile.get(colIndex).get(rowIndex - 1);
            }

            if(colIndex == 0){
                east = playedTile.get(colIndex + 1).get(rowIndex);
            }

            else if(colIndex == playedTile.size() - 1){
                west = playedTile.get(colIndex - 1).get(rowIndex);
            }
            else{
                east = playedTile.get(colIndex + 1).get(rowIndex);
                west = playedTile.get(colIndex - 1).get(rowIndex);
            }

            boolean canPlace = true;
            int numNull = 0;

            if(north != null){
                if(north.getSides()[2].charAt(0) != tile.getSides()[0].charAt(0)){
                    canPlace = false;
                }
                numNull ++;
            }

            if(south != null){
                if(south.getSides()[0].charAt(0) != tile.getSides()[2].charAt(0)){
                    canPlace = false;
                }
                numNull ++;
            }

            if(east != null){
                if(east.getSides()[3].charAt(0) != tile.getSides()[1].charAt(0)){
                    canPlace = false;
                }
                numNull ++;
            }

            if(west != null){
                if(west.getSides()[1].charAt(0) != tile.getSides()[3].charAt(0)){
                    canPlace = false;
                }
                numNull ++;
            }

            if(numNull == 0){
                canPlace = false;
            }

            return canPlace;
        }
        else{
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

    public void rotate(KeyEvent event){
        // checking wether the user has pressed r
        if ("r".equals(event.getCharacter()) && selectionOption.getChildren().size() == 0) {
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

            // adjusting the current rotation
            currentRotationPreviewTile += 90;
            // rotating the preview image view
            previewTile.setRotate(currentRotationPreviewTile);

            showCanPlace(tiles.get(0));
        }
    }

    public void rotate2(){
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

            showCanPlace(tiles.get(0));
        }
    }

    public void placeTile2(HBox place, int colIndex, int rowIndex){

        tiles.get(0).setRotation(currentRotationPreviewTile);

        for(int i = 0; i < 4; i ++){
            if(tiles.get(0).getSides()[i].charAt(0) != 'F'){
                ArrayList<Tile> checked = new ArrayList<>();
                projectsInProject.clear();
                ArrayList<Tile> entireProject = checkFinishProject(tiles.get(0), colIndex, rowIndex, tiles.get(0).getSides()[i], checked);
                if(!entireProject.contains(null)) {
                    ArrayList<Meeple> deleteMeeples = getDeleteMeeples(entireProject);
                    if(deleteMeeples != null){
                        clearMeeples(entireProject, deleteMeeples);
                    }
                }
            }
        }
        for(int i = 0; i < 8; i ++){
            if(tiles.get(0).getFields()[i].charAt(0) != 'c'){
                ArrayList<Tile> checked = new ArrayList<>();
                projectsInProject.clear();
                ArrayList<Tile> entireProject = getTilesOfField(tiles.get(0), colIndex, rowIndex, tiles.get(0).getFields()[i], checked);
                for(int j = 0; j < entireProject.size(); j ++){
                    System.out.print(Arrays.toString(entireProject.get(j).fields) + ", ");
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
        }
        else{
            // if it isn't we display the next tile in the preview Image view for the user to see
            previewTile.setImage(tiles.get(0).getImage());
            showCanPlace(tiles.get(0));
        }

        // reset the rotation back to 0
        currentRotationPreviewTile = 0;
        // reseat the rotation of the preview tile
        previewTile.setRotate(0);

        tilesLeftText.setText("Tiles Left: " + tiles.size());

        selectedProjectBox = null;
        selectedProject = null;
    }

    public void confirm (HBox place, int colIndex, int rowIndex){
        selectionOption.getChildren().clear();

        if (selectedProject != null) {
            HBox box = grid.get(colIndex).get(rowIndex);

            StackPane addBox = new StackPane();
            addBox.setPrefWidth(80);
            addBox.setPrefHeight(80);

            Node temp = box.getChildren().get(0);
            box.getChildren().clear();
            box.getChildren().add(addBox);

            HBox meepleBox = new HBox();
            meepleBox.setPrefWidth(80);
            meepleBox.setPrefHeight(80);

            Pane width = new Pane();

            Meeple meeple = new Meeple(selectedProject.getMeepleX(), selectedProject.getMeepleY(), colIndex, rowIndex);
            meeple.setProject(selectedProject);
            selectedProject.setMeeple(meeple);
            placedMeeples.add(meeple);

            if (currentRotationPreviewTile == 0) {
                width.setPrefSize(meeple.getX(), 80 - meeple.getY());
                meepleBox.setPadding(new Insets(meeple.getY(), 0, 0, 0));
            }
            else if (currentRotationPreviewTile == 90) {
                width.setPrefSize(80 - meeple.getY() - 12, 80 - meeple.getX());
                meepleBox.setPadding(new Insets(meeple.getX(), 0, 0, 0));
            }
            else if (currentRotationPreviewTile == 180) {
                width.setPrefSize(80 - meeple.getX() - 12, meeple.getY() - 12);
                meepleBox.setPadding(new Insets(80 - meeple.getY() - 12, 0, 0, 0));
            }
            else{
                width.setPrefSize(meeple.getY(), meeple.getX()  - 12);
                meepleBox.setPadding(new Insets(80 - meeple.getX() - 12, 0, 0, 0));
            }
            meepleBox.getChildren().addAll(width, meeple.getDisplay());

            addBox.getChildren().addAll(temp, meepleBox);
        }


        Node temp2 = preview.getChildren().get(0);
        preview.getChildren().clear();
        preview.getChildren().add(temp2);

        Tile tilePlaced = tiles.get(0);

        placeTile2(place, colIndex, rowIndex);
    }

    public void projectSelection(HBox projectBox, Project project){
        if(selectedProjectBox == null){
            projectBox.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(5), new Insets(1))));
        }
        else{
            selectedProjectBox.setBackground(new Background(new BackgroundFill(Variables.getDarkYellow(), new CornerRadii(5), new Insets(1))));
            projectBox.setBackground(new Background(new BackgroundFill(Variables.getYellow(), new CornerRadii(5), new Insets(1))));
        }

        Node temp = preview.getChildren().get(0);
        preview.getChildren().clear();
        preview.getChildren().add(temp);

        selectedProjectBox = projectBox;
        selectedProject = project;

        Meeple previewPlayer = new Meeple(project.getMeepleX(), project.getMeepleY(), 0, 0);

        previewPlayer.setSize();

        Pane width = new Pane();
        HBox ph = new HBox();

        if (currentRotationPreviewTile == 0) {
            width.setPrefSize(previewPlayer.getX() * 2, 160 - previewPlayer.getY());
            ph.setPadding(new Insets(previewPlayer.getY() * 2, 0, 0, 0));
        }
        else if (currentRotationPreviewTile == 90) {
            width.setPrefSize(160 - (previewPlayer.getY() * 2) - 24, 160 - (previewPlayer.getX() * 2));
            ph.setPadding(new Insets(previewPlayer.getX() * 2, 0, 0, 0));
        }
        else if (currentRotationPreviewTile == 180) {
            width.setPrefSize(160 - (previewPlayer.getX() * 2) - 24, previewPlayer.getY() * 2 - 24);
            ph.setPadding(new Insets(160 - (previewPlayer.getY() * 2) - 24, 0, 0, 0));
        }
        else{
            width.setPrefSize(previewPlayer.getY() * 2, previewPlayer.getX() * 2 - 24);
            ph.setPadding(new Insets(160 - (previewPlayer.getX() * 2) - 24, 0, 0, 0));
        }


        ph.getChildren().addAll(width, previewPlayer.getDisplay());
        preview.getChildren().add(ph);
    }

    public void clear(HBox place, int colIndex, int rowIndex){
        selectedProject = null;
        confirm(place, colIndex, rowIndex);
    }

    private ArrayList<Tile> checkFinishProject(Tile tile, int colIndex, int rowIndex, String projectID, ArrayList<Tile> alreadyChecked){

        ArrayList<Tile> checked = new ArrayList<>();

        checked.add(tile);
        checked.addAll(alreadyChecked);

        String[] sides = tile.getSides();

        for(int i = 0; i < 4; i++){
            if(sides[i].equals(projectID)){
                ArrayList<Project> projects = tile.getProjects();

                for(int j = 0; j < projects.size(); j++){
                    if((int) projects.get(j).getType().charAt(0) == (int) projectID.charAt(0) + 32 && (int) projects.get(j).getType().charAt(projects.get(j).getType().length() - 1) == (int) projectID.charAt(1)){
                        projectsInProject.add(projects.get(j));
                    }
                }

                if(i == 0 || i == 2){
                    Tile newTile;
                    try{
                        newTile = playedTile.get(colIndex).get(rowIndex - 1 + i);
                    }
                    catch(Exception e){
                        newTile = null;
                    }

                    boolean flag1 = false;
                    for(int j = 0; j < checked.size(); j++) {
                        if(checked.get(j) == newTile) {
                            flag1 = true;
                        }
                    }
                    if(!flag1){
                        if(newTile != null){
                            checked.addAll(checkFinishProject(newTile, colIndex, rowIndex - 1 + i, newTile.getSides()[2 - i], checked));
                        }
                        else{
                            checked.add(null);
                        }
                    }
                }
                else {
                    Tile newTile;
                    try{
                        newTile = playedTile.get(colIndex + 2 - i).get(rowIndex);
                    }
                    catch(Exception e){
                        newTile = null;
                    }
                    boolean flag1 = false;
                    for(int j = 0; j < checked.size(); j++) {
                        if(checked.get(j) == newTile) {
                            flag1 = true;
                        }
                    }
                    if(!flag1){
                        if(newTile != null){
                            checked.addAll(checkFinishProject(newTile, colIndex + 2 - i, rowIndex, newTile.getSides()[4 - i], checked));
                        }
                        else{
                            checked.add(null);
                        }
                    }
                }
            }
        }

        ArrayList<Tile> checked2 = new ArrayList<>();
        for(int i = 0; i < checked.size(); i++){
            if(!checked2.contains(checked.get(i))){
                checked2.add(checked.get(i));
            }
        }

        return checked2;
    }

    private void clearMeeples (ArrayList<Tile> entireProject, ArrayList<Meeple> deleteMeeples){
        for(int j = 0; j < entireProject.size(); j ++){
            for(int k = 0; k < playedTile.size(); k ++) {
                for (int l = 0; l < playedTile.get(k).size(); l++) {
                    if (entireProject.get(j).equals(playedTile.get(k).get(l))) {
                        for (int h = 0; h < deleteMeeples.size(); h++) {
                            if (k == deleteMeeples.get(h).getXgrid() && deleteMeeples.get(h).getYgird() == l) {
                                Tile tile = entireProject.get(j);
                                HBox box = grid.get(k).get(l);
                                box.getChildren().clear();

                                // if not create a new image view for the tile so it can be displayed
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

                                box.getChildren().add(tileImg);
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Meeple> getDeleteMeeples (ArrayList<Tile> entireProject){
        ArrayList<Project> projectsInProject2 = new ArrayList<>();
        for(int j = 0; j < projectsInProject.size(); j++){
            if(!projectsInProject2.contains(projectsInProject.get(j))){
                projectsInProject2.add(projectsInProject.get(j));
            }
        }

        // System.out.println(entireProject.size());
        ArrayList<Meeple> allMeeples = new ArrayList<>();
        for (int j = 0; j < entireProject.size(); j ++){
            ArrayList<Project> tileProjects = entireProject.get(j).getProjects();
            for (int k = 0; k < tileProjects.size(); k ++){
                allMeeples.add(tileProjects.get(k).getMeeple());
            }
        }

        for(int j = 0; j < allMeeples.size(); j ++){
            if(allMeeples.get(j) == null){
                allMeeples.remove(j);
                j --;
            }
        }

        if(!(allMeeples.size() == 0)) {
            ArrayList<Meeple> deleteMeeples = new ArrayList<>();
            for (int j = 0; j < allMeeples.size(); j++) {
                for (int k = 0; k < projectsInProject2.size(); k++) {
                    if (allMeeples.get(j).getProject() == projectsInProject2.get(k)) {
                        deleteMeeples.add(allMeeples.get(j));
                    }
                }
            }
            return deleteMeeples;
        }
        return null;
    }
    private void checkChurch (Tile tile, boolean complete){
        int x = 0;
        int y = 0;
        for(int i = 0; i < playedTile.size(); i++){
            for(int j = 0; j < playedTile.get(i).size(); j ++){
                if(playedTile.get(i).get(j) == tile){
                    x = i;
                    y = j;
                }
            }
        }

        int xStart = x - 1;
        int yStart = y - 1;

        ArrayList<Tile> tilesAround = new ArrayList<>();

        for(int i = 0; i < 3; i ++){
            for(int j = 0; j < 3; j ++){
                if(playedTile.get(xStart + i).get(yStart + j) != null){
                    if(playedTile.get(xStart + i).get(yStart + j).isChurch()){
                        if(complete){
                            checkChurch(playedTile.get(xStart + i).get(yStart + j), false);
                        }
                    }
                    tilesAround.add(playedTile.get(xStart + i).get(yStart + j));
                }
            }
        }

        if(!complete && tilesAround.size() == 9){
            // add points to player
            ArrayList<Tile> project = new ArrayList<>();
            project.add(tile);
            ArrayList<Meeple> meeple = new ArrayList<>();
            ArrayList<Project> projects = tile.getProjects();
            for(int i = 0; i < projects.size(); i ++){
                if(projects.get(i).getType().charAt(0) == 'c'){
                    if(projects.get(i).getMeeple() != null){
                        meeple.add((projects.get(i).getMeeple()));
                    }
                }
            }
            if(meeple.size() != 0){
                clearMeeples(project, meeple);
            }
        }
    }

    private ArrayList<Tile> getTilesOfField(Tile tile, int colIndex, int rowIndex, String projectID, ArrayList<Tile> alreadyChecked){

        ArrayList<Tile> checked = new ArrayList<>();

        checked.add(tile);
        checked.addAll(alreadyChecked);

        String[] fields = tile.getFields();

        for(int i = 0; i < 8; i++){
            if(fields[i].charAt(0) == projectID.charAt(0)){
                ArrayList<Project> projects = tile.getProjects();

                for(int j = 0; j < projects.size(); j++){
                    if('f' == projectID.charAt(0) && (int) projects.get(j).getType().charAt(projects.get(j).getType().length() - 1) == (int) projectID.charAt(0)){
                        projectsInProject.add(projects.get(j));
                    }
                }

                if(i == 0 || i == 5){
                    Tile newTile;
                    try{
                        newTile = playedTile.get(colIndex).get(rowIndex + ((2 * i - 5) / 5));
                    }
                    catch(Exception e){
                        newTile = null;
                    }

                    boolean flag1 = false;
                    for(int j = 0; j < checked.size(); j++) {
                        if(checked.get(j) == newTile) {
                            flag1 = true;
                        }
                    }
                    if(!flag1){
                        if(newTile != null){
                            checked.addAll(getTilesOfField(newTile, colIndex, rowIndex + ((2 * i - 5) / 5), newTile.getFields()[i + (-2 * i + 5)], checked));
                            System.out.println(i + ((-2) * i + 5) + " " + i);
                        }
                    }
                }
                else if(i == 3 || i == 6){
                    Tile newTile;
                    try{
                        newTile = playedTile.get(colIndex + (((-2) * i + 9)/3)).get(rowIndex);
                    }
                    catch(Exception e){
                        newTile = null;
                    }

                    boolean flag1 = false;
                    for(int j = 0; j < checked.size(); j++) {
                        if(checked.get(j) == newTile) {
                            flag1 = true;
                        }
                    }
                    if(!flag1){
                        if(newTile != null){
                            checked.addAll(getTilesOfField(newTile, colIndex + (((-2) * i + 9)/3), rowIndex, newTile.getFields()[i + ((-2) * i + 9)], checked));
                            System.out.println(i + ((-2) * i + 9) + " " + i);
                        }
                    }
                }
                else if(i == 2 || i == 7){
                    Tile newTile;
                    try{
                        newTile = playedTile.get(colIndex + (((-2) * i + 9)/5)).get(rowIndex);
                    }
                    catch(Exception e){
                        newTile = null;
                    }

                    boolean flag1 = false;
                    for(int j = 0; j < checked.size(); j++) {
                        if(checked.get(j) == newTile) {
                            flag1 = true;
                        }
                    }
                    if(!flag1){
                        if(newTile != null){
                            checked.addAll(getTilesOfField(newTile, colIndex + (((-2) * i + 9)/5), rowIndex, newTile.getFields()[i + ((-2) * i + 9)], checked));
                            System.out.println(i + ((-2) * i + 9) + " " + i);
                        }
                    }
                }
                else {
                    Tile newTile;
                    try{
                        newTile = playedTile.get(colIndex).get(rowIndex + ((2 * i - 5)/3));
                    }
                    catch(Exception e){
                        newTile = null;
                    }

                    boolean flag1 = false;
                    for(int j = 0; j < checked.size(); j++) {
                        if(checked.get(j) == newTile) {
                            flag1 = true;
                        }
                    }
                    if(!flag1){
                        if(newTile != null){
                            checked.addAll(getTilesOfField(newTile, colIndex, rowIndex + ((2 * i + 5)/3), newTile.getFields()[i + ((-2) * i + 5)], checked));
                            System.out.println(i + ((-2) * i + 5) + " " + i);
                        }
                    }
                }
            }
        }

        ArrayList<Tile> checked2 = new ArrayList<>();
        for(int i = 0; i < checked.size(); i++){
            if(!checked2.contains(checked.get(i))){
                checked2.add(checked.get(i));
            }
        }

        return checked2;
    }
}
