package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
    View view;


    public Controller(View v){
        view = v;
        view.applyButton.addActionListener(this);

        view.generatorDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (view.generatorDropdown.getSelectedIndex() != 0) {
                    view.maxLinksLabel.setVisible(false);
                    view.maxLinksPerStepText.setVisible(false);
                }
                else {
                    view.maxLinksLabel.setVisible(true);
                    view.maxLinksPerStepText.setVisible(true);
                }
                view.maxLinksPerStepText.revalidate();
            }}
        );
    }

    public void actionPerformed( ActionEvent e ) {

        if(e.getSource() == view.applyButton){

            // check that all the fields are full
            int size = 0;
            int steps = 0;
            int maxLinksPerStep = 0;

            SegregAnalysis.GeneratorType generatorType;

            // todo: stop the graph from being displayed if the input is not sanitized
            if (StringUtils.isNumeric(view.sizeText.getText())) {
                size = Integer.parseInt(view.sizeText.getText());
            } else {
                JOptionPane.showMessageDialog(view, "Please insert a numeric value for Size");
            }

            if (StringUtils.isNumeric(view.stepsText.getText())) {
                steps = Integer.parseInt(view.stepsText.getText());
            } else {
                JOptionPane.showMessageDialog(view, "Please insert a numeric value for Steps");
            }

            switch (view.generatorDropdown.getSelectedIndex()){

                case 0:
                    generatorType = SegregAnalysis.GeneratorType.PREFERENTIAL_ATTACHMENT;
                    if ( StringUtils.isNumeric(view.maxLinksPerStepText.getText())) {
                        maxLinksPerStep = Integer.parseInt(view.maxLinksPerStepText.getText());
                    }
                    else {
                        JOptionPane.showMessageDialog(view, "Please insert a numeric value for Max Links Per Step");
                    }
                    break;
                case 1:
                    generatorType = SegregAnalysis.GeneratorType.RANDOM;
                    break;
                case 2:
                    generatorType = SegregAnalysis.GeneratorType.RANDOM_EUCLIDEAN;
                    break;
                default:
                    generatorType = SegregAnalysis.GeneratorType.PREFERENTIAL_ATTACHMENT;
                    break;
            }

            // todo implement algorithm choice
            switch (view.algorithmDropdown.getSelectedIndex()){
                case 0:
                    break;
                case 1:
                    break;
                default:
                    break;
            }


            // start the simulation
            new SegregAnalysis(size, generatorType , steps, maxLinksPerStep);




        }
    }
}
