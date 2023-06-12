package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class GameController {
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

    @FXML
    ImageView playButton = new ImageView();

    private Game game;

    @FXML
    private VBox optionSelection;

    @FXML
    StackPane preview = new StackPane();

    @FXML
    public void play() throws FileNotFoundException {
        game = new Game(board, side, previewTile, screen, tilesLeftText, playButton, optionSelection, preview);
        game.play();
    }

    @FXML
    public void addColBack(){
        game.addColBack();
    }

    @FXML
    private void rotate(KeyEvent event){
        game.rotate(event);
    }

    @FXML
    private void rotate2() {
        game.rotate2();
    }

}
