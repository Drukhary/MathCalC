package drawer;

import math.Approximator;
import model.ApproxResult;
import model.FunctionType;
import model.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ResultDrawerTest {
    @Test
    public void getAllUsers() {
        //создаем тестовые данные
        Scanner terminalScanner = new Scanner(System.in);
        FunctionType type = FunctionType.QUADRATIC;
        ArrayList<Point> points = new ArrayList<>();
//        points.add(new Point(0d, -1.7d));
//        points.add(new Point(3.4d, 0d));
//        points.add(new Point(9.12, 2.84));
//        points.add(new Point(13.50, 5.3));
        points.add(new Point(1d, 1d));
        points.add(new Point(2d, 3d));
        points.add(new Point(3d, 4d));
        points.add(new Point(5d, 8d));
        Approximator approximator = new Approximator();
        ApproxResult result = Approximator.approximate(type, points);
        if (result != null) {
            System.out.println(result.toString());

            ResultDrawer drawer = new ResultDrawer();
            drawer.drawResult(result);
        } else {
            System.out.println("Аппроксимировать невозможно. АШИБКА");
        }
        int i=0;
        i = terminalScanner.nextInt();
    }
}