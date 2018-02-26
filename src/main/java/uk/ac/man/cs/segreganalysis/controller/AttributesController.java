package uk.ac.man.cs.segreganalysis.controller;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import scala.util.parsing.combinator.testing.Str;
import uk.ac.man.cs.segreganalysis.utilities.RelativeSizeSplitter;
import uk.ac.man.cs.segreganalysis.view.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttributesController {
    public static List<String> attributeTypes = new ArrayList<String>();
    private String[] colours = {"red", "cyan", "green", "purple",
            "magenta", "orange", "yellow", "aquamarine", "blue"};

    private View view;
    private int numberOfAttributes;
    private int[] attributesDistribution;


    public AttributesController(View view) {
        this.view = view;
        String[] distrib = view.getAttributesDistributionText().getText().split(" ?, ?");
        this.numberOfAttributes = distrib.length;
        this.attributesDistribution = new int[numberOfAttributes];

        for (int i=0; i<distrib.length; i++) {
            attributesDistribution[i] = Integer.parseInt(distrib[i]);
        }

        attributeTypes.add("colour");
    }

    public void initialiseAttributes(Graph graph) {

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
    }

    private int[] calculateActualSetSizes(int numberOfNodes) {

        return RelativeSizeSplitter.calculateApproximateSize(numberOfNodes, attributesDistribution);
    }
}
