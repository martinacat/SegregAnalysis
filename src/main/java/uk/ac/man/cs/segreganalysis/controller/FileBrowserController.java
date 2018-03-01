package uk.ac.man.cs.segreganalysis.controller;

import uk.ac.man.cs.segreganalysis.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileBrowserController  implements ActionListener {
    private MainWindow view;

    FileBrowserController(MainWindow v) {
        view = v;

        view.getImportButton().addActionListener(this);

    }

    public void actionPerformed( ActionEvent e ) {

        if (e.getSource() == view.getImportButton() ) {

            final JFileChooser fileChooser = new JFileChooser();

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                view.getFileBrowseField().setText(selectedFile.getAbsolutePath());
            }


        }


    }

}
