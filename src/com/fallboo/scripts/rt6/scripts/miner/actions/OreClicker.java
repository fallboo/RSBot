/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.scripts.miner.actions;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.scripts.miner.data.Rocks;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Interactive;
import org.powerbot.script.rt6.Menu;
import org.powerbot.script.rt6.MobileIdNameQuery;

import java.util.concurrent.Callable;

/**
 * @author Jake
 */
public class OreClicker {

    private final Rocks ores;
    private GameObject interacting = null, next = null;
    private final Filter<Menu.Command> menuFilter;
    private final ClientContext ctx;

    public OreClicker(ClientContext ctx, final Rocks ores) {
        this.ctx = ctx;
        this.ores = ores;
        menuFilter = new Filter<Menu.Command>() {

            @Override
            public boolean accept(Menu.Command t) {
                return t.action.equalsIgnoreCase("mine") && t.option.startsWith(ores.toString());
            }
        };
    }

    public boolean clickNextOre() {
        if (next == null || !next.valid() || next.actions().length == 0) {
            calculateNextRock();
        }
        if (next != null && next.valid()) {
            moveToNext();
            if (next != null || !next.valid() || next.actions().length == 0) {
                if (ctx.objects.select(Interactive.areInViewport()).id(ores.getIds()).isEmpty() && ctx.objects.select().id(ores.getIds()).isEmpty())
                    return false;
                calculateNextRock();
            }
            if (Random.nextInt(0, 12) == 1) {
                ctx.camera.turnTo(next, Random.nextInt(0, 20));
            }
            next.bounds(-148, 104, -224, -56, -120, 84);
            while (next.valid() && next.actions().length > 0 && !next.interact(menuFilter)) {
                if (next.valid()) {
                    moveToNext();
                } else {
                    return false;
                }
            }
            interacting = next;
            next = null;
            return true;
        }
        return false;
    }

    private void moveToNext() {
        if (!next.inViewport()) {
            ctx.camera.turnTo(next, Random.nextInt(0, 25));
        }
        if (ctx.players.local().tile().distanceTo(next) > 8)
            ctx.movement.step(next);
        Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return next.inViewport() || ctx.players.local().idle();
            }
        }, 500, 5);
    }

    public boolean isCurrentOreValid() {
        return interacting != null && interacting.valid();
    }

    private void calculateNextRock() {
        MobileIdNameQuery<GameObject> query = ctx.objects.nearest();
        GameObject tmp = query.poll();
        if (tmp.tile().distanceTo(ctx.players.local().tile()) < 2 || ctx.objects.isEmpty()) {
            next = tmp;
        } else if (tmp.tile().distanceTo(ctx.players.local().tile()) + 1 >= ctx.objects.peek().tile().distanceTo(ctx.players.local().tile())) {
            query.limit(3).shuffle();
            next = Random.nextInt(1, 5) == 1 ? query.poll() : tmp;
        } else {
            next = tmp;
        }
    }

    public void clearInteracting() {
        interacting = null;
    }
}
