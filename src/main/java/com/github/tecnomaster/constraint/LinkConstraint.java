package com.github.tecnomaster.constraint;

import com.github.tecnomaster.Sphere;
import com.github.tecnomaster.StaticConstraint;
import com.github.tecnomaster.utils.VectorUtil;

public class LinkConstraint implements StaticConstraint {
    private final Sphere sphere1,sphere2;
    private final double distance;
    public LinkConstraint(Sphere sphere1, Sphere sphere2) {
        this(sphere1,sphere2,sphere1.getRadius()+sphere2.getRadius());
    }
    public LinkConstraint(Sphere sphere1, Sphere sphere2, double distance) {
        this.sphere1 = sphere1;
        this.sphere2 = sphere2;
        this.distance = distance;
    }

    @Override
    public void apply() {
        double dx = sphere1.getX() - sphere2.getX();
        double dy = sphere1.getY() - sphere2.getY();
        final double dist = VectorUtil.length(dx,dy);
        dx /= dist;
        dy /= dist;
        final double delta = this.distance - dist;

        float weightDiff = sphere1.getWeight() / (sphere1.getWeight() + sphere2.getWeight());
        applyNewPosition(sphere1,dx * delta*(1-weightDiff),dy * delta*(1-weightDiff));
        applyNewPosition(sphere2,- dx * delta*(weightDiff),- dy * delta*(weightDiff));
    }

    private void applyNewPosition(Sphere sphere, double x, double y) {
        sphere.setX(sphere.getX() + x);
        sphere.setY(sphere.getY() + y);
    }
}
