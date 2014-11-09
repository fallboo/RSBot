package com.fallboo.scripts.rt6.cooker.actions;

import com.fallboo.scripts.rt6.cooker.data.CookableFood;
import com.fallboo.scripts.rt6.cooker.data.FoodTypes;
import com.fallboo.scripts.rt6.framework.AntiPattern;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Widget;

import java.util.concurrent.Callable;

/**
 * Created by Jake on 08/11/2014.
 */
public class OvenUser extends GraphScript.Action<ClientContext> {
    private final FoodTypes foodType;
    private final CookableFood food;
    private final int OVEN_WIDGET = 1370, CLOSE_WIDGET = 1251, OVEN_SELECT_COMPONENT = 38, CLOSE_WIDGET_COMPONENT = 49;
    private final int[] RANGES = {67061};

    public OvenUser(ClientContext ctx, FoodTypes foodType, CookableFood food) {
        super(ctx);
        this.foodType = foodType;
        this.food = food;
    }

    @Override
    public boolean valid() {
      /*  System.out.println("1: " + (ctx.backpack.select().id(food.getIds()).count() != 0));
        System.out.println("2: " + (isCookingWindowOpen()));
        System.out.println("3: " +(!ctx.objects.select().id(RANGES).isEmpty()) );
        System.out.println("4: " + (ctx.players.local().animation() == -1));
        System.out.println("5: " + (!ctx.players.local().inMotion()));*/
        return ctx.backpack.select().id(food.getIds()).count() != 0
                && (isCookingWindowOpen() || !ctx.objects.select().id(RANGES).isEmpty())
                && ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion();
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Cooking");
        ctx.widgets.select().id(OVEN_WIDGET, CLOSE_WIDGET);
        final Widget ovenWidget = getSelectedWidget(OVEN_WIDGET), closeWidget = getSelectedWidget(CLOSE_WIDGET);
        if (ovenWidget == null || closeWidget == null) {
            System.out.println("Return 1");
            return;
        }
        if (!ovenWidget.component(OVEN_SELECT_COMPONENT).visible() && !closeWidget.component(CLOSE_WIDGET_COMPONENT).visible()) {
            if (!clickRange()) {
                System.out.println("Return 2");
                return;
            }
            Condition.sleep(250);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ovenWidget.component(OVEN_SELECT_COMPONENT).visible();
                }
            }, 250, 10);
        }
        if (ovenWidget.component(OVEN_SELECT_COMPONENT).visible()) {
            ovenWidget.component(OVEN_SELECT_COMPONENT).click();
            Condition.sleep(1250);
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return (!ctx.players.local().inMotion() || closeWidget.component(CLOSE_WIDGET_COMPONENT).visible());
                }
            }, 500, 80);
        }
        if (closeWidget.component(CLOSE_WIDGET_COMPONENT).visible()) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    ctx.antiPattern.run(AntiPattern.State.IDLE);
                    return !closeWidget.component(CLOSE_WIDGET_COMPONENT).visible();
                }
            }, 250, 40);
        }
        System.out.println("End");

    }

    private boolean clickRange() {
        final GameObject range = ctx.objects.select().id(RANGES).nearest().poll();
        if (range == ctx.objects.nil())
            return false;
        if (!range.inViewport()) {
            ctx.camera.turnTo(range, 6);
            if (range.tile().distanceTo(ctx.players.local()) > 7)
                ctx.movement.step(range);
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return range.inViewport();
                }
            }, 200, 5);
        }
        return range.interact("Cook-at");
    }

    private Widget getSelectedWidget(int id) {
        for (Widget widget : ctx.widgets) {
            if (widget.id() == id) return widget;
        }
        return null;
    }

    private boolean isCookingWindowOpen() {
        return !ctx.widgets.select().id(OVEN_WIDGET, CLOSE_WIDGET).isEmpty() && (ctx.widgets.poll().valid() || ctx.
                widgets.poll().valid());
    }
}
