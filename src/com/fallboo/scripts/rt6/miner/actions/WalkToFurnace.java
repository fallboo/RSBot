package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.Bars;
import com.fallboo.scripts.rt6.miner.data.Furnaces;
import com.fallboo.scripts.rt6.framework.ClientContext;

import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;

public class WalkToFurnace extends GraphScript.Action<ClientContext> {

    private final Furnaces furnaces;
    private final Bars bars;
    private final int FURNACE_WIDGET = 1370, FURNACE_ACCEPT_COMPONENT = 20;
    private final int[] furnaceIds = {45310};

    public WalkToFurnace(ClientContext ctx, Furnaces furnaces, Bars bars) {
        super(ctx);
        this.furnaces = furnaces;
        this.bars = bars;
    }

    @Override
    public boolean valid() {
        return !furnaces.getFurnaceArea().contains(ctx.players.local())
                && ctx.players.local().animation() == -1 && (ctx.backpack.select().id(bars.getCost().getItem1().getId()).count() != 0
                && (bars.getCost().requiresSecondary() && ctx.backpack.select().id(bars.getCost().getItem2().getId()).count() != 0));
    }

    @Override
    public void run() {
        if (ctx.bank.opened()) {
            ctx.bank.close();
        }
        if (!ctx.objects.select().id(furnaceIds).isEmpty()) {
            if (!ctx.objects.peek().inViewport() && furnaces == Furnaces.LUMBRIDGE) {
                ctx.camera.turnTo(ctx.objects.peek());
                Condition.sleep(Random.nextInt(350, 850));
                Condition.wait(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        if (!ctx.objects.peek().inViewport()) {
                            ctx.camera.turnTo(ctx.objects.peek());
                        }
                        return ctx.objects.peek().inViewport();
                    }
                }, 100, 6);
            }
            if (ctx.objects.peek().inViewport()) {
                if (ctx.objects.poll().interact("Smelt")) {
                    Condition.sleep(Random.nextInt(350, 850));
                    Condition.wait(new Callable<Boolean>() {

                        @Override
                        public Boolean call() throws Exception {
                            return ctx.players.local().idle() || ctx.widgets.select().id(FURNACE_WIDGET).first().poll().component(FURNACE_ACCEPT_COMPONENT).visible();
                        }
                    }, 200, 50);
                    return;
                }
            }
        }
        if (!ctx.movement.newTilePath(furnaces.getBankToLocation()).traverse()) {
            ctx.movement.findPath(furnaces.getLocation()).traverse();
        }
        Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().inMotion()
                        || ctx.players.local().animation() != -1;
            }
        }, 100, 5);
    }
}
