package uk.ac.man.cs.segreganalysis.controller;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import scala.util.parsing.combinator.testing.Str;
import uk.ac.man.cs.segreganalysis.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttributesController {
    public static List<String> attributeTypes = new ArrayList<String>();


    public AttributesController(View view) {
        attributeTypes.add("colour");
    }

    public static void initialiseAttributes(Graph graph) {

        // initialise random attributes
        Iterator<Node> iterator = graph.iterator();

        while (iterator.hasNext()){
            Node n = iterator.next();
            if (Math.random() > 0.5) {
                n.addAttribute(attributeTypes.get(0), "blue");
                n.addAttribute("ui.class", "blue");
            }
            else {
                n.addAttribute(attributeTypes.get(0), "red");
                n.addAttribute("ui.class", "red");
            }
        }
    }
}
