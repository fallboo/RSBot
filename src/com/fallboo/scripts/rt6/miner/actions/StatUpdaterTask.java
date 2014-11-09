/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import org.powerbot.script.rt4.Skills;

/**
 * @author Jake
 */
public class StatUpdaterTask extends GraphScript.Action<ClientContext> {

    private long lastUpdate = 0;

    public StatUpdaterTask(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean valid() {
        return lastUpdate < ctx.controller.script().getTotalRuntime() && ctx.game.loggedIn();
    }

    @Override
    public void run() {
        if (!ctx.paint.hasLevels("Mining")) {
            int startLevel = ctx.skills.level(Skills.MINING),
                    startXp = ctx.skills.experience(Skills.MINING);
            if (startLevel <= 0) {
                lastUpdate += 7500;
                return;
            }
            ctx.paint
                    .setLevel("Mining", startLevel);
            ctx.paint.setExp("Mining", startXp);
        } else {
            int currLevel = ctx.skills.level(Skills.MINING),
                    currXp = ctx.skills.experience(Skills.MINING);
            if (currLevel > 0) {
                ctx.paint.setLevel("Mining", currLevel);
                ctx.paint.setExp("Mining", currXp);
            }
        }
        lastUpdate = ctx.controller.script().getTotalRuntime() + 7500;
    }

}
