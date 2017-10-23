package uk.ac.man.cs.segreganalysis.controller;

import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
    View view;


    public Controller(View v){
        view = v;
        view.applyButton.addActionListener(this);
    }

    public void actionPerformed( ActionEvent e ) {

        if(e.getSource() == view.applyButton){

            // check that all the fields are full
            int size = 0;
            int steps = 0;

            SegregAnalysis.GeneratorType generatorType;

            if (view.sizeText.getText() != null){
                size = Integer.parseInt(view.sizeText.getText());
            }

            if (view.stepsText.getText() != null){
                steps = Integer.parseInt(view.stepsText.getText());
            }


            switch (view.generatorDropdown.getSelectedIndex()){

                case 0:
                    generatorType = SegregAnalysis.GeneratorType.PREFERENTIAL_ATTACHMENT;
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
            new SegregAnalysis(size, generatorType ,steps);




        }
    }
}
