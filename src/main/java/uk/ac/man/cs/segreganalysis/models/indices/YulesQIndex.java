package uk.ac.man.cs.segreganalysis.models.indices;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class YulesQIndex {

    Graph graph;
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
    public float calculate(int node) {
        Node n = graph.getNode(node);
        int a = 0; // internal links
        int b = 0; // external links
        int c = 0; // non-links
        int d = 0; // non-links

        // count tied nodes
        for(Edge e:n.getEachEdge()) {
            if (e.getOpposite(n).getAttribute("gender") == n.getAttribute("gender")) {
                a++;
            }
            else {
                b++;
            }
        }
        for (Node node1:graph.getEachNode()) {
            if (!node1.hasEdgeToward(node)) {
                if (n.getAttribute("gender")== node1.getAttribute("gender")) {
                    c++;
                }
                else {
                    d++;
                }
            }
        }

        if (((a * d) + (b * c)) == 0) {
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
