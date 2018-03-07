package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/*  Based on the model broposed in
    A. D. Henry, P. Pralat, C. Zhang, "Emergence of segregation in evolving social networks"
 */
public class AversionModel extends Model{

    public AversionModel(Graph graph, double initialAversionBias, double coefficient, int totalSteps){
        super(graph, initialAversionBias, coefficient, totalSteps);
    }

    public AversionModel(Graph graph, int totalSteps, double startBias, double endBias){
        super(graph, totalSteps, startBias, endBias);
    }

    @Override
    public void iteration(int step) {

        // choose random edge uv
        Edge edge = getRandomEdge();

        // choose: delete or keep based on attribute distance
        if (isToBeDeleted(edge)) {

            Node n0 = edge.getNode0();
            Node n1 = edge.getNode1();

            graph.removeEdge(edge);
            rewire(n0, n1);
        }

        recalculateBias(step);


    }




    // rewires one of the two nodes given with a random node
    @Override
    void rewire(Node n0, Node n1) {
        Node randomNode = getRandomNode();

        if (Math.random() > 0.5) { // rewire n0
            while (randomNode.hasEdgeBetween(n0)) {
                randomNode = getRandomNode();
            }
            graph.addEdge(n0.getId() + "_" + randomNode.getId(), n0, randomNode);

        }
        else { // rewire n1
            while (randomNode.hasEdgeBetween(n1)) {
                randomNode = getRandomNode();
            }
            graph.addEdge(n1 + "_" + randomNode, n1, randomNode);

        }

    }

    private boolean isToBeDeleted(Edge edge) {
        // todo gaussian distribution?
        return (Math.random() <= probabilityOfDeletion(edge)) ;
    }

    private double probabilityOfDeletion(Edge edge) {

        return calculateAttributeDistance(edge) * bias;
    }




}
