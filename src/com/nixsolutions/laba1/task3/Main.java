package com.nixsolutions.laba1.task3;

import java.util.Random;

import com.nixsolutions.laba1.task2.Circle;
import com.nixsolutions.laba1.task2.Figure;
import com.nixsolutions.laba1.task2.Point;
import com.nixsolutions.laba1.task2.Triangle;

/**
 * @author shvidkoy
 *
 */
public class Main {

    // Suppresses default constructor, ensuring non-instantiability.
    private Main() {
    }

    /**
     * Prints name of figures
     * figures array of figures
     * 
     * @param figures
     *            array of figures
     */
    public static void printFigureNames(Figure[] figures) {
        for (int i = 0; i < figures.length; i++) {
            System.out.println(i + " " + figures[i].getClass().getSimpleName());
        }
    }

    /**
     * Determine the type of shape that will be randomly selected.
     * 
     * @return certain figure
     */
    public static Figure getRandomFigure() {
        int r = new Random().nextInt(5);
        if (r % 2 == 0) {
            return new Triangle(new Point(0, 0), new Point(3, 0),
                    new Point(3, 4));
        } else {
            return new Circle(new Point(2, 3), 3);
        }
    }

    public static void main(String[] args) {
        Figure[] figures = new Figure[11];
        for (int i = 0; i < figures.length; i++) {
            figures[i] = getRandomFigure();
        }

        for (Figure figure : figures) {
            figure.display();
        }

        printFigureNames(figures);
    }

}
