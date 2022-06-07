package de.geisel.oliver.matrix;

import java.util.Arrays;
import java.util.Iterator;

public class MatrixRow implements Iterable<MatrixElement> {
    private final MatrixElement[] row;


    public MatrixRow(int size, double... values) {
        row = new MatrixElement[size];
        if (values.length == 0) {
            for (int i = 0; i < row.length; i++) {
                row[i] = new MatrixElement();
            }
        } else {
            for (int i = 0; i < row.length; i++) {
                row[i] = new MatrixElement(values[i]);
            }
        }
    }


    public MatrixRow scalarMultiply(double factor) {
        var temp = Arrays.stream(row).mapToDouble(it -> it.multiply(factor).getValue());
        return new MatrixRow(row.length, temp.toArray());
    }

    public double getValue(int column) {
        return getElement(column).getValue();
    }

    public void setValue(int column, double value) {
        row[column] = new MatrixElement(value);
    }

    public MatrixElement getElement(int column){
        return row[column];
    }


    public double multiply(MatrixRow other) throws DifferentSizeException {
        if (row.length != other.row.length) {
            throw new DifferentSizeException();
        }
        MatrixElement[] newRow = new MatrixElement[row.length];
        for (int i = 0; i < row.length; i++) {
            newRow[i] = row[i].multiply(other.row[i].getValue());
        }
        return Arrays.stream(newRow).mapToDouble(MatrixElement::getValue).reduce(Double::sum).getAsDouble();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<MatrixElement> iterator() {
        return Arrays.stream(row).iterator();
    }

    public double[] toArray() {
        return Arrays.stream(row).mapToDouble(MatrixElement::getValue).toArray();
    }

    private static class DifferentSizeException extends RuntimeException {
    }
}
