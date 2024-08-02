package com.github.tecnomaster;

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
