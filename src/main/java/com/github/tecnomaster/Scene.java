package com.github.tecnomaster;

import com.github.tecnomaster.implementation.ConstraintRunnable;

/**
 * A Scene is a type of {@link VerletContainer} that can also handle Constraints.
 * Constraints are crucial to a simulation because Constraints are essential to make a good simulation.
 * A Scene can add, remove and invoke Constraints.
 *
 * @author tecno-master
 * @see com.github.tecnomaster.implementation.VerletScene
 * @see VerletContainer
 * @version 1.0.0
 */
public interface Scene extends VerletContainer {

    /**
     * Adds a Constraint to the Scene
     * @param constraint The Constraint to add
     */
    void addConstraint(Constraint constraint);

    /**
     * Removes a Constraint from the Scene
     * @param constraint The Constraint to remove
     */
    void removeConstraint(Constraint constraint);

    /**
     * Invokes every Constraint and calls the runnable
     * @param runnable The Runnable which is called by every Constraint
     */
    void invokeConstraints(ConstraintRunnable runnable);

}
