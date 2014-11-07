package com.fallboo.scripts.rt6.miner.gui;

import com.fallboo.scripts.rt6.miner.data.Mines;
import com.fallboo.scripts.rt6.miner.data.MiningStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jake on 05/11/2014.
 */
public class GuiV2 extends JFrame {
    public GuiV2() {
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

    public static void main(String[] args0) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuiV2().setVisible(true);
            }
        });
    }

    private void startButtonPressed() {

    }

    private void mineLocationChange() {
        oreBox.setModel(new DefaultComboBoxModel(Mines.values()[mineLocationBox.getSelectedIndex()].getOres()));
    }
}
