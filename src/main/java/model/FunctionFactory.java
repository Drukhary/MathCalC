package model;

import java.util.ArrayList;

import static java.lang.Math.E;
import static java.lang.Math.pow;

public class FunctionFactory {
    public static ArrayList<Point> createTableFunction
            (double left, double right, int n, double absoluteMaxDeviation){
        ArrayList<Point> resultTable = new ArrayList<>();
        ArrayList<Double> argTable = new ArrayList<>();
        double h = (right-left)/n;
        for (int i=0;i<n;i++){
            argTable.add(left+h*i);
        }
        for (double arg : argTable){
            resultTable.add(
                    new Point(arg,
                            function(arg)+(Math.random()-0.5)*absoluteMaxDeviation));
        }
        return resultTable;
    }
    private static double function(double arg){
//        return 0.5*arg*arg+3*arg-4;
        return 3*pow(E,arg);
    }
}
