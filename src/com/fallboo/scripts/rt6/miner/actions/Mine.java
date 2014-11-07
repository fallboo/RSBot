package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.miner.AIOMiner;
import com.fallboo.scripts.rt6.framework.GraphScript;
import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import com.fallboo.scripts.rt6.framework.ClientContext;

import com.fallboo.scripts.rt6.miner.data.Mines;
import com.fallboo.scripts.rt6.miner.data.Rocks;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Interactive;

public class Mine extends GraphScript.Action<ClientContext> {
    
    private final Mines mine;
    private final Rocks ore;
    private final OreClicker oreClicker;
    private final AIOMiner miner;
    
    public Mine(ClientContext ctx, Mines mines, Rocks ores, AIOMiner miner) {
        super(ctx);
        oreClicker = new OreClicker(ctx, ores);
        this.mine = mines;
        this.ore = ores;
        this.miner = miner;
    }
    
    @Override
    public boolean valid() {
        return ctx.backpack.select().count() < 28
                && mine.getMineArea().contains(ctx.players.local())
                && hasMine()
                && ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion();
    }
    
    @Override
    public void run() {
        miner.setStatus("Mining");
        oreClicker.clickNextOre();
        Condition.sleep(Random.nextInt(850, 1350));
        if (!Condition.wait(new Callable<Boolean>() {
            
            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().idle() || !oreClicker.isCurrentOreValid();
            }
        }, 200, 30)) {
            return;
        }
        if (!Condition.wait(new Callable<Boolean>() {
            
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() != -1 || !oreClicker.isCurrentOreValid();
            }
        }, 100, 5)) {
            return;
        }
        Condition.sleep(Random.nextInt(450, 950));
        Condition.wait(new Callable<Boolean>() {
            
            @Override
            public Boolean call() throws Exception {
                return !oreClicker.isCurrentOreValid();
            }
        }, 100, 120);
        oreClicker.clearInteracting();
    }
    
    private boolean hasMine() {
        if (!ctx.objects.select().id(ore.getIds()).select(Interactive.areInViewport()).isEmpty() || !ctx.objects.select().id(ore.getIds()).isEmpty()) {
            return true;
        }
        return false;
    }
}
