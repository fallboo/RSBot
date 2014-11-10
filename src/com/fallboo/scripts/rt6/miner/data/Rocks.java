package com.fallboo.scripts.rt6.miner.data;

public enum Rocks {

    COPPER(Ores.COPPER, new int[]{11960, 11961, 11962, 11936, 11937, 11938, 2090, 2091, 14906, 14907, 14856, 14857, 14858, 3229, 3027, 3226}),
    TIN(Ores.TIN, new int[]{11957, 11958, 11959, 11933, 11934, 11935, 2094, 2095, 14902, 14903, 3245, 3038}),
    IRON(Ores.IRON, new int[]{11954, 11955, 11956, 2092, 2093, 14900, 14901, 14913, 14914, 37309, 37308, 37307}),
    SILVER(Ores.SILVER, new int[]{11948, 11949, 11950, 2100, 2101, 37304, 37306, 37304, 37305}),
    COAL(Ores.COAL, new int[]{11963, 11964, 11965, 11930, 11931, 11932, 2096, 2097, 14850, 14851, 14852, 3233, 3032}),
    GOLD(Ores.GOLD, new int[]{11951, 11952, 11953, 2098, 2099}),
    MITHRIL(Ores.MITHRIL, new int[]{11954, 11955, 11956, 2092, 2093, 14900, 14901, 14913, 14914, 3280, 3041, 11942, 11944});
    private final int[] ids;
    private final Ores ore;

    Rocks(Ores ore, int[] ids) {
        this.ids = ids;
        this.ore = ore;
    }

    public int[] getIds() {
        return ids;
    }


    public Ores getOre() {
        return ore;
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
