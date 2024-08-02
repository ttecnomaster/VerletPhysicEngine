package com.github.tecnomaster.run;

import com.github.tecnomaster.*;
import com.github.tecnomaster.constraint.CircleAreaConstraint;
import com.github.tecnomaster.implementation.VerletGrid;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Scene scene = Verlet.createScene();
        Solver solver = Verlet.createSolver(scene);

        solver.setGrid(new VerletGrid(2,2,5));

        scene.addSphere(Verlet.createSphere(-250, 130, 25));
        scene.addSphere(Verlet.createSphere(250, 130, 25));
        scene.addSphere(Verlet.createSphere(250, 100, 25));
        scene.addConstraint(new CircleAreaConstraint(0,0,400));

        VerletPanel panel = new VerletPanel(scene);

        frame.add(panel);

        frame.setVisible(true);

        new Runner(60, () -> {

            solver.step(0.02f);
            panel.repaint();

        }).start();

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

    private static class Runner extends Thread {
        private int fps;
        private volatile double ns;
        private final Runnable runnable;

        protected Runner(int fps, Runnable runnable) {
            this.setFps(fps);
            this.runnable = runnable;
        }

        public synchronized void setFps(int fps) {
            this.fps = fps;
            this.ns();
        }

        private void ns() {
            this.ns = 1.0E9 / (double)this.fps;
        }

        public void run() {
            long lastTime = System.nanoTime();
            double delta = 0.0;
            long timer = System.currentTimeMillis();
            int frames = 0;

            while(true) {
                if (this.fps != -1) {
                    long now = System.nanoTime();
                    delta += (double)(now - lastTime) / this.ns;

                    for(lastTime = now; delta >= 1.0; --delta) {
                        this.tick();
                    }

                    ++frames;
                    if (System.currentTimeMillis() - timer > 1000L) {
                        timer += 1000L;
                        frames = 0;
                    }
                }
            }
        }

        public void tick() {
            this.runnable.run();
        }
    }
}
