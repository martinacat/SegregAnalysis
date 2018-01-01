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






    private JComboBox<String> algorithmDropdown; // choose algorithm
    private JComboBox<String> generatorDropdown;
    private JComboBox<String> decayDropdown;

    private JTextField fileBrowseField;
    private JTextField sizeText;
    private JTextField stepsText;
    private JTextField aversionText;
    private JTextField maxLinksPerStepText;
    private JTextField numberOfAttributes;

    private JLabel maxLinksPerStepLabel;

    private final JButton applyButton = new JButton("Start Simulation");
    private JButton importButton;
    private JLabel aversionBiasLabel;
    private JLabel decayLabel;


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
        setFileBrowseField(new JTextField());
        getFileBrowseField().setBounds(250, y_coordinate, 150, HEIGHT);
        setImportButton(new JButton("Browse"));
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
        setSizeText(new JTextField());
        getSizeText().setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        optionsPanel.add(graphSizeLabel);
        optionsPanel.add(getSizeText());

        y_coordinate += 30;

        JLabel generatorTypeLabel = new JLabel("Generator type: ");
        generatorTypeLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        String[] generatorTypes = {"Preferential Attachment", "Random", "Random Euclidean"};
        setGeneratorDropdown(new JComboBox<>(generatorTypes));
        getGeneratorDropdown().setBounds(FIELD_X, y_coordinate, 200, HEIGHT);
        optionsPanel.add(generatorTypeLabel);
        optionsPanel.add(getGeneratorDropdown());

        y_coordinate += 30;

        setMaxLinksPerStepLabel(new JLabel("Max Links per step: "));
        getMaxLinksPerStepLabel().setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        setMaxLinksPerStepText(new JTextField());
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

        setStepsText(new JTextField(20));
        getStepsText().setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(getStepsText());

        y_coordinate += 30;

        // type of algorithm applied

        JLabel algLabel = new JLabel("Algorithm type:");
        algLabel.setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,HEIGHT);
        panel.add(algLabel);

        String[] algorithmTypes = {"Dissimilarity", "Affinity", "Both"};
        setAlgorithmDropdown(new JComboBox<>(algorithmTypes));
        getAlgorithmDropdown().setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(getAlgorithmDropdown());

        y_coordinate += 30;

        // aversion bias

        setAversionBiasLabel(new JLabel("Aversion Bias:"));
        getAversionBiasLabel().setBounds(LABEL_X, y_coordinate,LABEL_WIDTH, HEIGHT);
        panel.add(getAversionBiasLabel());

        setAversionText(new JTextField(20));
        getAversionText().setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(getAversionText());

        y_coordinate += 30;


        setDecayLabel(new JLabel("Bias decay:"));
        getDecayLabel().setBounds(LABEL_X, y_coordinate,LABEL_WIDTH,HEIGHT);
        panel.add(getDecayLabel());

        String[] decay = {"None", "Linear", "Exponential"};
        setDecayDropdown(new JComboBox<>(decay));
        getDecayDropdown().setBounds(FIELD_X, y_coordinate,FIELD_WIDTH, HEIGHT);
        panel.add(getDecayDropdown());




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
        setNumberOfAttributes(new JTextField());
        getNumberOfAttributes().setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        optionsPanel.add(numberofAttributesLabel);
        optionsPanel.add(getNumberOfAttributes());

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


    public JComboBox<String> getAlgorithmDropdown() {
        return algorithmDropdown;
    }

    public void setAlgorithmDropdown(JComboBox<String> algorithmDropdown) {
        this.algorithmDropdown = algorithmDropdown;
    }

    public JComboBox<String> getGeneratorDropdown() {
        return generatorDropdown;
    }

    public void setGeneratorDropdown(JComboBox<String> generatorDropdown) {
        this.generatorDropdown = generatorDropdown;
    }

    public JComboBox<String> getDecayDropdown() {
        return decayDropdown;
    }

    public void setDecayDropdown(JComboBox<String> decayDropdown) {
        this.decayDropdown = decayDropdown;
    }

    public JTextField getFileBrowseField() {
        return fileBrowseField;
    }

    public void setFileBrowseField(JTextField fileBrowseField) {
        this.fileBrowseField = fileBrowseField;
    }

    public JTextField getSizeText() {
        return sizeText;
    }

    public void setSizeText(JTextField sizeText) {
        this.sizeText = sizeText;
    }

    public JTextField getStepsText() {
        return stepsText;
    }

    public void setStepsText(JTextField stepsText) {
        this.stepsText = stepsText;
    }

    public JTextField getAversionText() {
        return aversionText;
    }

    public void setAversionText(JTextField aversionText) {
        this.aversionText = aversionText;
    }

    public JTextField getMaxLinksPerStepText() {
        return maxLinksPerStepText;
    }

    public void setMaxLinksPerStepText(JTextField maxLinksPerStepText) {
        this.maxLinksPerStepText = maxLinksPerStepText;
    }

    public JTextField getNumberOfAttributes() {
        return numberOfAttributes;
    }

    public void setNumberOfAttributes(JTextField numberOfAttributes) {
        this.numberOfAttributes = numberOfAttributes;
    }

    public JLabel getMaxLinksPerStepLabel() {
        return maxLinksPerStepLabel;
    }

    public void setMaxLinksPerStepLabel(JLabel maxLinksPerStepLabel) {
        this.maxLinksPerStepLabel = maxLinksPerStepLabel;
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public void setImportButton(JButton importButton) {
        this.importButton = importButton;
    }

    public JLabel getAversionBiasLabel() {
        return aversionBiasLabel;
    }

    public void setAversionBiasLabel(JLabel aversionBiasLabel) {
        this.aversionBiasLabel = aversionBiasLabel;
    }

    public JLabel getDecayLabel() {
        return decayLabel;
    }

    public void setDecayLabel(JLabel decayLabel) {
        this.decayLabel = decayLabel;
    }
}
