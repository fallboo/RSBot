package com.fallboo.scripts.rt6.scripts.miner.actions;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript.Action;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Widget;

import java.util.concurrent.Callable;

public class KeyUsing extends Action<ClientContext> {

    public KeyUsing(ClientContext ctx) {
        super(ctx);
    }

    private final int KEY_WIDGET = 1048, KEY_COMPONENT = 30;

    @Override
    public boolean valid() {
        return !ctx.backpack.select().id(24154).isEmpty();
    }

    @Override
    public void run() {
        final Widget keyWidget = ctx.widgets.select().id(KEY_WIDGET).poll();
        for (Item i : ctx.backpack.first()) {
            i.interact("Claim key");
            if (Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return keyWidget.component(KEY_COMPONENT).visible();
                }
            }, 100, 6)) {
                keyWidget.component(KEY_COMPONENT).click();
            }
        }
    }

}
