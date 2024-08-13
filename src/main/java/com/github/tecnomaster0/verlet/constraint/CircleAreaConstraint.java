package com.github.tecnomaster0.verlet.constraint;

import com.github.tecnomaster0.verlet.SceneConstraint;
import com.github.tecnomaster0.verlet.Sphere;
import com.github.tecnomaster0.verlet.utils.VectorUtil;
import com.github.tecnomaster0.verlet.Constraint;

/**
 * The CircleAreaConstraint is a type of {@link SceneConstraint} and forces every Sphere to stay inside a defined circle.
 * It calculates the length using {@link VectorUtil#length(double, double)} from the circle center to every Sphere. Then it subtracts the circle radius.
 * If the outcome is bigger than 0 then the Sphere needs to be moved back into the circle.
 *
 * @author tecno-master
 * @see Constraint
 * @see SceneConstraint
 * @version 1.0.0
 */
public class CircleAreaConstraint implements SceneConstraint {
    private final double x,y;
    private final float radius;

    /**
     * Defines the circle that the Spheres are staying in. Every position outside the circle is invalid and the Sphere will be moved back.
     *
     * @param x The X center position of the circle
     * @param y The Y center position of the circle
     * @param radius The radius of the circle. Defines how big the circle is
     */
    public CircleAreaConstraint(double x, double y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * @return Where the x center position of the circle is
     */
    public double getX() {
        return x;
    }

    /**
     * @return Where the y center position of the circle is
     */
    public double getY() {
        return y;
    }

    /**
     * @return the radius of the circle
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Ensures that the current sphere is inside the circle.
     * If not then move it back.
     * First it calculates the length using {@link VectorUtil#length(double, double)} from the circle center to the current Sphere.
     * Then it compares the length with the circle radius.
     * Finally, if the radius is bigger,then the sphere is moved back
     *
     * @param sphere the current sphere to check
     */
    @Override
    public void apply(Sphere sphere) {

        double dx = sphere.getX() - x;
        double dy = sphere.getY() - y;

        double length = VectorUtil.length(dx,dy) + sphere.getRadius();

        if(length <= radius) return;

        sphere.setX(dx / length * radius + x);
        sphere.setY(dy / length * radius + y);

    }
}
