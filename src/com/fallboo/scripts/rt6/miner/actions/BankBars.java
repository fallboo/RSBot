package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.miner.data.Bars;
import java.util.concurrent.Callable;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;

import org.powerbot.script.rt6.Bank.Amount;
import com.fallboo.scripts.rt6.framework.ClientContext;

public class BankBars extends GraphScript.Action<ClientContext> {

    private final Bars bars;

    public BankBars(ClientContext ctx, Bars bar) {
        super(ctx);
        this.bars = bar;
    }

    @Override
    public boolean valid() {
        return (ctx.backpack.select().id(bars.getCost().getItem1().getId()).count() == 0 || (bars.getCost().requiresSecondary() && ctx.backpack.select().id(bars.getCost().getItem2().getId()).count() == 0)) && ctx.bank.opened();
    }

    @Override
    public void run() {
        if (ctx.backpack.select().id(bars.getId()).count() > 0) {
            ctx.bank.depositInventory();
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.select().id(bars.getId()).count() == 0;
                }
            }, 200, 4);
        }
        if (ctx.backpack.select().id(bars.getCost().getItem1().getId())
                .count() != bars.getPrimaryAmount() && ctx.bank.select().id(bars.getCost().getItem1().getId()).count() >= 0) {
            if (bars.getCost().requiresSecondary()) {
                ctx.bank.withdraw(bars.getCost().getItem1().getId(), bars.getPrimaryAmount() - ctx.backpack.select().id(bars.getCost().getItem1().getId())
                        .count());
            } else {
                ctx.bank.withdraw(bars.getCost().getItem1().getId(), Amount.ALL);
            }
        }
        if (bars.getCost().requiresSecondary()) {
            if (ctx.backpack.select().count() != 28 && ctx.bank.select().id(bars.getCost().getItem2().getId()).count() >= 0) {
                if (bars.getCost().requiresSecondary()) {
                    ctx.bank.withdraw(bars.getCost().getItem2().getId(), Random.nextInt(1, 5) == 2 ? Amount.ALL_BUT_ONE : Amount.ALL);
                }
            }
        }
        // TODO Paint((AIOMiner) ctx.script()).incrementBanks(1);
    }

}
