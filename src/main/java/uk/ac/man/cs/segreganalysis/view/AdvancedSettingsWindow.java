package uk.ac.man.cs.segreganalysis.view;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AdvancedSettingsWindow extends JFrame {

    private final int WIDTH = 300;

    private final JButton applyChangesButton = new JButton("Apply");
    private JTextField initialBiasForAllText = new JTextField("1");
    private final JRadioButton differentForSetsRadioButton = new JRadioButton("Different aversion bias for sets of nodes");

    private final JRadioButton sameForAllRadioButton = new JRadioButton("Same initial bias for all the nodes");


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

    private void populateView(JPanel container) {


        // panel with generic options for bias

        JPanel p = new JPanel(new SpringLayout());
        p.setMaximumSize(new Dimension(WIDTH, 100));

        JLabel biasEvolutionLabel = new JLabel("Bias evolution in time:", JLabel.TRAILING);
        p.add(biasEvolutionLabel);
        String[] biasEvolutionInTimeChoices = {"Decay", "Growth"};
        JComboBox biasEvolutionInTimeDropdown = new JComboBox<>(biasEvolutionInTimeChoices);
        biasEvolutionLabel.setLabelFor(biasEvolutionInTimeDropdown);
        p.add(biasEvolutionInTimeDropdown);

        JLabel biasFunctionLabel = new JLabel("Bias evolution function:", JLabel.TRAILING);
        p.add(biasFunctionLabel);
        String[] biasEvolutionFunctionChoices = {"None", "Linear", "Curve"};
        JComboBox biasEvolutionFunctionDropdown = new JComboBox<>(biasEvolutionFunctionChoices);
        biasFunctionLabel.setLabelFor(biasEvolutionFunctionDropdown);
        p.add(biasEvolutionFunctionDropdown);


        // Lay out the panel p
        SpringUtilities.makeCompactGrid(p,
                2, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad


        container.add(p);


        // panel with options for the distribution of the bias (in sets or same for all nodes)

        JPanel q = new JPanel();
        q.setLayout(new FlowLayout());
        //q.setMaximumSize(new Dimension(450, 300));

        sameForAllRadioButton.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(sameForAllRadioButton);
        buttonGroup.add(differentForSetsRadioButton);


        // layout

        q.add(sameForAllRadioButton);
        q.add(initialBiasForAllText);

        q.add(differentForSetsRadioButton);

        JLabel numberOfSetsLabel = new JLabel("Number of sets:", JLabel.TRAILING);
        q.add(numberOfSetsLabel);
        JTextField numberOfSetsText = new JTextField(10);
        numberOfSetsLabel.setLabelFor(numberOfSetsText);
        q.add(numberOfSetsText);

        container.add(q);

//        JPanel numberOfSetsPanel = new JPanel(new SpringLayout());

        // panel with the different aversion bias for sets of nodes
//        JLabel numberOfSetsLabel = new JLabel("Number of sets:", JLabel.TRAILING);
//        numberOfSetsPanel.add(numberOfSetsLabel);
//        JTextField numberOfSetsText = new JTextField(10);
//        numberOfSetsLabel.setLabelFor(numberOfSetsText);
//        numberOfSetsPanel.add(numberOfSetsText);

//        SpringUtilities.makeCompactGrid(numberOfSetsPanel,
//                1, 2, //rows, cols
//                6, 6,        //initX, initY
//                6, 6);       //xPad, yPad
//
//        container.add(numberOfSetsPanel);


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

            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            setsPanel.add(textField);

            JLabel m = new JLabel("size:", JLabel.TRAILING);
            setsPanel.add(m);

            JTextField textField1 = new JTextField(10);
            m.setLabelFor(textField1);

            setsPanel.add(textField1);
        }

        //Lay out the panel.
        SpringUtilities.makeGrid(setsPanel,
                4, 5, //rows, cols
                5, 5, //initialX, initialY
                5, 5);//xPad, yPad     //xPad, yPad

        container.add(setsPanel);

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


}
