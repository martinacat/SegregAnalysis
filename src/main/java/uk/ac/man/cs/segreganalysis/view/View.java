package uk.ac.man.cs.segreganalysis.view;

import uk.ac.man.cs.segreganalysis.SegregAnalysis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
    public View(String title){

        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new SpringLayout());
        add(panel);
        placeComponents(panel);

        pack();
        setSize(340, 210);
        setLocationRelativeTo(null); // center
        setVisible(true);

    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        // graph size

        JLabel sizeLabel = new JLabel("Graph size:");
        sizeLabel.setBounds(10,20,100,25);
        panel.add(sizeLabel);

        JTextField sizeText = new JTextField(20);
        sizeText.setBounds(140,20,165,25);
        panel.add(sizeText);


        // type of generator

        JLabel genLabel = new JLabel("Generator type:");
        genLabel.setBounds(10,50,100,25);
        panel.add(genLabel);

        String[] generatorTypes = {"Grid", "Preferential Attachment", "Random", "Random Euclidean"};
        JComboBox generatorDropdown = new JComboBox(generatorTypes);
        generatorDropdown.setBounds(140,50,165,25);
        panel.add(generatorDropdown);


        // type of algorithm applied

        JLabel algLabel = new JLabel("Algorithm:");
        algLabel.setBounds(10,80,100,25);
        panel.add(algLabel);

        String[] algorithms = {"Henry", "Schelling"};
        JComboBox algorithmDropdown = new JComboBox(algorithms);
        algorithmDropdown.setBounds(140,80,165,25);
        panel.add(algorithmDropdown);


        // number of steps of the algorithm

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setBounds(10,110,100,25);
        panel.add(stepsLabel);

        JTextField stepsText = new JTextField(20);
        stepsText.setBounds(140,110,165,25);
        panel.add(stepsText);

        // Creating button

        JButton applyButton = new JButton("Generate");
        applyButton.setBounds(10, 150, 90, 25);
        panel.add(applyButton);

        applyButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new SegregAnalysis(100, SegregAnalysis.GeneratorType.PREFERENTIAL_ATTACHMENT, 100);

            }
        });
    }


}
