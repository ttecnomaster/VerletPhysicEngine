package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;

import javax.swing.*;
import java.awt.*;

public class DemoFrame extends JFrame {
    public DemoFrame(Scene scene) {
        super("Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1000, 750);

        add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                DemoRenderer.renderScene(scene);
            }
        });

        setVisible(true);
    }
}
