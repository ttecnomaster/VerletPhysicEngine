package com.github.tecnomaster.verlet.implementation;

import com.github.tecnomaster.verlet.utils.VectorUtil;
import com.github.tecnomaster.verlet.*;

/**
 * The VerletSolver an implementation of the {@link Solver}. It handles all the physics and ensures that the simulation is stepping correctly.
 * It allows to set sub steps who decide how many times the simulation is stepped between each step.
 * It also allows to set a {@link VerletGrid} which can drastically improve performance.
 * It requires a {@link VerletContainer} in order to run. If it gets a {@link Scene} it can also handle Constraints.
 *
 * @author tecno-master
 * @see Solver
 * @see VerletGrid
 * @see VerletContainer
 * @version 1.0.0
 */
public class VerletSolver implements Solver {
    private VerletContainer container;
    private int subSteps;
    private VerletGrid grid;
    private double gx,gy = -1000;
    private boolean enableCollisions;
    private VerletSolverThread[] solverThreads;

    /**
     * Cannot be instanced! <br>
     * Use {@link Verlet#createSolver(VerletContainer)} instead!
     */
    private VerletSolver() {
        this.subSteps = 1;
        this.enableCollisions = true;
        this.solverThreads = new VerletSolverThread[0];
    }

    /**
     * Enables/Disables collisions between spheres. This can drastically improve performance but spheres obviously won't collide.
     * @param b If true spheres WILL collide
     */
    @Override
    public void enableCollisions(boolean b) {
        this.enableCollisions = b;
    }

    /**
     * Sets a new VerletContainer. The Solver will now solve a different Scenario
     * @param container the new Container to solve from now onwards
     */
    public void setContainer(VerletContainer container) {
        this.container = container;
    }

    /**
     * Sets a VerletGrid to the Solver. By default, the Grid is null. Insert null value to remove the Grid
     * @param grid The VerletGrid to use for the Solver. Can be null
     */
    @Override
    public void setGrid(VerletGrid grid) {
        this.grid = grid;
    }

    public void setMultiThreading(int threads) {
        for(VerletSolverThread thread : solverThreads) thread.shutdown();
        solverThreads = new VerletSolverThread[threads-1];
        for(int i = 0; i < solverThreads.length; i++) {
            solverThreads[i] = new VerletSolverThread(i+1);
            solverThreads[i].start();
        }
    }

    /**
     * Sets the gravity of the solver.
     * @param x X Velocity of the gravity (0 = zero x gravity)
     * @param y Y Velocity of the gravity (0 = zero y gravity)
     */
    @Override
    public void setGravity(double x, double y) {
        this.gx = x;
        this.gy = y;
    }

    /**
     * Sets the amount of sub steps. The amount of sub steps decide how many times the simulation is stepped between each step.
     * Will increase the accuracy of the simulation but tank the performance.
     * Default is 0
     * @param subSteps the amount of sub stepping to take between each step.
     */
    @Override
    public void setSubSteps(int subSteps) {
        this.subSteps = subSteps;
    }

    /**
     * Steps the simulation. Every Physic Object will move towards their next position.
     * @param dt the amount of "time" to step forwards
     */
    @Override
    public void step(float dt) {

        final float sub_dt = dt / subSteps;
        for(int i = 0; i < subSteps; i++) {

            // accelerate every object to simulate gravity
            applyGravity();

            // apply constraints if container supports them
            if(container instanceof Scene) applyConstraints((Scene) container);

            // solve object collisions
            if(enableCollisions) solveCollisions();

            // update position of every object
            updatePosition(sub_dt);

        }
    }

    /**
     * Updates the position according to the verlet formula of each sphere
     * @param dt the amount of "time" to step forwards
     */
    private void updatePosition(float dt) {
        container.invokeSpheres(sphere -> {
            sphere.updatePosition(dt);
        });
    }

