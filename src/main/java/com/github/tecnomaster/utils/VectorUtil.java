package com.github.tecnomaster.utils;

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
