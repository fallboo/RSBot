package com.fallboo.scripts.rt6.miner.actions;

import com.fallboo.scripts.rt6.miner.AIOMiner;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.framework.ClientContext;

public class BankOres extends GraphScript.Action<ClientContext> {

    private final AIOMiner miner;
    
    public BankOres(ClientContext ctx, AIOMiner miner) {
        super(ctx);
        this.miner = miner;
    }
    
    @Override
    public boolean valid() {
        return ctx.backpack.select().count() == 28 && ctx.bank.opened();
    }
    
    @Override
    public void run() {
        miner.setStatus("Banking");
        ctx.bank.depositInventory();
    }
}
