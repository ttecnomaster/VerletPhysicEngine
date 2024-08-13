package com.github.tecnomaster0.demo;

import com.github.tecnomaster0.verlet.custom.CustomSphere;

import java.awt.*;

public class DemoSphere extends CustomSphere {
    private final static boolean TRAIL = true;
    private boolean cMode, hMode;
    public DemoSphere(double x, double y, float radius) {
        super(x, y, radius);
        setClickMode(false);
        setHoverMode(false);
    }

    @Override
    public void updatePosition(float dt) {
        if(!getClickMode()) {
            super.updatePosition(dt);
            return;
        }
        double x = getX();
        double y = getY();
        double lx = getOldX();
        double ly= getOldY();
        super.updatePosition(dt);
        setX(x);
        setY(y);
        setOldX(lx);
        setOldY(ly);
    }

    public Color getColor() {
        if(getClickMode()) return Color.RED;
        if(getHoverMode()) return Color.LIGHT_GRAY;
        return Color.DARK_GRAY;
    }

    public void setClickMode(boolean mode) {
        this.cMode = mode;
    }

    public boolean getClickMode() {
        return cMode;
    }

    public void setHoverMode(boolean mode) {
        this.hMode = mode;
    }

    public boolean getHoverMode() {
        return hMode;
    }
}
