package com.fallboo.scripts.rt6.scripts.cooker.data;

/**
 * Created by Jake on 09/11/2014.
 */
public enum Meat implements CookableFood {
    CHICKEN(2138), UNDEAD_CHICKEN(4289), BEEF(2132), RAT(2134), BEAR(2136), YAK(10816)/** UGTHANKI(1859), RABBIT(3226)*/
    ;
    private final int id;

    Meat(int id) {
        this.id = id;
    }

    @Override
    public int[] getIds() {
        return new int[]{id};
    }

    @Override
    public String getName() {
        return toString();
    }

    @Override
    public int getComponent() {
        if (ordinal() <= 1)
            return ordinal() * 4;
        if (ordinal() >= 2 && ordinal() <= 5)
            return 2 * 4;
        return (ordinal() - 3) * 4;
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
