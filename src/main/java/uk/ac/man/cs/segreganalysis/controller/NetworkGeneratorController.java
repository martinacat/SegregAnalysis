package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.MainWindow;

import javax.swing.*;

public class NetworkGeneratorController {
    private MainWindow view;


    public enum GeneratorType {
        PREFERENTIAL_ATTACHMENT, RANDOM, RANDOM_EUCLIDEAN
    }

    public NetworkGeneratorController(MainWindow v) {
        view = v;
    }

    public Graph generate(Graph graph) {
        Generator gen;
        GeneratorType generatorType = GeneratorType.values()[view.getGeneratorDropdown().getSelectedIndex()];
        int numberOfNodes = 0;

        if (StringUtils.isNumeric(view.getSizeText().getText())) {
            numberOfNodes = Integer.parseInt(view.getSizeText().getText());
        } else {
            JOptionPane.showMessageDialog(view, "Please insert a numeric value for \'Graph size\'");
        }

        switch (generatorType){
            case PREFERENTIAL_ATTACHMENT:
                SegregAnalysis.logger.info("Generator type: PREFERENTIAL_ATTACHMENT");
                int maxLinksPerStep = Integer.parseInt(view.getMaxLinksPerStepText().getText());
                gen = new BarabasiAlbertGenerator(maxLinksPerStep);
                break;
            case RANDOM:
                SegregAnalysis.logger.info("Generator type: RANDOM");
                gen = new RandomGenerator(4);
                break;
            case RANDOM_EUCLIDEAN:
                SegregAnalysis.logger.info("Generator type: RANDOM_EUCLIDEAN");
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
