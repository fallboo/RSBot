package com.fallboo.scripts.rt6.scripts.miner.actions;

import com.fallboo.scripts.rt6.framework.AntiPattern;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.scripts.miner.data.Mines;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;

import java.util.concurrent.Callable;

public class WalkToBank extends GraphScript.Action<ClientContext> {

    private final Tile bank;
    private final Tile[] pathToBank;

    public WalkToBank(ClientContext ctx, Mines mines) {
        super(ctx);
        bank = mines.getBank();
        pathToBank = mines.getLocationToBank();
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().count() == 28
                && ctx.players.local().animation() == -1 && (!ctx.bank.opened() || !ctx.bank.inViewport());
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Walking to Bank");
        if (bank.distanceTo(ctx.players.local().tile()) > 20) {
            if (!ctx.movement.newTilePath(pathToBank).traverse()) {
                ctx.movement.findPath(bank).traverse();
            }
            ctx.antiPattern.run(AntiPattern.State.MOVING);
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().idle();
                }
            }, 100, 5);
            return;
        } else if (!ctx.bank.inViewport()) {
            ctx.movement.findPath(bank).traverse();
            ctx.antiPattern.run(AntiPattern.State.MOVING);
        }
        if (!ctx.bank.open()) {
            ctx.camera.turnTo(ctx.bank.nearest());
        }
    }

}
