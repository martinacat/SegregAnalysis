package uk.ac.man.cs.segreganalysis.view;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;
import org.graphstream.stream.ProxyPipe;

import javax.swing.*;
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

    private ConnectedComponents cc;
    private SpriteManager sm;
    private Sprite ccCount;


    public LinLogLayout (String filename) {
        try {
            findCommunities(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GraphParseException e) {
            e.printStackTrace();
        }
    }

    public void findCommunities(String fileName)

        throws IOException, GraphParseException {


        graph = new SingleGraph("communities");

        viewer = graph.display(false);
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);


        // Create a back link from the viewer to our program to receive its interactions.
        fromViewer = viewer.newThreadProxyOnGraphicGraph();

        layout = new LinLog(false);
        cc = new ConnectedComponents(graph);
        sm = new SpriteManager(graph);
        ccCount = sm.addSprite("CC");
        layout.configure(a, r, true, force);
        cc.setCutAttribute("cut");
        ccCount.setPosition(StyleConstants.Units.PX, 20, 20, 0);

        layout.addSink(graph);
        graph.addSink(layout);

        //Connect this link to our graph so that it is modified by the viewer events.
        fromViewer.addSink(graph);

        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.read(fileName);


        // All code inside SwingWorker runs on a seperate thread
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            public Integer doInBackground() {

                long i = 0;

                // Connect the graph to the layout so that the layout receive
                // each modification event on the graph.
                // Check if the user closed the viewer window to properly
                // end the program.
                while(i<100000 && !graph.hasAttribute("ui.viewClosed")) {
                    i++;
                    // Consult these events regularly to update the graph from the user interactions.
                    fromViewer.pump();
                    layout.compute();
                    showCommunities();
                    ccCount.setAttribute("ui.label",
                            String.format(fileName + " modules: %d", cc.getConnectedComponentsCount()));
                }
                return 1;
            }
        };

        worker.execute();

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

        for(int i=0; i<nEdges; i++) {
            Edge edge = graph.getEdge(i);
            edgesDist[i] = GraphPosLengthUtils.edgeLength(edge);
            averageDist += edgesDist[i];
        }

        averageDist /= nEdges;

        /*
        Browse anew each edge in the same order.
        Compare their length stored in the array of lengths with the average length.
            If they are longer, mark them with a "cut" attribute and a "cut" ui.class.
            Else, remove the "cut" attribute and ui.class.
         */

        for(int i=0; i<nEdges; i++) {
            Edge edge = graph.getEdge(i);

            if(edgesDist[i] > averageDist * cutThreshold) {
                edge.addAttribute("ui.class", "cut");
                edge.addAttribute("cut");
            } else {
                edge.removeAttribute("ui.class");		
                edge.removeAttribute("cut");
            }
        }
    }

    private static String styleSheet = "url('./stylesheet.css')";

}