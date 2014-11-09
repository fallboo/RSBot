package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.AntiPattern;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.Rocks;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Interactive;

import java.util.concurrent.Callable;

public class Mine extends GraphScript.Action<ClientContext> {

    private final Rocks ore;
    private final OreClicker oreClicker;

    public Mine(ClientContext ctx, Rocks ores) {
        super(ctx);
        oreClicker = new OreClicker(ctx, ores);
        this.ore = ores;
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().count() < 28
                && hasMine()
                && ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion();
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Mining");
        oreClicker.clickNextOre();
        if (!oreClicker.isCurrentOreValid()) {
            oreClicker.clearInteracting();
            return;
        }
        Condition.sleep(Random.nextInt(850, 1350));
        if (!Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().idle() || !oreClicker.isCurrentOreValid();
            }
        }, 200, 30)) {
            return;
        }
        ctx.antiPattern.run(AntiPattern.State.IDLE);
        if (!Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() != -1 || !oreClicker.isCurrentOreValid();
            }
        }, 100, 5)) {
            return;
        }
        Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return !oreClicker.isCurrentOreValid();
            }
        }, 100, 120);
        oreClicker.clearInteracting();
    }

    private boolean hasMine() {
        return !ctx.objects.select().id(ore.getIds()).select(Interactive.areInViewport()).isEmpty() || !ctx.objects.select().id(ore.getIds()).isEmpty();
    }
}
