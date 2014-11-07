/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.miner.data.Rocks;
import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Locatable;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Menu;
import org.powerbot.script.rt6.MobileIdNameQuery;

/**
 *
 * @author Jake
 */
public class OreClicker {

    private final Rocks ores;
    private GameObject interacting = null, next = null;
    private final Filter<Menu.Command> menuFilter;
    private final Filter<GameObject> oreFilter;
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
        oreFilter = new Filter<GameObject>() {

            @Override
            public boolean accept(GameObject t) {
                return interacting == null || interacting.tile().compareTo(t.tile()) != 0;
            }
        };
    }

    public void clickNextOre() {
        if (next == null || !next.valid() || next.actions().length == 0) {
            MobileIdNameQuery<GameObject> query = ctx.objects.nearest();
            GameObject tmp = query.peek();
            if (tmp.tile().distanceTo(ctx.players.local().tile()) < 2) {
                next = tmp;
            } else {
                query.limit(3).shuffle();
                next = query.poll();
            }
        }
        if (next != null && next.valid()) {
            if (!next.inViewport()) {
                ctx.camera.turnTo(next);
                ctx.movement.step(next);
                Condition.sleep(Random.nextInt(800, 1200));
                Condition.wait(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        return next.inViewport() || ctx.players.local().idle();
                    }
                }, 500, 5);
            }
            if (next.actions().length == 0) {
                GameObject tmp = getNextRock();
                if (tmp.valid() && next.actions().length != 0) {
                    next = tmp;
                } else {
                    return;
                }
            }
            if (Random.nextInt(0, 12) == 1) {
                ctx.camera.turnTo(next);
            }
            next.bounds(-148, 104, -224, -56, -120, 84);
            int counter = 0;
            while (next.valid() && next.actions().length > 0 && !next.interact(menuFilter)) {
                counter++;
                if (next.valid()) {
                    ctx.camera.turnTo(next, 20);
                    ctx.movement.step(next);
                    Condition.sleep(900);
                    Condition.wait(new Callable<Boolean>() {

                        @Override
                        public Boolean call() throws Exception {
                            return next.inViewport() || ctx.players.local().idle();
                        }
                    }, 500, 5);
                } else {
                    calculateNextRock();
                }
            }
            interacting = next;
            next = null;
        }
    }

    public boolean isCurrentOreValid() {
        return interacting != null && interacting.valid();
    }

    public void calculateNextRock() {
        GameObject go = getNextRock();
        if (go != null) {
            if (interacting == null) {
                next = go;
            } else {
                if (go == interacting) {

                }
            }
        }
    }

    public void checkForCloser() {
        if (next == null) {
            return;
        }
        GameObject go = getNextRock();
        if (go == null) {
            return;
        }
        if (next.valid() || go.tile().distanceTo(ctx.players.local().tile()) < next.tile().distanceTo(ctx.players.local().tile())) {
            next = go;
        }
    }

    public GameObject getNextRock() {
        MobileIdNameQuery<GameObject> query = ctx.objects.select(oreFilter).id(ores.getIds())
                .nearest().limit(3);
        if (query.isEmpty()) {
            return null;
        }
        GameObject tmp = query.peek();
        Locatable workingFrom = interacting == null ? ctx.players.local() : interacting;
        if (tmp.tile().distanceTo(workingFrom) < 2.5) {
            return tmp;
        } else {
            query.limit(4).shuffle();
            return query.poll();
        }
    }

    public void hoverNext() {
        if (next != null) {
            ctx.input.move(next.nextPoint());
        }
    }

    public boolean hasCloserNext() {
        if (next == null) {
            return true;
        }
        GameObject go = getNextRock();
        if (go == null) {
            return false;
        }
        if (go.tile().distanceTo(ctx.players.local().tile()) < next.tile().distanceTo(ctx.players.local().tile())) {
            return true;
        }
        return false;
    }

    public void clearInteracting() {
        interacting = null;
    }
}
