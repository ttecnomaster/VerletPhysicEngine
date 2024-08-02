package com.github.tecnomaster.implementation;

import com.github.tecnomaster.Constraint;
import com.github.tecnomaster.Scene;
import com.github.tecnomaster.Sphere;
import com.github.tecnomaster.Verlet;

import java.util.ArrayList;
import java.util.List;

public class VerletScene implements Scene {
    private final List<Sphere> spheres;
    private final List<Constraint> constraints;

    /**
     * Cannot be instanced! <br>
     * Use {@link Verlet#createScene()} instead!
     */
    private VerletScene() {
        this.spheres = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    /**
     * Adds a Sphere to the VerletContainer
     * @param sphere The Sphere to add/spawn
     */
    @Override
    public void addSphere(Sphere sphere) {
        this.spheres.add(sphere);
    }

    /**
     * Removes a Sphere from the VerletContainer
     * @param sphere The Sphere to remove
     */
    @Override
    public void removeSphere(Sphere sphere) {
        this.spheres.remove(sphere);
    }

    /**
     * Adds a Constraint to the Scene
     * @param constraint The Constraint to add
     */
    @Override
    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }

    /**
     * Removes a Constraint from the Scene
     * @param constraint The Constraint to remove
     */
    @Override
    public void removeConstraint(Constraint constraint) {
        this.constraints.remove(constraint);
    }

    /**
     * Invokes every Sphere and calls the runnable
     * @param runnable The Runnable which is called by every Sphere
     */
    @Override
    public void invokeSpheres(SphereRunnable runnable) {
        for(int i = 0; i < spheres.size(); i++) {
            runnable.run(spheres.get(i));
        }
    }

    /**
     * Invokes every Sphere with each Sphere exactly once and calls the runnable
     * @param runnable The Runnable which is called by every Sphere Pairs
     */
    @Override
    public void invokeSpheresWithSpheres(TwoSphereRunnable runnable) {
        for(int i = 0; i < spheres.size(); ++i) {
            for(int j = i+1; j < spheres.size(); ++j) {
                runnable.run(spheres.get(i),spheres.get(j));
            }
        }
    }

    /**
     * Invokes every Constraint and calls the runnable
     * @param runnable The Runnable which is called by every Constraint
     */
    @Override
    public void invokeConstraint(ConstraintRunnable runnable) {
        for(int i = 0; i < constraints.size(); i++) {
            runnable.run(constraints.get(i));
        }
    }
}
