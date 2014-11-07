/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.miner.AIOMiner;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.ScheduledBreak;
import com.fallboo.scripts.utils.ScriptResumer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.fallboo.scripts.rt6.framework.ClientContext;

/**
 *
 * @author Jake
 */
public class Sleeping extends GraphScript.Action<ClientContext> {

    private ScheduledBreak[] breaks;
    private int index = 0;
    private long nextAction;
    private final AIOMiner miner;
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecuter;

    public Sleeping(ClientContext ctx, ScheduledBreak[] breaks, AIOMiner miner) {
        super(ctx);
        this.breaks = breaks;
        nextAction = (breaks[index].getWorkTime() * 60000) + ctx.controller.script().getTotalRuntime();
        this.miner = miner;
        scheduledThreadPoolExecuter = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public boolean valid() {
        return nextAction < ctx.controller.script().getTotalRuntime();
    }

    @Override
    public void run() {
        miner.setStatus("Sleeping");
        ctx.game.logout(true);
        int currentBreak = breaks[index].getBreakTime() * 60000;
        scheduledThreadPoolExecuter.schedule(new ScriptResumer(ctx), currentBreak, TimeUnit.MILLISECONDS);
        index++;
        if (index == breaks.length) {
            index = 0;
        }
        nextAction = (breaks[index].getWorkTime() * 60000) + currentBreak + ctx.controller.script().getTotalRuntime();
        ctx.controller.suspend();
    }
}
