package uk.ac.man.cs.segreganalysis.models.indices;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class YulesQIndexTest {
    @Test
    void testCalculate() {
        Graph graph = new SingleGraph("TestGraph");

        graph.addNode("A" );
        graph.getNode("A").addAttribute("gender", "f");
        graph.addNode("B" );
        graph.getNode("B").addAttribute("gender", "f");
        graph.addNode("C" );
        graph.getNode("C").addAttribute("gender", "f");

        graph.addNode("D" );
        graph.getNode("D").addAttribute("gender", "m");
        graph.addNode("E" );
        graph.getNode("E").addAttribute("gender", "m");
        graph.addNode("F" );
        graph.getNode("F").addAttribute("gender", "m");


        int indexOfB = graph.getNode("B").getIndex();

        graph.addEdge("BA", "B", "A");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("BE", "B", "E");

        YulesQIndex index = new YulesQIndex(graph);
        assertEquals(1, index.calculate(indexOfB));

        graph.removeEdge("BC");
        graph.addEdge("BF", "B", "F");

        assertEquals(-0.3333333432674408, index.calculate(indexOfB));

        graph.removeEdge("BA");
        graph.addEdge("BD", "B", "D");

        assertEquals(-1, index.calculate(indexOfB));

    }

    @Test
    void movingAverage() {
    }

}