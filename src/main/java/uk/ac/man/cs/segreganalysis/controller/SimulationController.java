package uk.ac.man.cs.segreganalysis.controller;

import uk.ac.man.cs.segreganalysis.view.MainWindow;

public class SimulationController {
    private MainWindow view;

    public SimulationController(MainWindow v){

        view = v;
        view.getApplyButton().addActionListener(new Start(view));
        AttributesController attributesController = new AttributesController(view);
        FileBrowserController fileBrowserController = new FileBrowserController(view);
        AversionBiasController aversionBiasController = new AversionBiasController(view);


        // max link per step
        view.getGeneratorDropdown().addActionListener(e -> {
            if (view.getGeneratorDropdown().getSelectedItem() != "Preferential Attachment") {
                view.getMaxLinksPerStepLabel().setVisible(false);
                view.getMaxLinksPerStepText().setVisible(false);
            }
            else {
                view.getMaxLinksPerStepLabel().setVisible(true);
                view.getMaxLinksPerStepText().setVisible(true);
            }
            view.getMaxLinksPerStepText().revalidate();
        }
        );


    }

}
