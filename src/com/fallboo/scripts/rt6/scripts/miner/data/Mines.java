package com.fallboo.scripts.rt6.scripts.miner.data;

import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum Mines {

    VARROCK_WEST(new Tile(3179, 3369), new Tile(3187, 3439),
            new Tile[]{new Tile(3186, 3441, 0),
                    new Tile(3182, 3431, 0),
                    new Tile(3172, 3429, 0),
                    new Tile(3170, 3419, 0),
                    new Tile(3169, 3409, 0),
                    new Tile(3168, 3399, 0),
                    new Tile(3173, 3390, 0),
                    new Tile(3176, 3380, 0),
                    new Tile(3179, 3370, 0)},
            new Rocks[]{Rocks.COPPER, Rocks.TIN, Rocks.IRON, Rocks.SILVER}
    ),
    VARROCK_EAST(new Tile(3284, 3365), new Tile(3252, 3418),
            new Tile[]{new Tile(3253, 3421, 0),
                    new Tile(3259, 3429, 0),
                    new Tile(3269, 3430, 0),
                    new Tile(3279, 3429, 0),
                    new Tile(3288, 3424, 0),
                    new Tile(3291, 3414, 0),
                    new Tile(3292, 3404, 0),
                    new Tile(3291, 3394, 0),
                    new Tile(3294, 3384, 0),
                    new Tile(3291, 3374, 0),
                    new Tile(3285, 3366, 0)},
            new Rocks[]{Rocks.COPPER, Rocks.TIN, Rocks.IRON}
    ), LUMBRIDGE_EAST(new Tile(3229, 3148), new Tile(3270, 3168),
            new Tile[]{new Tile(3270, 3168, 0),
                    new Tile(3259, 3173, 0),
                    new Tile(3241, 3169, 0),
                    new Tile(3236, 3158, 0),
                    new Tile(3230, 3150, 0)},
            new Rocks[]{Rocks.COPPER, Rocks.TIN}
    ),
    LUMBRIDGE_WEST(new Tile(3146, 3146), new Tile(3092, 3245),
            new Tile[]{new Tile(3094, 3243, 0),
                    new Tile(3099, 3242, 0),
                    new Tile(3103, 3239, 0),
                    new Tile(3106, 3235, 0),
                    new Tile(3109, 3231, 0),
                    new Tile(3112, 3227, 0),
                    new Tile(3115, 3223, 0),
                    new Tile(3118, 3219, 0),
                    new Tile(3122, 3216, 0),
                    new Tile(3126, 3213, 0),
                    new Tile(3129, 3209, 0),
                    new Tile(3133, 3206, 0),
                    new Tile(3136, 3202, 0),
                    new Tile(3138, 3197, 0),
                    new Tile(3140, 3192, 0),
                    new Tile(3143, 3188, 0),
                    new Tile(3144, 3183, 0),
                    new Tile(3145, 3178, 0),
                    new Tile(3146, 3173, 0),
                    new Tile(3148, 3168, 0),
                    new Tile(3148, 3163, 0),
                    new Tile(3148, 3158, 0),
                    new Tile(3148, 3153, 0),
                    new Tile(3146, 3148, 0)},
            new Rocks[]{Rocks.COAL, Rocks.MITHRIL}
    ), AL_KHARID(new Tile(3300, 3310), new Tile(3270, 3168),
            new Tile[]{new Tile(3270, 3170, 0),
                    new Tile(3273, 3174, 0),
                    new Tile(3276, 3178, 0),
                    new Tile(3279, 3182, 0),
                    new Tile(3281, 3187, 0),
                    new Tile(3281, 3192, 0),
                    new Tile(3281, 3197, 0),
                    new Tile(3282, 3202, 0),
                    new Tile(3282, 3207, 0),
                    new Tile(3282, 3212, 0),
                    new Tile(3282, 3217, 0),
                    new Tile(3279, 3222, 0),
                    new Tile(3280, 3227, 0),
                    new Tile(3282, 3232, 0),
                    new Tile(3285, 3235, 0),
                    new Tile(3290, 3237, 0),
                    new Tile(3291, 3242, 0),
                    new Tile(3293, 3247, 0),
                    new Tile(3294, 3252, 0),
                    new Tile(3295, 3257, 0),
                    new Tile(3296, 3262, 0),
                    new Tile(3294, 3267, 0),
                    new Tile(3293, 3272, 0),
                    new Tile(3297, 3275, 0),
                    new Tile(3300, 3279, 0),
                    new Tile(3301, 3284, 0),
                    new Tile(3299, 3289, 0),
                    new Tile(3298, 3294, 0),
                    new Tile(3298, 3299, 0),
                    new Tile(3299, 3304, 0)},
            new Rocks[]{Rocks.COAL, Rocks.SILVER, Rocks.IRON, Rocks.MITHRIL}
    );

    private final Tile location, bank;
    private final Rocks[] ores;
    private final Tile[] bankToLocation;

    Mines(Tile location, Tile bankLocation, Tile bankToLocation[],
          Rocks[] ores) {
        this.location = location;
        this.bank = bankLocation;
        this.ores = ores;
        this.bankToLocation = bankToLocation;
    }

    public Tile getLocation() {
        return location;
    }

    public Rocks[] getOres() {
        return ores;
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
