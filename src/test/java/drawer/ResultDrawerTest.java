package drawer;

import com.sun.org.apache.bcel.internal.generic.FNEG;
import model.Function;
import model.FunctionFactory;
import model.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

public class ResultDrawerTest {
    @Test
    public void getAllUsers() {
        //создаем тестовые данные
        Scanner terminalScanner = new Scanner(System.in);
        ArrayList<Point> points = new ArrayList<>();
//        points.add(new Point(1.2d, 7.4d));
//        points.add(new Point(2.9d, 9.5d));
//        points.add(new Point(4.1d, 11.1d));
//        points.add(new Point(5.5d, 12.9d));
//        points.add(new Point(6.7d, 14.6d));
//        points.add(new Point(7.8d, 17.3d));
//        points.add(new Point(9.2d, 18.2d));
//        points.add(new Point(10.3d, 20.7d));
        points.add(new Point(1.1d, 3.5d));
        points.add(new Point(2.3d, 4.1d));
        points.add(new Point(3.7d, 5.2d));
        points.add(new Point(4.5d, 6.9d));
        points.add(new Point(5.4d, 8.3d));
        points.add(new Point(6.8d, 14.8d));
        points.add(new Point(7.5d, 21.2d));
//        points.add(new Point(-3d, -3d));
//        points.add(new Point(-3d, 3d));
//        points.add(new Point(3d, -3d));
//        points.add(new Point(3d, 3d));
        double minDeviation=Double.MAX_VALUE;
        Function theFunction = Function.LINEAR;
        Function[] functions = new Function[]{Function.LINEAR,Function.QUADRATIC,Function.LOGARITHMIC, Function.EXPONENTIAL, Function.POWERFUL};
        for (Function function: functions){
            function.setPoints(points);
            function.approximate();
            if (minDeviation>function.getDeviation()){
                theFunction = function;
                minDeviation=theFunction.getDeviation();
            }
        }
        if (theFunction.isFoundCoefficients()) {
            System.out.println(theFunction.toString());

            ResultDrawer drawer = new ResultDrawer();
            drawer.drawResult(theFunction);
        } else {
            System.out.println("Аппроксимировать невозможно. А ЖАЛЬ");
        }
        int i=0;
        i = terminalScanner.nextInt();
    }
    @Test
    public void test(){
        Scanner terminalScanner = new Scanner(System.in);
        ArrayList<Point> tableFunction = FunctionFactory.createTableFunction(1.,10,8,40);
        double minDeviation=Double.MAX_VALUE;
        Function theFunction = Function.LINEAR;
        Function[] functions = new Function[]{Function.LINEAR,Function.QUADRATIC,Function.LOGARITHMIC, Function.EXPONENTIAL, Function.POWERFUL};
        for (Function function: functions){
            function.setPoints(tableFunction);
            function.approximate();
            if (minDeviation>function.getDeviation()){
                theFunction = function;
                minDeviation=theFunction.getDeviation();
            }
        }
        if (theFunction.isFoundCoefficients()) {
            System.out.println(theFunction.toString());

            ResultDrawer drawer = new ResultDrawer();
            drawer.drawResult(theFunction);
        } else {
            System.out.println("Аппроксимировать невозможно. А ЖАЛЬ");
        }
        int i=0;
        i = terminalScanner.nextInt();
    }
}