package com.github.tecnomaster.verlet.implementation;

public interface MultiThreadingSupport {
    void solveCollisionPartition(int partitionIndex, int partitionCount, TwoSphereRunnable runnable);
}
