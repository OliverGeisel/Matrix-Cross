package de.geisel.oliver.matrix;

import java.util.LinkedList;
import java.util.List;

public class MatrixWithThread extends MatrixArray1D {
    public MatrixWithThread(int rows, int columns, boolean random) {
        super(rows, columns, random);
    }


    @Override
    public Matrix multiply(Matrix other) {
        Matrix result = new MatrixArray1D(this.getRows(), other.getColumns(), false);
        int size = getColumns();
        Thread zellThread;
        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < result.getRows(); i++) {
            double[] row = getRow(i);
            zellThread = new LineThread(row, other, result, i, size);
            threads.add(zellThread);
            zellThread.start();

        }
        threads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    private static  class ZellThread extends Thread {

        private double[] matrixA, matrixB;
        private Matrix result;
        private int row, column, size;

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

        private final double[] matrixA;
        private final Matrix result, matrixB;
        private final int row, size;

        public LineThread(double[] matrixA, Matrix matrixB, Matrix result, int row, int size) {

            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.result = result;
            this.row = row;
            this.size = size;
        }

        public void run() {
            double temp = 0.0;
            for (int j = 0; j < matrixB.getRows(); j++) {
                double[] column = matrixB.getColumn(j);
                for (int k = 0; k < size; k++) {
                    temp += matrixA[k] * column[k];
                }
                result.setValue(row, j, temp);
            }
        }
    }
}
