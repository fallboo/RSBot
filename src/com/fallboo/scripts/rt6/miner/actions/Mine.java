package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.AntiPattern;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.Mines;
import com.fallboo.scripts.rt6.miner.data.Rocks;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.Interactive;

import java.util.concurrent.Callable;

public class Mine extends GraphScript.Action<ClientContext> {

    private final Rocks ore;
    private final OreClicker oreClicker;
    private final Mines mines;

    public Mine(ClientContext ctx, Mines mines, Rocks ores) {
        super(ctx);
        this.mines = mines;
        oreClicker = new OreClicker(ctx, ores);
        this.ore = ores;
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().count() < 28
                && hasMine()
                && !ctx.players.local().inMotion() && mines.getLocation().distanceTo(ctx.players.local()) < 25;
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Mining");
        if (!oreClicker.clickNextOre()) return;
        if (!oreClicker.isCurrentOreValid()) {
            oreClicker.clearInteracting();
            return;
        }
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
        }, 100, 25)) {
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
        return !ctx.objects.select(Interactive.areInViewport()).id(ore.getIds()).isEmpty() || !ctx.objects.select().id(ore.getIds()).isEmpty();
    }
}
