package com.github.tecnomaster.constraint;

import com.github.tecnomaster.Sphere;
import com.github.tecnomaster.StaticConstraint;

/**
 * The LinkConstraint is a type of {@link StaticConstraint} and turns a Sphere completely static.
 * The Sphere will stop moving and always stay in the same space.
 * It still has collisions meaning other Spheres will collide with it.
 * Besides staying in the same place forever it functions like a normal Sphere.
 *
 * @author tecno-master
 * @see com.github.tecnomaster.Constraint
 * @see StaticConstraint
 * @version 1.0.0
 */
public class StaticPositionConstraint implements StaticConstraint {
    private final Sphere sphere;
    private final double x,y;

    /**
     * Defines the Sphere that should be turned static.
     * Uses the last sphere position as the new static position.
     * Meaning wherever the sphere was when the constraint gets applied, it will stay forever.
     *
     * @param sphere The Sphere that should be turned static.
     */
    public StaticPositionConstraint(Sphere sphere) {
        this(sphere,sphere.getX(),sphere.getY());
    }

    /**
     * Defines the Sphere that should static as well as the new static position of the Sphere
     *
     * @param sphere The Sphere that will be turned static.
     * @param x the new x position of the Sphere.
     * @param y the new y position of the Sphere.
     */
    public StaticPositionConstraint(Sphere sphere, double x, double y) {
        this.sphere = sphere;
        this.x = x;
        this.y = y;
    }

    /**
     * Ensures that the Sphere always stays in the same place.
     * Constantly sets the x and y position of the Sphere to the static x and y position.
     */
    @Override
    public void apply() {
        sphere.setX(x);
        sphere.setY(y);
    }
}
