package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <p>
 *     This class contains all the methods to read files that will be used in
 *     the application. So one methode to read text files and other to read json
 *     files. They are separated from the actual code so the access of the methods
 *     can be controlled.
 * </p>
 */
public class FileRead {

    /**
     * <p>
     *     This method takes a file path in the form of a string and then reads the file
     *     the file must be a text file the where the data is separated by commas, otherwise
     *     the data will not be processed correctly. The data will be returned in the order
     *     that it was written in the text file
     * </p>
     *
     * @param filename                  this is the string which holds the path to the file
     *                                  that needs to be read.
     *
     * @return                          the method returns an arraylist of string arrays, with
     *                                  each element of the arraylist being a line in the file,
     *                                  and each element in the string array a piece of data.
     *
     * @throws FileNotFoundException    this exception is thrown if the file asked to read is
     *                                  not a file that exist this is to prevent an error from
     *                                  happening.
     */
    public static ArrayList<String[]> tiles (String filename) throws FileNotFoundException {
        // creating a file which will be the file that is going to be read
        File text = new File(filename);

        // creating a scanner to scan through the text (works that same as scanning the consule)
        Scanner s = new Scanner(text);

        // creating a arraylist of strings to hold the data
        ArrayList<String[]> tilesData = new ArrayList<>();

        // looping through the text file for as long as we aren't at the last line
        while(s.hasNextLine()){
            // obtain the line as string
            String line = s.nextLine();
            // we create a string array by spliting the line array at each comma (this seperates the data)
            String[] line2 = line.split(", ");
            // add the data to the arraylist
            tilesData.add(line2);
        }

        // we return all the data of the tiles
        return tilesData;
    }

    /**
     * <p>
     *     this methode reads the JSON file that has the previous game saved
     *     if the user choose to save the game. It reads the data and saves it
     *     in a SaveGame object.
     * </p>
     *
     * @return  this is the data saved in the SaveGame object
     *          saved in a special object because it excludes
     *          the javaFX.
     */

    public static SaveGame readSavedGame(){
        // creating a new object mapper which will be able to read the JSON file efficiently
        ObjectMapper objectMapper = new ObjectMapper();

        // initialising a game variable to save the data in
        SaveGame game;

        // we are trying first so no error occurs
        try{
            // retrieving the file that will be read
            File file = new File("save.json");
            // using the object mapper to read the file
            game = objectMapper.readValue(file, SaveGame.class);
            // deleting the file because there is no futher use
            file.delete();
        }
        catch (IOException e) {
            // throwing a specific error  to indicate where the error is
            throw new RuntimeException(e);
        }
        // returning the data in the form of a saved game
        return game;
    }
}
