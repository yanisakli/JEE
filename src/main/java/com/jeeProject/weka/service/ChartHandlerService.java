package com.jeeProject.weka.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChartHandlerService extends Application {

    public static String mode = "";
    public static HashMap<String,Double> listValued = new HashMap<>();

    public void start(Stage stage) {
        switch (mode) {
            case "PieChart":
                pieChart(stage);
                break;
            case "LineChart":
                lineChart();
                break;
        }
    }


    private void pieChart(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setWidth(500);
        stage.setHeight(500);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Correctly Instance", 96.6667),
                        new PieChart.Data("Incorrectly Instances", 3.3333));
        for (Map.Entry<String, Double> value : listValued.entrySet()) {
            pieChartData.add(new PieChart.Data(value.getKey(), value.getValue()));
        }
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Evaluation on 30 instances");
        ((Group) scene.getRoot()).getChildren().add(chart);
        saveAsPng(scene, "../image.png");
        Platform.exit();
    }


    private void lineChart() {
        //defining the axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of model tested");
        //creating the chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Kappa Statistic on all model tested");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //populating the series with data
        int compteur = 0;
        for (Map.Entry<String, Double> value : listValued.entrySet()) {
            series.getData().add(new XYChart.Data(compteur, value.getValue()));
            compteur ++;
        }
        lineChart.getData().addAll(series);
        series.setName("Kappa stat");
        lineChart.setAnimated(false);
        Scene scene = new Scene(lineChart, 500, 500);
        saveAsPng(scene, "../image.png");
        Platform.exit();
    }


    private void saveAsPng(Scene scene, String path) {

        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
