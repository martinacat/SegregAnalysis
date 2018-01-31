package uk.ac.man.cs.segreganalysis.controller;

import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AversionBiasController implements ActionListener{

    private View view;

    AversionBiasController(View view) {

        this.view = view;
        this.view.getAversionBiasAdvancedSettingsButton().addActionListener(this);
        this.view.aversionBiasAdvancedSettings.getDifferentForSetsRadioButton().addActionListener(this);
        this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton().addActionListener(this);

    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.view.getAversionBiasAdvancedSettingsButton()) {

            SegregAnalysis.logger.info("Aversion Bias Advanced settings Window opened");
            view.aversionBiasAdvancedSettings.setVisible(true);
        }

        if (event.getSource() == this.view.aversionBiasAdvancedSettings.getDifferentForSetsRadioButton()) {
            if (!this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
                this.view.aversionBiasAdvancedSettings.getInitialBiasForAllText().setEnabled(false);
            }
        }

        if (event.getSource() == this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton()) {
            if (this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
                this.view.aversionBiasAdvancedSettings.getInitialBiasForAllText().setEnabled(true);

            }
        }

    }
}
