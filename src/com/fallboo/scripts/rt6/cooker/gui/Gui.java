package com.fallboo.scripts.rt6.cooker.gui;

import com.fallboo.scripts.rt6.cooker.BurthCooker;
import com.fallboo.scripts.rt6.cooker.data.Bank;
import com.fallboo.scripts.rt6.cooker.data.CookableFood;
import com.fallboo.scripts.rt6.cooker.data.Fish;
import com.fallboo.scripts.rt6.cooker.data.FoodTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jake on 05/11/2014.
 */
public class Gui extends JFrame {
    private final BurthCooker cooker;

    public Gui(BurthCooker cooker) {
        this.cooker = cooker;
        initComponents();
    }

    private void initComponents() {
        GridLayout layout = new GridLayout(0, 1);
        container = new JPanel(layout);
        labelTitle = new JLabel("hMiner", JLabel.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 22));
        labelBank = new JLabel("Location", JLabel.CENTER);
        labelFoodType = new JLabel("Type", JLabel.CENTER);
        bankLocationBox = new JComboBox(new DefaultComboBoxModel(Bank.values()));
        foodTypeBox = new JComboBox(new DefaultComboBoxModel(FoodTypes.values()));
        foodSelectionBox = new JComboBox(new DefaultComboBoxModel(Fish.values()));
        button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonPressed();
            }
        });
        foodTypeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foodTypeChange();
            }
        });
        container.add(labelTitle);
        container.add(labelBank);
        container.add(bankLocationBox);
        container.add(labelFoodType);
        container.add(foodTypeBox);
        container.add(foodSelectionBox);
        container.add(button);
        add(container);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel container;
    private JLabel labelTitle, labelBank, labelFoodType;
    private JComboBox bankLocationBox, foodTypeBox, foodSelectionBox;
    private JButton button;

    public static void main(String[] args0) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui(null).setVisible(true);
            }
        });
    }

    private void startButtonPressed() {
        setVisible(false);
        cooker.setupCooking((Bank) bankLocationBox.getSelectedItem(), (FoodTypes) foodTypeBox.getSelectedItem(), (CookableFood) foodSelectionBox.getSelectedItem());
        dispose();
    }

    private void foodTypeChange() {
        switch ((FoodTypes) foodTypeBox.getSelectedItem()) {
            case FISH:
                foodSelectionBox.setModel(new DefaultComboBoxModel(Fish.values()));
                break;
        }
    }
}
