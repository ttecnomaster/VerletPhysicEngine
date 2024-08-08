package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;

import javax.swing.*;
import java.awt.*;

public class DemoFrame extends JFrame {
    public DemoFrame(Scene scene) {
        super("Demo");

        DemoRenderer renderer = new DemoRenderer();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1000, 750);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                renderer.renderScene(g, scene);
            }
        };
        renderer.setPanel(panel);
        add(panel);

        setVisible(true);
    }
}
