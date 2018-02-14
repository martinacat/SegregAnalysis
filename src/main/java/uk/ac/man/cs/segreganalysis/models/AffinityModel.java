package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class AffinityModel extends Model{

    public AffinityModel(Graph graph, double initialBias, double coefficient){
        super(graph, initialBias, coefficient);
    }

    @Override
    public void iteration(int step) {
        // choose two random nodes
        Node n1 = getRandomNode();
        Node n2 = getRandomNode();

        // assess whether they will connect or not
        if (areToBeConnected(n1, n2)) {

            // drop one random link if they connected
            graph.removeEdge(getRandomEdge());

            rewire(n1, n2);


        }


    }

    @Override
    void rewire(Node n1, Node n2) {

        if (n1.hasEdgeBetween(n2)) {
            // the nodes are already connected
            return;
        }
        else {
            graph.addEdge(n1.getId() + "_" + n2.getId(), n1, n2);
        }

        return;
    }

    private boolean areToBeConnected(Node n1, Node n2) {
        return (Math.random() <= probabilityOfConnection(n1, n2));
    }


    private double probabilityOfConnection(Node n1, Node n2) {
        return calculateAttributeDistance(n1,n2) * bias;
    }
}
