package com.nixsolutions.laba1.task1;

import java.util.Random;
/**
 * Class for launch Matrix 
 * @see com.nixsolutions.laba1.task1.Matrix
 * @author shvidkoy
 *
 */
public class Main {

    // Suppresses default constructor, ensuring non-instantiability.
    private Main() {
    }
    /**
     * Fill matrix with generated random integer number from min to max
     * @param m - empty matrix
     * @param min - min range
     * @param max - max range
     * @return completed random matrix 
     */
    public static Matrix randFillMatrix(Matrix m , int min, int max) {
        Random rand = new Random();
        for (int i = 0;  i < m.getRows(); i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                m.getMatrix()[i][j] = rand.nextInt((max - min) + 1) + min;
            }
        }
        return m;
    }

    public static void main(String[] args) {
        Matrix matrixA = randFillMatrix(new Matrix(),0,100);
        Matrix matrixB = randFillMatrix(new Matrix(),0,100);
        matrixA.display();
        matrixB.display();
        matrixA.transpose().display();
        matrixB.transpose().display();
        matrixA.addition(matrixB).display();
        matrixA.multiply(matrixB).display();



    }
}
