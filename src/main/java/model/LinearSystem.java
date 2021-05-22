package model;

import java.util.Arrays;

public class LinearSystem {
    private double[][] matrix;
    private double[] freeMembers;

    public LinearSystem(double[][] matrix, double[] freeMembers) {
        this.matrix = matrix;
        this.freeMembers = freeMembers;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            output.append(Arrays.toString(matrix[i])).append(" * x =  ").append(freeMembers[i]).append("\n");
        }
        return output.toString();
    }

    public double[] getFreeMembers() {
        return freeMembers;
    }

    public double[][] getMatrix() {
        return matrix;
    }

}
