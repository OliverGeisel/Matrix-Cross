package de.geisel.oliver.matrix;

public class MatrixElement {
    private double value;

    public MatrixElement() {
        value = 0;
    }

    public MatrixElement(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public void add(double value) {
        this.value += value;
    }

    public void multiyply(double value){
        this.value*= value;
    }
}
