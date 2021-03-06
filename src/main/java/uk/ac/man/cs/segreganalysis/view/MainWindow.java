package uk.ac.man.cs.segreganalysis.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

    private static final int LABEL_X = 40;
    private static final int FIELD_X = 240;

    private static final int LABEL_WIDTH = 140;
    private static final int FIELD_WIDTH = 200;

    private static final int HEIGHT = 20;

    public final AdvancedSettingsWindow biasAdvancedSettings =
            new AdvancedSettingsWindow("Bias Advanced Settings");


    private static int y_coordinate;

    private JComboBox<String> algorithmDropdown; // choose algorithm
    private JComboBox<String> generatorDropdown;

    // measurement window
    private final JCheckBox checkBoxYules = new JCheckBox("Yule's Q");
    private final JCheckBox checkBoxDSI = new JCheckBox("DSI (Duncan Segregation Index)");
    private final JCheckBox checkBoxSave = new JCheckBox("Save graph stats");


    private JTextField fileBrowseField;
    private JTextField sizeText;
    private JTextField stepsText;
    private JTextField maxLinksPerStepText;

    private JLabel maxLinksPerStepLabel;

    private final JButton applyButton = new JButton("Start Simulation");
    private final JButton aversionBiasAdvancedSettingsButton =
            new JButton("Bias Settings");
    private JButton importButton;

    private JRadioButton fromFileRadioButton;



    private JTextField attributesDistributionText;
    private JRadioButton attributesFromFileRadioButton;


    public MainWindow(String title){

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

        getApplyButton().setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(getApplyButton());

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
        fromFileRadioButton = new JRadioButton("Import graph from file (dsg)");
        JRadioButton randomGenerationRadioButton = new JRadioButton("Generate new graph");
        randomGenerationRadioButton.setSelected(true);
        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(fromFileRadioButton);
        group.add(randomGenerationRadioButton);

        y_coordinate = 10;

        fromFileRadioButton.setBounds(10, y_coordinate, 210, HEIGHT);
        buttonsPanel.add(fromFileRadioButton);
        fileBrowseField = new JTextField();
        getFileBrowseField().setBounds(250, y_coordinate, 150, HEIGHT);
        importButton = new JButton("Browse");
        getImportButton().setBounds(400, y_coordinate, 55, HEIGHT);
        buttonsPanel.add(getFileBrowseField());
        buttonsPanel.add(getImportButton());

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
        sizeText = new JTextField("90");
        getSizeText().setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        optionsPanel.add(graphSizeLabel);
        optionsPanel.add(getSizeText());

        y_coordinate += 30;

        JLabel generatorTypeLabel = new JLabel("Generator type: ");
        generatorTypeLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        String[] generatorTypes = {"Preferential Attachment", "Random", "Random Euclidean"};
        generatorDropdown = new JComboBox<>(generatorTypes);
        getGeneratorDropdown().setBounds(FIELD_X, y_coordinate, 200, HEIGHT);
        optionsPanel.add(generatorTypeLabel);
        optionsPanel.add(getGeneratorDropdown());

        y_coordinate += 30;

        maxLinksPerStepLabel = new JLabel("Max Links per step: ");
        getMaxLinksPerStepLabel().setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        maxLinksPerStepText = new JTextField("3");
        getMaxLinksPerStepText().setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        optionsPanel.add(getMaxLinksPerStepLabel());
        optionsPanel.add(getMaxLinksPerStepText());

        // add everything to a container panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(buttonsPanel);
        panel.add(optionsPanel);

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
        attributesFromFileRadioButton = new JRadioButton("Keep the attributes from the file");
        JRadioButton randomGenerationRadioButton = new JRadioButton("Generate new attributes (override file)");
        randomGenerationRadioButton.setSelected(true);
        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(attributesFromFileRadioButton);
        group.add(randomGenerationRadioButton);

        y_coordinate = 10;

        attributesFromFileRadioButton.setBounds(10, y_coordinate, 500, HEIGHT);
        buttonsPanel.add(attributesFromFileRadioButton);

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


        JLabel attributesDistributionLabel = new JLabel("Relative size of attribute sets: ");
        attributesDistributionLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        attributesDistributionText = new JTextField("1, 1, 1");
        attributesDistributionText.setToolTipText("If the number of attributes is 3 you will have " +
                "\nto specify: size_set1, size_set2, size_set3 (e.g. 1, 1, 2). You can specify at most 8 sets.");
        attributesDistributionText.setBounds(FIELD_X+20, y_coordinate, 120, HEIGHT);
        optionsPanel.add(attributesDistributionLabel);
        optionsPanel.add(attributesDistributionText);


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

        // number of steps of the algorithm

        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH, HEIGHT);
        panel.add(stepsLabel);

        stepsText = new JTextField("1000",20);
        getStepsText().setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(getStepsText());

        y_coordinate += 30;

        // type of algorithm applied

        JLabel algorithmTypeLabel = new JLabel("Segregation Model:");
        algorithmTypeLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,HEIGHT);
        panel.add(algorithmTypeLabel);

        String[] algorithmTypeChoices = {"Dissimilarity", "Affinity", "Interleaved"};
        algorithmDropdown = new JComboBox<>(algorithmTypeChoices);
        getAlgorithmDropdown().setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(getAlgorithmDropdown());

        y_coordinate += 50;

        // aversion bias settings

        aversionBiasAdvancedSettingsButton.setBounds(130, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(aversionBiasAdvancedSettingsButton);






        return panel;
    }

    /* Creates the panel where the user specifies
     * details concerned with the measurements
     */
    private JComponent makeMeasurementsPanel() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        checkBoxYules.setSelected(true);
        checkBoxSave.setSelected(false);

        panel.add(checkBoxYules);
        panel.add(checkBoxSave);

        return panel;
    }


    public JRadioButton getAttributesFromFileRadioButton() {
        return attributesFromFileRadioButton;
    }


    public JTextField getAttributesDistributionText() {
        return attributesDistributionText;
    }

    public JComboBox<String> getAlgorithmDropdown() {
        return algorithmDropdown;
    }

    public JComboBox<String> getGeneratorDropdown() {
        return generatorDropdown;
    }

    public JTextField getFileBrowseField() {
        return fileBrowseField;
    }

    public JTextField getSizeText() {
        return sizeText;
    }

    public JTextField getStepsText() {
        return stepsText;
    }

    public JTextField getMaxLinksPerStepText() {
        return maxLinksPerStepText;
    }

    public JLabel getMaxLinksPerStepLabel() {
        return maxLinksPerStepLabel;
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public JButton getAversionBiasAdvancedSettingsButton() {
        return aversionBiasAdvancedSettingsButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JRadioButton getFromFileRadioButton() {
        return fromFileRadioButton;
    }

    public JCheckBox getCheckBoxYules() {
        return checkBoxYules;
    }

    public JCheckBox getCheckBoxDSI() {
        return checkBoxDSI;
    }

    public JCheckBox getCheckBoxSave() {
        return checkBoxSave;
    }


}
