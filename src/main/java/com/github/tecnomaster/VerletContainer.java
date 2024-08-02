package com.github.tecnomaster;

import com.github.tecnomaster.implementation.SphereRunnable;
import com.github.tecnomaster.implementation.TwoSphereRunnable;

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
