package com.github.ttecnomaster.verlet.constraint;

import com.github.ttecnomaster.verlet.SceneConstraint;
import com.github.ttecnomaster.verlet.Sphere;

import java.awt.*;

/**
 * The RectangleConstraint is a type of {@link SceneConstraint} and ensures that no spheres leave the defined rectangle.
 * The rectangle can be created the same way you create a {@link Rectangle}.
 * You can also pass a {@link Rectangle} directly.
 * Minus values for the height are not supported and your program might break when doing so.
 * Utilizes four {@link BorderConstraint} in order to apply the rectangle constraint.
 *
 * @author tecno-master
 * @see SceneConstraint
 * @see BorderConstraint
 * @see Rectangle
 * @version 1.0.0
 */
public class RectangleConstraint implements SceneConstraint {

    private final BorderConstraint top, bottom, right, left;

    /**
     * Constructs a new <code>Rectangle</code>, using the given values
     * Uses the width and height and creates the center of the Rectangle at 0, 0
     *
     * @param width the width of the <code>Rectangle</code>
     * @param height the height of the <code>Rectangle</code>
     */
    public RectangleConstraint(double width, double height) {
        this(-width/2, -height/2, width, height);
    }

    /**
     * Constructs a new <code>Rectangle</code>, initialized to match the values of the specified <code>Rectangle</code>.
     *
     * @param r the <code>Rectangle</code> from which to copy initial values to a newly constructed <code>Rectangle</code>
     */
    public RectangleConstraint(Rectangle r) {
        this(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * The rectangle is created the same way you create a {@link Rectangle}.
     * Minus values for the height are not supported and your program might break when doing so.
     *
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @param width the width of the <code>Rectangle</code>
     * @param height the height of the <code>Rectangle</code>
     */
    public RectangleConstraint(double x, double y, double width, double height) {
        top = new BorderConstraint(BorderConstraint.Type.BOTTOM, y + height);
        bottom = new BorderConstraint(BorderConstraint.Type.TOP, y);
        right = new BorderConstraint(BorderConstraint.Type.RIGHT, x + width);
        left = new BorderConstraint(BorderConstraint.Type.LEFT, x);
    }

    /**
     * Checks if the current sphere is outside the rectangle.
     * Uses four {@link BorderConstraint} in order to apply the constraint.
     * For each direction there is one constraint.
     *
     * @param sphere the current sphere to check
     */
    @Override
    public void apply(Sphere sphere) {
        top.apply(sphere);
        bottom.apply(sphere);
        right.apply(sphere);
        left.apply(sphere);
    }

    /**
     * Uses the borders information in order to
     * gather the original input information
     *
     * @return the specified X coordinate
     */
    public double getX() {
        return left.getValue();
    }

    /**
     * Uses the borders information in order to
     * gather the original input information
     *
     * @return the specified Y coordinate
     */
    public double getY() {
        return bottom.getValue();
    }

    /**
     * Uses the borders information in order to
     * gather the original input information
     *
     * @return the width of the <code>Rectangle</code>
     */
    public double getWidth() {
        return right.getValue() - getX();
    }

    /**
     * Uses the borders information in order to
     * gather the original input information
     *
     * @return height the height of the <code>Rectangle</code>
     */
    public double getHeight() {
        return top.getValue() - getY();
    }
}
