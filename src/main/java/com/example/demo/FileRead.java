package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileRead {

    public static ArrayList<String[]> tiles (String filename) throws FileNotFoundException {
        File text = new File(filename);

        Scanner s = new Scanner(text);

        ArrayList<String[]> tiles = new ArrayList<>();

        while(s.hasNextLine()){
            String line = s.nextLine();
            String[] line2 = line.split(", ");
            tiles.add(line2);
        }

        return tiles;
    }
}
