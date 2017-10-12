package uk.ac.man.cs.segreganalysis;

import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import uk.ac.man.cs.segreganalysis.models.HenryModel;
import uk.ac.man.cs.segreganalysis.view.View;


public class SegregAnalysis {

    public enum GeneratorType {
        GRID, PREFERENTIAL_ATTACHMENT, RANDOM, RANDOM_EUCLIDEAN
    }

    public static void main(String args[]) {
        View frame = new View("SegregAnalysis");
    }


    public SegregAnalysis(int numberOfNodes, GeneratorType generator, int steps ) {


        Graph graph = new SingleGraph("Network");
        Generator gen;

        switch (generator){
            case GRID:
                gen = new GridGenerator();
                break;
            case PREFERENTIAL_ATTACHMENT:
                // Between 1 and 3 new links per node added.
                gen = new BarabasiAlbertGenerator(3);
                break;
            case RANDOM:
                gen = new RandomGenerator();
                break;
            case RANDOM_EUCLIDEAN:
                gen = new RandomEuclideanGenerator();
                break;
            default:
                gen = new BarabasiAlbertGenerator();
                break;
        }

        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < numberOfNodes; i++)
            gen.nextEvents();
        gen.end();

        HenryModel henryModel = new HenryModel(graph);

        // iterations of the algorithm
        int i = steps;
        while (i > 0) {
            henryModel.iteration();
            i--;
        }

        graph.display();

    }

}
