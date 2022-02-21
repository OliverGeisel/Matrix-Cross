package de.geisel.oliver.matrix;

public class MatrixArray2D extends MatrixArray{
    private double[][] elements;

    public MatrixArray2D(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    public double[] getRow(int index) {
        return new double[0];
    }

    @Override
    public double[] getColumn(int index) {
        return new double[0];
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
        return null;
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
