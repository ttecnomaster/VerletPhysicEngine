package com.github.tecnomaster.verlet.implementation;

interface MultiThreadingSupport {
    void solveCollisionPartition(int partitionIndex, int partitionCount, VerletGrid.Cell2Runnable runnable);
}
