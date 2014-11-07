package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.Bars;
import com.fallboo.scripts.rt6.miner.data.Furnaces;
import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import com.fallboo.scripts.rt6.framework.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Widget;

public class Smelt extends GraphScript.Action<ClientContext> {

    private final Furnaces furnace;
    private final Bars bar;

    public Smelt(ClientContext ctx, Furnaces furnace, Bars bar) {
        super(ctx);
        this.furnace = furnace;
        this.bar = bar;
    }

    @Override
    public boolean valid() {
        return (ctx.backpack.select().id(bar.getCost().getItem1().getId()).count() != 0
                && (bar.getCost().requiresSecondary() && ctx.backpack.select().id(bar.getCost().getItem2().getId()).count() != 0))
                && furnace.getFurnaceArea().contains(ctx.players.local())
                && !ctx.objects.select().id(45310).isEmpty()
                && ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion();
    }

    @Override
    public void run() {
        final Widget widget = ctx.widgets.select().id(1370).first().poll();
        final Widget close = ctx.widgets.select().id(1251).first().poll();
        Component c = widget.component(20);
        if (!widget.component(20).visible()) {
            final GameObject obj = ctx.objects.nearest().poll();
            if (obj == null) {
                return;
            }
            if (!obj.inViewport()) {
                ctx.camera.turnTo(obj);
                ctx.movement.step(obj);
                Condition.wait(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        return obj.inViewport();
                    }
                }, 200, 5);
            }
            if (obj.inViewport()) {
                if (Random.nextInt(1, 4) == 2) {
                    ctx.camera.turnTo(obj);
                }
                if (obj.interact("Smelt")) {
                    Condition.wait(new Callable<Boolean>() {

                        @Override
                        public Boolean call() throws Exception {
                            return widget.component(20).visible();
                        }
                    }, 200, 5);
                } else {
                    return;
                }
            }
        }
        if (widget.component(20).visible()) {
            widget.component(20).click();
            Condition.sleep(1250);
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return (!ctx.players.local().inMotion() && !close.component(49).visible());
                }
            }, 500, 80);
        }
    }
}
