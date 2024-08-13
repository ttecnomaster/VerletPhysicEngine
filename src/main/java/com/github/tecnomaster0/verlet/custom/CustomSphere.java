package com.github.tecnomaster0.verlet.custom;

import com.github.tecnomaster0.verlet.Sphere;
import com.github.tecnomaster0.verlet.Verlet;

/**
 * The CustomSphere is an implementation of the {@link Sphere} interface
 * that serves the sole purpose of allowing users to extend from it.
 * If someone would like to add additional features to spheres they can
 * do so by extending this class.
 * It requires a {@link Sphere} object which servers as the parent.
 * This class uses the parent methods in order to satisfy the Sphere interface.
 * This parent is prohibited to be another CustomSphere.
 *
 * @author tecno-master
 * @see Sphere
 * @see Verlet
 * @version 1.0.0
 */
public abstract class CustomSphere implements Sphere {

    private final Sphere parent;

    /**
     * Instantiates a CustomSphere.
     * It creates a {@link Sphere} object which servers as the parent from the parameters provided.
     * The CustomSphere attaches itself to this {@link Sphere}
     * and allows for future child classes to add additional features.
     * This parent is prohibited to be another CustomSphere
     * because a CustomSphere inside a CustomSphere does not make sense.
     *
     * @param x the X position of the sphere.
     * @param y the Y position of the sphere.
     * @param radius the radius of the sphere.
     * @throws IllegalArgumentException if the parent sphere is an instance of CustomSphere.
     */
    public CustomSphere(double x, double y, float radius) throws IllegalArgumentException {
        this(Verlet.createSphere(x, y, radius));
    }

    /**
     * Instantiates a CustomSphere.
     * It requires a {@link Sphere} object which servers as the parent.
     * The CustomSphere attaches itself to this {@link Sphere}
     * and allows for future child classes to add additional features.
     * This parent is prohibited to be another CustomSphere
     * because a CustomSphere inside a CustomSphere does not make sense.
     *
     * @param sphere The parent sphere used by the CustomSphere. Will attach itself to it.
     * @throws IllegalArgumentException if the parent sphere is an instance of CustomSphere.
     */
    public CustomSphere(Sphere sphere) throws IllegalArgumentException {
        if(sphere instanceof CustomSphere) throw new IllegalArgumentException("Cannot customize CustomSphere! Please provide a basic Sphere!");
        this.parent = sphere;
    }

    /**
     * Updates the Spheres position according to the verlet formula.
     * @param dt the amount of "time" to step forwards
     */
    @Override
    public void updatePosition(float dt) {
        parent.updatePosition(dt);
    }

    /**
     * Accelerates the Sphere adding momentum to it.
     * @param x The X Momentum
     * @param y The Y Momentum
     */
    @Override
    public void accelerate(double x, double y) {
        parent.accelerate(x, y);
    }

    /**
     * Sets the X Position.
     * @param x The value to set
     */
    @Override
    public void setX(double x) {
        parent.setX(x);
    }

    /**
     * Sets the y Position.
     * @param y The value to set
     */
    @Override
    public void setY(double y) {
        parent.setY(y);
    }

    /**
     * Sets the Old X Position.
     * will change how the sphere calculates its momentum
     * @param x The value to set
     */
    @Override
    public void setOldX(double x) {
        parent.setOldX(x);
    }

    /**
     * Sets the Old Y Position.
     * will change how the sphere calculates its momentum
     * @param y The value to set
     */
    @Override
    public void setOldY(double y) {
        parent.setOldY(y);
    }

    /**
     * Gets the X Position.
     * @return the value that is returned
     */
    @Override
    public double getX() {
        return parent.getX();
    }

    /**
     * Gets the Y Position.
     * @return the value that is returned
     */
    @Override
    public double getY() {
        return parent.getY();
    }

    /**
     * Gets the Old X Position.
     * @return the value that is returned
     */
    @Override
    public double getOldX() {
        return parent.getOldX();
    }

    /**
     * Gets the Old Y Position.
     * @return the value that is returned
     */
    @Override
    public double getOldY() {
        return parent.getOldY();
    }

    /**
     * Gets the radius of the Sphere
     * @return the value that is returned
     */
    @Override
    public float getRadius() {
        return parent.getRadius();
    }

    /**
     * Gets the weight of the Sphere.
     * In normal scenarios this only depends on the radius, but the weight can be modified when implementing a different Sphere
     * @return the value that is returned
     */
    @Override
    public float getWeight() {
        return parent.getWeight();
    }
}
