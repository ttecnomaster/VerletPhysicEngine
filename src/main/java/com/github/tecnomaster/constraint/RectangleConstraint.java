package com.github.tecnomaster.constraint;

import com.github.tecnomaster.SceneConstraint;
import com.github.tecnomaster.Sphere;

public class RectangleConstraint implements SceneConstraint {

    private final BorderConstraint top, bottom, right, left;

    public RectangleConstraint(double x, double y, double width, double height) {
        top = new BorderConstraint(BorderConstraint.Type.BOTTOM, y + height);
        bottom = new BorderConstraint(BorderConstraint.Type.TOP, y);
        right = new BorderConstraint(BorderConstraint.Type.RIGHT, x + width);
        left = new BorderConstraint(BorderConstraint.Type.LEFT, x);
    }

    @Override
    public void apply(Sphere sphere) {
        top.apply(sphere);
        bottom.apply(sphere);
        right.apply(sphere);
        left.apply(sphere);
    }
}
