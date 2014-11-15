package com.fallboo.scripts.rt6.scripts.cooker.actions;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.scripts.cooker.data.CookableFood;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Bank.Amount;

import java.util.concurrent.Callable;

public class BankFood extends GraphScript.Action<ClientContext> {
    private final CookableFood food;

    public BankFood(ClientContext ctx, CookableFood food) {
        super(ctx);
        this.food = food;
    }

    @Override
    public boolean valid() {
        return ctx.bank.opened() && (ctx.backpack.select().id(food.getIds()).count() == 0);
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Banking");
        if (ctx.bank.depositInventory())
            if (!Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.select().count() == 0;
                }
            }, 750, 5)) return;
        if (ctx.backpack.select().count() > 0)
            return;
        if (ctx.bank.select().id(food.getIds()).count() == 0) {
            if (Random.nextInt(0, 10) == 2)
                ctx.bank.close();
            ctx.controller.stop();
            return;
        }
        if (food.getIds().length == 1) {
            ctx.bank.withdraw(food.getIds()[0], Amount.ALL);
        }
    }
}
