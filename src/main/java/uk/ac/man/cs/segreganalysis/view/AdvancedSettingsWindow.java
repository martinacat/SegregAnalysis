package uk.ac.man.cs.segreganalysis.view;


import uk.ac.man.cs.segreganalysis.utilities.SpringUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AdvancedSettingsWindow extends JFrame {

    public static final int HEIGHT = 20;
    private final int WIDTH = 350;
    private static final int LABEL_X = 10;
    private static final int FIELD_X = 280;

    private int y_coordinate = 10;

    private JTextField initialBiasForAllText = new JTextField("1");

    private JTextField coefficientText = new JTextField("0.01", 10);

    private final JRadioButton differentForSetsRadioButton = new JRadioButton("Different bias for sets of nodes");

    private final JRadioButton sameForAllRadioButton = new JRadioButton("Same initial bias for all the nodes");

    private JTextField[] sizeTextFields = new JTextField[4]; // relative size of set

    private JTextField[] biasTextFields = new JTextField[4]; // aversion bias for that set
    private final JTextField numberOfSetsText = new JTextField("4",10);
    private JComboBox biasEvolutionInTimeDropdown;
    private JComboBox biasEvolutionFunctionDropdown;
    private final JLabel coefficientLabel = new JLabel("Coefficient b:");
    private final JLabel biasEvolutionLabel = new JLabel("Bias evolution in time:");
    private final JPanel evolutionOptionsPanel = new JPanel(null);
    private JPanel container;
    private final JPanel distributionOptionsPanel = new JPanel();
    private final JLabel linearStartBiasLabel = new JLabel("Bias Start value");
    private final JLabel linearEndBiasLabel = new JLabel("Bias End value");
    private final JTextField linearEndBiasText = new JTextField("1");
    private final JTextField linearStartBiasText = new JTextField("0");


    public AdvancedSettingsWindow (String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // top to bottom
        add(container);

        populateView(container);

        pack();
        setVisible(false);
        setSize(WIDTH, 380);
        setLocationRelativeTo(null); // center
    }


    public JComboBox getBiasEvolutionInTimeDropdown() {
        return biasEvolutionInTimeDropdown;
    }

    public JComboBox getBiasEvolutionFunctionDropdown() {
        return biasEvolutionFunctionDropdown;
    }

    public JLabel getCoefficientLabel() {
        return coefficientLabel;
    }

    public JLabel getBiasEvolutionLabel() {
        return biasEvolutionLabel;
    }



    private void populateView(JPanel container) {

        this.container = container;

        populateDistributionOptionsPanel();



        JPanel setsPanel = new JPanel();
        setsPanel.setLayout(new SpringLayout());
        setsPanel.setMaximumSize(new Dimension(WIDTH, 150));

        TitledBorder title = BorderFactory.createTitledBorder("Sets bias and relative size");
        title.setTitleJustification(TitledBorder.CENTER);
        setsPanel.setBorder(title);



        for (int i = 1; i <= 4; i++) {


            JLabel l = new JLabel("Set " + i);
            setsPanel.add(l);

            JLabel n = new JLabel("bias:", JLabel.TRAILING);
            setsPanel.add(n);

            biasTextFields[i-1] = new JTextField("1",10);
            biasTextFields[i-1].setEnabled(false);
            l.setLabelFor(biasTextFields[i-1]);
            setsPanel.add(biasTextFields[i-1]);

            JLabel m = new JLabel("size:", JLabel.TRAILING);
            setsPanel.add(m);

            sizeTextFields[i-1] = new JTextField("1",10);
            sizeTextFields[i-1].setEnabled(false);
            m.setLabelFor(sizeTextFields[i-1]);
            setsPanel.add(sizeTextFields[i-1]);
        }

        //Lay out the panel.
        SpringUtilities.makeGrid(setsPanel,
                4, 5, //rows, cols
                5, 5, //initialX, initialY
                5, 5);//xPad, yPad     //xPad, yPad

        container.add(setsPanel);

    }



    public JLabel getLinearStartBiasLabel() {
        return linearStartBiasLabel;
    }

    public JLabel getLinearEndBiasLabel() {
        return linearEndBiasLabel;
    }

    public JTextField getLinearEndBiasText() {
        return linearEndBiasText;
    }

    public JTextField getLinearStartBiasText() {
        return linearStartBiasText;
    }

    private void populateDistributionOptionsPanel() {


        distributionOptionsPanel.setLayout(null);

        // panel with generic options for bias


        JLabel biasFunctionLabel = new JLabel("Evolution function:");
        biasFunctionLabel.setBounds(LABEL_X, y_coordinate, 250, HEIGHT);
        distributionOptionsPanel.add(biasFunctionLabel);
        String[] biasEvolutionFunctionChoices = {"None", "Linear", "Curve"};
        biasEvolutionFunctionDropdown = new JComboBox<>(biasEvolutionFunctionChoices);
        biasEvolutionFunctionDropdown.setBounds(FIELD_X-100, y_coordinate, 150, HEIGHT);

        biasFunctionLabel.setLabelFor(biasEvolutionFunctionDropdown);
        distributionOptionsPanel.add(biasEvolutionFunctionDropdown);

        y_coordinate += 30;

        // Starting Bias (linear mode)
        linearStartBiasLabel.setBounds(LABEL_X, y_coordinate, 250, HEIGHT);
        linearStartBiasLabel.setVisible(false);
        linearStartBiasText.setBounds(FIELD_X-100, y_coordinate, 150, HEIGHT);
        linearStartBiasText.setVisible(false);
        distributionOptionsPanel.add(linearStartBiasLabel);
        distributionOptionsPanel.add(linearStartBiasText);


        // Same Y

        biasEvolutionLabel.setBounds(LABEL_X, y_coordinate, 250, HEIGHT);
        biasEvolutionLabel.setVisible(false);
        distributionOptionsPanel.add(biasEvolutionLabel);
        String[] biasEvolutionInTimeChoices = {"Decay", "Growth"};
        biasEvolutionInTimeDropdown = new JComboBox<>(biasEvolutionInTimeChoices);
        biasEvolutionInTimeDropdown.setBounds(FIELD_X-100, y_coordinate, 150, HEIGHT);
        biasEvolutionInTimeDropdown.setVisible(false);
        biasEvolutionInTimeDropdown.setToolTipText(
                "<html>" +
                        "<img src=" + getClass().getResource("/img/decay.png") + "/><br>" +
                        "<img src=" + getClass().getResource("/img/decay_math.png") + "/><br><br>" +
                        "<img src=" + getClass().getResource("/img/growth.png") + "/><br> " +
                        "<img src=" + getClass().getResource("/img/growth_math.png") + "/>" +
                        "</html>");
        biasEvolutionLabel.setLabelFor(biasEvolutionInTimeDropdown);
        distributionOptionsPanel.add(biasEvolutionInTimeDropdown);

        y_coordinate += 30;

        // End Bias (linear mode)
        linearEndBiasLabel.setBounds(LABEL_X, y_coordinate, 250, HEIGHT);
        linearEndBiasLabel.setVisible(false);
        linearEndBiasText.setBounds(FIELD_X-100, y_coordinate, 150, HEIGHT);
        linearEndBiasText.setVisible(false);
        distributionOptionsPanel.add(linearEndBiasLabel);
        distributionOptionsPanel.add(linearEndBiasText);


        // Same Y

        coefficientLabel.setVisible(false);
        coefficientLabel.setBounds(10, y_coordinate, 250, HEIGHT);
        coefficientText.setVisible(false);
        coefficientText.setBounds(FIELD_X, y_coordinate, 50, HEIGHT);
        distributionOptionsPanel.add(coefficientLabel);
        coefficientLabel.setLabelFor(coefficientText);
        coefficientText.setToolTipText("The exponential coefficient when \"Curve mode\" is selected");
        distributionOptionsPanel.add(coefficientText);

        y_coordinate += 30;



        // panel with options for the distribution of the bias (in sets or same for all nodes)



        // create button group
        sameForAllRadioButton.setSelected(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(sameForAllRadioButton);
        buttonGroup.add(differentForSetsRadioButton);


        // layout

        sameForAllRadioButton.setBounds(LABEL_X, y_coordinate, 250, HEIGHT);
        initialBiasForAllText.setBounds(FIELD_X, y_coordinate, 50, HEIGHT);;
        distributionOptionsPanel.add(sameForAllRadioButton);
        distributionOptionsPanel.add(initialBiasForAllText);

        y_coordinate += 30;

        differentForSetsRadioButton.setBounds(LABEL_X, y_coordinate, 250, HEIGHT);
        distributionOptionsPanel.add(differentForSetsRadioButton);

        y_coordinate += 30;

        JLabel numberOfSetsLabel = new JLabel("Number of sets:", JLabel.TRAILING);
        numberOfSetsLabel.setBounds(LABEL_X, y_coordinate, 200, HEIGHT);
        numberOfSetsText.setBounds(FIELD_X, y_coordinate, 50, HEIGHT);

        distributionOptionsPanel.add(numberOfSetsLabel);
        numberOfSetsLabel.setLabelFor(numberOfSetsText);
        numberOfSetsText.setEnabled(false);
        distributionOptionsPanel.add(numberOfSetsText);

        container.add(distributionOptionsPanel);
    }



    public JRadioButton getDifferentForSetsRadioButton() {
        return differentForSetsRadioButton;
    }

    public JTextField getInitialBiasForAllText() {
        return initialBiasForAllText;
    }

    public JRadioButton getSameForAllRadioButton() {
        return sameForAllRadioButton;
    }

    public JTextField[] getSizeTextFields() {
        return sizeTextFields;
    }


    public JTextField getNumberOfSetsText() {
        return numberOfSetsText;
    }

    public JTextField getCoefficientText() {
        return coefficientText;
    }

    public JTextField[] getBiasTextFields() {
        return biasTextFields;
    }

    public void setBiasTextFields (int index, String value) {

        // check index not out of bound
        if (index > biasTextFields.length) {return;}
        if (index < 0) {return;}

        biasTextFields[index].setText(value);
    }

    public void setSizeTextFields (int index, String value) {

        // check index not out of bound
        if (index > sizeTextFields.length) {return;}
        if (index < 0) {return;}

        sizeTextFields[index].setText(value);
    }

    private void drawGrid(JPanel p) {
        // Lay out the panel evolutionOptionsPanel
        SpringUtilities.makeCompactGrid(p,
                3, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        container.add(p);
    }

}
