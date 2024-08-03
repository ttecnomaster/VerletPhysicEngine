package com.github.tecnomaster.constraint;

import com.github.tecnomaster.SceneConstraint;
import com.github.tecnomaster.Sphere;

public class BorderConstraint implements SceneConstraint {

    private final Type type;
    private final double v;
    public BorderConstraint(Type type, double v) {
        this.type = type;
        this.v = v;
    }

    @Override
    public void apply(Sphere sphere) {

        if(type == Type.TOP) if(sphere.getY() > v-sphere.getRadius()) sphere.setY(v - sphere.getRadius());
        if(type == Type.BOTTOM) if(sphere.getY() < v+sphere.getRadius()) sphere.setY(v + sphere.getRadius());
        if(type == Type.RIGHT) if(sphere.getX() > v-sphere.getRadius()) sphere.setX(v - sphere.getRadius());
        if(type == Type.LEFT) if(sphere.getX() < v+sphere.getRadius()) sphere.setX(v + sphere.getRadius());

    }

    public enum Type {
        TOP, BOTTOM, RIGHT, LEFT
    }
}
