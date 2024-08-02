package com.github.tecnomaster;

import com.github.tecnomaster.implementation.ConstraintRunnable;

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
    void invokeConstraint(ConstraintRunnable runnable);

}
