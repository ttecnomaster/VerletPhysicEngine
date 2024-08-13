package io.github.tecnomaster0.verlet;

import io.github.tecnomaster0.verlet.implementation.VerletGrid;
import io.github.tecnomaster0.verlet.implementation.VerletSolver;

/**
 * The Solver is the heart of the simulation. It handles all the physics and ensures that the simulation is stepping correctly.
 * It allows to set sub steps who decide how many times the simulation is stepped between each step.
 * It also allows to set a {@link VerletGrid} which can drastically improve performance.
 * It allows to use multi-threading.
 * It requires a {@link VerletContainer} in order to run. If it gets a {@link Scene} it can also handle Constraints.
 *
 * @author tecno-master
 * @see VerletSolver
 * @see VerletGrid
 * @see VerletContainer
 * @version 1.0.0
 */
public interface Solver {

    /**
     * Enables/Disables collisions between spheres. This can drastically improve performance but spheres obviously won't collide.
     * @param b If true spheres WILL collide
     */
    void enableCollisions(boolean b);

    /**
     * Sets a VerletGrid to the Solver. By default, the Grid is null. Insert null value to remove the Grid
     * @param grid The VerletGrid to use for the Solver. Can be null
     */
    void setGrid(VerletGrid grid);

    /**
     * Allows the user to use multi-threading for the solver.
     * Multi-threading splits the collision checks over all the existing threads.
     * Default value is 1. This means putting 1 as the amount of threads disables multi-threading
     * and only uses one thread.
     * @param threads the amount of threads the solver should use. Default is 1.
     */
    void setMultiThreading(int threads);

    /**
     * Sets the gravity of the solver.
     * @param x X Velocity of the gravity (0 = zero x gravity)
     * @param y Y Velocity of the gravity (0 = zero y gravity)
     */
    void setGravity(double x, double y);

    /**
     * Sets the amount of sub steps. The amount of sub steps decide how many times the simulation is stepped between each step.
     * Will increase the accuracy of the simulation but tank the performance.
     * Default is 0
     * @param subSteps the amount of sub stepping to take between each step.
     */
    void setSubSteps(int subSteps);

    /**
     * Steps the simulation. Every Physic Object will move towards their next position.
     * @param dt the amount of "time" to step forwards
     */
    void step(float dt);

}
