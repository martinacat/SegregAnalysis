package uk.ac.man.cs.segreganalysis.controller;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import uk.ac.man.cs.segreganalysis.models.Model;

import javax.swing.*;

public class Clicks implements ViewerListener {
    protected boolean loop = true;
    protected Graph graph;

    public Clicks(Graph graph, int steps, Model model)  {
        this.graph = graph;
        // We do as usual to display a graph. This
        // connect the graph outputs to the viewer.
        // The viewer is a sink of the graph.
        Viewer viewer = graph.display();

        // The default action when closing the view is to quit
        // the program.
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        // We connect back the viewer to the graph,
        // the graph becomes a sink for the viewer.
        // We also install us as a viewer listener to
        // intercept the graphic events.
        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);

        // Then we need a loop to do our work and to wait for events.
        // In this loop we will need to call the
        // pump() method before each use of the graph to copy back events
        // that have already occurred in the viewer thread inside
        // our thread.

        for (int i = 0; i < steps; i++) {

            fromViewer.pump(); // or fromViewer.blockingPump(); in the nightly builds
            model.iteration(i);

        }
    }

    public void viewClosed(String id) {
        loop = false;
    }

    public void buttonPushed(String id) {
        Node n = graph.getNode(id);

        String attributes[] = n.getAttributeKeySet().toArray(new String[n.getAttributeKeySet().size()]);

        String attributeToChange = (String) JOptionPane.showInputDialog(null, "Select attibute to modify", "Attribute for " + id, JOptionPane.QUESTION_MESSAGE, null, attributes, attributes[0]);
        String curValue = n.getAttribute(attributeToChange);
        String newValue
                = JOptionPane.showInputDialog("New Value", curValue);
        n.setAttribute(attributeToChange, newValue);
    }

    public void buttonReleased(String id) {
        System.out.println("Button released on node "+id);
    }
}