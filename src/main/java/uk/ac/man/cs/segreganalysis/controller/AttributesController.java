package uk.ac.man.cs.segreganalysis.controller;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Iterator;

public class AttributesController {

    public static void initialiseAttributes(Graph graph) {
        // initialise random attribute
        Iterator<Node> iterator = graph.iterator();

        while (iterator.hasNext()){
            Node n = iterator.next();
            if (Math.random() > 0.5) {
                n.addAttribute("gender", "male");
                n.addAttribute("ui.class", "male");
            }
            else {
                n.addAttribute("gender", "female");
                n.addAttribute("ui.class", "female");
            }
        }

    }
}
