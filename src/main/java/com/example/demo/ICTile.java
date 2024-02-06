package com.example.demo;

import java.util.ArrayList;

/**
 * <p>
 *     This is a extension class of the Tile class, there Tiles are all
 *     the tiles that are a part of the inns and cathedral expansion, they
 *     therefore have special extra data which normal tiles don't need.
 * </p>
 */
public class ICTile extends Tile{

    /**
     * This instance variable is a boolean that says whether the tile
     * has an inn or not (true or false)
     */
    private boolean inn;

    /**
     * This instance variable is a boolean that says whether the tile
     * has a cathedral or not (true or false)
     */
    private boolean cathedrale;

    /**
     * This instance variable is the arraylist which contains all the
     * projects of the tiles. They are objects of the class ICProject
     * and not Project because they are a part of a ICTiles.
     */
    private ArrayList<ICProject> ICprojects = new ArrayList<>();

    /**
     * <p>
     *     This is the constructor of the ICTile class. All the
     *     parameters will be send to the Tile constructor instead.
     * </p>
     *
     * @param sides             this parameter is a array of strings which
     *                          contains the project types and codes of each
     *                          side staring at the top of the tile for example
     *                          C1 would be city1.
     *
     * @param fields            this parameter is an array of strings which
     *                          contains all codes of the fields around the
     *                          tile, 8 strings long and starts in the top
     *                          left corner.
     *
     * @param church            this parameter is a boolean which states
     *                          whether the tiles hae a church
     *
     * @param shield            this parameter is a boolean which states whether
     *                          the tiles have a shield
     *
     * @param shieldProjectID   this parameter is a string which contains
     *                          the project code of the project on this tile
     *                          which has a field. If there is no shield
     *                          this is null.
     *
     * @param imageName         this parameter is a string which contains
     *                          the name of the image for the tile.
     *
     * @param numProjects       this parameter is an integer which states
     *                          how many projects this tile has
     *
     * @param code              this parameter is an integer which is the
     *                          unique code of the tile
     */
    public ICTile(String[] sides, String[] fields, boolean church, boolean shield, String shieldProjectID, String imageName, int numProjects, int code) {
        // initialising the ICTile using the super class
        super(sides, fields, church, shield, shieldProjectID, "ictiles/" + imageName, numProjects, code);
        // setting instance variables to false (later they can be changed)
        inn = false;
        cathedrale = false;
    }

    public void addICProject(String projectName, String projectID, int meepleX, int meepleY, boolean shield, String innOrCath){
        ICProject project = new ICProject(projectName, projectID, meepleX, meepleY, null, shield, this);

        if(innOrCath.equals("c")){
            project.addCathedral();
        }
        else if(innOrCath.equals("i")){
            project.addInn();
        }

        super.getProjects().add(project);
        ICprojects.add(project);
    }
    public ArrayList<ICProject> getICProjects() {
        return ICprojects;
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
