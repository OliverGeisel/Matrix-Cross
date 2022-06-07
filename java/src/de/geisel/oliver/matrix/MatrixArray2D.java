package de.geisel.oliver.matrix;

public class MatrixArray2D extends MatrixArray {
	private double[][] elements;

	public static MatrixArray2D zero(int rows, int columns) {
		return new MatrixArray2D(rows, columns);
	}

	public static MatrixArray2D random(int rows, int columns) {
		var back = new MatrixArray2D(rows, columns);
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; j++) {
				back.elements[i][j] = Math.random() * 200 - 100;
			}
		}
		return back;
	}


	public MatrixArray2D(int rows, int columns) {
		super(rows, columns);
		elements = new double[rows][columns];
	}

	@Override
	public double[] getRow(int index) {
		return new double[0];
	}

	@Override
	public void setRow(int index, double[] row) {

	}

	@Override
	public double[] getColumn(int index) {
		return new double[0];
	}

	@Override
	public void setColumn(int index, double[] column) {

	}

	@Override
	public double getValue(int row, int column) {
		return elements[row][column];
	}

	@Override
	public void setValue(int row, int column, double value) {
		elements[row][column] = value;
	}

	@Override
	public Matrix multiply(Matrix other) {
		MatrixArray2D back = new MatrixArray2D(rows, other.columns);
		int column = other.getColumns();
		int same = getColumns();
		final double[][] A = this.elements;
		final double[][] B = ((MatrixArray2D) other).elements;
		final double[][] C = back.elements;
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < same; k++) {
				for (int j = 0; j < column; j++) {
					C[i][j] += A[i][k] * B[k][j];
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
	public Matrix scalarMultiply(double factor) {
		MatrixArray2D back = new MatrixArray2D(rows, columns);
		for (int i = 0; i < elements.length; ++i) {
			var tempRow = elements[i];
			for (int j = 0; j < tempRow.length; ++j) {
				tempRow[j] *= factor;
			}
			back.elements[i] = tempRow;
		}
		return back;
	}

	@Override
	public Matrix getUnitMatrix() {
		return null;
	}
}
