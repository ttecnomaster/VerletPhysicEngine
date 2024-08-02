package com.github.tecnomaster.constraint;

import com.github.tecnomaster.Sphere;
import com.github.tecnomaster.StaticConstraint;

public class StaticPositionConstraint implements StaticConstraint {
    private final Sphere sphere;
    private final double x,y;
    public StaticPositionConstraint(Sphere sphere) {
        this(sphere,sphere.getX(),sphere.getY());
    }
    public StaticPositionConstraint(Sphere sphere, double x, double y) {
        this.sphere = sphere;
        this.x = x;
        this.y = y;
    }
    @Override
    public void apply() {
        sphere.setX(x);
        sphere.setY(y);
    }
}
