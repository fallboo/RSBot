package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.miner.AIOMiner;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.Bars;
import com.fallboo.scripts.rt6.miner.data.Furnaces;
import com.fallboo.scripts.rt6.miner.data.Mines;
import com.fallboo.scripts.rt6.miner.data.Style;
import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import com.fallboo.scripts.rt6.framework.ClientContext;

public class WalkToBank extends GraphScript.Action<ClientContext> {

    private final Style style;
    private final Bars bars;
    private final Tile bank;
    private final Tile[] pathToBank;
    private final AIOMiner miner;

    public WalkToBank(ClientContext ctx, Mines mines, Furnaces furnaces, Style style, Bars bars, AIOMiner miner) {
        super(ctx);
        this.miner = miner;
        if (mines != null) {
            bank = mines.getBank();
            pathToBank = mines.getLocationToBank();
        } else {
            bank = furnaces.getBank();
            pathToBank = furnaces.getLocationToBank();
        }
        this.style = style;
        this.bars = bars;
    }

    @Override
    public boolean valid() {
        return ((ctx.backpack.select().count() == 28 && style == Style.MINING) || (style == Style.SMELTING && ctx.backpack.select().id(bars.getCost().getItem1().getId()).count() == 0))
                && ctx.players.local().animation() == -1 && (!ctx.bank.opened() || !ctx.bank.inViewport());
    }

    @Override
    public void run() {
        miner.setStatus("Walking to bank");
        if (bank.distanceTo(ctx.players.local().tile()) > 20) {
            if (!ctx.movement.newTilePath(pathToBank).traverse()) {
                ctx.movement.findPath(bank).traverse();
            }
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().idle();
                }
            }, 100, 5);
            return;
        } else if (!ctx.bank.inViewport()) {
            ctx.movement.findPath(bank).traverse();
        }
        if (!ctx.bank.open()) {
            ctx.camera.turnTo(ctx.bank.nearest());
        }
    }

}
