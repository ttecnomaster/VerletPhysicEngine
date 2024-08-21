package io.github.ttecnomaster.verlet;

import io.github.ttecnomaster.verlet.implementation.VerletSphere;

/**
 * A Sphere a Physic Object in the simulation. It is added to a {@link VerletContainer} and solved by the {@link Solver}.
 * A Sphere can have a position and a radius. It also returns a weight value which is normally equal to the radius.
 * The Sphere works by having a position and an old position. With the old position it can determine the momentum of the Sphere.
 * You can accelerate the Sphere by calling {@link Sphere#accelerate(double, double)}.
 * Gravity uses the accelerate method in order to add downwards momentum
 *
 * @author tecno-master
 * @see VerletSphere
 * @see VerletContainer
 * @see Solver
 * @version 1.0.0
 */
public interface Sphere {

    /**
     * Updates the Spheres position according to the verlet formula.
     * @param dt the amount of "time" to step forwards
     */
    void updatePosition(float dt);

    /**
     * Accelerates the Sphere adding momentum to it.
     * @param x The X Momentum
     * @param y The Y Momentum
     */
    void accelerate(double x, double y);

    /**
     * Sets the X Position.
     * @param x The value to set
     */
    void setX(double x);

    /**
     * Sets the y Position.
     * @param y The value to set
     */
    void setY(double y);

    /**
     * Sets the Old X Position.
     * will change how the sphere calculates its momentum
     * @param x The value to set
     */
    void setOldX(double x);

    /**
     * Sets the Old Y Position.
     * will change how the sphere calculates its momentum
     * @param y The value to set
     */
    void setOldY(double y);

    /**
     * Gets the X Position.
     * @return the value that is returned
     */
    double getX();

    /**
     * Gets the Y Position.
     * @return the value that is returned
     */
    double getY();

    /**
     * Gets the Old X Position.
     * @return the value that is returned
     */
    double getOldX();

    /**
     * Gets the Old Y Position.
     * @return the value that is returned
     */
    double getOldY();

    /**
     * Gets the radius of the Sphere
     * @return the value that is returned
     */
    float getRadius();

    /**
     * Gets the weight of the Sphere.
     * In normal scenarios this only depends on the radius, but the weight can be modified when implementing a different Sphere
     * @return the value that is returned
     */
    float getWeight();

}
