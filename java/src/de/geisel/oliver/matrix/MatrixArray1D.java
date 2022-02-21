package de.geisel.oliver.matrix;

import java.util.Arrays;

public class MatrixArray1D extends MatrixArray {
    protected double[] elements;

    public MatrixArray1D(int rows, int columns, boolean random) {
        super(rows, columns);
        elements = new double[rows * columns];
        for (int i = 0; i < rows * columns; i++) {
            elements[i] = random ? Math.random() * 200 - 100 : 0.0;
        }
    }

    @Override
    public double[] getRow(int index) {
        return Arrays.copyOfRange(elements,index* getColumns(), index* getColumns()+getColumns());
    }

    @Override
    public double[] getColumn(int index) {
        double[] back = new double[getRows()];
        for (int i=0; i < getRows(); i++){
            back[i] = elements[i*getColumns()+index];
        }
        return back;
    }

    @Override
    public double getValue(int row, int column) {
        return 0;
    }

    @Override
    public void setValue(int row, int column, double value) {

    }

    @Override
    public Matrix multiply(Matrix other) {
        MatrixArray1D back = new MatrixArray1D(this.getRows(), other.getColumns(), false);
        int rows = getRows();
        int column = other.getColumns();
        int same = getColumns();
        double[] A = this.elements;
        double[] B = ((MatrixArray1D) other).elements;
        double[] C = back.elements;
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < same; k++) {
                for (int j = 0; j < column; j++) {
                    // C[i][j] += A[i][k] * B[k][j]
                    C[i * rows + j] += A[i * rows + k] * B[k * rows + j];
                }
            }
        }
        return back;
    }

    @Override
    public Matrix add(Matrix other) {
        return null;
    }

    @Override
    public double getDet() {
        return 0;
    }

    @Override
    public double[] getMainDiagonal() {
        return new double[0];
    }

    @Override
    public Matrix transponate() {
        return null;
    }

    @Override
    public void scalarMultiply(double factor) {

    }

    @Override
    public Matrix getUnitMatrix() {
        return null;
    }
}
