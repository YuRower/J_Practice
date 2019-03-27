package com.nixsolutions.laba1.task2;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Class that represent Triangle figure
 * 
 * @author shvidkoy
 *
 */
public class Triangle extends Figure {

    private Point[] points;
    private static final int SIZE = 3;

    /**
     * Creates an empty triangle
     */
    public Triangle() {
        this(new Point(0.0, 0.0), new Point(0.0, 0.0), new Point(0.0, 0.0));
    }

    /**
     * Constructor creates a triangle with three points
     * 
     * @param p1
     *            - first point
     * @param p2
     *            - second point
     * @param p3
     *            - third point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        points = new Point[SIZE];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
    }

    /**
     * Makes a resize of a triangle through changing radius scalding
     * 
     * @param scalingFactor
     *            -represent the measure of how out radius of circle will be
     *            scaled
     */
    @Override
    public void scaling(double scalingFactor) {
        double dx, dy;
        for (int i = 0; i < SIZE; i++) {
            dx = points[i].getX() * scalingFactor;
            dy = points[i].getX() * scalingFactor;
            points[i].setPoint(dx, dy);

        }
    }

    /**
     * Move every triangle's point through set x value to x coordinate and y
     * value to y coordinate.
     * 
     * @param dx
     *            - new x value for every triangle point
     * @param dy
     *            - new y value for every triangle point
     * @see com.nixsolutions.laba1.task2.Figure#moveTo(double, double)
     */
    @Override
    public void moveTo(double dx, double dy) {
        for (int i = 0; i < SIZE; i++) {
            points[i].setPoint(dx, dy);
        }
    }

    /**
     * Prints triangle perimeter and coordinate
     * 
     * @see com.nixsolutions.laba1.task2.Figure#display()
     */
    @Override
    public void display() {
        for (int i = 0; i < SIZE; i++) {
            System.out.println("Triangle :{ " + points[i] + " }");
        }
        System.out.println(" Perimeter = " + getPerimeter());

    }

    /**
     * Evaluates a triangle's perimeter.
     * 
     * @return perimeter
     */
    private double getPerimeter() {
        Point p1 = points[0];
        Point p2 = points[1];
        Point p3 = points[2];
        double ab = sqrt(pow(p2.getX(), 2) - pow(p1.getX(), 2)
                + pow(p2.getY(), 2) - pow(p1.getY(), 2));
        double bc = sqrt(pow(p3.getX(), 2) - pow(p2.getX(), 2)
                + pow(p3.getY(), 2) - pow(p2.getY(), 2));
        double ac = sqrt(pow(p3.getX(), 2) - pow(p1.getX(), 2)
                + pow(p3.getY(), 2) - pow(p1.getY(), 2));

        return ab + bc + ac;
    }

}
