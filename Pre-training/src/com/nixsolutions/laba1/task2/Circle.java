package com.nixsolutions.laba1.task2;

/**
 * Class that represent Circle figure
 * 
 * @author shvidkoy
 *
 */
public class Circle extends Figure {

    public Circle() {
    }

    private Point point;
    private double radius;

    /**
     * Constructor creates a circle with certain radius in specified point
     * 
     * @param point
     *            - starting point
     * @param radius
     *            - starting radius
     */
    public Circle(Point point, int radius) {
        this.point = point;
        this.radius = radius;
    }

    /**
     * Makes a resize of a circle through changing radius scalding
     * 
     * @param scalingFactor
     *            -represent the measure of how out radius of circle will be
     *            scaled
     */
    @Override
    public void scaling(double scalingFactor) {
        setRadius(radius *= scalingFactor);
    }

    /**
     * Move to new point of circle radius
     * 
     * @param dx
     *            - new radius x value
     * @param dy
     *            - new radius y value
     * @see com.nixsolutions.laba1.task2.Figure#moveTo(double, double)
     */
    @Override
    public void moveTo(double dx, double dy) {
        point.setPoint(dx, dy);
    }

    /**
     * Prints circle perimeter and coordinate
     * 
     * @see com.nixsolutions.laba1.task2.Figure#display()
     */
    @Override
    public void display() {
        System.out.println(
                "Circle : { " + point + "} Perimeter = " + getPerimeter());
    }

    /**
     * Evaluates a circle's perimeter.
     * 
     * @return perimeter
     */
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    /**
     * @return the point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @param point
     *            the point to set
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius
     *            the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

}
