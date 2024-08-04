package com.github.tecnomaster.implementation;

import com.github.tecnomaster.Sphere;
import com.github.tecnomaster.VerletContainer;

import java.util.HashSet;
import java.util.Set;

/**
 * A VerletGrid defines an area and a radius in which collisions are performed.
 * Adding a VerletGrid to a {@link com.github.tecnomaster.Solver} can drastically increase performance.
 * The radius defines the size of one grid cell. No Sphere is allowed to be bigger than that defined radius
 *
 * @author tecno-master
 * @see com.github.tecnomaster.Solver
 * @see VerletSolver
 * @version 1.0.0
 */
public class VerletGrid {
    private final double x,y,width,height;
    private final float radius;
    private final Cell[][] cells;

    /**
     * Defines an area and a radius in which collisions are performed.
     * Automatically creates an area from the x and y inputs
     *
     * @param x The X Position of the area
     * @param y The Y Position of the area
     * @param radius defines the size of one grid cell. No Sphere is allowed to be bigger than that defined radius
     */
    public VerletGrid(double x, double y, float radius) {
        this(-x,-y,x*2,y*2,radius);
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
        this.radius = radius;
        this.cells = new Cell[(int) (width/radius)][(int) (height/radius)];
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
     * Invokes every Cell, skipping border Cells, and calls the Neighbour Cells on this Cell in order to call the runnable on it
     * @param runnable The runnable which is called by all defined Cells
     */
    void invokeCellsSkipBordersAndNeighborCell(Cell2Runnable runnable) {
        for(int i = 1; i < cells.length-1; i++) {
            for(int j = 1; j < cells[i].length-1; j++) {
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
                runnable.run(cells[x+dx][y+dy]);
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
