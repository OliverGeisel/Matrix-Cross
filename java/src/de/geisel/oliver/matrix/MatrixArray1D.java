package de.geisel.oliver.matrix;

import java.util.Arrays;

public class MatrixArray1D extends MatrixArray {
	protected double[] elements;

	public MatrixArray1D(int rows, int columns) {
		super(rows, columns);
		elements = new double[rows * columns];
	}

	public static MatrixArray1D zero(int rows, int columns) {
		return new MatrixArray1D(rows, columns);
	}

	public static MatrixArray1D random(int rows, int columns) {
		var back = new MatrixArray1D(rows, columns);
		for (int i = 0; i < rows * columns; ++i) {
			back.elements[i] = Math.random() * 200 - 100;
		}
		return back;
	}


	@Override
	public double[] getRow(int index) {
		final int offset = index * columns;
		return Arrays.copyOfRange(elements, offset, offset + columns);
	}

	@Override
	public void setRow(int index, double[] row) {
		int offset = columns * index;
		System.arraycopy(row, 0, elements, offset, columns);
	}

	@Override
	public double[] getColumn(int index) {
		double[] back = new double[rows];
		for (int i = 0; i < rows; i++) {
			back[i] = elements[i * columns + index];
		}
		return back;
	}

	@Override
	public void setColumn(int index, double[] column) {
		throw new NoSuchMethodError();
	}

	@Override
	public double getValue(int row, int column) {
		return elements[row * columns + column];
	}

	@Override
	public void setValue(int row, int column, double value) {
		elements[row * columns + column] = value;
	}

	@Override
	public Matrix multiply(Matrix other) {
		MatrixArray1D back = new MatrixArray1D(this.getRows(), other.getColumns());
		int rows = getRows();
		int column = other.getColumns();
		int same = getColumns();
		final double[] matrixA = this.elements;
		final double[] matrixB = ((MatrixArray1D) other).elements;
		final double[] matrixC = back.elements;
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < same; k++) {
				for (int j = 0; j < column; j++) {
					// C[i][j] += A[i][k] * B[k][j]
					matrixC[i * rows + j] += matrixA[i * rows + k] * matrixB[k * rows + j];
				}
			}
		}
		return back;
	}

	@Override
	public Matrix add(Matrix other) {
		MatrixArray1D back = new MatrixArray1D(rows, columns);
		double[] otherElements = ((MatrixArray1D) other).elements;
		for (int i = 0; i < columns * rows; ++i) {
			back.elements[i] = elements[i] + otherElements[i];
		}
		return back;
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
		MatrixArray1D back = new MatrixArray1D(rows, columns);
		// Todo change to pure array
		back.elements = Arrays.stream(elements).map(it -> it * factor).parallel().toArray();
		return back;
	}

	@Override
	public Matrix getUnitMatrix() {
		return null;
	}
}
