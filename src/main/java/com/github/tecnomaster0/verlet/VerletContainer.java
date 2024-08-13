package com.github.tecnomaster0.verlet;

import com.github.tecnomaster0.verlet.implementation.MultiThreadingSupport;
import com.github.tecnomaster0.verlet.implementation.SphereRunnable;
import com.github.tecnomaster0.verlet.implementation.TwoSphereRunnable;
import com.github.tecnomaster0.verlet.implementation.VerletScene;

/**
 * A VerletContainer is Container that holds and handles Spheres.
 * Spheres are the Physic Objects of the simulation therefor a Container for those is required.
 * The {@link Solver} needs a VerletContainer in order to step the simulation.
 * A Scene can add, remove and invoke Constraints.
 *
 * @author tecno-master
 * @see Solver
 * @see Scene
 * @see VerletScene
 * @version 1.0.0
 */
public interface VerletContainer extends MultiThreadingSupport {

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
    @Deprecated
    void invokeSpheresWithSpheres(TwoSphereRunnable runnable);

}
