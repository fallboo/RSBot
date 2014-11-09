package com.fallboo.scripts.rt6.framework;

/**
 * Created by Jake on 08/11/2014.
 */
public class IntegerPair {
    private final int startX;
    private int currentX;

    public IntegerPair(int x) {
        this.startX = x;
        this.currentX = x;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getStartX() {
        return startX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getDifference() {
        return currentX - startX;
    }
}
