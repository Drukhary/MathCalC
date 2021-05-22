package model;

import java.util.ArrayList;
import java.util.Collections;

public class ApproxResult {
    private ArrayList<Function> functions;
    private ArrayList<Point> points;

    public ApproxResult(ArrayList<Function> functions, ArrayList<Point> points) {
        this.functions = functions;
        this.points = points;
    }

    @Override
    public String toString() {
        return "Точки, участвующие в аппроксимации: " + Collections.singletonList(getPoints()) + "\n" +
                "Полученная функция: y = " + getFunctions().get(0).toString() + "\n" +
                "Новая полученная функция: y = " + getFunctions().get(1).toString() + "\n" +
                "Точка с наибольшим отклонением: " + getPoints().get(getPoints().size() - 1) + "\n" +
                "Вид аппроксимирующей функции: " + getFunctions().get(1).getType();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }
}