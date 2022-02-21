package de.geisel.oliver.matrix;

public class MatrixOO extends Matrix {
    private MatrixRow[] matrix;

    public MatrixOO(int rows, int columns) {
        super(rows, columns);
        matrix = new MatrixRow[rows];
        for (int i = 0; i < rows; i++) {
            double[] values = new double[columns];
            for (int j = 0; j < columns; j++) {
                values[j] = Math.random() * 200 - 100;
            }
            matrix[i] = new MatrixRow(columns, values);
        }
    }

    @Override
    public double[] getRow(int index) {
        return new double[0];
    }

    @Override
    public double[] getColumn(int index) {
        return new double[0];
    }

    private MatrixElement getElement(int row, int column) {
        return matrix[row].getElement(column);
    }


    @Override
    public double getValue(int row, int column) {
        return getElement(row, column).getValue();
    }

    @Override
    public void setValue(int row, int column, double value) {

    }

    @Override
    public Matrix multiply(Matrix other) {
        MatrixOO back = new MatrixOO(getRows(), other.getColumns());
        int dim = getColumns();
        for (int i = 0; i < dim; i++) {
            for (int k = 0; k < dim; k++) {
                for (int j = 0; j < dim; j++) {
                    // C[i][j] += A[i][k] * B[k][j]
                    back.getElement(i, j).add(this.getValue(i, k) * other.getValue(k, j));
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
