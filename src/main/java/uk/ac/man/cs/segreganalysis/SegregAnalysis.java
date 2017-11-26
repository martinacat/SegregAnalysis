package uk.ac.man.cs.segreganalysis;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import uk.ac.man.cs.segreganalysis.controller.FileBrowserController;
import uk.ac.man.cs.segreganalysis.controller.SimulationController;
import uk.ac.man.cs.segreganalysis.view.View;


public class SegregAnalysis {


    public static void main(String args[]) {
        View view = new View("SegregAnalysis");
        SimulationController simulationController = new SimulationController(view);
        view.setVisible(true);
    }


}
