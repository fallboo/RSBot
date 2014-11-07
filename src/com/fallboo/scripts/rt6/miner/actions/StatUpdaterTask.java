/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.AIOMiner;
import org.powerbot.script.rt4.Skills;
import com.fallboo.scripts.rt6.framework.ClientContext;

/**
 *
 * @author Jake
 */
public class StatUpdaterTask extends GraphScript.Action<ClientContext> {

    private long lastUpdate = 0;
    private final AIOMiner miner;

    public StatUpdaterTask(ClientContext ctx, AIOMiner miner) {
        super(ctx);
        this.miner = miner;
    }

    @Override
    public boolean valid() {
        return lastUpdate < ctx.controller.script().getTotalRuntime() && ctx.game.loggedIn();
    }

    @Override
    public void run() {
        if (miner.getStartLevel() == 0) {
            int startLevel = ctx.skills.level(Skills.MINING),
                    startXp = ctx.skills.experience(Skills.MINING);
            miner.setStartLevel(startLevel);
            miner.setStartXp(startXp);
        } else {
            int currLevel = ctx.skills.level(Skills.MINING),
                    currXp = ctx.skills.experience(Skills.MINING);
            if (currLevel > 0) {
                miner.setCurrLevel(currLevel);
                miner.setCurrXp(currXp);
            }
        }
        lastUpdate = ctx.controller.script().getTotalRuntime() + 7500;
    }

}
