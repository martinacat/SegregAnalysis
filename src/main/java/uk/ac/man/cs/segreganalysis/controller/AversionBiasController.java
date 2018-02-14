package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import scala.annotation.meta.field;
import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AversionBiasController implements ActionListener {

    private View view;
    private AversionBiasFocusAdapter focusAdapter = new AversionBiasFocusAdapter(this.view);

    AversionBiasController(View view) {

        this.view = view;
        this.view.getAversionBiasAdvancedSettingsButton().addActionListener(this);
        this.view.aversionBiasAdvancedSettings.getDifferentForSetsRadioButton().addActionListener(this);
        this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton().addActionListener(this);
        this.view.aversionBiasAdvancedSettings.getNumberOfSetsText()
                .addFocusListener(new AversionBiasFocusAdapter(this.view));

    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.view.getAversionBiasAdvancedSettingsButton()) {

            SegregAnalysis.logger.info("Bias Advanced settings Window opened");
            view.aversionBiasAdvancedSettings.setVisible(true);
        }

        if (event.getSource() == this.view.aversionBiasAdvancedSettings.getDifferentForSetsRadioButton()) {
            if (!this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
                this.view.aversionBiasAdvancedSettings.getInitialBiasForAllText().setEnabled(false);

                // enable "number of sets" textfield
                this.view.aversionBiasAdvancedSettings.getNumberOfSetsText().setEnabled(true);

                // enable textfields for sets' unique aversion bias
                for (int i = 0;
                     i < this.view.aversionBiasAdvancedSettings.getBiasTextFields().length;
                     i++) {
                    this.view.aversionBiasAdvancedSettings.getBiasTextFields()[i].setEnabled(true);
                    this.view.aversionBiasAdvancedSettings.getSizeTextFields()[i].setEnabled(true);

                }
            }
        }

        if (event.getSource() == this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton()) {
            if (this.view.aversionBiasAdvancedSettings.getSameForAllRadioButton().isSelected()) {
                this.view.aversionBiasAdvancedSettings.getInitialBiasForAllText().setEnabled(true);

                // disable "number of sets" textfield
                this.view.aversionBiasAdvancedSettings.getNumberOfSetsText().setEnabled(false);

                // disable textfields for sets' unique aversion bias
                for (int i = 0;
                     i < this.view.aversionBiasAdvancedSettings.getBiasTextFields().length;
                     i++) {
                    this.view.aversionBiasAdvancedSettings.getBiasTextFields()[i].setEnabled(false);
                    this.view.aversionBiasAdvancedSettings.getSizeTextFields()[i].setEnabled(false);

                }

            }
        }
    }
}
