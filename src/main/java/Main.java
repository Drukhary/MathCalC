import drawer.ResultDrawer;
import math.Approximator;
import model.ApproxResult;
import model.FunctionType;
import model.Point;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner terminalScanner = new Scanner(System.in);
        FunctionType type = null;
        String line;
        System.out.println("Лабораторная работа №4. Аппроксимация методом наименьших квадратов\n" +
                "Задайте аппроксимирующую функцию\n" +
                "LINEAR - ЛИНЕЙНАЯ АППРОКСИМАЦИЯ (y = A * x + B)\n" +
                "QUADRATIC - КВАДРАТНАЯ АППРОКСИМАЦИЯ (y = A * x^2 + B * x + C)\n" +
                "EXPONENTIAL - ЭКСПОНЕНЦИАЛЬНАЯ АППРОКСИМАЦИЯ (y = A * exp(B * x)\n" +
                "LOGARITHMIC - ЛОГАРИФМИЧЕСКАЯ АППРОКСИМАЦИЯ (y = A + B * ln(x)");
        boolean flag = true;
        while (flag) {
            line = terminalScanner.nextLine().trim().toUpperCase();
            try{
                type = FunctionType.valueOf(line);
                flag=false;
            } catch (Exception e){
                System.out.println("Тип введен неверно. Возможные варианты: LINEAR, QUADRATIC, EXPONENTIAL, LOGARITHMIC");
            }
        }
        System.out.println("Введите количество точек, которые будут участовать. Как минимум 4");
        boolean flag1 = false;
        Integer amountOfPoints = null;
        while (!flag1) {
            try {
                amountOfPoints = Integer.parseInt(terminalScanner.nextLine().trim());
                if (amountOfPoints >= 4) {
                    flag1 = true;
                } else {
                    System.out.println("Это должно быть целое число большее 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Это должно быть целое число большее 3.");
            }
        }
        double x, y;
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < amountOfPoints; i++) {
            System.out.println("Введите координату X точки №" + (i + 1));
            if (type == FunctionType.LOGARITHMIC) {
                do {
                    System.out.println("Координата X должна быть строго больше нуля");
                    x = setCoordinate(terminalScanner);
                }
                while (x <= 0);
            } else {
                x = setCoordinate(terminalScanner);
            }
            System.out.println("Введите координату Y точки №" + (i + 1));
            if (type == FunctionType.EXPONENTIAL) {
                do {
                    System.out.println("Координата Y должна быть строго больше нуля");
                    y = setCoordinate(terminalScanner);
                }
                while (y <= 0);
            } else {
                y = setCoordinate(terminalScanner);
            }
            Point point = new Point(x, y);
            points.add(point);
        }

        ApproxResult result = Approximator.approximate(type, points);
        if (result != null) {
            System.out.println(result.toString());

            ResultDrawer drawer = new ResultDrawer();
            drawer.drawResult(result);
        } else {
            System.out.println("Аппроксимировать невозможно");
        }

    }


    private static Double setCoordinate(Scanner terminalScanner) {
        Double value = null;
        while (true) {
            try {
                value = terminalScanner.nextDouble();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Это должно быть число.");
            }
        }
    }
}
