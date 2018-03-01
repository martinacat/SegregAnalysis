package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;

public abstract class Model {
    protected Graph graph;

    public double getBias() {
        return bias;
    }

    protected double bias;
    protected int initialXValue;
    private double coefficient;

    public Model(Graph graph, double initialBias, double coefficient){
        this.graph = graph;
        this.bias = initialBias;
        this.coefficient = coefficient;
        calculateBiasStartingXValue();

        SegregAnalysis.logger.info("Initial bias: " + bias);
        SegregAnalysis.logger.info("Bias starting x value: " + initialXValue);

    }

    abstract public void iteration(int step);
    abstract void rewire(Node n0, Node n1);


    private void calculateBiasStartingXValue() {
        if (Flags.direction == Flags.Direction.DECAY) {
            initialXValue = (int) (-Math.log(bias) / coefficient);
        }
        else if (Flags.direction == Flags.Direction.GROWTH) {
            initialXValue = (int) (Math.log(1-bias)/-coefficient);
        }
        else {
            initialXValue = 0;
        }

    }

    public void recalculateBias(int timeStep) {

        int x = timeStep + initialXValue;
        if (bias <= 1 && bias >= 0) {

            if (Flags.algorithm == Flags.Algorithm.BOTH) {
                return;
            }

            if (Flags.direction == Flags.Direction.NONE)
                return;

            if (Flags.direction == Flags.Direction.DECAY) {
                if (Flags.function == Flags.Function.CURVE) {
                    bias = Math.pow(Math.E, (coefficient * (-x)));
                }
                if (Flags.function == Flags.Function.LINEAR) {
                    // TODO
                }

            }

            if (Flags.direction == Flags.Direction.GROWTH) {

                if (Flags.function == Flags.Function.CURVE) {
                    bias = 1 - Math.exp(coefficient * (-x));
                }
                if (Flags.function == Flags.Function.LINEAR) {
                    // TODO
                }

            }
        }
    }

    protected Edge getRandomEdge() {
        int numberOfEdges = this.graph.getEdgeCount();
        int randomEdge = (int)(Math.random()*100) % numberOfEdges;
        return graph.getEdge(randomEdge);
    }

    protected int getRandomNodeIndex() {
        int numberOfNodes = this.graph.getNodeCount();
        return (int)(Math.random()*100) % numberOfNodes;
    }

    protected Node getRandomNode() {
        int numberOfNodes = this.graph.getNodeCount();
        int index =  (int)(Math.random()*100) % numberOfNodes;
        return graph.getNode(index);
    }


    protected double calculateAttributeDistance(Edge edge) {
        if (edge.getNode1().getAttribute("colour").equals(edge.getNode0().getAttribute("colour"))) {
            return 0;
        }
        return 1;
    }

    protected double calculateAttributeDistance(Node n1, Node n2) {
        if (n1.getAttribute("colour").equals(n2.getAttribute("colour"))) {
            return 0;
        }
        return 1;
    }


}
