package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Graph;

public class SimilarityModel extends Model{

    SimilarityModel (Graph graph, double initialBias, double coefficient){
        super(graph, initialBias, coefficient);
    }

    @Override
    void iteration(int step) {

    }
}
