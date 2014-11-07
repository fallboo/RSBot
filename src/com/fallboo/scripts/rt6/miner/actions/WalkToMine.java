package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.miner.AIOMiner;
import com.fallboo.scripts.rt6.framework.GraphScript.Action;
import com.fallboo.scripts.rt6.miner.data.Mines;
import com.fallboo.scripts.rt6.framework.ClientContext;

import java.util.concurrent.Callable;
import org.powerbot.script.Condition;

public class WalkToMine extends Action<ClientContext> {
    
    private final Mines mine;
    private final AIOMiner miner;
    
    public WalkToMine(ClientContext ctx, Mines mines, AIOMiner miner) {
        super(ctx);
        this.mine = mines;
        this.miner = miner;
    }
    
    @Override
    public boolean valid() {
        return ctx.backpack.select().count() < 28
                && !mine.getMineArea().contains(ctx.players.local())
                && ctx.players.local().animation() == -1;
    }
    
    @Override
    public void run() {
        miner.setStatus("Walking to mine");
        if (!ctx.movement.newTilePath(mine.getBankToLocation()).traverse()) {
            ctx.movement.findPath(mine.getLocation()).traverse();
        }
        Condition.wait(new Callable<Boolean>() {
            
            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().idle();
            }
        }, 100, 5);
    }
}
