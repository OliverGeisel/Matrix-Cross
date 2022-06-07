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
		return matrix[index].toArray();
	}

	@Override
	public void setRow(int index, double[] row) {
		matrix[index] = new MatrixRow(row.length, row);
	}

	@Override
	public double[] getColumn(int index) {
		// todo fix
		return new double[0];
	}

	@Override
	public void setColumn(int index, double[] column) {

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
		matrix[row].setValue(column, value);
	}

	@Override
	public Matrix multiply(Matrix other) {
		MatrixOO back = new MatrixOO(getRows(), other.getColumns());
		int dim = getColumns();
		for (int i = 0; i < dim; i++) {
			for (int k = 0; k < dim; k++) {
				for (int j = 0; j < dim; j++) {
					// C[i][j] += A[i][k] * B[k][j]
					back.setValue(i, j, back.getElement(i, j).add(this.getValue(i, k) * other.getValue(k, j)).getValue());
				}
			}
		}
		/*if (other instanceof MatrixOO matrixOO) {

			for (MatrixRow otherRow:matrixOO.matrix)
			for (MatrixRow row : matrix) {
				row.multiply(row);
			}

		}*/
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
		MatrixOO back = new MatrixOO(getColumns(), getRows());
		for (int i = 0; i < matrix.length; ++i) {
			back.setColumn(i, getColumn(i));
		}

		return back;
	}

	@Override
	public Matrix scalarMultiply(double factor) {
		MatrixOO back = new MatrixOO(rows, columns);

		for (int i = 0; i < matrix.length; ++i) {
			back.matrix[i] = matrix[i].scalarMultiply(factor);
		}
		return back;
	}

	@Override
	public Matrix getUnitMatrix() {
		return null;
	}
}
