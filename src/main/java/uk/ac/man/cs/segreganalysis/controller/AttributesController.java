package uk.ac.man.cs.segreganalysis.controller;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.utilities.GraphUtilities;
import uk.ac.man.cs.segreganalysis.view.LinLogLayout;
import uk.ac.man.cs.segreganalysis.view.MainWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttributesController {
    public static List<String> attributeTypes = new ArrayList<String>();
    private String[] colours = {"red", "cyan", "green", "purple",
            "magenta", "orange", "yellow", "aquamarine", "blue"};

    private int numberOfAttributes;
    private int[] attributesDistribution;
    private MainWindow view;


    public AttributesController(MainWindow view) {
        this.view = view;
        if (view.getAttributesFromFileRadioButton().isSelected()) {
            return;
        }

        String[] distrib = view.getAttributesDistributionText().getText().split(" ?, ?");
        this.numberOfAttributes = distrib.length;
        this.attributesDistribution = new int[numberOfAttributes];

        for (int i=0; i<distrib.length; i++) {
            attributesDistribution[i] = Integer.parseInt(distrib[i]);
        }

        attributeTypes.add("colour");
    }

    public void initialiseAttributes(Graph graph) {

        if (view.getAttributesFromFileRadioButton().isSelected()) {
            
            SegregAnalysis.logger.info("Keeping attributes from file: " + view.getFileBrowseField().getText());
            // display
            new LinLogLayout(view.getFileBrowseField().getText());
            return;
        }

        int[] setSizes = calculateActualSetSizes(graph.getNodeCount());

        // initialise random attributes
        Iterator<Node> iterator = graph.iterator();

        while (iterator.hasNext()) {
            Node n;

            // the length of setSizes is the number of attribute sets
            for (int i = 0; i < setSizes.length; i++) {
                for (int j = 0; j < setSizes[i]; j++) {
                    n = iterator.next();
                    n.addAttribute(attributeTypes.get(0), colours[i]);
                    n.addAttribute("ui.class", colours[i]);
                }
            }
        }

        // save generated graph to a file
        GraphUtilities.saveGraph(graph, "initial_graph.gml");

        // display
        new LinLogLayout("initial_graph.gml");

    }

    private int[] calculateActualSetSizes(int numberOfNodes) {

        return GraphUtilities.calculateApproximateSize(numberOfNodes, attributesDistribution);
    }
}
