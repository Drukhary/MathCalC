package drawer;

import model.Function;
import model.Point;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ResultDrawer {

    private static final double STEPS = 1000;

    public void drawResult(ArrayList<Function> result) {
        // Create Chart
//        XYChart chart = new XYChartBuilder().width(800).height(600).title("Approximation "+'('+result.getType()+')').xAxisTitle("X").yAxisTitle("Y").build();
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Approximation").xAxisTitle("X").yAxisTitle("Y").build();
        // Customize Chart
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setLegendFont(new Font("Helvetica Neue Cyr", Font.ITALIC, 12));

        // Annotation
        //chart.addAnnotation(new AnnotationTextPanel(result.toString(), 800, -100, true));

        // Set intervals
        double left = 1e10;
        double right = -1e10;
        for (Point point : result.get(0).getPoints()) {
            if (point.getX() < left) left = point.getX();
            if (point.getX() > right) right = point.getX();
        }
        double extra = (right - left) / 10;
        left -= extra;
//        if (result.getType().equals(Function.LOGARITHMIC.getType()) && left <= 0) left = 1e-3;
        right += extra;

        // Series
        for (Function function : result) {
            XYSeries functionSeries = chart.addSeries("I y = " + function.getNoteFunction(),
                    createXSeries(left, right), createYSeries(function, left, right));
            functionSeries.setLineWidth(2f);
        }


        // Points
        for (int i = 0; i < result.get(0).getPoints().size(); i++) {
            XYSeries series = chart.addSeries("Point" + i,
                    Collections.singletonList(result.get(0).getPoints().get(i).getX()),
                    Collections.singletonList(result.get(0).getPoints().get(i).getY()));
            series.setMarker(SeriesMarkers.DIAMOND);
            series.setMarkerColor(Color.RED);
            series.setShowInLegend(false);
        }
        //Show chart
        new SwingWrapper(chart).displayChart();
    }

    private static ArrayList<Double> createXSeries(double left, double right) {
        double step = (right - left) / STEPS;
        double x = left;
        ArrayList<Double> xs = new ArrayList<>();
        while (x < right) {
            xs.add(x);
            x += step;
        }
        return xs;
    }

    private static ArrayList<Double> createYSeries(Function function, double left, double right) {
        double step = (right - left) / STEPS;
        double x = left;
        ArrayList<Double> ys = new ArrayList<>();
        while (x < right) {
            ys.add(function.value(x));
            x += step;
        }
        return ys;
    }
}

