package model;

import lombok.Getter;

public class Point {
    @Getter
    private final Double x;
    @Getter
    private final Double y;

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
