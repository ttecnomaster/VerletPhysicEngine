package com.github.tecnomaster.run;

import com.github.tecnomaster.*;
import com.github.tecnomaster.constraint.CircleAreaConstraint;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        Scene scene = Verlet.createScene();

        scene.addSphere(Verlet.createSphere(0, 0, 50));

        VerletPanel panel = new VerletPanel(scene);

        frame.add(panel);

    }

    private static class VerletPanel extends JPanel {
        private final VerletContainer container;
        public VerletPanel(VerletContainer container) {
            this.container = container;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            container.invokeSpheres((sphere -> {
                drawSphere(sphere,g);
            }));

            if(container instanceof Scene) ((Scene)container).invokeConstraint(constraint -> drawConstraint(constraint, g));
        }

        private void drawSphere(Sphere sphere, Graphics g) {
            int radius = (int) sphere.getRadius();
            g.fillOval(translateX(sphere.getX()) - radius, translateY(sphere.getY()) - radius, radius*2, radius*2);
        }

        private void drawConstraint(Constraint constraint, Graphics g) {
            if(constraint instanceof CircleAreaConstraint) {
                CircleAreaConstraint circleAreaConstraint = (CircleAreaConstraint)constraint;
                int radius = (int) circleAreaConstraint.getRadius();
                g.drawOval(translateX(circleAreaConstraint.getX()) - radius, translateY(circleAreaConstraint.getY()) - radius, radius * 2, radius * 2);
            }
        }

        private int translateX(double x) {
            return (int) (x + getWidth()/2);
        }
        private int translateY(double y) {
            return (int) -(y - getHeight()/2);
        }
    }
}
