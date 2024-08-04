package com.github.tecnomaster;

import com.github.tecnomaster.implementation.SphereRunnable;
import com.github.tecnomaster.implementation.TwoSphereRunnable;

/**
 * A VerletContainer is Container that holds and handles Spheres.
 * Spheres are the Physic Objects of the simulation therefor a Container for those is required.
 * The {@link Solver} needs a VerletContainer in order to step the simulation.
 * A Scene can add, remove and invoke Constraints.
 *
 * @author tecno-master
 * @see Solver
 * @see Scene
 * @see com.github.tecnomaster.implementation.VerletScene
 * @version 1.0.0
 */
public interface VerletContainer {

    /**
     * Adds a Sphere to the VerletContainer
     * @param sphere The Sphere to add/spawn
     */
    void addSphere(Sphere sphere);

    /**
     * Removes a Sphere from the VerletContainer
     * @param sphere The Sphere to remove
     */
    void removeSphere(Sphere sphere);

    /**
     * Invokes every Sphere and calls the runnable
     * @param runnable The Runnable which is called by every Sphere
     */
    void invokeSpheres(SphereRunnable runnable);

    /**
     * Invokes every Sphere with each Sphere exactly once and calls the runnable
     * @param runnable The Runnable which is called by every Sphere Pairs
     */
    void invokeSpheresWithSpheres(TwoSphereRunnable runnable);

}
