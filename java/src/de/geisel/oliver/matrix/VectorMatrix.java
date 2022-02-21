package de.geisel.oliver.matrix;

import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;


public class VectorMatrix extends Matrix {

	static final VectorSpecies<Double> SPECIES = DoubleVector.SPECIES_PREFERRED;

	private DoubleVector[][] matrix;

	public VectorMatrix(int rows, int columns) {
		super(rows, columns);
		matrix = new DoubleVector[rows][columns / SPECIES.length()];
		for (int i = 0; i < rows; i++) {
			double[] values = new double[columns];
			for (int j = 0; j < columns; j++) {
				values[j] = Math.random() * 200 - 100;
			}
			for (int k = 0; k < matrix[i].length; k++) {
				matrix[i][k] = DoubleVector.fromArray(SPECIES, values, k * SPECIES.length());
			}
		}
	}

	private VectorMatrix(int rows, int columns, double value) {
		super(rows, columns);
		matrix = new DoubleVector[rows][columns / SPECIES.length()];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = DoubleVector.broadcast(SPECIES, value);
			}
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

	@Override
	public double getValue(int row, int column) {
		return 0;
	}

	@Override
	public void setValue(int row, int column, double value) {

	}

	@Override
	public Matrix multiply(Matrix other) {
		return null;
	}

	private DoubleVector[] getRowVector(int row){
		return matrix[row];
	}


	public Matrix multiply(VectorMatrix other) {
		var back = new VectorMatrix(this.getRows(), other.getColumns(), 0.0);
		for (int row = 0; row < getRows(); row++) {
			var row1 = this.matrix[row];
			for (int column = 0; column < getColumns(); column++) {
				var row2 = other.getRowVector(column);
				for (int j = 0; j < row1.length; j++) {
					var vector1 = row1[j];
					var vector2 = row2[j];
					var result = vector1.mul(vector2);
					back.setValue(row, column, result.reduceLanes(VectorOperators.ADD));
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
