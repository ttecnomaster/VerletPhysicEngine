package com.github.ttecnomaster.verlet.implementation;

import com.github.ttecnomaster.verlet.Solver;

/**
 * The VerletSolverThread is a thread that can run collision-check-loops.
 * It defines one thread of potentially multiple threads.
 * It can only be stopped/terminated by calling {@link VerletSolverThread#shutdown()}.
 * By calling {@link VerletSolverThread#solve(MultiThreadingSupport, int, TwoSphereRunnable)}
 * one can start the collision-check-loop in this thread. The solve method can be safely
 * called by the main thread. With the {@link VerletSolverThread#isReady()} method one can
 * check if the thread finished doing the collision-check-loop. The {@link VerletSolver}
 * class needs this in order to check if all threads are done executing.
 *
 * @author tecno-master
 * @see MultiThreadingSupport
 * @see VerletSolver
 * @version 1.0.0
 */
public class VerletSolverThread extends Thread {
    private final int partitionIndex;
    private volatile boolean running;
    private volatile Runnable r;

    /**
     * Creates a new thread that can run collision-check-loops.
     * Automatically starts the thread too.
     * @param partitionIndex defines what index this thread is.
     *                       index 0 is reserved for the main thread.
     */
    public VerletSolverThread(int partitionIndex) {
        this.partitionIndex = partitionIndex;
        this.running = true;
        this.r = null;
        start();
    }

    /**
     * With this method one can check if the thread finished doing the collision-check-loop. The {@link VerletSolver}
     * class needs this in order to check if all threads are done executing.
     * @return if finished with the collision-check-loop returns true
     */
    public boolean isReady() {
        return r == null;
    }

    /**
     * By calling this method one can start the collision-check-loop in this thread.
     * The solve method can be safely called by the main thread.
     * Uses a {@link MultiThreadingSupport} object in order to execute the runnable.
     * @param solver Required in order to execute the runnable.
     * @param partitionCount The total amount of partitions/threads. Can be specified by {@link Solver#setMultiThreading(int)}
     * @param runnable The runnable that should run on two spheres which are tested for a collision
     */
    public void solve(MultiThreadingSupport solver, int partitionCount, TwoSphereRunnable runnable) {
        r = () -> solver.solveCollisionPartition(partitionIndex, partitionCount, runnable);
    }

    /**
     * Shuts down the thread.
     * This is the only way to terminate the thread as it loops inside a while loop.
     */
    public void shutdown() {
        running = false;
    }

    /**
     * The only part of the thread that actually exists in the new thread.
     * Loops forever and checks as fast as possible for a new Runnable to execute.
     * This is because {@link VerletSolverThread#solve(MultiThreadingSupport, int, TwoSphereRunnable)}
     * will be called by the main thread, but the runnable needs to be called by this thread.
     */
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
