package uk.ac.man.cs.segreganalysis.models.indices;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Iterator;

public class DuncanSegregationIndex {
    private Graph graph;
    private int neg; // total number of individuals whose attribute "x" is -1
    private int pos; // total number of individuals whose attribute "x" is 1

    public DuncanSegregationIndex(Graph g) {
        graph = g;
        neg = 0;
        pos = 0;

        // count
        Iterator<Node> iter = graph.iterator();
        while (iter.hasNext()){
            Node n = iter.next();
            if (n.getAttribute("gender").equals("male")) {
                neg++;
            }else{
                pos++;
            }

        }

        System.out.println("total males: " + neg + "total females: " + pos);

    }

    public float calculate() {

        int negNeighbours = 0;
        int posNeighbours = 0;

        float result = 0;
        Node opposite;

        for (Node n : graph) {

            // obtain neighbour information
            Iterable<Edge> edges = n.getEachEdge();

            for (Edge e : edges) {
                opposite = e.getOpposite(n);
                if (opposite.getAttribute("gender").equals("male")) {
                    negNeighbours++;

                }
                else {
                    posNeighbours++;

                }
            }

            // Duncan
            result += Math.abs((float)posNeighbours/(float)pos - (float)negNeighbours/(float)neg);

            posNeighbours = 0;
            negNeighbours = 0;
        }


        return result * (float) 0.5;
    }

}
