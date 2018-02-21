package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.file.FileSinkDOT;
import org.graphstream.stream.file.FileSinkGML;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.models.*;
import uk.ac.man.cs.segreganalysis.models.indices.DuncanSegregationIndex;
import uk.ac.man.cs.segreganalysis.models.indices.YulesQIndex;
import uk.ac.man.cs.segreganalysis.view.XYChart;
import uk.ac.man.cs.segreganalysis.view.View;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;

public class SimulationController {
    private View view;
    private Graph graph;


    public SimulationController(View v){

        view = v;
        view.getApplyButton().addActionListener(new Start(view, graph));
        AttributesController attributesController = new AttributesController(view);
        FileBrowserController fileBrowserController = new FileBrowserController(view);
        AversionBiasController aversionBiasController = new AversionBiasController(view);


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

}
