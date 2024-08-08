package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.Verlet;

public class Demo {
    public static void main(String[] args) {

        Scene scene = Verlet.createScene();

        scene.addSphere(Verlet.createSphere(0, 0, 25));

        new DemoFrame(scene);

    }
}
