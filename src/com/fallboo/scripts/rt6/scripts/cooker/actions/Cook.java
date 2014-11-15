package com.fallboo.scripts.rt6.scripts.cooker.actions;

import com.fallboo.scripts.rt6.framework.AntiPattern;
import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.scripts.cooker.data.CookableFood;
import com.fallboo.scripts.rt6.scripts.cooker.data.FoodTypes;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.*;

import java.util.concurrent.Callable;

/**
 * Created by Jake on 08/11/2014.
 */
public class Cook extends GraphScript.Action<ClientContext> {
    private final FoodTypes foodType;
    private final CookableFood food;
    private final int OVEN_WIDGET = 1370, OVEN_FOOD_WIDGET = 1371, OVEN_FOOD_COMPONENT = 44, OVEN_SELECT_COMPONENT = 38, CLOSE_WIDGET = 1251, CLOSE_WIDGET_COMPONENT = 49;
    private final int[] RANGES = {67061, 76295};
    private final boolean clickFood = false;

    public Cook(ClientContext ctx, FoodTypes foodType, CookableFood food) {
        super(ctx);
        this.foodType = foodType;
        this.food = food;
    }

    @Override
    public boolean valid() {
        return ctx.backpack.select().id(food.getIds()).count() != 0
                && (isCookingWindowOpen() || (!ctx.objects.select().id(RANGES).isEmpty() && ctx.objects.peek().inViewport()))
                /*&& ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion()*/;
    }

    @Override
    public void run() {
        ctx.paint.setStatus("Cooking " + food.getName());
        ctx.widgets.select().id(OVEN_WIDGET, CLOSE_WIDGET);
        final Widget ovenWidget = getSelectedWidget(OVEN_WIDGET), closeWidget = getSelectedWidget(CLOSE_WIDGET);
        if (ovenWidget == null || closeWidget == null) {
            return;
        }
        if (!ovenWidget.component(OVEN_SELECT_COMPONENT).visible() && !closeWidget.component(CLOSE_WIDGET_COMPONENT).visible()) {
            if (!clickRange()) {
                return;
            }
            Condition.sleep(250);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return (!ctx.players.local().inMotion() && ctx.players.local().animation() == -1) || ovenWidget.component(OVEN_SELECT_COMPONENT).visible();
                }
            }, 450, 30);
        }
        if (!clickFood) {
            checkSelectedFood();
        }
        clickCook(ovenWidget);
        waitTillCooked(closeWidget);

    }

    private void checkSelectedFood() {
        final Widget cookFoodWidget = ctx.widgets.select().id(OVEN_FOOD_WIDGET).poll();
        Component selectedFood = cookFoodWidget.component(OVEN_FOOD_COMPONENT).component(food.getComponent());
        if (selectedFood.textureId() == -1) {
            ctx.antiPattern.clickComponent(selectedFood);
        }
    }

    private void waitTillCooked(final Widget closeWidget) {
        if (closeWidget.component(CLOSE_WIDGET_COMPONENT).visible()) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    ctx.antiPattern.run(AntiPattern.State.IDLE);
                    return !closeWidget.component(CLOSE_WIDGET_COMPONENT).visible();
                }
            }, 250, 40);
        }
    }

    private void clickCook(final Widget ovenWidget) {
        if (ovenWidget.component(OVEN_SELECT_COMPONENT).visible()) {
            ovenWidget.component(OVEN_SELECT_COMPONENT).click();
            Condition.sleep(1250);
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return !ovenWidget.component(OVEN_SELECT_COMPONENT).visible();
                }
            }, 200, 15);
        }
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
        if (clickFood && !ctx.backpack.itemSelected()) {
            final Item item = ctx.backpack.select().id(food.getIds()).shuffle().poll();
            if (!item.interact(true, "Use")) {
                return false;
            }
            if (!Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.itemSelected();
                }
            }, 100, 6))
                return false;
        }
        return range.interact(false, new Filter<Menu.Command>() {
            @Override
            public boolean accept(Menu.Command command) {
                return clickFood ? command.action.contains("Use") && (command.option.contains("Oven") || command.option.contains("Range")) :
                        command.action.contains("Cook at") && (command.option.contains("Oven") || command.option.contains("Range"));
            }
        });
    }

    private Widget getSelectedWidget(int id) {
        for (Widget widget : ctx.widgets) {
            if (widget.id() == id) return widget;
        }
        return null;
    }

    private boolean isCookingWindowOpen() {
        return (!ctx.widgets.select().id(OVEN_WIDGET, CLOSE_WIDGET).isEmpty() &&
                (getSelectedWidget(OVEN_WIDGET).component(OVEN_SELECT_COMPONENT).visible() || getSelectedWidget(CLOSE_WIDGET).component(CLOSE_WIDGET_COMPONENT).visible()));

    }
}
