package uk.ac.man.cs.segreganalysis.models.indices;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import static uk.ac.man.cs.segreganalysis.controller.AttributesController.attributeTypes;

public class YulesQIndex {

    private Graph graph;
    float avg;

    /*
     *   Maps perfect homophily to 1 and perfect eterophily to -1
     *   Analyzing Social Networks By Stephen P Borgatti, Martin G Everett, Jeffrey C,
     *   Section 15.3.4

        Based on contingency table:

               | Same  |  Different
       -----------------------------
       Tie     |   a   |    b
       No Tie  |   c   |    d

     */

    public YulesQIndex(Graph g) {

        graph = g;


    }


    // done for each node and averaged
    public float calculate(int n) {

        Node node = graph.getNode(n);

        int a = 0; // same - tie
        int b = 0; // diff - tie
        int c = 0; // same - no tie
        int d = 0; // diff - no tie

        // count tied nodes
        for(Edge e : node.getEachEdge()) {
            if (e.getOpposite(node).getAttribute(attributeTypes.get(0))
                    .equals(node.getAttribute(attributeTypes.get(0)))) {
                a++;
            }

            else {
                b++;
            }
        }

        for (Node x : graph.getEachNode()) {
            if (!x.hasEdgeBetween(node) && x != node) { // if there is no tie between x and n

                if (node.getAttribute(attributeTypes.get(0))
                        .equals(x.getAttribute(attributeTypes.get(0)))) {
                    c++;
                }
                else {
                    d++;
                }
            }
        }

        if ((float)((a * d) + (b * c)) == 0) {
            return 0;
        }

        return (float)((a * d) - (b * c)) /(float)((a * d) + (b * c));
    }


    // running mean of all the nodes
    public float movingAverage() {
        int n = 1;
        float avg = this.calculate(n);

        while (n < graph.getNodeCount()){
            avg = (calculate(n-1) + (n-1)*avg)/n;
            n++;
        }

        return avg;
    }

}
