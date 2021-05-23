package math;

import model.LinearSystem;

public class SystemSolver {

    public static double[] solveSystem(LinearSystem system) {
        double[][] matrix = system.getMatrix();
        double[] freeMembers = system.getFreeMembers();
        int n = freeMembers.length;
        //ПРЯМОЙ ХОД
        //удаляем переменные 0 .. n-1
        for (int i = 0; i < n - 1; i++) {
            boolean flag = true;
            if (matrix[i][i] == 0) {
                flag = false;
                for (int j = i + 1; j < n; j++) {
                    if (matrix[i][j] != 0) {
                        swapColumns(matrix, i, j);
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) return null;
            //в уравнениях i+1 .. n
            for (int k = i + 1; k < n; k++) {
                double c = matrix[k][i] / matrix[i][i];
                matrix[k][i] = 0;
                //меняем коэффициенты i+1 .. n
                for (int j = i + 1; j < n; j++) {
                    matrix[k][j] -= c * matrix[i][j];
                }
                freeMembers[k] -= c * freeMembers[i];
            }
        }
        //ОБРАТНЫЙ ХОД
        double[] resultVector = new double[n];
        for (int i = n - 1; i > -1; i--) {
            double s = 0;
            for (int j = i + 1; j < n; j++) {
                s += matrix[i][j] * resultVector[j];
            }
            resultVector[i] = (freeMembers[i] - s) / matrix[i][i];
        }
        return resultVector;
    }

    private static void swapColumns(double[][] matrix, int oldIndex, int newIndex) {
        for (int i = 0; i < matrix.length; i++) {
            double element = matrix[i][oldIndex];
            matrix[i][oldIndex] = matrix[i][newIndex];
            matrix[i][newIndex] = element;
        }
    }

}