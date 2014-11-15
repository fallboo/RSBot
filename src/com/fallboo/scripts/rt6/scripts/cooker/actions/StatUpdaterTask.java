/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fallboo.scripts.rt6.scripts.cooker.actions;

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
        if (!ctx.paint.hasLevels("Cooking")) {
            int startLevel = ctx.skills.level(Skills.COOKING),
                    startXp = ctx.skills.experience(Skills.COOKING);
            if (startLevel <= 0) {
                lastUpdate += 7500;
                return;
            }
            ctx.paint
                    .setLevel("Cooking", startLevel);
            ctx.paint.setExp("Cooking", startXp);
        } else {
            int currLevel = ctx.skills.level(Skills.COOKING),
                    currXp = ctx.skills.experience(Skills.COOKING);
            if (currLevel > 0) {
                ctx.paint.setLevel("Cooking", currLevel);
                ctx.paint.setExp("Cooking", currXp);
            }
        }
        lastUpdate = ctx.controller.script().getTotalRuntime() + 1500;
    }

}
