package com.fallboo.scripts.rt6.scripts.cooker.data;

/**
 * Created by Jake on 08/11/2014.
 */
public enum Fish implements CookableFood {
    MINNOW(30076), CRAYFISH(13435), SHRIMP(317), KARAMBWANJI(3150), SARDINE(327), ANCHOVIES(321), POISON_KARAMBWAN(3142),
    HERRING(345), MACKEREL(353), TROUT(335), COD(341), PIKE(349), SALMON(331), SLIMY_EEL(3379), TUNA(359), COOKED_KARAMBWANJI(3142),
    RAINBOW_FISH(10138), CAVE_EEL(5001), LOBSTER(379), BASS(363);
    private final int id;

    Fish(int id) {
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
        return ordinal() * 4;
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
