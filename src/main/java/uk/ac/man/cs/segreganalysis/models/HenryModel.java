package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import uk.ac.man.cs.segreganalysis.view.View;

/*  Based on the model broposed in
    A. D. Henry, P. Pralat, C. Zhang, "Emergence of segregation in evolving social networks"
 */
public class HenryModel {

    private Graph graph;

    private View view;
    private double aversionBias;

    public HenryModel(Graph graph, View view){
        this.graph = graph;
        this.view = view;

        if (view.aversionBiasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
            this.aversionBias =
                    Double.parseDouble(this.view.aversionBiasAdvancedSettings.getInitialBiasForAllText().getText());
        }
    }

    public void iteration() {

        // choose random edge uv
        Edge edge = getRandomEdge();

        // choose: delete or keep based on attribute distance
        if (isToBeDeleted(edge)) {

            int n0 = edge.getNode0().getIndex();
            int n1 = edge.getNode1().getIndex();
            graph.removeEdge(edge);
            rewire(n0, n1);
        }


    }

    private void recalculateAversionBias() {


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


    // rewires one of the two nodes given with a random node
    private void rewire(int n0, int n1) {
        int randomNode = getRandomNodeIndex();

        if (Math.random() > 0.5) { // rewire n0
            while (graph.getNode(randomNode).hasEdgeBetween(n0)) {
                randomNode = getRandomNodeIndex();
            }
            graph.addEdge(n0 + "_" + randomNode, n0, randomNode);
            if (!graph.getNode(n0).getAttribute("gender").equals(graph.getNode(randomNode).getAttribute("gender"))){
                graph.getEdge(n0 + "_" + randomNode).changeAttribute("layout.weight", 3);
            }else {
                graph.getEdge(n0 + "_" + randomNode).changeAttribute("layout.weight", 0.5);

            }
        }
        else { // rewire n1
            while (graph.getNode(randomNode).hasEdgeBetween(n1)) {
                randomNode = getRandomNodeIndex();
            }
            graph.addEdge(n1 + "_" + randomNode, n1, randomNode);
            if (!graph.getNode(n1).getAttribute("gender").equals(graph.getNode(randomNode).getAttribute("gender"))){
                graph.getEdge(n1 + "_" + randomNode).changeAttribute("layout.weight", 3);
            }else {
                graph.getEdge(n1 + "_" + randomNode).changeAttribute("layout.weight", 0.5);

            }
        }

    }

    private boolean isToBeDeleted(Edge edge) {
        // calculate attribute distance todo
        // calculateAttributeDistance(edge);

        return (!edge.getNode1().getAttribute("gender").equals(edge.getNode0().getAttribute("gender"))) ;
    }

    // todo
    private double calculateAttributeDistance(Edge edge) {
        double attributeDistance = 0;
        if (!edge.getNode1().getAttribute("gender").equals(edge.getNode0().getAttribute("gender"))) {
            attributeDistance = 1;
        }
        attributeDistance = attributeDistance * aversionBias;
        return attributeDistance;
    }


}
