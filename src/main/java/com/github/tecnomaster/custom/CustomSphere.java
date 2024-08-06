package com.github.tecnomaster.custom;

import com.github.tecnomaster.Sphere;

public abstract class CustomSphere implements Sphere {

    private final Sphere parent;

    public CustomSphere(Sphere sphere) {
        if(sphere instanceof CustomSphere) throw new IllegalArgumentException("Cannot customize CustomSphere! Please provide a basic Sphere!");
        this.parent = sphere;
    }

    @Override
    public void updatePosition(float dt) {
        parent.updatePosition(dt);
    }

    @Override
    public void accelerate(double x, double y) {
        parent.accelerate(x, y);
    }

    @Override
    public void setX(double x) {
        parent.setX(x);
    }

    @Override
    public void setY(double y) {
        parent.setY(y);
    }

    @Override
    public void setOldX(double x) {
        parent.setOldX(x);
    }

    @Override
    public void setOldY(double y) {
        parent.setOldY(y);
    }

    @Override
    public double getX() {
        return parent.getX();
    }

    @Override
    public double getY() {
        return parent.getY();
    }

    @Override
    public double getOldX() {
        return parent.getOldX();
    }

    @Override
    public double getOldY() {
        return parent.getOldY();
    }

    @Override
    public float getRadius() {
        return parent.getRadius();
    }

    @Override
    public float getWeight() {
        return parent.getWeight();
    }
}
