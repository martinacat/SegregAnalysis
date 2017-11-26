package uk.ac.man.cs.segreganalysis.controller;

import uk.ac.man.cs.segreganalysis.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileBrowserController  implements ActionListener {
    View view;

    public FileBrowserController(View v) {
        view = v;

        view.importButton.addActionListener(this);

    }

    public void actionPerformed( ActionEvent e ) {

        if (e.getSource() == view.importButton) {

            final JFileChooser fileChooser = new JFileChooser();

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                view.fileBrowseField.setText(selectedFile.getAbsolutePath().toString());
            }



        }

    }

}
