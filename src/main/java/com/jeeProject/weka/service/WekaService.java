package com.jeeProject.weka.service;

import com.jeeProject.weka.ModelClassifier;
import com.jeeProject.weka.ModelGenerator;
import com.jeeProject.weka.model.Modele;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class WekaService {

    private final String DATASETPATH = "C:/Users/Shadow/Documents/ESGI/JEE/data/iris.2D.arff";
    private final String MODELPATH = "C:/Users/Shadow/Documents/ESGI/JEE/output/model.bin";

    private ModelGenerator modelGenerator;
    private Instances dataset;
    private Filter filter;
    private Instances datasetnor;
    private MultilayerPerceptron ann;
    private ModelClassifier classifier;

    private String classname;
    private int trainSize;
    private int testSize;
    private Instances traindataset;
    private Instances testdataset;

    public WekaService(Modele modele) throws Exception {
        this.setModelGenerator(new ModelGenerator());
        this.setDataset(this.getModelGenerator().loadDataset(modele.getFile()));
        this.setFilter(new Normalize());

        // divide data set to train data set 80% and test data set 20%
        this.setTrainSize((int) Math.round(this.getDataset().numInstances() * 0.8));
        this.setTestSize(this.getDataset().numInstances() - this.getTrainSize());

        //Normalize data set
        this.getFilter().setInputFormat(this.getDataset());
        this.setDatasetnor(Filter.useFilter(this.getDataset(), this.getFilter()));

        this.setTraindataset(new Instances(this.getDatasetnor(), 0, this.getTrainSize()));
        this.setTestdataset(new Instances(this.getDatasetnor(), this.getTrainSize(), this.getTestSize()));
    }

    public Modele ExecuteModel(Modele modele) throws Exception {

        // if you comment this line the accuracy of the model will be droped from 96.6% to 80%
        dataset.randomize(new Debug.Random(1));

        // build classifier with train dataset
        this.setAnn((MultilayerPerceptron) this.getModelGenerator().buildClassifier(this.getTraindataset()));

        // Evaluate classifier with test dataset
        String evalSummary = this.getModelGenerator().evaluateModel(this.getAnn(),
                this.getTraindataset(), this.getTestdataset());
        System.out.println("Evaluation: " + evalSummary);

        //Save model
        this.getModelGenerator().saveModel(this.getAnn(), modele.getFile());

        // Classify a single instance
        this.setClassifier(new ModelClassifier());
        this.setClassname(this.getClassifier().classifiy(Filter.useFilter(
                this.getClassifier().createInstance(1.6, 0.2, 0), this.getFilter()),
                MODELPATH));

        modele.setNb_instance((int) this.getModelGenerator().getNumInstances());
        modele.setCorrect_instances(this.getModelGenerator().getCorrect());
        modele.setIncorrect_instances(this.getModelGenerator().getIncorrect());

        System.out.println("\n The class name for the instance with \n\r" +
                "petallength = 1.6 and petalwidth =0.2 is  " + this.getClassname());

        return modele;
    }

    public ModelGenerator getModelGenerator() {
        return modelGenerator;
    }

    public void setModelGenerator(ModelGenerator modelGenerator) {
        this.modelGenerator = modelGenerator;
    }

    public Instances getDataset() {
        return dataset;
    }

    public void setDataset(Instances dataset) {
        this.dataset = dataset;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Instances getDatasetnor() {
        return datasetnor;
    }

    public void setDatasetnor(Instances datasetnor) {
        this.datasetnor = datasetnor;
    }

    public MultilayerPerceptron getAnn() {
        return ann;
    }

    public void setAnn(MultilayerPerceptron ann) {
        this.ann = ann;
    }

    public ModelClassifier getClassifier() {
        return classifier;
    }

    public void setClassifier(ModelClassifier classifier) {
        this.classifier = classifier;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getTrainSize() {
        return trainSize;
    }

    public void setTrainSize(int trainSize) {
        this.trainSize = trainSize;
    }

    public int getTestSize() {
        return testSize;
    }

    public void setTestSize(int testSize) {
        this.testSize = testSize;
    }

    public Instances getTraindataset() {
        return traindataset;
    }

    public void setTraindataset(Instances traindataset) {
        this.traindataset = traindataset;
    }

    public Instances getTestdataset() {
        return testdataset;
    }

    public void setTestdataset(Instances testdataset) {
        this.testdataset = testdataset;
    }
}
