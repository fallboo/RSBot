package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.miner.AIOMiner;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.MiningStyle;
import com.fallboo.scripts.rt6.miner.data.Ores;

import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import com.fallboo.scripts.rt6.framework.ClientContext;
import org.powerbot.script.rt6.Item;

public class OreDrop extends GraphScript.Action<ClientContext> {

    private final MiningStyle miningStyle;
    private final Ores ore;
    private final AIOMiner miner;

    public OreDrop(ClientContext ctx, MiningStyle miningStyle, Ores ore, AIOMiner miner) {
        super(ctx);
        this.miningStyle = miningStyle;
        this.ore = ore;
        this.miner = miner;
    }

    @Override
    public boolean valid() {
        if (miningStyle == MiningStyle.DROP) {
            return ctx.backpack.select().count() == 28;
        } else if (miningStyle == MiningStyle.M1D1) {
            return ctx.backpack.select().count() >= 1;
        }
        return false;
    }

    @Override
    public void run() {
        miner.setStatus("Dropping");
        if (!ctx.combatBar.select().id(ore.getId()).isEmpty()) {
            int ores = ctx.backpack.id(ore.getId()).count();
            int key = ctx.combatBar.poll().slot() + 1;
            for (int i = 1; i <= ores + (miningStyle == MiningStyle.M1D1 ? 0 : Random.nextInt(0, 6)); i++) {
                ctx.input.send("{VK_" + key + "}");
                Condition.sleep(100);
            }
        }
        if (!Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return ctx.backpack.select().isEmpty();
            }
        }, 250, 8)) {
            if (!ctx.backpack.select().isEmpty()) {
                for (Item i : ctx.backpack.items()) {
                    if (i.valid()) {
                        i.interact("Drop");
                    }
                }
            }
        }
    }

}
