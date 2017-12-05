package uk.ac.man.cs.segreganalysis;

import uk.ac.man.cs.segreganalysis.controller.SimulationController;
import uk.ac.man.cs.segreganalysis.view.View;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SegregAnalysis {

    public static final Logger logger = LogManager.getLogger(SegregAnalysis.class);

    public static void main(String args[]) {
        View view = new View("SegregAnalysis");
        SimulationController simulationController = new SimulationController(view);
        view.setVisible(true);
    }


}
