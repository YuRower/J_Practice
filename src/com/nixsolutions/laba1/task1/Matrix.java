package com.nixsolutions.laba1.task1;

/**
 * A simple class for demonstrating operation with matrix.
 * 
 * @author shvidkoy
 * @version 1.0
 */
public class Matrix {

    private static final int SIZE = 4;// default size for array

    private double matrix[][];

    private int row, col;

    /**
     * Constructor creating a new marix with default size
     * 
     * @see Matrix(int size)
     */
    public Matrix() {
        this(SIZE);
    }

    /**
     * Constructor creating a new marix with certain size
     * 
     * @param size
     *            - size of dimension matrix
     */
    public Matrix(int size) {
        setMatrix(new double[size][size]);
    }

    /**
     * Constructor creating a new marix with certain length and width
     * 
     * @param row
     *            - number of rows
     * @param col
     *            - number of columns
     */
    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        setMatrix(new double[row][col]);
    }

    /**
     * Get amount of matrix rows
     * 
     * @return Matrix rows
     */
    public int getRows() {
        return matrix.length;
    }

    /**
     * Get amount of matrix columns
     * 
     * @return Matrix columns
     */
    public int getColumns() {
        return matrix[0].length;
    }

    /**
     * Accomplish matrix transpose
     * 
     * @return The transpose of a matrix is a new matrix whose rows are the
     *         columns of the original.
     */
    public Matrix transpose() {
        System.out.println("transpose");
        Matrix result = new Matrix();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                result.matrix[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    /**
     * Multiply current matrix with another
     * 
     * @param otherMatrix
     *            - matrix which will be multiplied
     * @return result of matrix multiplication
     */
    public Matrix multiply(Matrix otherMatrix) {
        System.out.println("multiply");
        isMatrixValid(otherMatrix);
        Matrix result = new Matrix();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                for (int k = 0; k < matrix.length; k++) {
                    result.matrix[i][j] += matrix[i][k]
                            * otherMatrix.matrix[k][j];
                }
            }
        }
        return result;
    }

    /**
     * Add current matrix with another
     * 
     * @param otherMatrix
     *            - matrix which will be added
     * @return result of matrix addition
     */
    public Matrix addition(Matrix otherMatrix) {
        System.out.println("addition");
        isMatrixValid(otherMatrix);
        Matrix result = new Matrix();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                result.matrix[i][j] = matrix[i][j] + otherMatrix.matrix[i][j];

            }
        }
        return result;

    }

    /**
     * Display current matrix
     */
    public void display() {
        System.out.println("Matrix");
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                System.out.printf("%10.3f", matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Check the validelity of matrix
     * 
     * @throws IllegalArgumentException
     *             - if matrix columns are not equals with another matrix rows
     * @throws NullPointerException
     *             - if received matrix is null
     * @param matrix
     *            for checking
     */
    public void isMatrixValid(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException("Matrix == null");
        }
        if (this.getColumns() != matrix.getRows()) {
            throw new IllegalArgumentException(
                    "matrix columns must be the same size with another matrix rows ");
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double matrix[][]) {
        this.matrix = matrix;
    }

}
