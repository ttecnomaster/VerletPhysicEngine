package io.github.ttecnomaster.verlet;

/**
 * A SceneConstraint is called on every Sphere each simulation step.
 * Inside the apply method the behaviour can be modified.
 *
 * @author tecno-master
 * @see Constraint
 * @version 1.0.0
 */
public interface SceneConstraint extends Constraint {
    /**
     * The apply method modifies the behaviour of each Sphere.
     * It is called on every Sphere each simulation step right before handling collisions.
     * Therefor it affects every Sphere the same.
     *
     * @param sphere The Sphere of which the behaviour is currently modified
     */
    void apply(Sphere sphere);
}
