import drawer.ResultDrawer;
import model.Function;
import model.Point;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner terminalScanner = new Scanner(System.in);
        Function type = null;
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
                type = Function.valueOf(line);
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
            if (type == Function.LOGARITHMIC) {
                do {
                    System.out.println("Координата X должна быть строго больше нуля");
                    x = setCoordinate(terminalScanner);
                }
                while (x <= 0);
            } else {
                x = setCoordinate(terminalScanner);
            }
            System.out.println("Введите координату Y точки №" + (i + 1));
            if (type == Function.EXPONENTIAL) {
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
        type.approximate();
        if (type.isFoundCoefficients()) {
            System.out.println(type.toString());

            ResultDrawer drawer = new ResultDrawer();
            drawer.drawResult(type);
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
