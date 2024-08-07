package com.github.tecnomaster.verlet.implementation;

import com.github.tecnomaster.verlet.VerletContainer;

public class VerletSolverThread extends Thread {
    private final int partitionIndex;
    private volatile boolean running;
    public volatile Runnable r;
    public VerletSolverThread(int partitionIndex) {
        this.partitionIndex = partitionIndex;
        this.running = true;
        this.r = null;
    }

    public void solve(MultiThreadingSupport solver, int partitionCount, TwoSphereRunnable runnable) {
        r = () -> solver.solveCollisionPartition(partitionIndex, partitionCount, runnable);
    }

    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            if(r != null) {
                r.run();
                r = null;
            }
        }
    }
}
