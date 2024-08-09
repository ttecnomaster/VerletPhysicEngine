package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.implementation.SphereRunnable;
import com.github.tecnomaster.verlet.utils.VectorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DemoFrame extends JFrame implements MouseListener, MouseMotionListener {
    private final JPanel panel;
    private Scene scene;
    private DemoRenderer renderer;
    private int mouseX, mouseY;
    public DemoFrame(Scene scene) {
        super("Demo");

        renderer = new DemoRenderer();

        mouseX = mouseY = 0;

        this.scene = scene;
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
        add(panel);

        setVisible(true);
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
