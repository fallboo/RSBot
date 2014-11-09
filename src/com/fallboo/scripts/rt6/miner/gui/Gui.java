package com.fallboo.scripts.rt6.miner.gui;

import com.fallboo.scripts.rt6.miner.AIOMiner;
import com.fallboo.scripts.rt6.miner.data.Mines;
import com.fallboo.scripts.rt6.miner.data.MiningStyle;
import com.fallboo.scripts.rt6.miner.data.Rocks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jake on 05/11/2014.
 */
public class Gui extends JFrame {
    private final AIOMiner miner;

    public Gui(AIOMiner miner) {
        this.miner = miner;
        initComponents();
    }

    private void initComponents() {
        GridLayout layout = new GridLayout(0, 1);
        container = new JPanel(layout);
        labelTitle = new JLabel("hMiner", JLabel.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 22));
        labelMine = new JLabel("Location", JLabel.CENTER);
        labelOre = new JLabel("Ore", JLabel.CENTER);
        labelMiningStyle = new JLabel("Mining Style", JLabel.CENTER);
        mineLocationBox = new JComboBox(new DefaultComboBoxModel(Mines.values()));
        oreBox = new JComboBox(new DefaultComboBoxModel(Mines.values()[0].getOres()));
        mineMethodBox = new JComboBox(new DefaultComboBoxModel(MiningStyle.values()));
        button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonPressed();
            }
        });
        mineLocationBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineLocationChange();
            }
        });
        container.add(labelTitle);
        container.add(labelMine);
        container.add(mineLocationBox);
        container.add(labelOre);
        container.add(oreBox);
        container.add(labelMiningStyle);
        container.add(mineMethodBox);
        container.add(button);
        add(container);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel container;
    private JLabel labelTitle, labelMine, labelOre, labelMiningStyle;
    private JComboBox mineLocationBox, oreBox, mineMethodBox;
    private JButton button;

    private void startButtonPressed() {
        setVisible(false);
        miner.setupMining((Mines) mineLocationBox.getSelectedItem(), (Rocks) oreBox.getSelectedItem(), (MiningStyle) mineMethodBox.getSelectedItem());
        dispose();
    }

    private void mineLocationChange() {
        oreBox.setModel(new DefaultComboBoxModel(Mines.values()[mineLocationBox.getSelectedIndex()].getOres()));
    }
}
