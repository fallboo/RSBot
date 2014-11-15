package com.fallboo.scripts.rt6.scripts.miner;

import com.fallboo.scripts.rt6.framework.ClientContext;
import com.fallboo.scripts.rt6.framework.GraphScript;
import com.fallboo.scripts.rt6.scripts.miner.actions.*;
import com.fallboo.scripts.rt6.scripts.miner.data.Mines;
import com.fallboo.scripts.rt6.scripts.miner.data.MiningStyle;
import com.fallboo.scripts.rt6.scripts.miner.data.Rocks;
import com.fallboo.scripts.rt6.scripts.miner.gui.Gui;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;

import java.awt.*;

@Script.Manifest(name = "hMiner", description = "Mines ores F2P")
public class AIOMiner extends GraphScript<ClientContext> implements PaintListener, MessageListener {

    private Gui gui = null;
    private final double version = 0.2;
    private AIOMiner instance;

    @SuppressWarnings("serial")
    @Override
    public void start() {
        instance = this;
        ctx.paint.setTitle("hMiner   v" + version);
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

    private void setupNormalActions() {
        chain.add(new KeyUsing(ctx));
        chain.add(new StatUpdaterTask(ctx));
    }

    public void setupMining(Mines mine, Rocks ores, MiningStyle ms) {
        setupNormalActions();
        chain.add(new WalkToMine(ctx, mine));
        chain.add(new Mine(ctx, mine, ores));
        if (ms == MiningStyle.BANK) {
            chain.add(new BankOres(ctx));
            chain.add(new WalkToBank(ctx, mine));
        } else {
            chain.add(new OreDrop(ctx, ms, ores.getOre()));
        }
    }

    @Override
    public void messaged(MessageEvent me) {
        if (me.type() == 109 && me.text().contains("You manage to mine some ")) {
            ctx.paint.addItem("Ore");
            //TODO Add jewel finding 'You just found a _____!'
        }
    }

}
