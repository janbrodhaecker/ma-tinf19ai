package com.dhbw.tinf19ai.task6;

public class DataModel {

    private int level;
    private boolean charging;

    public DataModel(int level, boolean charging) {
        this.level = level;
        this.charging = charging;
    }

    public int getLevel() {
        return level;
    }

    public boolean isCharging() {
        return charging;
    }
}

