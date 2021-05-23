package model;

import math.SystemSolver;

import java.util.ArrayList;

import static java.lang.Math.*;

public enum FunctionType implements ApproxTypeInterface {
    EXPONENTIAL() {
        private double a,b;
        private boolean foundCoefficients;

        @Override
        public double value(double argument) {
            return a * pow(E, b * argument);
        }

        @Override
        public String toString() {
            return String.format("%.3f", a) + " * exp (" + String.format("%.3f", b) + " * x)";
        }

        @Override
        public void CalculateCoefficient(ArrayList<Point> points) {
            double[][] matrix;
            double[] freeMembers;
            matrix = new double[2][2];
            freeMembers = new double[2];
            matrix[0][0] = points.size();
            matrix[0][1] = points.stream().mapToDouble(Point::getX).sum();
            matrix[1][0] = matrix[0][1];
            matrix[1][1] = points.stream().mapToDouble(point -> pow(point.getX(), 2)).sum();
            freeMembers[0] = points.stream().mapToDouble(point -> log(point.getY())).sum();
            freeMembers[1] = points.stream().mapToDouble(point -> log(point.getY()) * point.getX()).sum();
            LinearSystem system = new LinearSystem(matrix, freeMembers);
            double[] result = SystemSolver.solveSystem(system);
            foundCoefficients = true;
            if (result != null) {
                result[0] = pow(E, result[0]);
                this.a = result[0];
                this.b = result[1];
            } else {
                foundCoefficients = false;
            }
        }

        public boolean isFoundCoefficients() {
            return foundCoefficients;
        }
    },
    LINEAR() {
        private double a,b;
        private boolean foundCoefficients;

        @Override
        public double value(double argument) {
            return a * argument + b;
        }

        @Override
        public String toString() {
            return String.format("%.3f", a) + " * x + " + String.format("%.3f", b);
        }

        @Override
        public void CalculateCoefficient(ArrayList<Point> points) {
            double[][] matrix;
            double[] freeMembers;
            matrix = new double[2][2];
            freeMembers = new double[2];
            matrix[0][0] = points.stream().mapToDouble(point -> pow(point.getX(), 2)).sum();
            matrix[0][1] = points.stream().mapToDouble(Point::getX).sum();
            matrix[1][0] = matrix[0][1];
            matrix[1][1] = points.size();
            freeMembers[0] = points.stream().mapToDouble(point -> point.getX() * point.getY()).sum();
            freeMembers[1] = points.stream().mapToDouble(Point::getY).sum();
            LinearSystem system = new LinearSystem(matrix, freeMembers);
            double[] result = SystemSolver.solveSystem(system);
            foundCoefficients = true;
            if (result != null) {
                this.a = result[0];
                this.b = result[1];
            } else {
                foundCoefficients = false;
            }
        }

        public boolean isFoundCoefficients() {
            return foundCoefficients;
        }
    },
    LOGARITHMIC() {
        private double a,b;
        private boolean foundCoefficients;

        @Override
        public double value(double argument) {
            return a + b * log(argument);
        }

        @Override
        public String toString() {
            return String.format("%.3f", a) + " + " + String.format("%.3f", b) + " * ln(x)";
        }

        @Override
        public void CalculateCoefficient(ArrayList<Point> points) {
            double[][] matrix;
            double[] freeMembers;
            matrix = new double[2][2];
            freeMembers = new double[2];
            matrix[0][0] = points.size();
            matrix[0][1] = points.stream().mapToDouble(point -> log(point.getX())).sum();
            matrix[1][0] = matrix[0][1];
            matrix[1][1] = points.stream().mapToDouble(point -> pow(log(point.getX()), 2)).sum();
            freeMembers[0] = points.stream().mapToDouble(Point::getY).sum();
            freeMembers[1] = points.stream().mapToDouble(point -> log(point.getX()) * point.getY()).sum();
            LinearSystem system = new LinearSystem(matrix, freeMembers);
            double[] result = SystemSolver.solveSystem(system);
            foundCoefficients = true;
            if (result != null) {
                this.a = result[0];
                this.b = result[1];
            } else {
                foundCoefficients = false;
            }
        }

        public boolean isFoundCoefficients() {
            return foundCoefficients;
        }
    },
    QUADRATIC() {
        private double a,b,c;
        private boolean foundCoefficients;

        @Override
        public double value(double argument) {
            return a * pow(argument, 2) + b * argument + c;
        }

        @Override
        public String toString() {
            return String.format("%.3f", a) + " * x² + " + String.format("%.3f", b) + " * x + " + String.format("%.3f", c);
        }

        @Override
        public void CalculateCoefficient(ArrayList<Point> points) {
            double[][] matrix;
            double[] freeMembers;
            matrix = new double[3][3];
            freeMembers = new double[3];
            matrix[0][0] = points.stream().mapToDouble(point -> pow(point.getX(), 4)).sum();
            matrix[0][1] = points.stream().mapToDouble(point -> pow(point.getX(), 3)).sum();
            matrix[0][2] = points.stream().mapToDouble(point -> pow(point.getX(), 2)).sum();
            matrix[1][0] = matrix[0][1];
            matrix[1][1] = matrix[0][2];
            matrix[1][2] = points.stream().mapToDouble(Point::getX).sum();
            matrix[2][0] = matrix[0][2];
            matrix[2][1] = matrix[1][2];
            matrix[2][2] = points.size();
            freeMembers[0] = points.stream().mapToDouble(point -> pow(point.getX(), 2) * point.getY()).sum();
            freeMembers[1] = points.stream().mapToDouble(point -> point.getX() * point.getY()).sum();
            freeMembers[2] = points.stream().mapToDouble(Point::getY).sum();

            LinearSystem system = new LinearSystem(matrix, freeMembers);
            double[] result = SystemSolver.solveSystem(system);
            foundCoefficients = true;
            if (result != null) {
                this.a = result[0];
                this.b = result[1];
                this.c = result[2];
            } else {
                foundCoefficients = false;
            }
        }

        public boolean isFoundCoefficients() {
            return foundCoefficients;
        }
    };

    @Override
    public double value(double argument) {
        return 0;
    }

    public String getType() {
        return this.name();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private boolean foundCoefficients;

    public boolean isFoundCoefficients() {
        return foundCoefficients;
    }
}