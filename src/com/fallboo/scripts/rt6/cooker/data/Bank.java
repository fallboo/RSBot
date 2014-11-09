package com.fallboo.scripts.rt6.cooker.data;

import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum Bank {
    BURTHROPE(new Tile(2896, 3441), new Tile(2876, 3417),
            new Tile[]{new Tile(2876, 3417, 0),
                    new Tile(2884, 3436, 0),
                    new Tile(2896, 3441, 0)}
    );

    private final Tile stoveLocation, bank;
    private final Tile[] bankToLocation;

    Bank(Tile stoveLocation, Tile bankLocation, Tile bankToLocation[]) {
        this.stoveLocation = stoveLocation;
        this.bank = bankLocation;
        this.bankToLocation = bankToLocation;
    }

    public Tile getStoveLocation() {
        return stoveLocation;
    }

    public Tile getBank() {
        return bank;
    }

    public Tile[] getBankToLocation() {
        return bankToLocation;
    }

    public Tile[] getLocationToBank() {
        ArrayList<Tile> rev = new ArrayList<Tile>();
        rev.addAll(Arrays.asList(getBankToLocation()));
        Collections.reverse(rev);
        return rev.toArray(new Tile[rev.size()]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name().toLowerCase().replace('_', ' '));
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        if (sb.indexOf(" ") > 0)
            sb.setCharAt(sb.lastIndexOf(" ") + 1,
                    Character.toUpperCase(sb.charAt(sb.lastIndexOf(" ") + 1)));
        return sb.toString();
    }
}
