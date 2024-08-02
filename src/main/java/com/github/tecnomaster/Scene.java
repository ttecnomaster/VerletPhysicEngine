package com.github.tecnomaster;

import com.github.tecnomaster.implementation.ConstraintRunnable;

public interface Scene extends VerletContainer {
    void addConstraint(Constraint constraint);
    void removeConstraint(Constraint constraint);
    void invokeConstraint(ConstraintRunnable runnable);
}
