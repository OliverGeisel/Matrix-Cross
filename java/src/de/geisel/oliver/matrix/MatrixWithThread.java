package de.geisel.oliver.matrix;

import java.util.LinkedList;
import java.util.List;

public class MatrixWithThread extends MatrixArray1D {
	public MatrixWithThread(int rows, int columns) {
		super(rows, columns);
	}

	public static MatrixWithThread zero(int rows, int columns){
		return new MatrixWithThread(rows,columns);
	}

	public static MatrixWithThread random (int rows, int columns){
		var back = new MatrixWithThread(rows,columns);
		for (int i = 0; i < rows* columns; ++i) {
			back.elements[i] = Math.random() * 200 - 100;
		}
		return back;
	}


	@Override
	public Matrix multiply(Matrix other) {
		MatrixArray1D result = MatrixArray1D.zero(getRows(), other.getColumns());
		int size = getColumns();
		Thread lineThread;
		List<Thread> threads = new LinkedList<>();
		// run through all rows
		int threadNum = 0;
		for (int rowNumber = 0; rowNumber < result.rows; ++rowNumber) {
			double[] row = getRow(rowNumber);
			lineThread = new LineThread(row, other, result, size, rowNumber);
			threads.add(lineThread);
			lineThread.start();
			++threadNum;
			if (threadNum == 8) {
				threads.forEach(x -> {
					try {
						x.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
				threads.clear();
				threadNum = 0;
			}
		}
		return result;
	}

	private static class ZellThread extends Thread {

		private final double[] matrixA, matrixB;
		private final Matrix result;
		private final int row, column, size;

		public ZellThread(double[] matrixA1, double[] matrixB1, Matrix result1, int row1, int column1, int size1) {

			this.matrixA = matrixA1;
			this.matrixB = matrixB1;
			this.result = result1;
			this.row = row1;
			this.column = column1;
			this.size = size1;
		}

		public void run() {
			double temp = 0.0;
			for (int k = 0; k < size; k++) {
				temp += matrixA[k] * matrixB[k];
			}
			result.setValue(row, column, temp);
		}
	}

	private static class LineThread extends Thread {

		private final double[] rowFromA;
		private final MatrixArray1D result;
		private final Matrix matrixB;
		private final int index;
		private final int size;

		public LineThread(double[] rowFromA, Matrix matrixB, MatrixArray1D result, int size, int index) {
			this.rowFromA = rowFromA;
			this.matrixB = matrixB;
			this.result = result;
			this.size = size;
			this.index = index;
		}

		public void run() {
			double[] resultRow = new double[size];
			// iterate through all cells in line
			for (int j = 0; j < size; ++j) {
				double[] column = matrixB.getColumn(j);
				double temp = 0.0;
				// calculate one cell
				for (int k = 0; k < size; ++k) {
					temp += rowFromA[k] * column[k];
				}
				resultRow[j] = temp;
			}
			System.arraycopy(resultRow, 0, result.elements, index * size, size);
		}
	}
}
