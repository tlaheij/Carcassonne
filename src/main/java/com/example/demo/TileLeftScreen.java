package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class TileLeftScreen implements Initializable {

    @FXML
    VBox box = new VBox();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ArrayList<Tile> tiles = Variables.getTilesNotPlayed();

        tiles.sort(Comparator.comparing(Tile::getID));

        HBox row = new HBox();
        row.setSpacing(20);
        for(int j = 0; j < tiles.size(); j ++){
            VBox tileBox = new VBox();
            tileBox.setPadding(new Insets(15));
            tileBox.setSpacing(5);
            tileBox.setBackground(new Background(new BackgroundFill(Variables.getDarkBlue(), new CornerRadii(10), new Insets(1))));

            ImageView tileImageView = new ImageView();
            tileImageView.setImage(tiles.get(j).getImage());
            tileImageView.resize(125, 125);
            tileImageView.setFitWidth(125);
            tileImageView.setFitHeight(125);
            tileBox.getChildren().add(tileImageView);

            Text tileID = new Text();
            tileID.setFont(Font.font("Ubuntu", FontWeight.BOLD, 15));
            tileID.setFill(Paint.valueOf("#FFB100"));
            tileID.setText(tiles.get(j).getID());
            tileBox.getChildren().add(tileID);

            Text projectSubTitle = new Text();
            projectSubTitle.setFont(Font.font("Ubuntu", FontWeight.BOLD, 15));
            projectSubTitle.setFill(Paint.valueOf("#FFB100"));
            projectSubTitle.setText("Project:");
            tileBox.getChildren().add(projectSubTitle);

            for(int i = 0; i < tiles.get(j).getProjects().size(); i ++){
                Text projectText = new Text();
                projectText.setFont(Font.font("Ubuntu", FontWeight.BOLD, 12));
                projectText.setFill(Paint.valueOf("#FFEBC6"));

                if(tiles.get(j).getProjects().get(i).isShield()){
                    projectText.setText("    " + tiles.get(j).getProjects().get(i).getType() + " + Shield");
                }
                else{
                    projectText.setText("    " + tiles.get(j).getProjects().get(i).getType());
                }
                tileBox.getChildren().add(projectText);
            }

            row.getChildren().add(tileBox);

            if(row.getChildren().size() == 4) {
                HBox row2 = new HBox();
                row2.setSpacing(20);
                row2.getChildren().addAll(row.getChildren());
                box.getChildren().add(row2);
                row.getChildren().clear();
            }
        }
        box.getChildren().add(row);
    }
}
