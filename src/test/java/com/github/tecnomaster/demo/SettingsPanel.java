package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.Sphere;
import com.github.tecnomaster.verlet.Verlet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private final DemoFrame demoFrame;
    private int borderType;
    private TestSceneRenderer testSceneRenderer;
    private JSlider size;
    private int objectType;
    public SettingsPanel(DemoFrame demoFrame) {
        this.demoFrame = demoFrame;
        borderType = 0;
        objectType = 0;

        setBorder(new LineBorder(Color.BLACK, 2));

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Scene", getScene());

        tabbedPane.addTab("Add Objects", getAddObjects());
        add(tabbedPane);
    }

    public int getBorderType() {
        return borderType;
    }

    private void updateBorder(int type) {
        borderType = type;
        demoFrame.updateWindowSize();
    }

    private void updateTestScene(int objectType) {

        this.objectType = objectType;

        Scene scene = Verlet.createScene();

        spawn(scene);

        testSceneRenderer.scene = scene;
        testSceneRenderer.repaint();
    }

    private void spawn(Scene scene) {
        if(objectType == 0) {
            scene.addSphere(new DemoSphere(0, 0, size.getValue()));
        } else if(objectType == 1) {
            Demo.spawnCube(0, 0, size.getValue()/2.5f, 3, scene);
        } else if(objectType == 2) {
            Demo.spawnRope(0, 0, size.getValue()/3.5f, 15, scene);
        }
    }

    private JPanel getAddObjects() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(10));

        testSceneRenderer = new TestSceneRenderer(200, 200);
        testSceneRenderer.setBorder(new LineBorder(Color.BLACK, 2));
        panel.add(testSceneRenderer);

        panel.add(Box.createVerticalStrut(10));

        JPanel coreSettings = new JPanel();
        coreSettings.setBorder(new LineBorder(Color.BLACK, 2));
        coreSettings.setLayout(new BoxLayout(coreSettings, BoxLayout.Y_AXIS));

        ButtonGroup group = new ButtonGroup();
        JRadioButton sphere = new JRadioButton("Basic Sphere");
        JRadioButton box = new JRadioButton("Box");
        JRadioButton rope = new JRadioButton("Rope");
        sphere.setSelected(true);
        sphere.addActionListener(l -> updateTestScene(0));
        box.addActionListener(l -> updateTestScene(1));
        rope.addActionListener(l -> updateTestScene(2));

        group.add(sphere);
        group.add(box);
        group.add(rope);

        coreSettings.add(new JLabel("Object:"));
        coreSettings.add(sphere);
        coreSettings.add(box);
        coreSettings.add(rope);

        coreSettings.add(Box.createVerticalStrut(10));

        size = new JSlider(10,75,25);
        size.addChangeListener(l -> updateTestScene(objectType));

        coreSettings.add(new JLabel("Size:"));
        coreSettings.add(size);

        panel.add(coreSettings);

        coreSettings.add(Box.createVerticalStrut(10));

        JButton spawnButton = new JButton("Spawn");
        spawnButton.addActionListener(l -> {
            spawn(demoFrame.getScene());
        });
        panel.add(spawnButton);

        updateTestScene(objectType);

        return panel;
    }

    private JPanel getScene() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel border = new JPanel();
        border.setBorder(new LineBorder(Color.BLACK, 2));
        border.setLayout(new BoxLayout(border, BoxLayout.Y_AXIS));

        ButtonGroup group = new ButtonGroup();
        JRadioButton b1 = new JRadioButton("Rectangle");
        JRadioButton b2 = new JRadioButton("Circle");
        JRadioButton b3 = new JRadioButton("Void");

        b1.setSelected(true);
        b1.addActionListener(e -> updateBorder(0));
        b2.addActionListener(e -> updateBorder(1));
        b3.addActionListener(e -> updateBorder(2));

        group.add(b1);
        group.add(b2);
        group.add(b3);

        border.add(new JLabel("Scene Borders"));
        border.add(b1);
        border.add(b2);
        border.add(b3);

        panel.add(Box.createVerticalStrut(10));
        panel.add(border);

        return panel;
    }

    private static class TestSceneRenderer extends JPanel {
        private Scene scene;
        private Dimension size;
        public TestSceneRenderer(int width, int height) {
            this.size = new Dimension(width, height);
            this.scene = null;
        }

        @Override
        public Dimension getPreferredSize() {
            return size;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if(scene != null) scene.invokeSpheres(sphere -> {
                render(g, sphere);
            });
        }

        private void render(Graphics g, Sphere sphere) {
            float radius = sphere.getRadius();
            g.fillOval((int) (translateX(sphere.getX()) - radius), (int) (translateY(sphere.getY()) - radius), (int) (radius * 2), (int) (radius * 2));
        }

        public int translateX(double x) {
            return (int) (x + getWidth() / 2d);
        }

        public int translateY(double y) {
            return (int) -(y - getHeight() / 2d);
        }
    }
}
