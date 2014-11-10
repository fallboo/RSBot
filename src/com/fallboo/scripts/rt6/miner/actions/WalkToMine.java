package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.AntiPattern;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript.Action;
import com.fallboo.scripts.rt6.miner.data.Mines;
import org.powerbot.script.Condition;

import java.util.concurrent.Callable;

public class WalkToMine extends Action<ClientContext> {

    private final Mines mine;

    public WalkToMine(ClientContext ctx, Mines mines) {
        super(ctx);
        this.mine = mines;
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().count() < 28
                && ctx.players.local().animation() == -1 && mine.getLocation().distanceTo(ctx.players.local()) > 25;
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Walking to mine");
        if (!ctx.movement.newTilePath(mine.getBankToLocation()).traverse()) {
            ctx.movement.findPath(mine.getLocation()).traverse();
        }
        ctx.antiPattern.run(AntiPattern.State.MOVING);
        Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().idle();
            }
        }, 100, 5);
    }
}
