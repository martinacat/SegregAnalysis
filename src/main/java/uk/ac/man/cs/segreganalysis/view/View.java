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

    public static final int HEIGHT = 20;


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

    /* Creates the panel where the user specifies how the initial
     * graph is generated
     */
    private JComponent makeGraphGenerationPanel() {

        // First sub-panel: buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(null);
        buttonsPanel.setMaximumSize(new Dimension(1000, 70));

        // Create buttons
        JRadioButton fromFileRadioButton = new JRadioButton("Import graph from file (dsg)");
        JRadioButton randomGenerationRadioButton = new JRadioButton("Generate new graph");
        fromFileRadioButton.setSelected(true);
        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(fromFileRadioButton);
        group.add(randomGenerationRadioButton);

        y_coordinate = 10;

        fromFileRadioButton.setBounds(10, y_coordinate, 210, HEIGHT);
        buttonsPanel.add(fromFileRadioButton);
        fileBrowseField = new JTextField();
        fileBrowseField.setBounds(250, y_coordinate, 150, HEIGHT);
        importButton = new JButton("Browse");
        importButton.setBounds(400, y_coordinate, 55, HEIGHT);
        buttonsPanel.add(fileBrowseField);
        buttonsPanel.add(importButton);

        y_coordinate += 30;

        randomGenerationRadioButton.setBounds(10, y_coordinate , 500, HEIGHT);
        buttonsPanel.add(randomGenerationRadioButton);



        // Second sub-Panel: Generation options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        optionsPanel.setMaximumSize(new Dimension(1000, 150));

        TitledBorder title = BorderFactory.createTitledBorder("Generation options");
        title.setTitleJustification(TitledBorder.CENTER);
        optionsPanel.setBorder(title);

        y_coordinate = 30;

        JLabel graphSizeLabel = new JLabel("Graph size: ");
        graphSizeLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        sizeText = new JTextField();
        sizeText.setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        optionsPanel.add(graphSizeLabel);
        optionsPanel.add(sizeText);

        y_coordinate += 30;

        JLabel generatorTypeLabel = new JLabel("Generator type: ");
        generatorTypeLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        String[] generatorTypes = {"Preferential Attachment", "Random", "Random Euclidean"};
        generatorDropdown = new JComboBox<>(generatorTypes);
        generatorDropdown.setBounds(FIELD_X, y_coordinate, 200, HEIGHT);
        optionsPanel.add(generatorTypeLabel);
        optionsPanel.add(generatorDropdown);

        y_coordinate += 30;

        maxLinksPerStepLabel = new JLabel("Max Links per step: ");
        maxLinksPerStepLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        maxLinksPerStepText = new JTextField();
        maxLinksPerStepText.setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        optionsPanel.add(maxLinksPerStepLabel);
        optionsPanel.add(maxLinksPerStepText);

        // add everything to a container panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(buttonsPanel);
        panel.add(optionsPanel);

        return panel;
    }

    /* Creates the panel where the user specifies
     * how to run the simulation
     */
    private JComponent makeSimulationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        y_coordinate = 40;

        // type of algorithm applied

        JLabel algLabel = new JLabel("Algorithm type:");
        algLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,HEIGHT);
        panel.add(algLabel);

        String[] algorithmTypes = {"Dissimilarity", "Affinity", "Both"};
        algorithmDropdown = new JComboBox<>(algorithmTypes);
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

        y_coordinate += 30;

        // aversion bias

        JLabel aversionBiasLabel = new JLabel("Aversion Bias:");
        aversionBiasLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH, HEIGHT);
        panel.add(aversionBiasLabel);

        aversionText = new JTextField(20);
        aversionText.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(aversionText);

        y_coordinate += 30;

        JLabel decayLabel = new JLabel("Bias decay:");
        decayLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,HEIGHT);
        panel.add(decayLabel);

        String[] decay = {"None", "Linear", "Exponential"};
        decayDropdown = new JComboBox<>(decay);
        decayDropdown.setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(decayDropdown);




        return panel;
    }

    /* Creates the panel where the user specifies
     * details concerned with the measurements
     */
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

    /* Creates the panel where the user specifies
     * details concerned with the attributes
     */
    private JComponent makeAttributesPanel() {

        // First sub-panel: buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(null);
        buttonsPanel.setMaximumSize(new Dimension(1000, 70));

        // Create buttons
        JRadioButton fromFileRadioButton = new JRadioButton("Keep the attributes from the file");
        JRadioButton randomGenerationRadioButton = new JRadioButton("Generate new attributes (override file)");
        fromFileRadioButton.setSelected(true);
        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(fromFileRadioButton);
        group.add(randomGenerationRadioButton);

        y_coordinate = 10;

        fromFileRadioButton.setBounds(10, y_coordinate, 210, HEIGHT);
        buttonsPanel.add(fromFileRadioButton);

        y_coordinate += 30;

        randomGenerationRadioButton.setBounds(10, y_coordinate , 500, HEIGHT);
        buttonsPanel.add(randomGenerationRadioButton);



        // Second sub-Panel: Attributes Generation options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        optionsPanel.setMaximumSize(new Dimension(1000, 150));

        TitledBorder title = BorderFactory.createTitledBorder("Attributes generation options");
        title.setTitleJustification(TitledBorder.CENTER);
        optionsPanel.setBorder(title);

        y_coordinate = 30;

        JLabel numberofAttributesLabel = new JLabel("Number of attributes: ");
        numberofAttributesLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        numberOfAttributes = new JTextField();
        numberOfAttributes.setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        optionsPanel.add(numberofAttributesLabel);
        optionsPanel.add(numberOfAttributes);

        y_coordinate += 30;

        JLabel attributesDistributionLabel = new JLabel("Attributes distribution: ");
        attributesDistributionLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        optionsPanel.add(attributesDistributionLabel);


        // add everything to a container panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(buttonsPanel);
        panel.add(optionsPanel);

        return panel;
    }






}
