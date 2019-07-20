package com.jeeProject.weka;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelGenerator {

    private double numInstances;
    private double correct;
    private double incorrect;
    private double kappaStatistic;

    public Instances loadDataset(String path) {
        Instances dataset = null;
        try {
            dataset = DataSource.read(path);
            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataset;
    }

    public Classifier buildClassifier(Instances traindataset) {
        MultilayerPerceptron m = new MultilayerPerceptron();

        try {
            m.buildClassifier(traindataset);

        } catch (Exception ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    public String evaluateModel(Classifier model, Instances traindataset, Instances testdataset) {
        Evaluation eval = null;
        Evaluation evalBeta = null;
        try {
            // Evaluate classifier with test dataset
            eval = new Evaluation(traindataset);
            eval.evaluateModel(model, testdataset);

            evalBeta = new Evaluation(traindataset);
            evalBeta.evaluateModel(model, testdataset);
            evalBeta.toClassDetailsString();

            System.out.println("Number of instances: " + evalBeta.numInstances());
            System.out.println("Evaluation correct: " + evalBeta.correct());
            System.out.println("Evaluation incorrect: " + evalBeta.incorrect());
            System.out.println("Kappa statistic: " + evalBeta.kappa());

            this.numInstances = evalBeta.numInstances();
            this.correct = evalBeta.correct();
            this.incorrect = evalBeta.incorrect();
            this.kappaStatistic = evalBeta.kappa();
        } catch (Exception ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eval.toSummaryString("", true);
    }

    public void saveModel(Classifier model, String modelpath) {

        try {
            SerializationHelper.write(modelpath, model);
        } catch (Exception ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getNumInstances() {
        return numInstances;
    }

    public void setNumInstances(double numInstances) {
        this.numInstances = numInstances;
    }

    public double getCorrect() {
        return correct;
    }

    public void setCorrect(double correct) {
        this.correct = correct;
    }

    public double getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(double incorrect) {
        this.incorrect = incorrect;
    }

    public double getKappaStatistic() {
        return kappaStatistic;
    }

    public void setKappaStatistic(double kappaStatistic) {
        this.kappaStatistic = kappaStatistic;
    }
}
