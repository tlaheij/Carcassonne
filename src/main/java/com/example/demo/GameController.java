package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController{
    @FXML
    // The board which is inside a scroll plane
    // colomuns and rows will be added because it starts blank
    HBox board = new HBox();

    @FXML
    // is the VBox on the side where all the player data is kepted
    VBox side = new VBox();

    @FXML
    // the ImageView where the tile currently being placed is shown to the user
    ImageView previewTile = new ImageView();

    @FXML
    // the basis of the whole screen
    AnchorPane screen = new AnchorPane();

    @FXML
    Text tilesLeftText = new Text();

    private Game game;

    @FXML
    private VBox optionSelection;

    @FXML
    StackPane preview = new StackPane();

    @FXML
    private HBox playersBox;
    @FXML
    private VBox turnPlayerBox;

    @FXML
    private HBox tilesLeftButton;

    @FXML
    private VBox ICMeepleSelection;

    @FXML
    private VBox meepleSelectionSmallMeeple;

    @FXML
    private VBox meepleSelectionBigMeeple;

    @FXML
    private HBox goldPointsButton;

    @FXML
    private StackPane playScreen;

    @FXML
    public void play() throws FileNotFoundException {
        playScreen.getChildren().remove(1);

        game = new Game(board, side, previewTile, screen, tilesLeftText, optionSelection, preview, playersBox, turnPlayerBox, tilesLeftButton, ICMeepleSelection, meepleSelectionBigMeeple, meepleSelectionSmallMeeple, goldPointsButton);
        game.play();
    }

    @FXML
    public void addColBack(){
        game.addColBack();
    }

    /**
     * <p>
     *     runs the rotate method in the game object when the user
     *     press the 'r' key on their keyboard. This will rotate
     *     the tile that is currently being placed.
     * </p>
     *
     * @param event the key that the user pressed this can
     *              any key on the keyboard.
     */
    @FXML
    private void rotate(KeyEvent event){
        // checking if the user pressed the r key and not any other
        if ("r".equals(event.getCharacter())){
            // running the rotate method if the user did press 'r'
            game.rotate();
        }
    }

    /**
     * <p>
     *     runs the method function in the game object when the
     *     user preses the rotate button on the game screen. The
     *     rotate method will rotate the tile that is currently
     *     being placed
     * </p>
     */
    @FXML
    private void rotate2() {
        // running the rotate method of the game object
        game.rotate();
    }

    @FXML
    private void openTilesLeftScreen() throws IOException {
        game.openTilesLeftScreen();
    }

    @FXML
    private void selectSmallMeeples(){
        game.selectSmallMeeples();
    }

    @FXML
    private void selectBigMeeples(){
        game.selectBigMeeples();
    }

    @FXML
    private void openGoldPointsScreen() throws IOException {
        game.openGoldPointsScreen();
    }

    @FXML
    private void save(){
        SaveGame sGame = new SaveGame(game.getCurrentRotationPreviewTile(), game.getPlayedTile(), game.getPlayedTiles(), game.getTiles(),  Variables.getNumPlayers(), Variables.getPlayers(), game.getMeepleCounter(), Variables.isInnCathedral(), game.getICTiles(), Variables.isGoldmines(), game.getGTiles());

        CustomFileWriter.saveGameData(sGame);
        Variables.getGameStage().hide();
        System.exit(0);
    }

    @FXML
    private void openRules(){
        Variables.getRulesStage().show();
    }
}
