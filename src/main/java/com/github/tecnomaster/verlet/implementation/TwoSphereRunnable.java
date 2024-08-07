package com.github.tecnomaster.verlet.implementation;

import com.github.tecnomaster.verlet.Sphere;
import com.github.tecnomaster.verlet.VerletContainer;

/**
 * This interface is used for running a Runnable with two Spheres as parameters.
 * This is primarily used by the {@link VerletContainer#invokeSpheresWithSpheres(TwoSphereRunnable)} method.
 * The intentional use for this interface is to perform actions which requires two Spheres.
 * An example for such an action would be the {@link VerletSolver#solveCollisions(Sphere, Sphere)} method.
 * As a functional interface this should be used with lambda only.
 *
 * @author tecno-master
 * @see VerletContainer
 * @see VerletSolver
 * @version 1.0.0
 */
@FunctionalInterface
public interface TwoSphereRunnable {
    /**
     * When called passes two Sphere parameters. run executes whatever the interface was created for
     *
     * @param sphere_1 The first Sphere that gets called on the run method
     * @param sphere_2 The second Sphere that gets called on the run method
     */
    void run(Sphere sphere_1, Sphere sphere_2);
}
