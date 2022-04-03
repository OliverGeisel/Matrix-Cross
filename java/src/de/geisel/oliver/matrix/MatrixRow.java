package de.geisel.oliver.matrix;

import java.util.Arrays;

public class MatrixRow {
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


    public void scalarMultiply(double factor) {
        Arrays.stream(row).forEach(it -> it.multiyply(factor));
    }

    public double getValue(int column) {
        return getElement(column).getValue();
    }

    public void setValue(int column, double value) {
        row[column]= new MatrixElement(value);
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
            newRow[i] = row[i].multiyply(other.row[i].getValue()) ;
        }
        return Arrays.stream(newRow).mapToDouble(MatrixElement::getValue).reduce(Double::sum).getAsDouble();
    }

    private static class DifferentSizeException extends RuntimeException {
    }
}
