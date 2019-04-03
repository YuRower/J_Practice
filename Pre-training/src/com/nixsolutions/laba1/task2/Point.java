package com.nixsolutions.laba1.task2;

/**
 * Class that represent point in the coordinate system
 * 
 * @author shvidkoy
 *
 */
public class Point {

    private double x;
    private double y;

    /**
     * Constructor creating a new point at the origin of the coordinate system
     * 
     * @see Point(double x, double y)
     */
    public Point() {
        this(0.0, 0.0);
    }

    /**
     * Constructor creating a new point with the certain coordinate
     * 
     * @see Point(double x, double y)
     * @param x
     *            - certain x value
     * @param y
     *            - certain y value
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get X
     * 
     * @return x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Set point
     * 
     * @param x
     *            - x coordinate
     * @param y
     *            - y coordinate
     */
    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;

    }

    /**
     * Get Y
     * 
     * @return y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Representation of point as a string
     */
    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

}
