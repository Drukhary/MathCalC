package model;

import java.util.ArrayList;

public interface ApproxTypeInterface {

    double value(double argument);
    void CalculateCoefficient(ArrayList<Point> points);
}
