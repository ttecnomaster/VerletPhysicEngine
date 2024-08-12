package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Constraint;
import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.Solver;
import com.github.tecnomaster.verlet.constraint.CircleAreaConstraint;
import com.github.tecnomaster.verlet.constraint.RectangleConstraint;
import com.github.tecnomaster.verlet.implementation.SphereRunnable;
import com.github.tecnomaster.verlet.utils.VectorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DemoFrame extends JFrame implements MouseListener, MouseMotionListener {
    private final JPanel panel;
    private final SettingsPanel settings;
    private Scene scene;
    private Solver solver;
    private DemoRenderer renderer;
    private int mouseX, mouseY;
    private Constraint borderConstraint;
    public DemoFrame(Scene scene, Solver solver) {
        super("Demo");

        renderer = new DemoRenderer();

        mouseX = mouseY = 0;

        this.scene = scene;
        this.solver = solver;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1000, 750);
        addMouseListener(this);
        addMouseMotionListener(this);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                renderer.renderScene(g, scene);
            }
        };
        renderer.setPanel(panel);

        settings = new SettingsPanel(this);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateWindowSize();
            }
        });

        main.add(panel, BorderLayout.CENTER);
        main.add(settings, BorderLayout.EAST);

        add(main);

        setVisible(true);
    }

    public Scene getScene() {
        return scene;
    }

    protected void updateWindowSize() {
        if(borderConstraint != null) scene.removeConstraint(borderConstraint);

        // VerletGrid

        // Border Constraint
        int borderType = settings.getBorderType();
        if(borderType == 0) borderConstraint = new RectangleConstraint(getPanelDimension().width, getPanelDimension().height);
        else if(borderType == 1) borderConstraint = new CircleAreaConstraint(0, 0, Math.min(getPanelDimension().width, getPanelDimension().height)/2f);
        else borderConstraint = null;

        if(borderConstraint != null) {
            scene.addConstraint(borderConstraint);
        }
    }

    public Dimension getPanelDimension() {
        return panel.getSize();
    }

    public void render() {
        scene.invokeSpheres(sphere -> {
            if(sphere instanceof DemoSphere) ((DemoSphere)sphere).setHoverMode(false);
        });
        runAllHoveredSpheres(sphere -> {
            ((DemoSphere)sphere).setHoverMode(true);
        }, mouseX, mouseY);
        panel.repaint();
    }

    public double getMouseX() {
        return renderer.translateBackX(mouseX);
    }

    public double getMouseY() {
        return renderer.translateBackY(mouseY);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Get the x and y coordinates relative to the window
        int x = e.getX();
        int y = e.getY();
        runAllHoveredSpheres(sphere -> {
            ((DemoSphere)sphere).setClickMode(true);
        }, x, y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        scene.invokeSpheres(sphere -> {
            if(sphere instanceof DemoSphere) ((DemoSphere)sphere).setClickMode(false);
        });
    }

    private void runAllHoveredSpheres(SphereRunnable runnable, int x, int y) {
        scene.invokeSpheres(sphere -> {
            if(sphere instanceof DemoSphere && VectorUtil.length(renderer.translateBackX(x) - sphere.getX(), renderer.translateBackY(y - 30) - sphere.getY()) <= sphere.getRadius()) {
                runnable.run(sphere);
            }
        });
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
