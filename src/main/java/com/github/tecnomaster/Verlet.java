package com.github.tecnomaster;

import com.github.tecnomaster.implementation.VerletScene;
import com.github.tecnomaster.implementation.VerletSolver;
import com.github.tecnomaster.implementation.VerletSphere;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Verlet {
    public static Scene createScene() {
        return invokeEmptyConstructor(VerletScene.class);
    }

    public static Sphere createSphere(double x, double y, float radius) {
        VerletSphere sphere = invokeEmptyConstructor(VerletSphere.class);
        sphere.setAttributes(x,y,radius);
        return sphere;
    }

    public static Solver createSolver(VerletContainer container) {
        VerletSolver solver = invokeEmptyConstructor(VerletSolver.class);
        solver.setContainer(container);
        return solver;
    }

    private static <T> T invokeEmptyConstructor(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
