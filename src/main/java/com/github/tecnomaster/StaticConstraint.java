package com.github.tecnomaster;

/**
 * A StaticConstraint gets applied once per simulation step.
 * In order to modify Sphere behaviour it's recommended to set Sphere Objects in the Constructor
 */
public interface StaticConstraint extends Constraint {
    void apply();
}
