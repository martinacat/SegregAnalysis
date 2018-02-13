package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;

public abstract class Model {
    protected Graph graph;

    protected double aversionBias;
    private double coefficient;

    public Model(Graph graph, double initialAversionBias, double coefficient){
        this.graph = graph;
        this.aversionBias = initialAversionBias;
        this.coefficient = coefficient;
    }

    abstract void iteration(int step);
    // calculate how aversion bias changes over time
    // three modes exist:
    // Fixed: no change
    // Linear: Growth / Decay
    // Curve: Growth / Decay
    public void recalculateBias(int x) {

        if (aversionBias < 1 && aversionBias > 0) {

            if (Flags.direction == Flags.Direction.NONE)
                return;

            if (Flags.direction == Flags.Direction.DECAY) {
                if (Flags.function == Flags.Function.CURVE) {
                    aversionBias = Math.pow(Math.E, (coefficient * (-x)));
                }
                if (Flags.function == Flags.Function.LINEAR) {
                    // TODO
                }

            }

            if (Flags.direction == Flags.Direction.GROWTH) {
                SegregAnalysis.logger.info(Flags.direction);

                if (Flags.function == Flags.Function.CURVE) {
                    aversionBias = 1 - Math.exp(coefficient * (-x));
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

    protected double calculateAttributeDistance(Edge edge) {
        if (!edge.getNode1().getAttribute("gender").equals(edge.getNode0().getAttribute("gender"))) {
            return 1;
        }
        return 0;
    }


}
