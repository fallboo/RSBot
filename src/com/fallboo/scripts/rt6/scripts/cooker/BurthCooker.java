package com.fallboo.scripts.rt6.scripts.cooker;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.scripts.cooker.actions.*;
import com.fallboo.scripts.rt6.scripts.cooker.data.Bank;
import com.fallboo.scripts.rt6.scripts.cooker.data.CookableFood;
import com.fallboo.scripts.rt6.scripts.cooker.data.FoodTypes;
import com.fallboo.scripts.rt6.scripts.cooker.gui.Gui;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;

import java.awt.*;

@Script.Manifest(name = "hCooker", description = "Cooks food in Burthrope and Al Kahrid")
public class BurthCooker extends GraphScript<ClientContext> implements PaintListener, MessageListener {

    private Gui gui = null;
    private final double version = 0.2;
    private BurthCooker instance;
    private CookableFood food;

    @SuppressWarnings("serial")
    @Override
    public void start() {
        instance = this;
        ctx.paint.setTitle("hCooker v" + version);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui = new Gui(instance);
                gui.setVisible(true);
            }
        });
    }


    @Override
    public void repaint(Graphics g1) {
        ctx.paint.draw(g1);
    }


    public void setupCooking(Bank bank, FoodTypes foodTypes, CookableFood food) {
        chain.add(new StatUpdaterTask(ctx));
        this.food = food;
        chain.add(new WalkToRange(ctx, bank, food));
        chain.add(new Cook(ctx, foodTypes, food));
        chain.add(new BankFood(ctx, food));
        chain.add(new WalkToBank(ctx, bank, food));
    }

    @Override
    public void messaged(MessageEvent me) {
        if (me.type() == 109) {
            if (me.text().matches("(You )(success?fully|cook|manage to cook).*")) {
                ctx.paint.addItem("Cooked " + food.getName());
            } else if (me.text().contains("burn")) {
                ctx.paint.addItem("Burnt " + food.getName());
            }
        }
    }

}
