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
    View view;
    Graph graph;

    // fields of the view
    int steps = 0;


    public SimulationController(View v){

        view = v;
        view.applyButton.addActionListener(this);
        FileBrowserController fileBrowserController = new FileBrowserController(view);


        // adapt view to algorithm
        view.generatorDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (view.generatorDropdown.getSelectedIndex() != 0) {
                    view.maxLinksPerStepLabel.setVisible(false);
                    view.maxLinksPerStepText.setVisible(false);
                }
                else {
                    view.maxLinksPerStepLabel.setVisible(true);
                    view.maxLinksPerStepText.setVisible(true);
                }
                view.maxLinksPerStepText.revalidate();
            }}
        );
    }

    public void actionPerformed( ActionEvent e ) {

        if (e.getSource() == view.applyButton) {
            graph = new SingleGraph("Network");
            getParametersFromView();
            start();
            graph = null;
        }
    }

    private void getParametersFromView() {

        // if no file is selected, generated random graph
        if (view.fileBrowseField.getText().endsWith(".dgs")) {
            SegregAnalysis.logger.info("Reading network from file " + view.fileBrowseField.getText());
            String filePath = view.fileBrowseField.getText();

            FileSource fs = null;
            try {
                fs = FileSourceFactory.sourceFor(filePath);
                fs.addSink(graph);
                fs.readAll(filePath);

            } catch( IOException e) {


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




        if (StringUtils.isNumeric(view.stepsText.getText())) {
            steps = Integer.parseInt(view.stepsText.getText());
        } else {
            JOptionPane.showMessageDialog(view, "Please insert a numeric value for Steps");
        }


    }

    private void start() {
        graph.addAttribute("ui.stylesheet", "url('./stylesheet.css')");

        // segregation index
        DuncanSegregationIndex DSIndex = new DuncanSegregationIndex(graph);
        YulesQIndex yulesQIndex = new YulesQIndex(graph);

        // iterative model
        HenryModel henryModel = new HenryModel(graph, view);

        // dataset for plotting graph jfree
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        final XYSeries duncan = new XYSeries( "DSI" );
        final XYSeries yules = new XYSeries( "Yule's Q" );


        // iterations of the algorithm
        for (int i = 0; i < steps; i++) {
            duncan.add(i, DSIndex.calculate());
            yules.add(i, yulesQIndex.movingAverage());
            henryModel.iteration();
        }

        dataset.addSeries(duncan);
        dataset.addSeries(yules);

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
