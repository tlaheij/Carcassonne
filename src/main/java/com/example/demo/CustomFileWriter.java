package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>
 *     this class contains all the static methods that are used to write files
 *     with data that needs to be saved. It only contains one method as the
 *     only occasion that we need to save data is when writing a json file
 * </p>
 */
public class CustomFileWriter {

    /**
     * <p>
     *     this method save the object that is given to it in a new json file
     *     that is called save.json. Th object is referred to as game because
     *     the only use of this method will mainly be used to save a game.
     * </p>
     *
     * @param game  this parameter is the object that needs to be saved
     *              in the json file. In this application it is mainly
     *              the game of the user that needs to be saved.
     */
    public static void saveGameData(SaveGame game){
        // create an object mapper inorder to efficiently write teh json file
        ObjectMapper objectMapper = new ObjectMapper();

        // trying to create a new file to write t
        try(FileWriter file = new FileWriter("save.json")){
            // using the object mapper writing game object in the json file
            objectMapper.writeValue(file, game);
        } catch (IOException e) {
            // an expection is thrown in case the file can't be created
            throw new RuntimeException(e);
        }
    }
}
