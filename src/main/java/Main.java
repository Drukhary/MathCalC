import drawer.ResultDrawer;
import lombok.Data;
import model.Function;
import model.Point;
import util.InputTable;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Лабораторная работа №4. Аппроксимация методом наименьших квадратов\n");
        InputTable.approximateTableFunction();
    }



}
