package model;

import java.util.ArrayList;

public interface ApproxTypeInterface {

    double value(double argument);
    void calculateCoefficient(ArrayList<Point> points);
    String getNoteFunction();
    void approximate();
}
