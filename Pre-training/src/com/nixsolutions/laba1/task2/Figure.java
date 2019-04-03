package com.nixsolutions.laba1.task2;

/**
 * An abstract class which declare an essential functionality of figures
 * 
 * @author shvidkoy
 * @version 1.0
 *
 */
public abstract class Figure {

    public Figure() {
    }

    /**
     * Change the scale of the figure via scalingFactor
     * 
     * @param scalingFactor
     *            - represent the measure of how out figure will be scaled
     */
    public abstract void scaling(double scalingFactor);

    /**
     * Change the coordinate of a figure
     * 
     * @param x
     *            - abscissa direction
     * @param y
     *            - ordinate direction
     */
    public abstract void moveTo(double x, double y);

    /**
     * Display coordinate and perimeter of figure
     */
    public abstract void display();
}
