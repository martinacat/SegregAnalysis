package uk.ac.man.cs.segreganalysis.models;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.view.Viewer;
import org.graphstream.stream.ProxyPipe;

import java.io.File;
import java.io.IOException;


public class LinLogLayout {

    private ProxyPipe fromViewer;

    // Specify a cut threshold (a factor for the average edge length).
    private double cutThreshold = 1;

    private LinLog layout;
    private double a = 0;
    private double r = -1.3;
    private double force = 3;

    private Graph graph;
    private Viewer viewer;

    public static void main(String args[]) throws IOException, GraphParseException {
        (new LinLogLayout()).findCommunities("/Users/martinacatizone/git/segreganalysis/graph.gml");


    }

    public void findCommunities(String fileName)

        throws IOException, GraphParseException {

        graph = new SingleGraph("communities");
        //this.graph = graph;
        viewer = graph.display(false);

        // Create a back link from the viewer to our program to receive its interactions.
        fromViewer = viewer.newThreadProxyOnGraphicGraph();

        layout = new LinLog(false);        // 2
        layout.configure(a, r, true, force);    // 3
        layout.addSink(graph);            // 4
        graph.addSink(layout);

        //Connect this link to our graph so that it is modified by the viewer events.
        fromViewer.addSink(graph);				// 2

        graph.addAttribute("ui.antialias");			// 2
        graph.addAttribute("ui.stylesheet", styleSheet);	// 1
        graph.read(fileName);

        // Connect the graph to the layout so that the layout receive each modification event on the graph.
        // Check if the user closed the viewer window to properly end the program.

        // TODO Problem is here (LOOP)

        while(! graph.hasAttribute("ui.viewClosed")) {
            // Consult these events regularly to update the graph from the user interactions.
            fromViewer.pump();
            layout.compute();
            showCommunities();                // 3
        }
    }

    /*
    Browse each edge of the graph
    Store each edge length in an array of real values.
    Doing this we will compute the average edge length.
     */
    public void showCommunities() {
        int nEdges = graph.getEdgeCount();
        double averageDist = 0;
        double edgesDist[] = new double[nEdges];

        for(int i=0; i<nEdges; i++) {					// 1
            Edge edge = graph.getEdge(i);
            edgesDist[i] = GraphPosLengthUtils.edgeLength(edge);	// 2
            averageDist += edgesDist[i];				// 3
        }

        averageDist /= nEdges;						// 3

        /*
        Browse anew each edge in the same order.
        Compare their length stored in the array of lengths with the average length.
            If they are longer, mark them with a "cut" attribute and a "cut" ui.class.
            Else, remove the "cut" attribute and ui.class.
         */

        for(int i=0; i<nEdges; i++) {					// 1
            Edge edge = graph.getEdge(i);

            if(edgesDist[i] > averageDist * cutThreshold) {		// 2
                edge.addAttribute("ui.class", "cut");		// 2a
                edge.addAttribute("cut");
            } else {
                edge.removeAttribute("ui.class");		// 2b
                edge.removeAttribute("cut");
            }
        }
    }

    protected static String styleSheet =				// 1
            "node { size: 7px; fill-color: rgb(150,150,150); }" +
            "edge { fill-color: rgb(255,50,50); size: 2px; }" +
            "edge.cut { fill-color: rgba(200,200,200,128); }";
}