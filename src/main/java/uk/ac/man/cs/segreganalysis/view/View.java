package uk.ac.man.cs.segreganalysis.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class View extends JFrame {

    private static final int LABEL_X = 40;
    private static final int FIELD_X = 240;

    private static final int LABEL_WIDTH = 140;
    private static final int FIELD_WIDTH = 200;

    private static int y_coordinate;






    public JComboBox<String> algorithmDropdown; // choose algorithm
    public JComboBox<String> generatorDropdown;
    public JComboBox<String> decayDropdown;

    public JTextField fileBrowseField;
    public JTextField sizeText;
    public JTextField stepsText;
    public JTextField aversionText;
    public JTextField maxLinksPerStepText;
    public JTextField numberOfAttributes;

    public JLabel maxLinksPerStepLabel;

    public final JButton applyButton = new JButton("Start Simulation");
    public JButton importButton;



    public View(String title){

        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // top to bottom
        add(container);

        populateView(container);

        pack();
        setVisible(true);
        setSize(500, 300);
        setLocationRelativeTo(null); // center

    }

    private void populateView(JPanel container) {

        JTabbedPane tabbedPane = new JTabbedPane();


        JComponent graphGenPanel = makeGraphGenerationPanel();
        tabbedPane.addTab("Graph Generation", graphGenPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent attributesPanel = makeAttributesPanel();
        tabbedPane.addTab("Graph Attributes", attributesPanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent simulationPanel = makeSimulationPanel();
        tabbedPane.addTab("Simulation", simulationPanel);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent measurementsPanel = makeMeasurementsPanel();
        tabbedPane.addTab("Measurements", measurementsPanel);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        container.add(tabbedPane);

        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(applyButton);

    }

    private JComponent makeMeasurementsPanel() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JCheckBox checkBoxYules = new JCheckBox("Yule's Q");
        JCheckBox checkBoxDSI = new JCheckBox("DSI (Duncan Segregation Index)");
        JCheckBox save = new JCheckBox("Save initial graph for comparison");

        checkBoxYules.setSelected(true);
        checkBoxDSI.setSelected(true);
        save.setSelected(false);

        panel.add(checkBoxYules);
        panel.add(checkBoxDSI);
        panel.add(save);

        return panel;
    }

    private JComponent makeAttributesPanel() {
        JPanel panel = new JPanel(false);
        return panel;
    }

    private JComponent makeGraphGenerationPanel() {

        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons
        JRadioButton fromFile = new JRadioButton("Import graph from file (dsg)");
        JRadioButton randomGeneration = new JRadioButton("Generate new graph");

        fromFile.setSelected(true);

        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(fromFile);
        group.add(randomGeneration);

        // Import graph from file (Panel)
        JPanel importOptionsPanel = new JPanel();
        importOptionsPanel.setLayout(new BoxLayout(importOptionsPanel, BoxLayout.X_AXIS));

        fileBrowseField = new JTextField();
        JLabel fileBrowseLabel = new JLabel("File:  ");
        fileBrowseField.setMaximumSize(new Dimension(200, 20 ));
        importButton = new JButton("Browse");
        importOptionsPanel.add(fileBrowseLabel);
        importOptionsPanel.add(fileBrowseField);
        importOptionsPanel.add(importButton);

        // Generation options (Panel)
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        TitledBorder title = BorderFactory.createTitledBorder("Generation options");
        title.setTitleJustification(TitledBorder.CENTER);
        optionsPanel.setBorder(title);

        y_coordinate = 30;

        JLabel graphSizeLabel = new JLabel("Graph size: ");
        graphSizeLabel.setBounds(LABEL_X, y_coordinate, 200, 20);
        sizeText = new JTextField();
        sizeText.setBounds(FIELD_X, y_coordinate, 50, 20);
        optionsPanel.add(graphSizeLabel);
        optionsPanel.add(sizeText);

        y_coordinate += 30;

        JLabel generatorTypeLabel = new JLabel("Generator type: ");
        generatorTypeLabel.setBounds(LABEL_X, y_coordinate, 200, 20);
        String[] generatorTypes = {"Preferential Attachment", "Random", "Random Euclidean"};
        generatorDropdown = new JComboBox<>(generatorTypes);
        generatorDropdown.setBounds(FIELD_X, y_coordinate, 200, 20);
        optionsPanel.add(generatorTypeLabel);
        optionsPanel.add(generatorDropdown);

        y_coordinate += 30;

        maxLinksPerStepLabel = new JLabel("Max Links per step: ");
        maxLinksPerStepLabel.setBounds(LABEL_X, y_coordinate, 200, 20);
        maxLinksPerStepText = new JTextField();
        maxLinksPerStepText.setBounds(FIELD_X, y_coordinate, 50, 20);
        optionsPanel.add(maxLinksPerStepLabel);
        optionsPanel.add(maxLinksPerStepText);

        // add everything to the container panel
        panel.add(fromFile);
        panel.add(randomGeneration);
        panel.add(importOptionsPanel);
        panel.add(optionsPanel);

        return panel;
    }

    private JComponent makeSimulationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        y_coordinate = 40;

        // type of algorithm applied

        JLabel algLabel = new JLabel("Algorithm type:");
        algLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,25);
        panel.add(algLabel);

        String[] algorithmTypes = {"Dissimilarity", "Affinity", "Both"};
        algorithmDropdown = new JComboBox<>(algorithmTypes);
        algorithmDropdown.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, 20);
        panel.add(algorithmDropdown);

        y_coordinate += 30;


        // number of steps of the algorithm

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH, 20);
        panel.add(stepsLabel);

        stepsText = new JTextField(20);
        stepsText.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, 20);
        panel.add(stepsText);

        y_coordinate += 30;

        // aversion bias

        JLabel aversionBiasLabel = new JLabel("Aversion Bias:");
        aversionBiasLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH, 20);
        panel.add(aversionBiasLabel);

        aversionText = new JTextField(20);
        aversionText.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, 20);
        panel.add(aversionText);

        y_coordinate += 30;

        JLabel decayLabel = new JLabel("Bias decay:");
        decayLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,20);
        panel.add(decayLabel);

        String[] decay = {"None", "Linear", "Exponential"};
        decayDropdown = new JComboBox<>(decay);
        decayDropdown.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, 20);
        panel.add(decayDropdown);




        return panel;
    }



}
