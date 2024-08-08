package com.github.tecnomaster.demo;

import com.github.tecnomaster.verlet.Scene;
import com.github.tecnomaster.verlet.Verlet;

public class Demo {
    public static void main(String[] args) {

        Scene scene = Verlet.createScene();

        new DemoFrame(scene);

    }
}
