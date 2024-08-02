package com.github.tecnomaster;

import com.github.tecnomaster.implementation.SphereRunnable;
import com.github.tecnomaster.implementation.TwoSphereRunnable;

public interface VerletContainer {
    void addSphere(Sphere sphere);
    void removeSphere(Sphere sphere);
    void invokeSpheres(SphereRunnable runnable);
    void invokeSpheresWithSpheres(TwoSphereRunnable runnable);

}
