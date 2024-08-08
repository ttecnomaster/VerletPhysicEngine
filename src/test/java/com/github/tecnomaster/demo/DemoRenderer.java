package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.Sphere;

import javax.swing.*;
import java.awt.*;

public class DemoRenderer {

    private JPanel panel;

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void renderScene(Graphics g, Scene scene) {
        scene.invokeSpheres(sphere -> renderSphere(g, sphere));
    }

    private void renderSphere(Graphics g, Sphere sphere) {
        drawSphere(g, true, sphere.getX(), sphere.getY(), sphere.getRadius());
    }

    private void drawSphere(Graphics g, boolean fill, double x, double y, double radius) {
        if(fill) fillSphere(g, translateX(x) - radius, translateY(y) - radius, radius * 2, radius * 2);
        else drawSphere(g, translateX(x) - radius, translateY(y) - radius, radius * 2, radius * 2);
    }

    private void drawSphere(Graphics g, double x, double y, double width, double height) {
        g.drawOval((int) x, (int) y, (int) width, (int) height);
    }

    private void fillSphere(Graphics g, double x, double y, double width, double height) {
        g.fillOval((int) x, (int) y, (int) width, (int) height);
    }

    private int translateX(double x) {
        return (int) (x + panel.getWidth()/2d);
    }

    private int translateY(double y) {
        return (int) -(y - panel.getHeight()/2d);
    }
}
