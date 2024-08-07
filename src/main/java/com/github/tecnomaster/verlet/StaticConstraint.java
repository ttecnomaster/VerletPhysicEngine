package com.github.tecnomaster.verlet;

/**
 * A StaticConstraint gets applied once per simulation step.
 * In order to modify Sphere behaviour it's recommended to set Sphere Objects in the Constructor
 *
 * @author tecno-master
 * @see Constraint
 * @version 1.0.0
 */
public interface StaticConstraint extends Constraint {
    /**
     * The apply method can modify variables that it has stored as attributes.
     * If the constraint has a sphere as an attribute it can be called.
     * It is called each simulation step right before handling collisions.
     */
    void apply();
}
