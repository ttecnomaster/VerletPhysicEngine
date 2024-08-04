package com.github.tecnomaster.implementation;

import com.github.tecnomaster.Sphere;

/**
 * This interface is used for running a Runnable with a Sphere as a parameter.
 * This is primarily used by the {@link com.github.tecnomaster.VerletContainer#invokeSpheres(SphereRunnable)} method.
 * As a functional interface this should be used with lambda only.
 *
 * @author tecno-master
 * @see com.github.tecnomaster.VerletContainer
 * @version 1.0.0
 */
@FunctionalInterface
public interface SphereRunnable {
    /**
     * When called passes a Sphere parameter. run executes whatever the interface was created for
     *
     * @param sphere The Sphere that gets called on the run method
     */
    void run(Sphere sphere);
}
