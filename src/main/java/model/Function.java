package model;

import static java.lang.Math.*;

public class Function {
    private FunctionType type;
    private double[] coefficients;

    public Function(FunctionType type, double[] coefficients) {
        this.type = type;
        this.coefficients = coefficients;
    }

    public double f(double x) {
        switch (type.toString()) {
            case ("LINEAR"):
                return coefficients[0] * x + coefficients[1];
            case ("QUADRATIC"):
                return coefficients[0] * pow(x, 2) + coefficients[1] * x + coefficients[2];
            case ("EXPONENTIAL"):
                return coefficients[0] * pow(E, coefficients[1] * x);
            case ("LOGARITHMIC"):
                return coefficients[0] + coefficients[1] * log(x);
            default:
                return 0.0;
        }
    }

    @Override
    public String toString() {
        switch (type.toString()) {
            case ("LINEAR"):
                return String.format("%.3f", coefficients[0]) + " * x + " + String.format("%.3f", coefficients[1]);
            case ("QUADRATIC"):
                return String.format("%.3f", coefficients[0]) + " * x² + " + String.format("%.3f", coefficients[1]) + " * x + " + String.format("%.3f", coefficients[2]);
            case ("EXPONENTIAL"):
                return String.format("%.3f", coefficients[0]) + " * exp (" + String.format("%.3f", coefficients[1]) + " * x)";
            case ("LOGARITHMIC"):
                return String.format("%.3f", coefficients[0]) + " + " + String.format("%.3f", coefficients[1]) + " * ln(x)";
            default:
                return null;
        }
    }

    public FunctionType getType() {
        return type;
    }
}
