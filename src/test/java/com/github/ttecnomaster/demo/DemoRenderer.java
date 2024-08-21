package com.github.ttecnomaster.demo;

import com.github.ttecnomaster.verlet.Scene;
import com.github.ttecnomaster.verlet.Sphere;

import javax.swing.*;
import java.awt.*;

public class DemoRenderer {

    private JPanel panel;

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void renderScene(Graphics g, Scene scene) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(5));
        scene.invokeSpheres(sphere -> renderSphere(g2d, sphere));
    }

    private void renderSphere(Graphics2D g, Sphere sphere) {
        if(sphere instanceof DemoSphere) {
            DemoSphere demoSphere = (DemoSphere) sphere;
            g.setColor(demoSphere.getColor());
        }
        else g.setColor(Color.DARK_GRAY);
        drawSphere(g, false, sphere.getX(), sphere.getY(), sphere.getRadius());
    }

    private void drawSphere(Graphics2D g, boolean fill, double x, double y, double radius) {
        if(fill) fillSphere(g, translateX(x) - radius, translateY(y) - radius, radius * 2, radius * 2);
        else drawSphere(g, translateX(x) - radius, translateY(y) - radius, radius * 2, radius * 2);
    }

    private void drawSphere(Graphics2D g, double x, double y, double width, double height) {
        g.drawOval((int) x, (int) y, (int) width, (int) height);
    }

    private void fillSphere(Graphics2D g, double x, double y, double width, double height) {
        g.fillOval((int) x, (int) y, (int) width, (int) height);
    }

    public int translateX(double x) {
        return (int) (x + panel.getWidth() / 2d);
    }

    public int translateY(double y) {
        return (int) -(y - panel.getHeight() / 2d);
    }

    public double translateBackX(int x) {
        return x - panel.getWidth() / 2d;
    }

    public double translateBackY(int y) {
        return -(y - panel.getHeight() / 2d);
    }
}
