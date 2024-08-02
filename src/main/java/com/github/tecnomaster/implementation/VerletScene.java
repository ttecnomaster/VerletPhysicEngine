package com.github.tecnomaster.implementation;

import com.github.tecnomaster.Constraint;
import com.github.tecnomaster.Scene;
import com.github.tecnomaster.Sphere;

import java.util.ArrayList;
import java.util.List;

public class VerletScene implements Scene {
    private final List<Sphere> spheres;
    private final List<Constraint> constraints;

    private VerletScene() {
        this.spheres = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    @Override
    public void addSphere(Sphere sphere) {
        this.spheres.add(sphere);
    }

    @Override
    public void removeSphere(Sphere sphere) {
        this.spheres.remove(sphere);
    }

    @Override
    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }

    @Override
    public void removeConstraint(Constraint constraint) {
        this.constraints.remove(constraint);
    }

    @Override
    public void invokeSpheres(SphereRunnable runnable) {
        for(int i = 0; i < spheres.size(); i++) {
            runnable.run(spheres.get(i));
        }
    }

    @Override
    public void invokeSpheresWithSpheres(TwoSphereRunnable runnable) {
        for(int i = 0; i < spheres.size(); ++i) {
            for(int j = i+1; j < spheres.size(); ++j) {
                runnable.run(spheres.get(i),spheres.get(j));
            }
        }
    }

    @Override
    public void invokeConstraint(ConstraintRunnable runnable) {
        for(int i = 0; i < constraints.size(); i++) {
            runnable.run(constraints.get(i));
        }
    }
}
