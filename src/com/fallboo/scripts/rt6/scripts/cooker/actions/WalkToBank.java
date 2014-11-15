package com.fallboo.scripts.rt6.scripts.cooker.actions;

import com.fallboo.scripts.rt6.framework.AntiPattern;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.scripts.cooker.data.Bank;
import com.fallboo.scripts.rt6.scripts.cooker.data.CookableFood;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;

import java.util.concurrent.Callable;

public class WalkToBank extends GraphScript.Action<ClientContext> {

    private final Tile bank;
    private final Tile[] pathToBank;
    private final CookableFood cookableFood;

    public WalkToBank(ClientContext ctx, Bank banks, CookableFood cookableFood) {
        super(ctx);
        this.cookableFood = cookableFood;
        //TODO Change when more banks added
        if (banks != null) {
            bank = banks.getBank();
            pathToBank = banks.getLocationToBank();
        } else {
            bank = banks.getBank();
            pathToBank = banks.getLocationToBank();
        }
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().id(cookableFood.getIds()).isEmpty() && !ctx.bank.opened();
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
            if (Random.nextInt(0, 30) == 2)
                ctx.camera.turnTo(ctx.bank.nearest());
        }
    }

}
