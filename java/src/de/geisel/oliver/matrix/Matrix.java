package de.geisel.oliver.matrix;

public abstract class Matrix {

    private final int rows;
    private final int columns;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public abstract double getValue(int row, int column);

    public abstract void setValue(int row, int column, double value);

    public abstract Matrix multiply(Matrix other);

    public abstract Matrix add(Matrix other);

    public abstract double getDet();

    public abstract double[] getMainDiagonal();

    public abstract Matrix transponate();

    public abstract void scalarMultiply(double factor);

    public abstract Matrix getUnitMatrix();

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
