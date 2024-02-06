package com.example.demo;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * <p>
 *     This is a extension class of the Tile class, there Tiles are all
 *     the tiles that are a part of the goldmines expansion, they
 *     therefore have special extra data which normal tiles don't need.
 * </p>
 */
public class GTile extends Tile{
    /**
     * This instance variable is an integer which is the x coordinate
     * of the gold ingot on the tile ranges form 0-60
     */
    private int XGold;

    /**
     * This instance variable is an integer which is the y coordinate
     * of the gold ingot on the tile ranges form 0-60
     */
    private int YGold;

    /**
     * This instance variable is a boolean that states whether the
     * tile still has a gold ingot on it
     */
    private boolean hasGold;

    /**
     * This instance variable is the box which the gold ingot image
     * is in, it starts off as null.
     */
    private HBox goldBox;

    /**
     * This instance variable is the pane which the golBox HBox
     * is in. It also starts ofg as null.
     */
    private StackPane goldBoxParent;

    /**
     * <p>
     *     This is the constructor of the GTile class. All the
     *     parameters will be sent to the Tile constructor instead.
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
     *                          whether the tiles have a church
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
     *
     * @param XGold             this parameter is an integer and gives the
     *                          x coordinate of the gold ingot on the tile
     *
     * @param YGold             this parameter is an integer and gives the
     *                          x coordinate of the gold ingot on the tile
     */
    public GTile(String[] sides, String[] fields, boolean church, boolean shield, String shieldProjectID, String imageName, int numProjects, int code, int XGold, int YGold) {
        // using the super class to contract the GTile
        super(sides, fields, church, shield, shieldProjectID, "gtiles/" + imageName, numProjects, code);

        // using the data given to declare the instance variable
        this.XGold = XGold;
        this.YGold = YGold;
        // all GTiles start with a gold ingot
        hasGold = true;
    }

    /**
     * <p>
     *     The getter method for th goldBoxParent instance variable.
     * </p>
     *
     * @return  the goldBoxParent instance variable which is a StakePane.
     */
    public StackPane getGoldBoxParent() {
        return goldBoxParent;
    }

    /**
     * <p>
     *     The setter methode for the goldBoxParent instance variable.
     * </p>
     *
     * @param goldBoxParent     the StakePane which the goldBox
     *                          needs to be set to.
     */
    public void setGoldBoxParent(StackPane goldBoxParent) {
        this.goldBoxParent = goldBoxParent;
    }

    /**
     * <p>
     *     The getter method for th goldBox instance variable.
     * </p>
     *
     * @return  the goldBox instance variable which is a HBox.
     */
    public HBox getGoldBox() {
        return goldBox;
    }

    /**
     * <p>
     *     The setter methode for the goldBox instance variable.
     * </p>
     *
     * @param goldBox   the HBox which the goldBox needs to
     *                  be set to.
     */
    public void setGoldBox(HBox goldBox) {
        this.goldBox = goldBox;
    }

    /**
     * A kind of setter method that sets the hasGold instance variable
     * to false.
     */
    public void takeGold(){
        hasGold = false;
    }

    /**
     * A kind of setter method that sets the hasGold instance variable
     * to true.
     */
    public void giveGold(){
        hasGold = true;
    }

    /**
     * <p>
     *     The getter method for th hasGold instance variable.
     * </p>
     *
     * @return  the hasGold instance variable which is a boolean.
     */
    public boolean isHasGold() {
        return hasGold;
    }

    /**
     * <p>
     *     The getter method for th xGold instance variable.
     * </p>
     *
     * @return  the xGold instance variable which is an integer.
     */
    public int getXGold() {
        return XGold;
    }

    /**
     * <p>
     *     The getter method for th yGold instance variable.
     * </p>
     *
     * @return  the yGold instance variable which is an integer.
     */
    public int getYGold() {
        return YGold;
    }
}
