package com.fallboo.scripts.rt6.scripts.cooker.data;

/**
 * Created by Jake on 08/11/2014.
 */
public enum FoodTypes {

    FISH(Fish.class), MEAT(Meat.class);
    private final Class<? extends CookableFood> foodClass;

    <E extends CookableFood> FoodTypes(Class<E> foodClass) {
        this.foodClass = foodClass;
    }

    public CookableFood[] getValues() {
        return foodClass.getEnumConstants();
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
