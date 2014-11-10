package com.fallboo.scripts.rt6.cooker.data;

/**
 * Created by Jake on 09/11/2014.
 */
public enum Meat implements CookableFood {
    BEEF(2132), RAT(2134), BEAR(2136), YAK(10816), CHICKEN(2138), UGTHANKI(1859), RABBIT(3226);
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
