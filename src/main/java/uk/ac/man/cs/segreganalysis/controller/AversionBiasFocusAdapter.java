package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import uk.ac.man.cs.segreganalysis.view.View;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AversionBiasFocusAdapter extends FocusAdapter{

    private View view;

    AversionBiasFocusAdapter(View view) {
        this.view = view;
    }

    public void focusLost(FocusEvent e) {


        if (e.getSource() == this.view.aversionBiasAdvancedSettings.getNumberOfSetsText()) {
            adaptSetsOnView();
        }

    }

    public void adaptSetsOnView() {

        int numberOfSets = 0;

        String text = this.view.aversionBiasAdvancedSettings.getNumberOfSetsText().getText();

        if (StringUtils.isNumeric(text)) {
            numberOfSets =  Integer.parseInt(text);
        }
        else {
            JOptionPane.showMessageDialog(view.aversionBiasAdvancedSettings,
                    "Please insert a numeric value between 1 and 4 for \"Number of sets\"");
            return;
        }

        if (numberOfSets < 2) {
            JOptionPane.showMessageDialog(view.aversionBiasAdvancedSettings,
                    "\"Number of sets\" must be at least 2");
            return;
        }

        if (numberOfSets > 4) {
            JOptionPane.showMessageDialog(view.aversionBiasAdvancedSettings,
                    "\"Number of sets\" can't be more than 4");
            return;
        }

        // only set enabled the exact number of fields
        for (int i = 0; i < numberOfSets; i++) {
            view.aversionBiasAdvancedSettings.getBiasTextFields()[i].setEnabled(true);
            view.aversionBiasAdvancedSettings.getSizeTextFields()[i].setEnabled(true);

            view.aversionBiasAdvancedSettings.setBiasTextFields(i, "1");
            view.aversionBiasAdvancedSettings.setSizeTextFields(i, "1");
        }

        for (int i = numberOfSets; i < 4; i++) {
            view.aversionBiasAdvancedSettings.getBiasTextFields()[i].setEnabled(false);
            view.aversionBiasAdvancedSettings.getSizeTextFields()[i].setEnabled(false);

            view.aversionBiasAdvancedSettings.setBiasTextFields(i, "0");
            view.aversionBiasAdvancedSettings.setSizeTextFields(i, "0");

        }

    }
}
