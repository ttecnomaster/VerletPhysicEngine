package com.github.tecnomaster;

public interface Sphere {
    void updatePosition(float dt);
    void accelerate(double x, double y);
    void setX(double x);
    void setY(double y);
    void setOldX(double x);
    void setOldY(double y);
    double getX();
    double getY();
    double getOldX();
    double getOldY();
    float getRadius();
    float getWeight();
}
