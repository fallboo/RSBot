package com.fallboo.scripts.rt6.miner.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.powerbot.script.Area;

import org.powerbot.script.Tile;

public enum Furnaces {

    LUMBRIDGE( new Tile(3229, 3255), new Tile(3212, 3256),
            new Tile[]{new Tile(3211, 3258, 0),
                new Tile(3221, 3254, 0)}, new Area(
                    new Tile(3218, 3259, 0),
                    new Tile(3218, 3250, 0),
                    new Tile(3231, 3250, 0),
                    new Tile(3231, 3259, 0)
            )
    );

    private Tile location, bank;
    private Tile[] bankToLocation;
    private Area furnaceArea;

    Furnaces( Tile location, Tile bankLocation, Tile bankToLocation[], Area furnaceArea
    ) {
        this.location = location;
        this.bank = bankLocation;
        this.bankToLocation = bankToLocation;
        this.furnaceArea = furnaceArea;
    }

    public Tile getLocation() {
        return location;
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

    public Area getFurnaceArea() {
        return furnaceArea;
    }
}
