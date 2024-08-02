package com.github.tecnomaster;

public interface Solver {
    void enableCollisions(boolean b);
    void setGravity(double x, double y);
    void setSubSteps(int subSteps);
    void step(float dt);
}
