package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.models.AffinityModel;
import uk.ac.man.cs.segreganalysis.models.AversionModel;
import uk.ac.man.cs.segreganalysis.models.Flags;
import uk.ac.man.cs.segreganalysis.models.Model;
import uk.ac.man.cs.segreganalysis.models.indices.DuncanSegregationIndex;
import uk.ac.man.cs.segreganalysis.models.indices.YulesQIndex;
import uk.ac.man.cs.segreganalysis.utilities.GraphUtilities;
import uk.ac.man.cs.segreganalysis.view.MainWindow;
import uk.ac.man.cs.segreganalysis.view.XYChart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Start implements ActionListener {
    private MainWindow view;
    private Graph graph;

    // fields of the view
    private int steps = 0;
    Start(MainWindow view) {
        this.view = view;

    }

    public void actionPerformed( ActionEvent event ) {

        if (event.getSource() == view.getApplyButton()) {

            graph = new SingleGraph("Network");
            graph.addAttribute("ui.quality");
            graph.addAttribute("ui.antialias");

            getParametersFromView();
            start();
            GraphUtilities.saveGraph(graph, "final_graph.gml");

            // All code inside SwingWorker runs on a seperate thread
            SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
                @Override
                public Integer doInBackground() {
                    return (Integer) GraphUtilities.displayLinLogLayout("final_graph.gml");
                }
            };

            worker.execute();


        }
    }

    private void getParametersFromView() {

        // if fromFileRadioButton is selected
        if (view.getFromFileRadioButton().isSelected()) {
            if (view.getFileBrowseField().getText().equals("")) {
                SegregAnalysis.logger.error("No file selected");
                JOptionPane.showMessageDialog(view, "Please select a .dsg file");

            }

            SegregAnalysis.logger.info("Reading network from file " + view.getFileBrowseField().getText());
            String filePath = view.getFileBrowseField().getText();

            FileSource fs = null;
            try {
                fs = FileSourceFactory.sourceFor(filePath);
                fs.addSink(graph);
                fs.readAll(filePath);
            } catch( IOException e) {
                SegregAnalysis.logger.error("Problem reading file");
            } finally {
                fs.removeSink(graph);
            }

            for (Node node : graph) {
                node.addAttribute("ui.label", node.getId());
            }

        } else {
            SegregAnalysis.logger.info("Generating a network");
            NetworkGeneratorController networkGeneratorController = new NetworkGeneratorController(view);
            graph = networkGeneratorController.generate(graph);
        }

        AttributesController attributesController = new AttributesController(view);
        attributesController.initialiseAttributes(graph);




        if (StringUtils.isNumeric(view.getStepsText().getText())) {
            steps = Integer.parseInt(view.getStepsText().getText());
        } else {
            JOptionPane.showMessageDialog(view, "Please insert a numeric value for Steps");
        }


    }

    private void start() {

        // Bias: direction and function
        Flags.Direction direction;
        Flags.Function function;
        Flags.Algorithm algorithm;

        graph.addAttribute("ui.stylesheet", "url('./stylesheet.css')");

        boolean showDuncan = view.getCheckBoxDSI().isSelected();
        boolean showYulesQ = view.getCheckBoxYules().isSelected();


        // segregation index
        DuncanSegregationIndex DSIndex = new DuncanSegregationIndex(graph);
        YulesQIndex yulesQIndex = new YulesQIndex(graph);

        // initial aversion bias
        double aversionBias = 0;
        if (view.biasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
            aversionBias =
                    Double.parseDouble(this.view.biasAdvancedSettings
                            .getInitialBiasForAllText().getText());
        }

        // coefficient
        double coefficient =
                Double.parseDouble(this.view.biasAdvancedSettings.getCoefficientText().getText());

        // instantiate model
        Model[] interleavingModels = new Model[2];

        if (view.getAlgorithmDropdown().getSelectedItem() == "Dissimilarity") {
            algorithm = Flags.Algorithm.DISSIMILARITY;
            SegregAnalysis.logger.info(view.getAlgorithmDropdown().getSelectedItem() + " selected");
            Model aversionModel = new AversionModel(graph, aversionBias, coefficient);
            interleavingModels[0] = aversionModel;
            interleavingModels[1] = aversionModel;
        }
        else if (view.getAlgorithmDropdown().getSelectedItem() == "Affinity"){
            algorithm = Flags.Algorithm.AFFINITY;
            SegregAnalysis.logger.info(view.getAlgorithmDropdown().getSelectedItem() + " selected");
            Model affinityModel = new AffinityModel(graph, aversionBias, coefficient);
            interleavingModels[0] = affinityModel;
            interleavingModels[1] = affinityModel;
        }
        else {
            algorithm = Flags.Algorithm.BOTH;
            SegregAnalysis.logger.info("Interleaving Affinity and Dissimilarity models");
            interleavingModels[0] = new AversionModel(graph, aversionBias, coefficient);
            interleavingModels[1] = new AffinityModel(graph, aversionBias, coefficient);
        }



        String growthOrDecay = (String) this.view.biasAdvancedSettings
                .getBiasEvolutionInTimeDropdown().getSelectedItem();
        String functionFromDropdown = (String) this.view.biasAdvancedSettings
                .getBiasEvolutionFunctionDropdown().getSelectedItem();

        SegregAnalysis.logger.info("Bias evolution in time: " + growthOrDecay);

        if (functionFromDropdown.equals("None")) {
            // don't care about direction if function is none
            direction = Flags.Direction.NONE;
            function =  Flags.Function.NONE;
        } else {

            if (growthOrDecay.equals("Decay")) {
                direction = Flags.Direction.DECAY;
            } else {
                direction = Flags.Direction.GROWTH;
            }

            if (functionFromDropdown.equals("Linear")) {
                function = Flags.Function.LINEAR;
            } else {
                function = Flags.Function.CURVE;
            }
        }

        Flags flags = new Flags(direction, function, algorithm);

        // dataset for plotting graph jfree
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        final XYSeries duncan = new XYSeries( "DSI" );
        final XYSeries yules = new XYSeries( "Yule's Q" );

        // plot bias
        final XYSeries biasPlot = new XYSeries("Bias");
        if (algorithm == Flags.Algorithm.DISSIMILARITY) {
            biasPlot.setDescription("Aversion bias");
        }
        else if (algorithm == Flags.Algorithm.AFFINITY) {
            biasPlot.setDescription("Attachment bias" );
        }
        else if (algorithm == Flags.Algorithm.BOTH) {
            biasPlot.setDescription("Aversion/Attachment bias" );
        }


        // iterations of the algorithm
        for (int i = 0; i < steps; i++) {

            biasPlot.add(i, interleavingModels[i%2].getBias());
            if (showDuncan) {
                duncan.add(i, DSIndex.calculate());
            }

            if (showYulesQ) {
                yules.add(i, yulesQIndex.movingAverage());
            }

            interleavingModels[i%2].iteration(i);

        }


        if (showDuncan) { dataset.addSeries(duncan); }
        if (showYulesQ) { dataset.addSeries(yules); }

        dataset.addSeries(biasPlot);

        XYChart chart = new XYChart("Segregation Emergence Statistics",
                "", dataset);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

    }


}
