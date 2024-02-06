package com.example.demo;

public class SaveGTile {
    private int XGold;

    private int YGold;

    private boolean hasGold;

    private int code;

    public SaveGTile(int XGold, int YGold, boolean hasGold, int code) {
        this.XGold = XGold;
        this.YGold = YGold;
        this.hasGold = hasGold;
        this.code = code;
    }

    public SaveGTile() {
    }

    public int getXGold() {
        return XGold;
    }

    public int getYGold() {
        return YGold;
    }

    public boolean isHasGold() {
        return hasGold;
    }

    public int getCode() {
        return code;
    }

    public void setXGold(int XGold) {
        this.XGold = XGold;
    }

    public void setYGold(int YGold) {
        this.YGold = YGold;
    }

    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
