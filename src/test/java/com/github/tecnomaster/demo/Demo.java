package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.Solver;
import com.github.tecnomaster.verlet.Sphere;
import com.github.tecnomaster.verlet.Verlet;
import com.github.tecnomaster.verlet.constraint.LinkConstraint;
import com.github.tecnomaster.verlet.constraint.RectangleConstraint;
import com.github.tecnomaster.verlet.implementation.TwoSphereRunnable;
import com.github.tecnomaster.verlet.utils.VectorUtil;
import sun.awt.image.ImageWatched;

import java.awt.*;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        Scene scene = Verlet.createScene();

        // Spheres
        for(int i = 0; i < 15; i++) scene.addSphere(new DemoSphere(1, 0, (float) (25+Math.random()*50)));

        // Cube
        spawnCube(0, 0, 15, 3, scene);

        DemoFrame frame = new DemoFrame(scene);

        Solver solver = Verlet.createSolver(scene);

        solver.setSubSteps(8);

        scene.addConstraint(new RectangleConstraint(1920 - 75,1080 - 100));

        new Runner(60, () -> {
            scene.invokeSpheres(sphere -> {
                if (sphere instanceof DemoSphere && ((DemoSphere)sphere).getClickMode()) {
                    sphere.setOldX(frame.getMouseX() - (frame.getMouseX() - sphere.getX()) / 8);
                    sphere.setOldY(frame.getMouseY() - (frame.getMouseY() - sphere.getY()) / 8);
                    sphere.setX(frame.getMouseX());
                    sphere.setY(frame.getMouseY());
                }
            });
            solver.step(0.02f);
            frame.render();

        }).run();

    }

    private static void spawnCube(double x, double y, float radius, int spheres, Scene scene) {

        Sphere[] spheres1 = new Sphere[(spheres+1)*4];

        // Side 1
        for(int i = 0; i < spheres+1; i++) {
            spheres1[i] = new DemoSphere(x - radius * (spheres+1) + i*radius*2 ,y - radius * (spheres+1) ,radius);
        }
        // Side 2
        for(int i = 0; i < spheres+1; i++) {
            spheres1[i+(spheres+1)] = new DemoSphere(x + radius * (spheres+1) ,y - radius * (spheres+1) + i*radius*2 ,radius);
        }
        // Side 3
        for(int i = 0; i < spheres+1; i++) {
            spheres1[i+(spheres+1)*2] = new DemoSphere(x + radius * (spheres+1) - i*radius*2 ,y + radius * (spheres+1) ,radius);
        }
        // Side 4
        for(int i = 0; i < spheres+1; i++) {
            spheres1[i+(spheres+1)*3] = new DemoSphere(x - radius * (spheres+1) ,y + radius * (spheres+1) - i*radius*2 ,radius);
        }

        for(Sphere sphere : spheres1) {
            scene.addSphere(sphere);
        }

        for(int i = 1; i < spheres1.length; i++) {
            scene.addConstraint(new LinkConstraint(spheres1[i], spheres1[i-1]));
        }
        scene.addConstraint(new LinkConstraint(spheres1[0], spheres1[spheres1.length-1]));

        double length = VectorUtil.length(1,1) * radius * (spheres+1) * 2;
        scene.addConstraint(new LinkConstraint(spheres1[0], spheres1[(spheres+1)*2], length));
        scene.addConstraint(new LinkConstraint(spheres1[(spheres+1)], spheres1[(spheres+1)*3], length));

    }
}
