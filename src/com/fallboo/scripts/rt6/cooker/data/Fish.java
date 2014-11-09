package com.fallboo.scripts.rt6.cooker.data;

/**
 * Created by Jake on 08/11/2014.
 */
public enum Fish implements CookableFood {
    SHRIMP(317), ANCHOVIES(321), SARDINE(327), SALMON(331), TROUT(335), COD(341), HERRING(345), PIKE(349), MACKEREL(353), TUNA(359), BASS(363);
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
