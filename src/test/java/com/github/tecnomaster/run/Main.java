package com.github.tecnomaster.run;

import com.github.tecnomaster.verlet.constraint.CircleAreaConstraint;
import com.github.tecnomaster.verlet.constraint.RectangleConstraint;
import com.github.tecnomaster.verlet.custom.CustomSphere;
import com.github.tecnomaster.verlet.implementation.VerletGrid;
import com.github.tecnomaster.verlet.*;
import com.github.tecnomaster.verlet.implementation.VerletSolver;
import com.github.tecnomaster.verlet.utils.VectorUtil;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Scene scene = Verlet.createScene();
        Solver solver = Verlet.createSolver(scene);

        solver.setSubSteps(8);
        solver.setGrid(new VerletGrid(1920,1080, 8));
        ((VerletSolver)solver).setMultiThreading(8);

        for(int i = 0; i < 5000; i++) {
            //scene.addSphere(new ColorSphere(Verlet.createSphere(-900+Math.random()*1500, -500+Math.random()*900, 25), new Color((int) (Math.random()*256),(int) (Math.random()*256),(int) (Math.random()*256))));
            scene.addSphere(Verlet.createSphere(-900+Math.random()*1500, -500+Math.random()*900, (float) (6+Math.random()*2)));
        }

        scene.addConstraint(new RectangleConstraint(1920-100, 1080-100));
//        scene.addConstraint(new CircleAreaConstraint(0,0, 500));
//        scene.addConstraint(new RectangleConstraint(450*2,450*2));

        VerletPanel panel = new VerletPanel(scene);

        frame.add(panel);

        frame.setVisible(true);

        new Runner(60, () -> {
            solver.step(0.02f);
            panel.repaint();

        }).start();

    }

    private static class ColorSphere extends CustomSphere {
        private final Color color;

        public ColorSphere(Sphere sphere, Color color) {
            super(sphere);
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    private static class VerletPanel extends JPanel {
        private final VerletContainer container;
        public VerletPanel(VerletContainer container) {
            this.container = container;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if(container instanceof Scene) ((Scene)container).invokeConstraints(constraint -> drawConstraint(constraint, g));

            container.invokeSpheres(sphere -> {
                drawSphere(sphere,g);
            });
        }

        private void drawSphere(Sphere sphere, Graphics g) {
            int radius = (int) sphere.getRadius();
//            if(sphere instanceof ColorSphere) g.setColor(((ColorSphere)sphere).getColor());
//            else g.setColor(Color.BLACK);
            double velocity = VectorUtil.length(sphere.getX() - sphere.getOldX(), sphere.getY() - sphere.getOldY());
            g.setColor(new Color((int) Math.min(velocity*1000, 255),25, 25));
            g.fillOval(translateX(sphere.getX()) - radius, translateY(sphere.getY()) - radius, radius*2, radius*2);
        }

        private void drawConstraint(Constraint constraint, Graphics g) {
            if(constraint instanceof CircleAreaConstraint) {
                CircleAreaConstraint circleAreaConstraint = (CircleAreaConstraint)constraint;
                int radius = (int) circleAreaConstraint.getRadius();
                g.drawOval(translateX(circleAreaConstraint.getX()) - radius, translateY(circleAreaConstraint.getY()) - radius, radius * 2, radius * 2);
            }
            if(constraint instanceof RectangleConstraint) {
                RectangleConstraint rectangleConstraint = (RectangleConstraint) constraint;
                g.drawRect(translateX(rectangleConstraint.getX()), translateY(rectangleConstraint.getY()) - (int) rectangleConstraint.getHeight(), (int) rectangleConstraint.getWidth(), (int) rectangleConstraint.getHeight());
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
