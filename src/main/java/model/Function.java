package model;

import lombok.Getter;
import lombok.Setter;
import math.SystemSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.*;

public enum Function implements ApproxTypeInterface {
    EXPONENTIAL() {
        private double a,b;

        @Override
        public double value(double argument) {
            return a * pow(E, b * argument);
        }

        @Override
        public String getNoteFunction() {
            return String.format("%.3f", a) + " * exp (" + String.format("%.3f", b) + " * x)";
        }

        @Override
        public void calculateCoefficient(ArrayList<Point> points) {
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
            assert result != null;
            super.foundCoefficients = !Arrays.stream(result).allMatch(Double::isNaN);
            this.a = pow(E, result[0]);
            this.b = result[1];
        }
    },
    POWERFUL() {
        private double a,b;

        @Override
        public double value(double argument) {
            return a * pow(argument,b);
        }

        @Override
        public String getNoteFunction() {
            return String.format("%.3f", a) + " * x^"+String.format("%.3f", b);
        }

        @Override
        public void calculateCoefficient(ArrayList<Point> points) {
            double[][] matrix;
            double[] freeMembers;
            matrix = new double[2][2];
            freeMembers = new double[2];
            matrix[0][0] = points.size();
            matrix[0][1] = points.stream().mapToDouble(point -> log(point.getX())).sum();
            matrix[1][0] = matrix[0][1];
            matrix[1][1] = points.stream().mapToDouble(point -> pow(log(point.getX()), 2)).sum();
            freeMembers[0] = points.stream().mapToDouble(point -> log(point.getY())).sum();
            freeMembers[1] = points.stream().mapToDouble(point -> log(point.getY()) * log(point.getX())).sum();
            LinearSystem system = new LinearSystem(matrix, freeMembers);
            double[] result = SystemSolver.solveSystem(system);
            assert result != null;
            super.foundCoefficients = !Arrays.stream(result).allMatch(Double::isNaN);
            this.a = pow(E, result[0]);
            this.b = result[1];
        }
    },
    LINEAR() {
        private double a,b;

        @Override
        public double value(double argument) {
            return a * argument + b;
        }

        @Override
        public String getNoteFunction() {
            return String.format("%.3f", a) + " * x + " + String.format("%.3f", b);
        }

        @Override
        public void calculateCoefficient(ArrayList<Point> points) {
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
            assert result != null;
            super.foundCoefficients = !Arrays.stream(result).allMatch(Double::isNaN);
            this.a = result[0];
            this.b = result[1];
        }

        private double pearsonCoefficient(ArrayList<Point> points){
            double sumX=0,sumY=0,sumXY = 0,midX=0,midY=0;
            for (Point point: points){
                midX+=point.getX();
                midY+=point.getY();
            }
            midX/=points.size();
            midY/=points.size();
            for (Point point: points){
                sumX+=(point.getX()-midX)*(point.getX()-midX);
                sumY+=(point.getY()-midY)*(point.getY()-midY);
                sumXY+=(point.getX()-midX)*(point.getY()-midY);
            }
            return sumXY/Math.sqrt(sumX*sumY);
        }

        @Override
        public String toString() {
                return "Точки, участвующие в аппроксимации: " + Collections.singletonList(super.points) + "\n" +
                        "Полученная функция: y = " + getNoteFunction() + "\n" +
                        "Коэффициент корреляции Пирсона: " +  java.math.BigDecimal.valueOf(this.pearsonCoefficient(super.points)).toPlainString() +"\n" +
                        "Вид аппроксимирующей функции: " + this.name()+ "\n" +
                        "Отклонение : " + (super.foundCoefficients?java.math.BigDecimal.valueOf(super.deviation).toPlainString():"")+ "\n";
        }
    },
    LOGARITHMIC() {
        private double a,b;

        @Override
        public double value(double argument) {
            return a + b * log(argument);
        }

        @Override
        public String getNoteFunction() {
            return String.format("%.3f", a) + " + " + String.format("%.3f", b) + " * ln(x)";
        }

        @Override
        public void calculateCoefficient(ArrayList<Point> points) {
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
            assert result != null;
            super.foundCoefficients = !Arrays.stream(result).allMatch(Double::isNaN);
            this.a = result[0];
            this.b = result[1];
        }
    },
    QUADRATIC() {
        private double a,b,c;
        @Override
        public double value(double argument) {
            return a * pow(argument, 2) + b * argument + c;
        }

        @Override
        public String getNoteFunction() {
            return String.format("%.3f", a) + " * x² + " + String.format("%.3f", b) + " * x + " + String.format("%.3f", c);
        }

        @Override
        public void calculateCoefficient(ArrayList<Point> points) {
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
            assert result != null;
            super.foundCoefficients = !Arrays.stream(result).allMatch(Double::isNaN);
            this.a = result[0];
            this.b = result[1];
            this.c = result[2];
        }

    };

    @Override
    public double value(double argument) {
        return 0;
    }

    @Override
    public void calculateCoefficient(ArrayList<Point> points) {

    }

    @Override
    public String getNoteFunction() {
        return null;
    }

    public String getType() {
        return this.name();
    }
    @Getter
    private boolean foundCoefficients;
    @Setter
    @Getter
    private ArrayList<Point> points=null;
    @Getter
    private double deviation=Double.MAX_VALUE;


    @Override
    public String toString() {
        return "Точки, участвующие в аппроксимации: " + (this.points) + "\n" +
                "Полученная функция: y = " + getNoteFunction() + "\n" +
                "Вид аппроксимирующей функции: " + this.name()+ "\n" +
                "Отклонение : " + (this.foundCoefficients?java.math.BigDecimal.valueOf(this.deviation).toPlainString():"")+ "\n";
    }

    public void approximate() {
        this.calculateCoefficient(this.getPoints());
        if (this.isFoundCoefficients()) {
            this.deviation=calculateDeviation();
        } else {
            System.out.print("ERROR FATAL ERROR FATAL ERROR FATAL");
        }
    }

    private double calculateDeviation(){
        double S=0;
        for (Point point:this.points){
            S+=(this.value(point.getX())-point.getY())*(this.value(point.getX())-point.getY());
        }
        return S;
    }

}