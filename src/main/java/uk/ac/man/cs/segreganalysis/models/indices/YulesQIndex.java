package uk.ac.man.cs.segreganalysis.models.indices;

import org.graphstream.graph.Graph;

public class YulesQIndex {




    private int a; // internal links
    private int b; // external links
    private int c; // non-links internally
    private int d; // non-links externally



    /*
        Maps perfect homophily to 1 and perfect eterophily to -1
        Analyzing Social Networks By Stephen P Borgatti, Martin G Everett, Jeffrey C,
        Section 15.3.4

        Based on contingency table:

               | Same  |  Different
       -----------------------------
       Tie     |   a   |    b
       No Tie  |   c   |    d

     */

    public YulesQIndex(Graph g, int node) {

    }

    // for each node
    public float calculate() {
        return ((a * d) - (b * c)) / ((a * d) + (b * c));
    }

    public float median() {
        return 0;
    }

}
