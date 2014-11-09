package com.fallboo.scripts.rt6.cooker.actions;

import com.fallboo.scripts.rt6.cooker.data.Bank;
import com.fallboo.scripts.rt6.cooker.data.CookableFood;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript.Action;
import org.powerbot.script.Condition;

import java.util.concurrent.Callable;

public class WalkToRange extends Action<ClientContext> {

    private final Bank mine;
    private final CookableFood food;

    public WalkToRange(ClientContext ctx, Bank bank, CookableFood food) {
        super(ctx);
        this.mine = bank;
        this.food = food;
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().id(food.getIds()).count() > 0
                && ctx.players.local().animation() == -1 && mine.getStoveLocation().distanceTo(ctx.players.local()) > 10;
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Walking to mine");
        if (!ctx.movement.newTilePath(mine.getBankToLocation()).traverse()) {
            ctx.movement.findPath(mine.getStoveLocation()).traverse();
        }
        Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().idle();
            }
        }, 100, 5);
    }
}
