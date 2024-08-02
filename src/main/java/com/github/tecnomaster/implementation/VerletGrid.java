package com.github.tecnomaster.implementation;

import com.github.tecnomaster.Sphere;
import com.github.tecnomaster.VerletContainer;

import java.util.HashSet;
import java.util.Set;

public class VerletGrid {
    private double x,y,width,height;
    private float radius;
    private Cell[][] cells;
    VerletGrid(double x, double y, float radius) {
        this(-x,-y,x*2,y*2,radius);
    }
    VerletGrid(double x, double y, double width, double height, float radius) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.cells = new Cell[(int) (width/radius)][(int) (height/radius)];
        initializeCells();
    }

    private void initializeCells() {
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    void assignCells(VerletContainer container) {
        invokeCells(Cell::clear);
        container.invokeSpheres((sphere -> {
            double px = (sphere.getX()-x) / radius;
            double py = (sphere.getY()-y) / radius;
            if(validateCellPosition(px,py)) cells[(int) px][(int) py].spheres.add(sphere);
        }));
    }

    private boolean validateCellPosition(double x, double y) {
        return x >= 0 && x < this.cells.length && y >= 0 && y < this.cells[0].length;
    }

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

    void invokeNeighborCells(int x, int y, CellRunnable runnable) {
        for(int dy = -1; dy <= 1; dy++) {
            for(int dx = -1; dx <= 1; dx++) {
                runnable.run(cells[x+dx][y+dy]);
            }
        }
    }

    void invokeCells(CellRunnable runnable) {
        for(Cell[] cells : cells) {
            for(Cell cell : cells) {
                runnable.run(cell);
            }
        }
    }
    interface CellRunnable {
        void run(Cell cell);
    }

    interface Cell2Runnable {
        void run(Cell cell_1, Cell cell_2);
    }

    static class Cell {
        private final Set<Sphere> spheres = new HashSet<>();
        private void clear() {
            spheres.clear();
        }

        public Set<Sphere> getSpheres() {
            return spheres;
        }
    }
}
