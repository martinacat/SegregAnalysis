package uk.ac.man.cs.segreganalysis.view;

import javax.swing.*;

public class View extends JFrame {

    public static final int LABEL_X = 10;
    public static final int FIELD_X = 180;

    public static final int LABEL_WIDTH = 140;
    public static final int FIELD_WIDTH = 200;

    public static final int HEIGHT = 25;

    public static final int PANEL_WIDTH = 390;
    public static final int TITLE_WIDTH = 390/2;


    static int y_coordinate = 20;






    public JTextField sizeText;
    public JComboBox algorithmDropdown; // choose algorithm
    public JComboBox generatorDropdown;
    public JTextField stepsText;
    public JTextField maxLinksPerStepText;
    public JLabel maxLinksLabel;

    public JButton applyButton;
    public JButton importButton;

    public JTextField fileBrowseField;


    public View(String title){

        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new SpringLayout());
        add(panel);
        populateView(panel);

        pack();
        setSize(PANEL_WIDTH, 500);
        setLocationRelativeTo(null); // center

    }

    private void populateView(JPanel panel) {

        // Calculate center x
        int xCenter = PANEL_WIDTH/2 - TITLE_WIDTH/2;

        panel.setLayout(null);


        JLabel importGraph = new JLabel("<html><font color='gray'><b>Import your Graph (.dsg)</b></font>");
        importGraph.setBounds(xCenter, y_coordinate, TITLE_WIDTH, HEIGHT);
        panel.add(importGraph);

        y_coordinate += 30;

        fileBrowseField = new JTextField(20);
        fileBrowseField.setBounds(10,y_coordinate, 275, HEIGHT);
        panel.add(fileBrowseField);


        importButton = new JButton("Browse");
        importButton.setBounds(FIELD_X+100, y_coordinate, 100,  HEIGHT);
        panel.add(importButton);

        y_coordinate += 40;


        JLabel graphGeneration = new JLabel("<html><font color='gray'><b>or Generate a Random Graph</b></font>");
        graphGeneration.setBounds(xCenter, y_coordinate, TITLE_WIDTH+20, HEIGHT+5);
        panel.add(graphGeneration);

        y_coordinate += 30;


        // graph size

        JLabel sizeLabel = new JLabel("Graph size:");
        sizeLabel.setBounds(LABEL_X,y_coordinate,LABEL_WIDTH, HEIGHT);
        panel.add(sizeLabel);

        sizeText = new JTextField(20);
        sizeText.setBounds(FIELD_X,y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(sizeText);

        y_coordinate += 30;


        // type of generator

        JLabel genLabel = new JLabel("Generator type:");
        genLabel.setBounds(LABEL_X,y_coordinate,LABEL_WIDTH, HEIGHT);
        panel.add(genLabel);

        String[] generatorTypes = {"Preferential Attachment", "Random", "Random Euclidean"};
        generatorDropdown = new JComboBox(generatorTypes);
        generatorDropdown.setBounds(FIELD_X,y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(generatorDropdown);

        y_coordinate += 30;


        // max links per step for preferential attachment

        maxLinksLabel = new JLabel("Max links per step:");
        maxLinksLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH, HEIGHT);
        panel.add(maxLinksLabel);

        maxLinksPerStepText = new JTextField(20);
        maxLinksPerStepText.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(maxLinksPerStepText);

        y_coordinate += 60;


        // MODEL OPTIONS

        JLabel segregationModelOptions = new JLabel("<html><b><html><font color='gray'><b>Segregation Model Options</b></font></b>");
        segregationModelOptions.setBounds(xCenter, y_coordinate, TITLE_WIDTH, HEIGHT);
        panel.add(segregationModelOptions);

        y_coordinate += 30;



        // type of algorithm applied

        JLabel algLabel = new JLabel("Segregation Model:");
        algLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,25);
        panel.add(algLabel);

        String[] algorithms = {"Henry", "Schelling"};
        algorithmDropdown = new JComboBox(algorithms);
        algorithmDropdown.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(algorithmDropdown);

        y_coordinate += 30;



        // number of steps of the algorithm

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH, HEIGHT);
        panel.add(stepsLabel);

        stepsText = new JTextField(20);
        stepsText.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(stepsText);

        y_coordinate += 50;



        // Creating button

        applyButton = new JButton("Start Simulation");
        applyButton.setBounds(xCenter, y_coordinate, TITLE_WIDTH,  HEIGHT);
        panel.add(applyButton);

    }


}
