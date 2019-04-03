package com.nixsolutions.laba1.task2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to check figure functionality
 * 
 * @see com.nixsolutions.laba1.task2.Figure
 * @author shvidkoy
 *
 */
public class Main {

    // Suppresses default constructor, ensuring non-instantiability.
    private Main() {
    }

    public static void main(String[] args) {
        List<Figure> list = new ArrayList<>();
        list.add(new Circle(new Point(2, 3), 3));
        list.add(new Triangle(new Point(0, 0), new Point(3, 0),
                new Point(3, 4)));
        Circle c = new Circle(new Point(2, 3), 3);
        c.scaling(4);
        c.moveTo(4, 5);
        list.add(c);
        for (Figure fig : list) {
            fig.display();
        }

    }
}
