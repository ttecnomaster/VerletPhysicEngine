package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.Sphere;
import com.github.tecnomaster.verlet.Verlet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SettingsPanel extends JPanel {
    public static final float MINIMUM_SPHERE_RADIUS = 10;
    public static final float MAXIMUM_SPHERE_RADIUS = 75;
    private final DemoFrame demoFrame;
    private int borderType;
    private TestSceneRenderer testSceneRenderer;
    private JSlider size;
    private int objectType;
    private boolean spawn;
    private int gx, gy;
    public SettingsPanel(DemoFrame demoFrame) {
        this.demoFrame = demoFrame;
        borderType = 0;
        objectType = 0;
        gx = 0;
        gy = -1000;

        setBorder(new LineBorder(Color.BLACK, 2));

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Scene", getScene());

        tabbedPane.addTab("Add Objects", getAddObjects());
        add(tabbedPane);
    }

    public int getBorderType() {
        return borderType;
    }

    public boolean isSpawn() {
        return spawn;
    }

    public BufferedImage getObjectPreviewRender() {
        return testSceneRenderer.render;
    }

    private void updateBorder(int type) {
        borderType = type;
        demoFrame.updateWindowSize();
    }

    private void updateTestScene(int objectType) {

        this.objectType = objectType;

        Scene scene = Verlet.createScene();

        spawn(scene, 0, 0);

        testSceneRenderer.scene = scene;
        testSceneRenderer.repaint();
    }

    protected void spawn(Scene scene, double x, double y) {
        if(objectType == 0) {
            scene.addSphere(new DemoSphere(x, y, size.getValue()));
        } else if(objectType == 1) {
            Demo.spawnCube(x, y, size.getValue()/2.5f, 3, scene);
        } else if(objectType == 2) {
            Demo.spawnRope(x, y, size.getValue()/3.5f, 15, scene);
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

        size = new JSlider((int) MINIMUM_SPHERE_RADIUS, (int) MAXIMUM_SPHERE_RADIUS,25);
        size.addChangeListener(l -> updateTestScene(objectType));

        coreSettings.add(new JLabel("Size:"));
        coreSettings.add(size);

        panel.add(coreSettings);

        coreSettings.add(Box.createVerticalStrut(10));

        JCheckBox spawnButton = new JCheckBox("Spawn");
        spawnButton.addActionListener(l -> {
            updateTestScene(objectType);
            spawn = spawnButton.isSelected();
        });
        spawnButton.setSelected(false);
        panel.add(spawnButton);

        updateTestScene(objectType);

        return panel;
    }

    private JPanel getScene() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel gravity = new JPanel();
        gravity.setBorder(new LineBorder(Color.BLACK, 2));
        gravity.setLayout(new GridLayout(3,2));
        gravity.add(new JLabel("Gravity:"));
        gravity.add((new JLabel()));
        JLabel gXL = new JLabel("X: 0");
        gravity.add(gXL);
        JSlider gXS = new JSlider(-5000,5000,0);
        gXS.addChangeListener(l -> {
            gXL.setText("X: "+gXS.getValue());
            gx = gXS.getValue();
            demoFrame.getSolver().setGravity(gx, gy);
        });
        gravity.add(gXS);
        JLabel gYL = new JLabel("Y: -1000");
        gravity.add(gYL);
        JSlider gYS = new JSlider(-5000,5000,-1000);
        gYS.addChangeListener(l -> {
            gYL.setText("Y: "+gYS.getValue());
            gy = gYS.getValue();
            demoFrame.getSolver().setGravity(gx, gy);
        });
        gravity.add(gYS);

        JPanel borderWrap = new JPanel();
        borderWrap.setLayout(new GridLayout(1,1));
        JPanel border = new JPanel();
        borderWrap.add(border);
        borderWrap.setBorder(new LineBorder(Color.BLACK, 2));
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
        panel.add(gravity);
        panel.add(Box.createVerticalStrut(10));
        panel.add(borderWrap);

        return panel;
    }

    private static class TestSceneRenderer extends JPanel {
        private Scene scene;
        private Dimension size;
        private BufferedImage render;
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

            render = new BufferedImage(g.getClipBounds().width+100, g.getClipBounds().height+600, BufferedImage.TYPE_INT_ARGB);
            Graphics renderG = render.getGraphics();

            renderG.setColor(Color.BLACK);
            if(scene != null) scene.invokeSpheres(sphere -> {
                render(renderG, sphere);
            });

            g.drawImage(render, -50, -50, null);
        }

        private void render(Graphics g, Sphere sphere) {
            float radius = sphere.getRadius();
            g.fillOval((int) (translateX(sphere.getX()) - radius), (int) (translateY(sphere.getY()) - radius), (int) (radius * 2), (int) (radius * 2));
        }

        public int translateX(double x) {
            return (int) (x + getWidth() / 2d) + 50;
        }

        public int translateY(double y) {
            return (int) -(y - getHeight() / 2d) + 50;
        }
    }
}
