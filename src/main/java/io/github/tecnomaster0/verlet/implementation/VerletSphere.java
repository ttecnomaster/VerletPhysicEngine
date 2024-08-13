package io.github.tecnomaster0.verlet.implementation;

import io.github.tecnomaster0.verlet.Solver;
import io.github.tecnomaster0.verlet.Sphere;
import io.github.tecnomaster0.verlet.Verlet;
import io.github.tecnomaster0.verlet.VerletContainer;

/**
 * VerletSphere is an implementation of the {@link Sphere} interface. It is added to a {@link VerletContainer} and solved by the {@link Solver}.
 * A Sphere can have a position and a radius. It also returns a weight value which is normally equal to the radius.
 * The Sphere works by having a position and an old position. With the old position it can determine the momentum of the Sphere.
 * You can accelerate the Sphere by calling {@link Sphere#accelerate(double, double)}.
 * Gravity uses the accelerate method in order to add downwards momentum
 *
 * @author tecno-master
 * @see Sphere
 * @see VerletContainer
 * @see Solver
 * @version 1.0.0
 */
public class VerletSphere implements Sphere {
    private double ax,ay;
    private double lx,ly;
    private double x,y;
    private float radius;

    /**
     * Cannot be instanced! <br>
     * Use {@link Verlet#createSphere(double, double, float)} instead!
     */
    protected VerletSphere() {}

    public void setAttributes(double x, double y, float radius) {
        this.ax = 0;
        this.ay = y;
        this.lx = x;
        this.ly = y;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * Updates the Spheres position according to the verlet formula.
     * @param dt the amount of "time" to step forwards
     */
    @Override
    public void updatePosition(float dt) {

        // Calculate current velocity
        double vx = x - lx;
        double vy = y - ly;

        // Update last position
        lx = x;
        ly = y;

        // Update actual position
        x = x + vx + ax * dt * dt;
        y = y + vy + ay * dt * dt;

        // reset acceleration variable
        ax = 0;
        ay = 0;

    }

    /**
     * Accelerates the Sphere adding momentum to it.
     * @param x The X Momentum
     * @param y The Y Momentum
     */
    @Override
    public void accelerate(double x, double y) {
        ax += x;
        ay += y;
    }

    /**
     * Sets the X Position.
     * @param x The value to set
     */
    @Override
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y Position.
     * @param y The value to set
     */
    @Override
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the Old X Position.
     * will change how the sphere calculates its momentum
     * @param x The value to set
     */
    @Override
    public void setOldX(double x) {
        this.lx = x;
    }

    /**
     * Sets the Old Y Position.
     * will change how the sphere calculates its momentum
     * @param y The value to set
     */
    @Override
    public void setOldY(double y) {
        this.ly = y;
    }

    /**
     * Gets the X Position.
     * @return the value that is returned
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Gets the Y Position.
     * @return the value that is returned
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * Gets the Old X Position.
     * @return the value that is returned
     */
    @Override
    public double getOldX() {
        return lx;
    }

    /**
     * Gets the Old Y Position.
     * @return the value that is returned
     */
    @Override
    public double getOldY() {
        return ly;
    }

    /**
     * Gets the radius of the Sphere
     * @return the value that is returned
     */
    @Override
    public float getRadius() {
        return radius;
    }

    /**
     * Gets the weight of the Sphere.
     * In normal scenarios this only depends on the radius, but the weight can be modified when implementing a different Sphere
     * @return the value that is returned
     */
    @Override
    public float getWeight() {
        return getRadius();
    }
}
