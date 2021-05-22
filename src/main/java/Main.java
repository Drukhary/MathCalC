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
        Integer amount_of_points = null;
        while (!flag1) {
            try {
                amount_of_points = Integer.parseInt(terminalScanner.nextLine().trim());
                if (amount_of_points >= 4) {
                    flag1 = true;
                } else {
                    System.out.println("Это должно быть целое число большее 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Это должно быть целое число большее 3.");
            }
        }
        Double x, y;
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < amount_of_points; i++) {
            System.out.println("Введите координату X точки №" + (i + 1));
            if (type == FunctionType.LOGARITHMIC) {
                x = -1.0;
                while (x <= 0) {
                    if (x <= 0) System.out.println("Координата X должна быть больше нуля");
                    x = setCoordinate(terminalScanner);
                }
            } else {
                x = setCoordinate(terminalScanner);
            }
            System.out.println("Введите координату Y точки №" + (i + 1));
            if (type == FunctionType.EXPONENTIAL) {
                y = -1.0;
                while (y <= 0) {
                    if (y <= 0) System.out.println("Координата Y должна быть больше нуля");
                    y = setCoordinate(terminalScanner);
                }
            } else {
                y = setCoordinate(terminalScanner);
            }
            Point point = new Point(x, y);
            points.add(point);
        }

        Approximator approximator = new Approximator();
        ApproxResult result = approximator.approximate(type, points);
        if (result != null) {
            System.out.println(result.toString());

            ResultDrawer drawer = new ResultDrawer();
            drawer.drawResult(result);
        } else {
            System.out.println("Аппроксимировать невозможно. АШИБКА");
        }

    }


    private static Double setCoordinate(Scanner terminalScanner) {
        boolean flag2 = false;
        Double coord = null;
        while (!flag2) {
            try {
                coord = Double.parseDouble(terminalScanner.nextLine().trim().replace(",", "."));
                flag2 = true;
            } catch (NumberFormatException e) {
                System.out.println("Это должно быть число.");
            }
        }
        return coord;
    }
}
