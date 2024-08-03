package com.github.tecnomaster.implementation;

import com.github.tecnomaster.Sphere;
import com.github.tecnomaster.Verlet;

public class VerletSphere implements Sphere {
    private double ax,ay;
    private double lx,ly;
    private double x,y;
    private float radius;
    private float weight;

    /**
     * Cannot be instanced! <br>
     * Use {@link Verlet#createSphere(double, double, float)} instead!
     */
    protected VerletSphere() {
        weight = (float) Math.random();
    }

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
        return getRadius() * weight;
    }
}
