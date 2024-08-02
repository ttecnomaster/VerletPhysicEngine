package com.github.tecnomaster.implementation;

import com.github.tecnomaster.*;
import com.github.tecnomaster.utils.VectorUtil;

public class VerletSolver implements Solver {
    private VerletContainer container;
    private int subSteps;
    private VerletGrid grid;
    private double gx,gy = -1000;
    private boolean enableCollisions;
    private VerletSolver() {
        this.subSteps = 1;
        this.enableCollisions = true;
    }

    @Override
    public void enableCollisions(boolean b) {
        this.enableCollisions = b;
    }

    public void setContainer(VerletContainer container) {
        this.container = container;
    }

    @Override
    public void setGrid(VerletGrid grid) {
        this.grid = grid;
    }

    @Override
    public void setGravity(double x, double y) {
        this.gx = x;
        this.gy = y;
    }

    @Override
    public void setSubSteps(int subSteps) {
        this.subSteps = subSteps;
    }

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

    private void updatePosition(float dt) {
        container.invokeSpheres(sphere -> {
            sphere.updatePosition(dt);
        });
    }

    private void solveCollisions() {
        if(grid == null) container.invokeSpheresWithSpheres(this::solveCollisions);
        else solveCollisionsViaGrid();
    }

    private void solveCollisionsViaGrid() {
        grid.assignCells(container);
        grid.invokeCellsSkipBordersAndNeighborCell(this::solveCellCollisions);
    }

    private void solveCellCollisions(VerletGrid.Cell cell_1, VerletGrid.Cell cell_2) {
        for(Sphere sphere_1 : cell_1.getSpheres()) {
            for(Sphere sphere_2 : cell_2.getSpheres()) {
                if(sphere_1 != sphere_2) solveCollisions(sphere_1,sphere_2);
            }
        }
    }

    private void solveCollisions(Sphere sphere_1, Sphere sphere_2) {

        double dx = sphere_1.getX() - sphere_2.getX();
        double dy = sphere_1.getY() - sphere_2.getY();
        double dLength = VectorUtil.length(dx,dy);

        final float combinedRadius = sphere_1.getRadius() + sphere_2.getRadius();

        if(dLength < combinedRadius) {
            dx /= dLength;
            dy /= dLength;
            final double delta = (combinedRadius) - dLength;
            float weightDiff = sphere_1.getWeight() / (sphere_1.getWeight() + sphere_2.getWeight());
            applyNewPosition(sphere_1,dx * delta*(1-weightDiff),dy * delta*(1-weightDiff));
            applyNewPosition(sphere_2,- dx * delta*(weightDiff),- dy * delta*(weightDiff));
        }

    }

    private void applyNewPosition(Sphere sphere, double x, double y) {
        sphere.setX(sphere.getX() + x);
        sphere.setY(sphere.getY() + y);
    }

    private void applyConstraints(Scene scene) {
        scene.invokeConstraint(this::applyConstraint);
    }

    private void applyConstraint(Constraint constraint) {
        if(constraint instanceof SceneConstraint) container.invokeSpheres(((SceneConstraint)constraint)::apply);
        else if(constraint instanceof StaticConstraint) ((StaticConstraint)constraint).apply();
    }

    private void applyGravity() {
        container.invokeSpheres((sphere -> {
            sphere.accelerate(gx,gy);
        }));
    }
}
