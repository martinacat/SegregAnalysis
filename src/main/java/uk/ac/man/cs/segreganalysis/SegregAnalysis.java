package uk.ac.man.cs.segreganalysis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.man.cs.segreganalysis.controller.SimulationController;
import uk.ac.man.cs.segreganalysis.view.View;


public class SegregAnalysis {

    public static final Logger logger = LogManager.getLogger(SegregAnalysis.class);

    public static void main(String args[]) {

        View view = new View("SegregAnalysis");
        new SimulationController(view);


        view.setVisible(true);
    }
}
