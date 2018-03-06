package uk.ac.man.cs.segreganalysis.controller;

import uk.ac.man.cs.segreganalysis.SegregAnalysis;
import uk.ac.man.cs.segreganalysis.view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AversionBiasController implements ActionListener {

    private MainWindow view;
    private AversionBiasFocusAdapter focusAdapter = new AversionBiasFocusAdapter(this.view);

    AversionBiasController(MainWindow view) {

        this.view = view;
        this.view.getAversionBiasAdvancedSettingsButton().addActionListener(this);
        this.view.biasAdvancedSettings.getDifferentForSetsRadioButton().addActionListener(this);
        this.view.biasAdvancedSettings.getSameForAllRadioButton().addActionListener(this);
        this.view.biasAdvancedSettings.getNumberOfSetsText()
                .addFocusListener(new AversionBiasFocusAdapter(this.view));

        this.view.biasAdvancedSettings.getBiasEvolutionFunctionDropdown().addActionListener(this);

    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.view.getAversionBiasAdvancedSettingsButton()) {

            SegregAnalysis.logger.info("Bias Advanced settings Window opened");
            view.biasAdvancedSettings.setVisible(true);
        }

        // hide evolution in time and show source-target bias instead when linear is selected
        if (event.getSource() == this.view.biasAdvancedSettings.getBiasEvolutionFunctionDropdown()) {

            if (view.biasAdvancedSettings.getBiasEvolutionFunctionDropdown().getSelectedItem()
                == "Linear")
            {
                SegregAnalysis.logger.info("Linear selected");

                // hide evolution (growth - decay)
                view.biasAdvancedSettings.getBiasEvolutionLabel().setVisible(false);
                view.biasAdvancedSettings.getBiasEvolutionInTimeDropdown().setVisible(false);
                view.biasAdvancedSettings.getCoefficientText().setVisible(false);
                view.biasAdvancedSettings.getCoefficientLabel().setVisible(false);

                // show start end bias
                view.biasAdvancedSettings.getLinearStartBiasLabel().setVisible(true);
                view.biasAdvancedSettings.getLinearStartBiasText().setVisible(true);
                view.biasAdvancedSettings.getLinearEndBiasLabel().setVisible(true);
                view.biasAdvancedSettings.getLinearEndBiasText().setVisible(true);

            }
            if (view.biasAdvancedSettings.getBiasEvolutionFunctionDropdown().getSelectedItem()
                    == "Curve")
            {
                SegregAnalysis.logger.info("Curve selected");

                // show evolution (growth - decay)
                view.biasAdvancedSettings.getBiasEvolutionLabel().setVisible(true);
                view.biasAdvancedSettings.getBiasEvolutionInTimeDropdown().setVisible(true);
                view.biasAdvancedSettings.getCoefficientText().setVisible(true);
                view.biasAdvancedSettings.getCoefficientLabel().setVisible(true);

                // hide start end bias
                view.biasAdvancedSettings.getLinearStartBiasLabel().setVisible(false);
                view.biasAdvancedSettings.getLinearStartBiasText().setVisible(false);
                view.biasAdvancedSettings.getLinearEndBiasLabel().setVisible(false);
                view.biasAdvancedSettings.getLinearEndBiasText().setVisible(false);



            }

            if (view.biasAdvancedSettings.getBiasEvolutionFunctionDropdown().getSelectedItem()
                    == "None")
            {
                SegregAnalysis.logger.info("None selected");

                // hide evolution (growth - decay)
                view.biasAdvancedSettings.getBiasEvolutionLabel().setVisible(false);
                view.biasAdvancedSettings.getBiasEvolutionInTimeDropdown().setVisible(false);
                view.biasAdvancedSettings.getCoefficientText().setVisible(false);
                view.biasAdvancedSettings.getCoefficientLabel().setVisible(false);

                // hide start end bias
                view.biasAdvancedSettings.getLinearStartBiasLabel().setVisible(false);
                view.biasAdvancedSettings.getLinearStartBiasText().setVisible(false);
                view.biasAdvancedSettings.getLinearEndBiasLabel().setVisible(false);
                view.biasAdvancedSettings.getLinearEndBiasText().setVisible(false);
            }
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
