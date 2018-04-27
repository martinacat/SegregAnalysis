package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Iterator;

public class AffinityModel extends Model{

    public AffinityModel(Graph graph, double initialBias, double coefficient, int totalSteps){
        super(graph, initialBias, coefficient, totalSteps);
    }
    public AffinityModel(Graph graph, int totalSteps, double startBias, double endBias){
        super(graph, totalSteps, startBias, endBias);
    }

    @Override
    public void iteration(int step) {
        // choose two random nodes
        Node n1 = getRandomNode();
        Node n2 = getRandomNode();

        // assess whether they will connect or not
        if (areToBeConnected(n1, n2)) {


            // delete one of the edges of n1 at random
            int degree = n1.getDegree();

            if (degree != 0) {
                int random = (int) (Math.random() * 100) % degree;
                Edge e = n1.getEdge(random);
                graph.removeEdge(e);
            } else {
                graph.removeEdge(getRandomEdge());
            }

            rewire(n1, n2);

        }

        recalculateBias(step);


    }

    @Override
    void rewire(Node n1, Node n2) {

        // connect if the nodes are not already connected
        if (!n1.hasEdgeBetween(n2)) {
            graph.addEdge(n1.getId() + "_" + n2.getId(), n1, n2);
        }

        return;
    }

    private boolean areToBeConnected(Node n1, Node n2) {
        return (!n1.hasEdgeBetween(n2) && Math.random() >= probabilityOfConnection(n1, n2));
    }


    private double probabilityOfConnection(Node n1, Node n2) {
        return calculateAttributeDistance(n1,n2) * bias;
    }
}
