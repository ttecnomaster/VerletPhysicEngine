package io.github.ttecnomaster.verlet;

/**
 * A Constraint is used to modify the behaviour of Spheres in a Scene.
 * Every Constraint needs to be either a {@link SceneConstraint} or a {@link StaticConstraint} in order to be applied to a Scene.
 * A Constraint usually has an apply method.
 * For the {@link SceneConstraint} it is {@link SceneConstraint#apply(Sphere)}.
 * For the {@link StaticConstraint} it is {@link StaticConstraint#apply()}.
 *
 * @author tecno-master
 * @see SceneConstraint
 * @see StaticConstraint
 * @version 1.0.0
 */
public interface Constraint {
}
