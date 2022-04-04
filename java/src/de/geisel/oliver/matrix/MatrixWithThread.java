package de.geisel.oliver.matrix;

import java.util.LinkedList;
import java.util.List;

public class MatrixWithThread extends MatrixArray1D {
	protected MatrixWithThread(int rows, int columns) {
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
		Thread LineThread;
		List<Thread> threads = new LinkedList<>();
		// run through all rows
		int threadNum = 0;
		for (int i = 0; i < result.getRows(); ++i) {
			double[] row = getRow(i);
			LineThread = new LineThread(row, other, result, size, i);
			threads.add(LineThread);
			LineThread.start();
			++threadNum;
			if (threadNum == 32) {
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
		private final Matrix matrixC;
		private final Matrix matrixB;
		private final int index, size;

		public LineThread(double[] rowFromA, Matrix matrixB, Matrix result, int size, int index) {
			this.rowFromA = rowFromA;
			this.matrixB = matrixB;
			this.matrixC = result;
			this.size = size;
			this.index = index;
		}

		public void run() {
			double[] tempRow = new double[size];
			// iterate through all cell
			for (int j = 0; j < matrixB.getColumns(); ++j) {
				double[] column = matrixB.getColumn(j);
				double temp = 0.0;
				// calculate one cell
				for (int k = 0; k < size; ++k) {
					temp += rowFromA[k] * column[k];
				}
				tempRow[j] = temp;
			}
			matrixC.setRow(index, tempRow);
		}
	}
}
