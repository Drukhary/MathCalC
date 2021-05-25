package util;

import drawer.ResultDrawer;
import model.Function;
import model.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class InputTable {
    private static ArrayList<Point> inputFromKeyboard(Scanner terminalScanner) {
        ArrayList<Point> resultTable = new ArrayList<>();
        boolean flag1 = false;
        Integer amountOfPoints = null;
        System.out.println("Введите количество точек, которые будут участовать. Как минимум 4");
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
        for (int i = 0; i < amountOfPoints; i++) {
            System.out.println("Введите координату X точки №" + (i + 1));
            x = setCoordinate(terminalScanner);
            System.out.println("Введите координату Y точки №" + (i + 1));
            y = setCoordinate(terminalScanner);
            Point point = new Point(x, y);
            resultTable.add(point);
        }
        return resultTable;
    }

    private static ArrayList<Point> inputFromFile(File file) throws FileNotFoundException {
        ArrayList<Point> resultTable = new ArrayList<>();
        Scanner sc = new Scanner(file);
        int amountOfPoint = sc.nextInt();
        for (int i = 0; i < amountOfPoint; i++) {
            resultTable.add(new Point(sc.nextDouble(),
                    sc.nextDouble()));
        }
        return resultTable;
    }


    private static Double setCoordinate(Scanner terminalScanner) {
        while (true) {
            try {
                return terminalScanner.nextDouble();
            } catch (NumberFormatException e) {
                System.out.println("Это должно быть число.");
            }
        }
    }

    public static void approximateTableFunction() throws FileNotFoundException {
        Scanner terminalScanner = new Scanner(System.in);
        String line;
        do {
            System.out.println("Введите метод ввода из файла или с клавиатуры(f/k)");
            line = terminalScanner.nextLine();
        } while (!(line.equals("k") || line.equals("f")));

        ArrayList<Point> functionTable;
        if (line.equals("k"))
            functionTable = InputTable.inputFromKeyboard(terminalScanner);
        else {
            ApproximateFile approximateFile = ApproximateFile.LINEAR;
            functionTable = InputTable.inputFromFile(approximateFile.getFile());
        }
        double minDeviation = Double.MAX_VALUE;
        Function theFunction = Function.LINEAR;
        Function[] functions = new Function[]{Function.LINEAR, Function.QUADRATIC, Function.LOGARITHMIC, Function.EXPONENTIAL, Function.POWERFUL};
        ArrayList<Function> resultFunctions = new ArrayList<>();
        for (Function function : functions) {
            function.setPoints(functionTable);
            function.approximate();
            if (function.isFoundCoefficients()) {
                System.out.println(function.toString());
                resultFunctions.add(function);
            }
            if (minDeviation > function.getDeviation()) {
                theFunction = function;
                minDeviation = theFunction.getDeviation();
            }
        }
        if (theFunction.isFoundCoefficients()) {
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println(theFunction.toString());
            ResultDrawer drawer = new ResultDrawer();

            drawer.drawResult(resultFunctions);
            for (Function function:
                 resultFunctions) {
                ArrayList<Function> theFunctions = new ArrayList<>();
                theFunctions.add(function);
                drawer = new ResultDrawer();
                drawer.drawResult(theFunctions);
            }
        } else {
            System.out.println("Аппроксимировать невозможно. А ЖАЛЬ");
        }
        System.out.println("Введите команду");
        line = terminalScanner.nextLine();
    }
}
