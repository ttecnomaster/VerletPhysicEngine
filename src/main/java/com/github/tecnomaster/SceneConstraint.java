package com.github.tecnomaster;

/**
 * A SceneConstraint is called every Sphere each simulation step.
 * Inside the apply method the behaviour can be modified
 */
public interface SceneConstraint extends Constraint {
    void apply(Sphere sphere);
}
