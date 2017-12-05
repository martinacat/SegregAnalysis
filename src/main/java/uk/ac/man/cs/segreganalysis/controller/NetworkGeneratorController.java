package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.View;

import javax.swing.*;
import java.util.Iterator;

public class NetworkGeneratorController {
    private View view;


    public enum GeneratorType {
        GRID, PREFERENTIAL_ATTACHMENT, RANDOM, RANDOM_EUCLIDEAN
    }

    public NetworkGeneratorController(View v) {
        view = v;
    }

    public Graph generate(Graph graph) {
        Generator gen;
        GeneratorType generatorType = GeneratorType.values()[view.generatorDropdown.getSelectedIndex()];
        int numberOfNodes = 0;

        if (StringUtils.isNumeric(view.sizeText.getText())) {
            numberOfNodes = Integer.parseInt(view.sizeText.getText());
        } else {
            JOptionPane.showMessageDialog(view, "Please insert a numeric value for \'Graph size\'");
        }

        switch (generatorType){
            case PREFERENTIAL_ATTACHMENT:
                int maxLinksPerStep = Integer.parseInt(view.maxLinksPerStepText.getText());
                gen = new BarabasiAlbertGenerator(maxLinksPerStep);
                break;
            case RANDOM:
                gen = new RandomGenerator(4);
                break;
            case RANDOM_EUCLIDEAN:
                gen = new RandomEuclideanGenerator(1);
                break;
            default:
                gen = new RandomGenerator(4);
                break;
        }

        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < numberOfNodes; i++)
            gen.nextEvents();
        gen.end();

        return graph;

    }


}
