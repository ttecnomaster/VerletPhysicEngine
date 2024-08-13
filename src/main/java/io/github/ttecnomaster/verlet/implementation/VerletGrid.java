package io.github.ttecnomaster.verlet.implementation;

import io.github.ttecnomaster.verlet.Sphere;
import io.github.ttecnomaster.verlet.VerletContainer;
import io.github.ttecnomaster.verlet.Solver;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A VerletGrid defines an area and a radius in which collisions are performed.
 * Adding a VerletGrid to a {@link Solver} can drastically increase performance.
 * The radius defines the size of one grid cell. No Sphere is allowed to be bigger than that defined radius
 *
 * @author tecno-master
 * @see Solver
 * @see VerletSolver
 * @version 1.0.0
 */
public class VerletGrid implements MultiThreadingSupport {
    private final double x,y,width,height;
    private final float radius;
    private final Cell[][] cells;

    /**
     * Defines an area and a radius in which collisions are performed.
     * Automatically creates an area from the x and y inputs
     *
     * @param width The X Position of the area
     * @param height The Y Position of the area
     * @param radius defines the size of one grid cell. No Sphere is allowed to be bigger than that defined radius
     */
    public VerletGrid(double width, double height, float radius) {
        this(-width/2,-height/2, width, height, radius);
    }

    /**
     * Constructs a new <code>Rectangle</code>, initialized to match the values of the specified <code>Rectangle</code>.
     *
     * @param r the <code>Rectangle</code> from which to copy initial values to a newly constructed <code>Rectangle</code>
     */
    public VerletGrid(Rectangle r, float radius) {
        this(r.getX(), r.getY(), r.getWidth(), r.getHeight(), radius);
    }

    /**
     * Defines an area and a radius in which collisions are performed.
     *
     * @param x The X Position of the first corner of the area
     * @param y The Y Position of the first corner of the area
     * @param width The X Position of the second corner of the area
     * @param height The Y Position of the second corner of the area
     * @param radius defines the size of one grid cell. No Sphere is allowed to be bigger than that defined radius
     */
    public VerletGrid(double x, double y, double width, double height, float radius) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius * 2;
        this.cells = new Cell[(int) Math.ceil(this.width/this.radius)][(int) Math.ceil(this.height/this.radius)];
        initializeCells();
    }

    /**
     * Initialized every single Cell with a new Cell Object.
     * Cell Objects are only instantiated when creating a new VerletGrid
     */
    private void initializeCells() {
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    /**
     * Clears previously assigned Cells.
     * Assigns every Sphere to one Cell. A Cell can hold multiple Spheres
     * This action has to be performed before stepping the simulation
     * @param container The Container from which the Spheres should be assigned
     */
    void assignCells(VerletContainer container) {
        // Clear previously assigned Cells.
        invokeCells(Cell::clear);

        // Assign every Sphere to one Cell
        container.invokeSpheres((sphere -> {

            // calculate grid position
            double px = (sphere.getX()-x) / radius;
            double py = (sphere.getY()-y) / radius;

            // Only assign Cell if grid position is valid
            if(validateCellPosition(px,py)) cells[(int) px][(int) py].spheres.add(sphere);
        }));
    }

    /**
     * Checks for invalid grid positions. A grid position is invalid if it lies outside the grid area
     * @param x the X Position to check
     * @param y the Y Position to check
     * @return returns true if the position is valid
     */
    private boolean validateCellPosition(double x, double y) {
        return x >= 0 && x < this.cells.length && y >= 0 && y < this.cells[0].length;
    }

    /**
     * Implementing this method means,
     * that individual threads can access and call it in order to solve the correct partition part spheres.
     * Most implementations use a split up "for loop".
     * @param partitionIndex The index of the partition. Is it the first part? Is it the last part?
     * @param partitionCount The total amount of partitions/threads. Can be specified by {@link Solver#setMultiThreading(int)}
     * @param runnable The runnable that should run on two spheres which are tested for a collision
     */
    @Override
    public void solveCollisionPartition(int partitionIndex, int partitionCount, TwoSphereRunnable runnable) {
        int totalCells = cells.length;
        int partitionSize = (totalCells + partitionCount - 1) / partitionCount; // ceiling division to handle remainder cells
        int start = partitionIndex * partitionSize;
        int end = Math.min(start + partitionSize, totalCells);

        for (int i = start; i < end; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                Cell oCell = cells[i][j];
                invokeNeighborCells(i,j,(cell -> {
                    solveCellCollisions(oCell, cell, runnable);
                }));
            }
        }
    }

    /**
     * Solves the collisions between two Cells
     * @param cell_1 The first cell
     * @param cell_2 The second cell
     */
//    private void solveCellCollisions(VerletGrid.Cell cell_1, VerletGrid.Cell cell_2, TwoSphereRunnable runnable) {
//        for(int i = 0; i < cell_1.spheres.size(); i++) {
//            for(int j = 0; j < cell_2.spheres.size(); j++) {
//                if(cell_1.spheres.get(i) != cell_2.spheres.get(j)) runnable.run(cell_1.spheres.get(i), cell_2.spheres.get(j));
//            }
//        }
//    }
    private void solveCellCollisions(VerletGrid.Cell cell_1, VerletGrid.Cell cell_2, TwoSphereRunnable runnable) {
        for(Sphere sphere_1 : cell_1.getSpheres()) {
            for(Sphere sphere_2 : cell_2.getSpheres()) {
                if(sphere_1 != sphere_2) runnable.run(sphere_1,sphere_2);
            }
        }
    }

    /**
     * Invokes every Cell, skipping border Cells, and calls the Neighbour Cells on this Cell in order to call the runnable on it
     * @param runnable The runnable which is called by all defined Cells
     */
    @Deprecated
    void invokeCellsSkipBordersAndNeighborCell(Cell2Runnable runnable) {
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                Cell oCell = cells[i][j];
                invokeNeighborCells(i,j,(cell -> {
                    runnable.run(oCell,cell);
                }));
            }
        }
    }

    /**
     * Invokes all 8 Neighbour Cells including itself and calls the runnable on it
     * @param x the X center position
     * @param y the Y center position
     * @param runnable The runnable which is called by all 9 Cells
     */
    void invokeNeighborCells(int x, int y, CellRunnable runnable) {
        for(int dy = -1; dy <= 1; dy++) {
            for(int dx = -1; dx <= 1; dx++) {
                if(validateCellPosition(x+dx, y+dy)) runnable.run(cells[x+dx][y+dy]);
            }
        }
    }

    /**
     * Invokes every Cell and calls the runnable on it
     * @param runnable The runnable which is called by every Cell
     */
    void invokeCells(CellRunnable runnable) {
        for(Cell[] cells : cells) {
            for(Cell cell : cells) {
                runnable.run(cell);
            }
        }
    }

    /**
     * CellRunnable can be run by a Cell Object
     */
    interface CellRunnable {
        void run(Cell cell);
    }

    /**
     * Cell2Runnable can be run by two Cell Objects
     */
    interface Cell2Runnable {
        void run(Cell cell_1, Cell cell_2);
    }

    /**
     * A Cell Object represents one Cell in the Grid. They only get instantiated when the VerletGrid gets created.
     * Can store multiple Sphere Objects
     */
    static class Cell {
        private final Set<Sphere> spheres = new HashSet<>();

        /**
         * Clear all spheres resetting the sphere Set
         */
        private void clear() {
            spheres.clear();
        }

        /**
         * Returns all stores spheres
         * @return all spheres in the form of a Set
         */
        public Set<Sphere> getSpheres() {
            return spheres;
        }
    }
}
