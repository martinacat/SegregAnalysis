package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.view.Viewer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.models.Flags;
import uk.ac.man.cs.segreganalysis.models.HenryModel;
import uk.ac.man.cs.segreganalysis.models.indices.DuncanSegregationIndex;
import uk.ac.man.cs.segreganalysis.models.indices.YulesQIndex;
import uk.ac.man.cs.segreganalysis.view.View;
import uk.ac.man.cs.segreganalysis.view.XYChart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SimulationController implements ActionListener{
    private View view;
    private Graph graph;

    // fields of the view
    private int steps = 0;


    public SimulationController(View v){

        view = v;
        view.getApplyButton().addActionListener(this);
        FileBrowserController fileBrowserController = new FileBrowserController(view);
        AversionBiasController aversionBiasController = new AversionBiasController(view);



        // Action Listeners for dynamic view

        view.getAlgorithmDropdown().addActionListener((ActionEvent e) -> {
                    if (view.getAlgorithmDropdown().getSelectedItem() != "Dissimilarity") {
                        view.getAversionBiasAdvancedSettingsButton().setVisible(false);
                    }
                    else {
                        view.getAversionBiasAdvancedSettingsButton().setVisible(true);
                    }
                    view.getAversionBiasAdvancedSettingsButton().revalidate();
                }
        );

        // max link per step
        view.getGeneratorDropdown().addActionListener(e -> {
            if (view.getGeneratorDropdown().getSelectedItem() != "Preferential Attachment") {
                view.getMaxLinksPerStepLabel().setVisible(false);
                view.getMaxLinksPerStepText().setVisible(false);
            }
            else {
                view.getMaxLinksPerStepLabel().setVisible(true);
                view.getMaxLinksPerStepText().setVisible(true);
            }
            view.getMaxLinksPerStepText().revalidate();
        }
        );


    }

    public void actionPerformed( ActionEvent e ) {

        if (e.getSource() == view.getApplyButton()) {
            graph = new SingleGraph("Network");
            getParametersFromView();
            start();
            graph = null;
        }

        if (e.getSource() == view.getCheckBoxSave()) {
            SegregAnalysis.logger.info("Save pressed");
        }
    }

    private void getParametersFromView() {

        // if fromFileRadioButton is selected
        if (view.getFromFileRadioButton().isSelected()) {
            if (view.getFileBrowseField().getText().equals("")) {
                SegregAnalysis.logger.error("No .dsg file selected");
                JOptionPane.showMessageDialog(view, "Please select a .dsg file");

            }
            else if (!view.getFileBrowseField().getText().endsWith(".dgs")) {
                SegregAnalysis.logger.error("File selected is not the right format");
                JOptionPane.showMessageDialog(view, "File selected is not the right format," +
                        " please select a .dsg file");

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

        AttributesController.initialiseAttributes(graph);




        if (StringUtils.isNumeric(view.getStepsText().getText())) {
            steps = Integer.parseInt(view.getStepsText().getText());
        } else {
            JOptionPane.showMessageDialog(view, "Please insert a numeric value for Steps");
        }


    }

    private void start() {
        graph.addAttribute("ui.stylesheet", "url('./stylesheet.css')");

        boolean showDuncan = view.getCheckBoxDSI().isSelected();
        boolean showYulesQ = view.getCheckBoxYules().isSelected();


        // segregation index
        DuncanSegregationIndex DSIndex = new DuncanSegregationIndex(graph);
        YulesQIndex yulesQIndex = new YulesQIndex(graph);

        // iterative model
        // initial aversion bias

        double aversionBias = 0;
        if (view.aversionBiasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
            aversionBias =
                    Double.parseDouble(this.view.aversionBiasAdvancedSettings
                            .getInitialBiasForAllText().getText());
        }

        // coefficient
        double coefficient =
                Double.parseDouble(this.view.aversionBiasAdvancedSettings.getCoefficientText().getText());
        HenryModel henryModel = new HenryModel(graph, aversionBias, coefficient);

        // direction
        Flags.Direction direction = Flags.Direction.NONE;
        Flags.Function function = Flags.Function.NONE;

        String growthOrDecay = (String) this.view.aversionBiasAdvancedSettings
                .getBiasEvolutionInTimeDropdown().getSelectedItem();
        String functionFromDropdown = (String) this.view.aversionBiasAdvancedSettings
                .getBiasEvolutionFunctionDropdown().getSelectedItem();

        SegregAnalysis.logger.info("Bias evolution in time: " + growthOrDecay);

        if (functionFromDropdown.equals("None")) {
            // don't care about direction if function is none
            direction = Flags.Direction.NONE;
            function =  Flags.Function.NONE;
        } else {

            if (growthOrDecay.equals("Decay")) {
                direction = Flags.Direction.DECAY;
            } else { // Growth
                direction = Flags.Direction.GROWTH;
            }

            if (functionFromDropdown.equals("Linear")) {
                function = Flags.Function.LINEAR;
            } else {
                function = Flags.Function.CURVE;
            }
        }

        Flags flags = new Flags(direction, function);

        // dataset for plotting graph jfree
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        final XYSeries duncan = new XYSeries( "DSI" );
        final XYSeries yules = new XYSeries( "Yule's Q" );


        // iterations of the algorithm
        for (int i = 0; i < steps; i++) {

            if (showDuncan) {
                duncan.add(i, DSIndex.calculate());
            }

            if (showYulesQ) {
                yules.add(i, yulesQIndex.movingAverage());
            }

            henryModel.iteration(i);
        }

        if (showDuncan) {
            dataset.addSeries(duncan);
        }

        if (showYulesQ) {
            dataset.addSeries(yules);
        }

        XYChart chart = new XYChart("Segregation Emergence Statistics",
                "", dataset);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        // GraphStream
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);



    }
}
