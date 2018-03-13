package uk.ac.man.cs.segreganalysis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.man.cs.segreganalysis.controller.SimulationController;
import uk.ac.man.cs.segreganalysis.view.MainWindow;


public class SegregAnalysis {

    public static final Logger logger = LogManager.getLogger(SegregAnalysis.class);
    private static String styleSheet = "url('./stylesheet.css')";


    public static void main(String args[]) {

        MainWindow view = new MainWindow("SegregAnalysis");
        new SimulationController(view);
        view.setVisible(true);
    }
}
