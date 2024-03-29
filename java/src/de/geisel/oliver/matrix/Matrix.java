package de.geisel.oliver.matrix;

public abstract class Matrix {

	protected final int rows;
	protected final int columns;

	protected Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}

	public abstract double[] getRow(int index);

	public abstract void setRow(int index, double[] row);

	public abstract double[] getColumn(int index);

	public abstract void setColumn(int index, double[] column);

	public abstract double getValue(int row, int column);

	public abstract void setValue(int row, int column, double value);

	public abstract Matrix multiply(Matrix other);

	public abstract Matrix add(Matrix other);

	public abstract double getDet();

	public abstract double[] getMainDiagonal();

	public abstract Matrix transponate();

	public abstract Matrix scalarMultiply(double factor);

	public abstract Matrix getUnitMatrix();

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
}