    /**
     * Solves the collisions between every Sphere.
     * Either solves collisions by comparing every Sphere with each other or by using the VerletGrid if one exists
     */
    private void solveCollisions() {
        if(grid == null) solveViaClassic();
        else solveCollisionsViaGrid();
    }

    private void solveViaClassic() {
        container.solveCollisionPartition(0, solverThreads.length+1, this::solveCollisions);
        for(VerletSolverThread thread : solverThreads) thread.solve(container, solverThreads.length+1, this::solveCollisions);
    }

    /**
     * Solves collisions by using the VerletGrid.
     * First reassigns every Sphere to the Grid. Then Invokes every Sphere on the Grid with nearby Spheres
     */
    private void solveCollisionsViaGrid() {
        grid.assignCells(container);

        grid.solveCollisionPartition(0, solverThreads.length+1, this::solveCollisions);

        for(VerletSolverThread thread : solverThreads) {
            thread.solve(grid, solverThreads.length+1, this::solveCollisions);
        }

        boolean solving;
        do {
            solving = false;
            for(VerletSolverThread thread : solverThreads) {
                if (thread.r != null) {
                    solving = true;
                    break;
                }
            }
        } while (solving);
    }

    /**
     * Solves the collision between two Spheres
     * First checks if they are even colliding, if so calculate in what direction they bounce of.
     * Also accounts Sphere weight
     *
     * @param sphere_1 The first Sphere
     * @param sphere_2 The second Sphere
     */
    private void solveCollisions(Sphere sphere_1, Sphere sphere_2) {

        // calculate information on how the spheres relate
        double dx = sphere_1.getX() - sphere_2.getX();
        double dy = sphere_1.getY() - sphere_2.getY();
        double dLength = VectorUtil.length(dx,dy);

        // Check if both spheres have the same position
        if(dLength <= 0) {
            sphere_1.setX(sphere_1.getX() + Math.random() * sphere_1.getRadius() / 10);
            sphere_1.setY(sphere_2.getY() + Math.random() * sphere_1.getRadius() / 10);
            return;
        }

        final float combinedRadius = sphere_1.getRadius() + sphere_2.getRadius();

        // gets executed if the spheres collide
        if(dLength < combinedRadius) {
            dx /= dLength;
            dy /= dLength;
            final double delta = (combinedRadius) - dLength;
            float weightDiff = sphere_1.getWeight() / (sphere_1.getWeight() + sphere_2.getWeight());
            applyNewPosition(sphere_1,dx * delta*(1-weightDiff),dy * delta*(1-weightDiff));
            applyNewPosition(sphere_2,- dx * delta*(weightDiff),- dy * delta*(weightDiff));
        }

    }

    /**
     * Adds towards the existing position vector effectively moving the Sphere
     * @param sphere The Sphere to move
     * @param x The X Amount to move
     * @param y The Y Amount to move
     */
    private void applyNewPosition(Sphere sphere, double x, double y) {
        sphere.setX(sphere.getX() + x);
        sphere.setY(sphere.getY() + y);
    }

    /**
     * Applies every Constraint
     * @param scene Passes the Scene, because Constraints need a Scene to function
     */
    private void applyConstraints(Scene scene) {
        scene.invokeConstraints(this::applyConstraint);
    }

    /**
     * Applies a specific Constraint
     * Checks if Constraint is a SceneConstraint or a StaticConstraint, then applies it
     *
     * @param constraint The Constraint to apply
     */
    private void applyConstraint(Constraint constraint) {
        if(constraint instanceof SceneConstraint) container.invokeSpheres(((SceneConstraint)constraint)::apply);
        else if(constraint instanceof StaticConstraint) ((StaticConstraint)constraint).apply();
    }

    /**
     * Applies gravity to every Sphere using the existing values
     */
    private void applyGravity() {
        container.invokeSpheres((sphere -> {
            sphere.accelerate(gx,gy);
        }));
    }
}
