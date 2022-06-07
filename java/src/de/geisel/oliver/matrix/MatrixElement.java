package de.geisel.oliver.matrix;

public class MatrixElement {
    private final double value;

    public MatrixElement() {
        value = 0;
    }

    public MatrixElement(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public MatrixElement add(double value) {
        return new MatrixElement(value+this.value);
    }

    public MatrixElement multiply(double value) {
        return new MatrixElement(this.value * value);
    }
}
