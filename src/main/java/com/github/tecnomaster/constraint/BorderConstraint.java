package com.github.tecnomaster.constraint;

import com.github.tecnomaster.SceneConstraint;
import com.github.tecnomaster.Sphere;

/**
 * The BorderConstraint is a type of {@link SceneConstraint} and ensures that no sphere will go past a certain point.
 * A Border is defined by either TOP, BOTTOM, RIGHT or LEFT and also a value.
 * The value defines the axis position that the border sits at.
 * TOP and BOTTOM both are on the Y axis while RIGHT and LEFT are at the X axis.
 * The difference between TOP and BOTTOM is that TOP will not let any sphere go higher than the border while BOTTOM ensures that no sphere can go under the border.
 * Same principle for RIGHT and LEFT
 *
 * @author tecno-master
 * @see com.github.tecnomaster.Constraint
 * @see SceneConstraint
 * @version 1.0.0
 */
public class BorderConstraint implements SceneConstraint {

    private final Type type;
    private final double v;

    /**
     * A Border is defined by either TOP, BOTTOM, RIGHT or LEFT and also a value.
     *
     * @param type The type defines on which axis the border lies and on which side the sphere is allowed to exist.
     * @param v The value defines the axis position that the border sits at.
     */
    public BorderConstraint(Type type, double v) {
        this.type = type;
        this.v = v;
    }

    /**
     * Checks if the current sphere passed the border.
     * If so then the sphere will be moved at the last allowed position at the border.
     *
     * @param sphere the current sphere to check
     */
    @Override
    public void apply(Sphere sphere) {

        if(type == Type.TOP) if(sphere.getY() < v+sphere.getRadius()) sphere.setY(v + sphere.getRadius());
        if(type == Type.BOTTOM) if(sphere.getY() > v-sphere.getRadius()) sphere.setY(v - sphere.getRadius());
        if(type == Type.RIGHT) if(sphere.getX() > v-sphere.getRadius()) sphere.setX(v - sphere.getRadius());
        if(type == Type.LEFT) if(sphere.getX() < v+sphere.getRadius()) sphere.setX(v + sphere.getRadius());

    }

    /**
     * The type defines on which axis the border lies and on which side the sphere is allowed to exist.
     */
    public enum Type {
        TOP, BOTTOM, RIGHT, LEFT
    }
}
