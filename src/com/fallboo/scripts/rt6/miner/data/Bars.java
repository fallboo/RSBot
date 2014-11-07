package com.fallboo.scripts.rt6.miner.data;

public enum Bars {

    BRONZE(2349, new BarCost(Ores.COPPER, Ores.TIN, 1), 14), IRON(2351, new BarCost(Ores.IRON), 28), STEEL(2353, new BarCost(Ores.IRON, Ores.COAL, 2), 9);
    private int id, primaryAmount;
    private BarCost cost;

    Bars(int id, BarCost cost, int primaryAmount) {
        this.id = id;
        this.cost = cost;
        this.primaryAmount = primaryAmount;
    }

    public BarCost getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public int getPrimaryAmount() {
        return primaryAmount;
    }

}
