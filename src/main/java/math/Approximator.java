package math;

import model.*;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Approximator {

    public ApproxResult approximate(FunctionType type, ArrayList<Point> points) {
        double[] result = getCoefficient(type, points);
        if (result != null) {
            ArrayList<Function> functions = new ArrayList<>();
            functions.add(new Function(type, result));
            double max_deviation = 0.0;
            Point max_deviation_point = points.get(0);
            for (Point point : points) {
                if (abs(point.getY() - functions.get(0).f(point.getX())) > max_deviation) {
                    max_deviation = abs(point.getY() - functions.get(0).f(point.getX()));
                    max_deviation_point = point;
                }
            }
            points.remove(max_deviation_point);
            double[] new_result = getCoefficient(type, points);
            points.add(max_deviation_point);
            functions.add(new Function(type, new_result));
            return new ApproxResult(functions, points);
        } else {
            return null;
        }
    }

    private double[] getCoefficient(FunctionType type, ArrayList<Point> points) {
        double[][] matrix;
        double[] freeMembers;
        SystemSolver systemSolver = new SystemSolver();
        switch (type.toString()) {
            case ("LINEAR"):
                matrix = new double[2][2];
                freeMembers = new double[2];
                matrix[0][0] = points.stream().mapToDouble(point -> pow(point.getX(), 2)).sum();
                matrix[0][1] = points.stream().mapToDouble(Point::getX).sum();
                matrix[1][0] = matrix[0][1];
                matrix[1][1] = points.size();
                freeMembers[0] = points.stream().mapToDouble(point -> point.getX() * point.getY()).sum();
                freeMembers[1] = points.stream().mapToDouble(Point::getY).sum();
                break;
            case ("QUADRATIC"):
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
                break;
            case ("EXPONENTIAL"):
                matrix = new double[2][2];
                freeMembers = new double[2];
                matrix[0][0] = points.size();
                matrix[0][1] = points.stream().mapToDouble(Point::getX).sum();
                matrix[1][0] = matrix[0][1];
                matrix[1][1] = points.stream().mapToDouble(point -> pow(point.getX(), 2)).sum();
                freeMembers[0] = points.stream().mapToDouble(point -> log(point.getY())).sum();
                freeMembers[1] = points.stream().mapToDouble(point -> log(point.getY()) * point.getX()).sum();
                break;
            case ("LOGARITHMIC"):
                matrix = new double[2][2];
                freeMembers = new double[2];
                matrix[0][0] = points.size();
                matrix[0][1] = points.stream().mapToDouble(point -> log(point.getX())).sum();
                matrix[1][0] = matrix[0][1];
                matrix[1][1] = points.stream().mapToDouble(point -> pow(log(point.getX()), 2)).sum();
                freeMembers[0] = points.stream().mapToDouble(Point::getY).sum();
                freeMembers[1] = points.stream().mapToDouble(point -> log(point.getX()) * point.getY()).sum();
                break;
            default:
                matrix = new double[2][2];
                freeMembers = new double[2];
                break;
        }
        LinearSystem system = new LinearSystem(matrix, freeMembers);
        double[] result = systemSolver.solveSystem(system);
        if (result == null) return null;
        if (type == FunctionType.EXPONENTIAL) result[0] = pow(E, result[0]);
        return result;
    }
}
