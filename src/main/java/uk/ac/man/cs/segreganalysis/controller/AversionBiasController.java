package uk.ac.man.cs.segreganalysis.controller;

import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AversionBiasController implements ActionListener {

    private View view;
    private AversionBiasFocusAdapter focusAdapter = new AversionBiasFocusAdapter(this.view);

    AversionBiasController(View view) {

        this.view = view;
        this.view.getAversionBiasAdvancedSettingsButton().addActionListener(this);
        this.view.biasAdvancedSettings.getDifferentForSetsRadioButton().addActionListener(this);
        this.view.biasAdvancedSettings.getSameForAllRadioButton().addActionListener(this);
        this.view.biasAdvancedSettings.getNumberOfSetsText()
                .addFocusListener(new AversionBiasFocusAdapter(this.view));

    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.view.getAversionBiasAdvancedSettingsButton()) {

            SegregAnalysis.logger.info("Bias Advanced settings Window opened");
            view.biasAdvancedSettings.setVisible(true);
        }

        // enable or disable text fields
        if (event.getSource() == this.view.biasAdvancedSettings.getDifferentForSetsRadioButton()) {
            if (!this.view.biasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
                this.view.biasAdvancedSettings.getInitialBiasForAllText().setEnabled(false);

                // enable "number of sets" textfield
                this.view.biasAdvancedSettings.getNumberOfSetsText().setEnabled(true);

                // enable textfields for sets' unique aversion bias
                for (int i = 0;
                     i < this.view.biasAdvancedSettings.getBiasTextFields().length;
                     i++) {
                    this.view.biasAdvancedSettings.getBiasTextFields()[i].setEnabled(true);
                    this.view.biasAdvancedSettings.getSizeTextFields()[i].setEnabled(true);

                }
            }
        }

        if (event.getSource() == this.view.biasAdvancedSettings.getSameForAllRadioButton()) {
            if (this.view.biasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
                this.view.biasAdvancedSettings.getInitialBiasForAllText().setEnabled(true);

                // disable "number of sets" textfield
                this.view.biasAdvancedSettings.getNumberOfSetsText().setEnabled(false);

                // disable textfields for sets' unique aversion bias
                for (int i = 0;
                     i < this.view.biasAdvancedSettings.getBiasTextFields().length;
                     i++) {
                    this.view.biasAdvancedSettings.getBiasTextFields()[i].setEnabled(false);
                    this.view.biasAdvancedSettings.getSizeTextFields()[i].setEnabled(false);

                }

            }
        }
    }
}
