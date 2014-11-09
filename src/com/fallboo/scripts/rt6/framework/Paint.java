package com.fallboo.scripts.rt6.framework;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by Jake on 08/11/2014.
 */
public class Paint {
    private String status = "Setting up";
    private String title = "";
    private final HashMap<String, IntegerPair> levels = new HashMap<String, IntegerPair>(), expGain = new HashMap<String, IntegerPair>();
    private final HashMap<String, Integer> itemsGained = new HashMap<String, Integer>();
    private final ClientContext ctx;

    public Paint(ClientContext ctx) {
        this.ctx = ctx;
    }

    public boolean hasLevels(String stat) {
        return levels.containsKey(stat);
    }

    public void setLevel(String stat, int level) {
        if (expGain.containsKey(stat))
            levels.get(stat).setCurrentX(level);
        else
            levels.put(stat, new IntegerPair(level));
    }

    public IntegerPair getLevels(String stat) {
        return levels.get(stat);
    }

    public int getExpGain(String stat) {
        return expGain.get(stat).getDifference();
    }

    public void setExp(String stat, int newExp) {
        if (expGain.containsKey(stat))
            expGain.get(stat).setCurrentX(newExp);
        else
            expGain.put(stat, new IntegerPair(newExp));
    }

    public void addItem(String item) {
        addItem(item, 1);
    }

    public void addItem(String item, int amount) {
        if (!itemsGained.containsKey(item)) itemsGained.put(item, amount);
        else itemsGained.put(item, itemsGained.get(item) + amount);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void draw(Graphics g1) {
        double timePassed = 0;
        long runtime = ctx.controller.script().getRuntime();
        Graphics2D g = (Graphics2D) g1;
        int heightExcess = (levels.size() + expGain.size() + itemsGained.size()) * 20;
        if (ctx.controller.script().getRuntime() > 0) {
            timePassed = 3600000D / ctx.controller.script().getTotalRuntime();
        }
        g.setColor(color1);
        g.fillRoundRect(72, 26, 230, 70 + heightExcess, 16, 16);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRoundRect(72, 26, 230, 70 + heightExcess, 16, 16);
        g.setFont(font1);
        g.setColor(color3);
        g.drawString(title, 127, 45);
        g.setColor(color4);
        g.drawString(title, 128, 46);
        g.setFont(font2);
        g.setColor(color5);
        g.drawString("Status: " + status, 75, 67);
        g.drawString("Time Running: " + formatTime(ctx.controller.script().getTotalRuntime()), 75, 87);
        int height = 87;
        for (String name : levels.keySet()) {
            g.drawString(name + " levels gained: " + levels.get(name).getDifference(), 75, height += 20);
        }
        for (String name : expGain.keySet())
            g.drawString(name + " xp: " + expGain.get(name).getDifference() + (runtime <= 0 ? "" : " (" + (int) (expGain.get(name).getDifference() * timePassed) + ")"), 75, height += 20);
        for (String name : itemsGained.keySet())
            g.drawString(name + ": " + itemsGained.get(name) + (runtime <= 0 ? "" : " (" + (int) (itemsGained.get(name) * timePassed) + ")"), 75, height += 20);
    }

    private String formatTime(final long time) {
        final int sec = (int) (time / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private final Color color1 = new Color(0, 68, 255, 153);
    private final Color color2 = new Color(0, 126, 255, 144);
    private final Color color3 = new Color(0, 0, 0);
    private final Color color4 = new Color(255, 255, 255);
    private final Color color5 = Color.BLACK;

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Forte", 0, 18);
    private final Font font2 = new Font("Arial", 1, 16);

}
