package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;

public class BankOres extends GraphScript.Action<ClientContext> {

    public BankOres(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().count() == 28 && ctx.bank.opened();
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Banking");
        ctx.bank.depositInventory();
    }
}
