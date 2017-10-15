package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.*;

import java.util.UUID;


public class HenryModel {

    Graph graph;

    public HenryModel(Graph g){
        graph = g;
    }

    public void iteration() {

        // choose random edge uv
        Edge edge = getRandomEdge();

        // assess attribute distance d(u,v)
        double attributeDistance = calculateAttributeDistance(edge);

        // choose: delete or keep
        if (isToBeDeleted(attributeDistance)) {

            int n0 = edge.getNode0().getIndex();
            int n1 = edge.getNode1().getIndex();
            graph.removeEdge(edge);
            rewire(n0, n1);
        }


    }

    private Edge getRandomEdge() {
        int numberOfEdges = this.graph.getEdgeCount();
        int randomEdge = (int)(Math.random()*100) % numberOfEdges;
        return graph.getEdge(randomEdge);
    }

    private int getRandomNodeIndex() {
        int numberOfNodes = this.graph.getNodeCount();
        return (int)(Math.random()*100) % numberOfNodes;
    }


    private void rewire(int n0, int n1) {
        int randomNode = getRandomNodeIndex();

        // todo check if String identifier is okay this way
        if (Math.random() > 0.5) {
            while (graph.getNode(randomNode).hasEdgeBetween(n0)) {
                // try generating another random node
                randomNode = getRandomNodeIndex();
            }
            graph.addEdge(UUID.randomUUID().toString(), n0, randomNode);
        }
        else {
            while (graph.getNode(randomNode).hasEdgeBetween(n1)) {
                // try generating another random node
                randomNode = getRandomNodeIndex();
            }
            graph.addEdge(UUID.randomUUID().toString(), n1, randomNode);
        }

    }

    // todo
    private boolean isToBeDeleted(double attributeDistance) {

        return Math.random() > 0.5 ;
    }

    // todo
    private double calculateAttributeDistance(Edge edge) {
        return Math.random();
    }


}
