package com.github.ttecnomaster.verlet.utils;

import com.github.ttecnomaster.verlet.Constraint;
import com.github.ttecnomaster.verlet.Solver;

/**
 * VectorUtil class is a util class that provides methods for calculating with Vectors.
 * This is required because this project uses primitive datatype (double) for vectors.
 * The Solver and Constraints often need to calculate with Vectors.
 *
 * @author tecno-master
 * @see Constraint
 * @see Solver
 * @version 1.0.0
 */
public class VectorUtil {
    /**
     * Calculates the length of a vector. Uses the pythagorean theorem in order to calculate the length
     *
     * @param x The first part of the vector
     * @param y The second part of the vector
     * @return the length/magnitude of the vector
     */
    public static double length(double x, double y) {
        return Math.sqrt(x*x+y*y);
    }
}
