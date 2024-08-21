package com.github.ttecnomaster.verlet;

import com.github.ttecnomaster.verlet.implementation.VerletScene;
import com.github.ttecnomaster.verlet.implementation.VerletSolver;
import com.github.ttecnomaster.verlet.implementation.VerletSphere;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This class is mainly used for instantiating Verlet Objects.
 * Using the static methods you can create essential components for a working VerletEngine
 *
 * @author tecno-master
 * @version 1.0.0
 */
public class Verlet {
    /**
     * Uses Reflection in order to instantiate a new Scene Object
     * @return new VerletScene
     */
    public static Scene createScene() {
        return invokeEmptyConstructor(VerletScene.class);
    }

    /**
     * Uses Reflection in order to instantiate a new Sphere Object
     *
     * @param x the X Position for the new Sphere
     * @param y the Y Position for the new Sphere
     * @param radius the radius of the Sphere
     * @return new VerletSphere
     */

    public static Sphere createSphere(double x, double y, float radius) {
        VerletSphere sphere = invokeEmptyConstructor(VerletSphere.class);
        sphere.setAttributes(x,y,radius);
        return sphere;
    }

    /**
     * Uses Reflection in order to instantiate a new Solver Object
     *
     * @param container requires a VerletContainer in order to create a Solver.
     * @return new VerletContainer
     */
    public static Solver createSolver(VerletContainer container) {
        VerletSolver solver = invokeEmptyConstructor(VerletSolver.class);
        solver.setContainer(container);
        return solver;
    }

    /**
     * Every Verlet Object is obligated to have a private Constructor with zero Parameters.
     * Therefor reflection is required in order to instantiate the Verlet Objects.
     * This ensures that no outside user can call the Constructor or extend from them.
     *
     * @param clazz defines what kind of Verlet Object is returned
     * @return new Verlet Object of clazz
     * @param <T> represents the Verlet Object Type
     */
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
