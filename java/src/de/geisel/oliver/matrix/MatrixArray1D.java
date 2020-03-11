package de.geisel.oliver.matrix;

public class MatrixArray1D extends MatrixArray {
    private double[] elements;

    public MatrixArray1D(int rows, int columns,boolean random) {
        super(rows, columns);
        elements = new double[rows*columns];
        for (int i = 0; i < rows * columns; i++) {
            elements[i] = random ? Math.random() * 200 - 100: 0.0;
        }
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
        MatrixArray1D back = new MatrixArray1D(this.getRows(), other.getColumns(),false);
        int dim = getRows();
        MatrixArray1D A = this;
        MatrixArray1D B = (MatrixArray1D) other;
        for (int i = 0; i < dim; i++) {
            for (int k = 0; k < dim; k++) {
                for (int j = 0; j < dim; j++) {
                    // C[i][j] += A[i][k] * B[k][j]
                    back.elements[i * dim + j] += A.elements[i * dim + k] * B.elements[k * dim + j];
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
