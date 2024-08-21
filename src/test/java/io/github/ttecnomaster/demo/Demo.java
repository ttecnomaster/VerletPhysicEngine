package io.github.ttecnomaster.demo;

import io.github.ttecnomaster.verlet.*;
import io.github.ttecnomaster.verlet.constraint.LinkConstraint;
import io.github.ttecnomaster.verlet.constraint.StaticPositionConstraint;
import io.github.ttecnomaster.verlet.utils.VectorUtil;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        Scene scene = Verlet.createScene();
        Solver solver = Verlet.createSolver(scene);

        DemoFrame frame = new DemoFrame(scene, solver);

        frame.render();

        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        solver.setSubSteps(8);


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

            // Remove any sphere that is out of bounds
            if(frame.getSettings().getBorderType() == 2) scene.invokeSpheres(sphere -> {
                if(sphere.getY() < frame.getPanelDimension().height/-2d - sphere.getRadius()
                        || sphere.getY() > frame.getPanelDimension().height/2d + sphere.getRadius()
                        || sphere.getX() < frame.getPanelDimension().width/-2d - sphere.getRadius()
                        || sphere.getX() > frame.getPanelDimension().width/2d + sphere.getRadius())
                    removeSphere(sphere, scene);
            });

        }).run();

    }

    public static void spawnRope(double x, double y, float radius, int length, Scene scene) {

        Sphere last = new DemoSphere(x, y, radius);
        scene.addSphere(last);

        for(int i = 1; i < length; i++) {

            Sphere next = new DemoSphere(x, y - i*radius*2, radius);
            scene.addSphere(next);
            scene.addConstraint(new LinkConstraint(last, next));
            last = next;

        }

    }

    private static void removeSphere(Sphere sphere, Scene scene) {
        scene.removeSphere(sphere);

        List<Constraint> toRemove = new ArrayList<>();
        scene.invokeConstraints(constraint -> {
            if(constraint instanceof LinkConstraint && (((LinkConstraint)constraint).getSphere1() == sphere || ((LinkConstraint)constraint).getSphere2() == sphere)) toRemove.add(constraint);
            if(constraint instanceof StaticPositionConstraint && ((StaticPositionConstraint)constraint).getSphere() == sphere) toRemove.add(constraint);
        });

        toRemove.forEach(scene::removeConstraint);
    }

    public static void spawnCube(double x, double y, float radius, int spheres, Scene scene) {

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
