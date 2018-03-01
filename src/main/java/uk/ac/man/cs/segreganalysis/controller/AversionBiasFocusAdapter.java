package uk.ac.man.cs.segreganalysis.controller;

import org.apache.commons.lang3.StringUtils;
import uk.ac.man.cs.segreganalysis.view.MainWindow;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AversionBiasFocusAdapter extends FocusAdapter{

    private MainWindow view;

    AversionBiasFocusAdapter(MainWindow view) {
        this.view = view;
    }

    public void focusLost(FocusEvent e) {


        if (e.getSource() == this.view.biasAdvancedSettings.getNumberOfSetsText()) {
            adaptSetsOnView();
        }

    }

    public void adaptSetsOnView() {

        int numberOfSets = 0;

        String text = this.view.biasAdvancedSettings.getNumberOfSetsText().getText();

        if (StringUtils.isNumeric(text)) {
            numberOfSets =  Integer.parseInt(text);
        }
        else {
            JOptionPane.showMessageDialog(view.biasAdvancedSettings,
                    "Please insert a numeric value between 1 and 4 for \"Number of sets\"");
            return;
        }

        if (numberOfSets < 2) {
            JOptionPane.showMessageDialog(view.biasAdvancedSettings,
                    "\"Number of sets\" must be at least 2");
            return;
        }

        if (numberOfSets > 4) {
            JOptionPane.showMessageDialog(view.biasAdvancedSettings,
                    "\"Number of sets\" can't be more than 4");
            return;
        }

        // only set enabled the exact number of fields
        for (int i = 0; i < numberOfSets; i++) {
            view.biasAdvancedSettings.getBiasTextFields()[i].setEnabled(true);
            view.biasAdvancedSettings.getSizeTextFields()[i].setEnabled(true);

            view.biasAdvancedSettings.setBiasTextFields(i, "1");
            view.biasAdvancedSettings.setSizeTextFields(i, "1");
        }

        for (int i = numberOfSets; i < 4; i++) {
            view.biasAdvancedSettings.getBiasTextFields()[i].setEnabled(false);
            view.biasAdvancedSettings.getSizeTextFields()[i].setEnabled(false);

            view.biasAdvancedSettings.setBiasTextFields(i, "0");
            view.biasAdvancedSettings.setSizeTextFields(i, "0");

        }

    }
}
