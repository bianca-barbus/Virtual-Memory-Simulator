package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class View extends JFrame {
    //TOP PAGE PANEL
    private JButton setMemoryTypeButton;
    private JComboBox<String> memoryTypeComboBox;

    //PAGING PANEL
    private JButton choosePolicyButton;
    private JTextField pageNumberField;
    private JTextField dataField;
    private JTextField memorySizeField;
    private JButton setSizeButton;
    private JComboBox<String> replacementPolicyComboBox;

    //SEGMENTING PANEL
    private JButton chooseSegmentingPolicyButton;
    private JComboBox<String> segmentAllocationPolicy;
    private JTextField freeBlocksBaseAddressField, freeBlocksSizeField;
    private JTextField segmentSizeField, segmentDataField;
    private JButton setParametersButton;

    //BUTTON PANEL
    private JButton requestPageButton;
    private JButton requestSegmentButton;
    private JButton displayMemoryButton;
    private JButton displaySimulationButton;

    //TEXT PANEL
    private JTextArea outputTextArea;

    public View() {
        setTitle("Virtual Memory Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // TOP PAGE PANEL
        JPanel memoryTypePanel = new JPanel();
        memoryTypePanel.setLayout(new GridLayout(1, 2, 10, 10));
        memoryTypeComboBox = new JComboBox<>(new String[]{"PAGING", "SEGMENTING"});
        memoryTypeComboBox.setPreferredSize(new Dimension(100, 30));
        memoryTypePanel.add(new JLabel("Memory Type:"));
        memoryTypePanel.add(memoryTypeComboBox);
        setMemoryTypeButton = new JButton("Set Memory Type");
        memoryTypePanel.add(setMemoryTypeButton);
        add(memoryTypePanel, BorderLayout.PAGE_START);
        memoryTypePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // SPLIT PANEL
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(30);

        // PAGING PANEL - TOP
        JPanel pagingPanel = new JPanel();
        GridBagLayout gridBagLayout1 = new GridBagLayout();
        pagingPanel.setLayout(gridBagLayout1);
        GridBagConstraints gbc1 = new GridBagConstraints();
        pagingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pageNumberField = new JTextField();
        pageNumberField.setPreferredSize(new Dimension(100, 20));
        dataField = new JTextField();
        dataField.setPreferredSize(new Dimension(100, 20));
        memorySizeField = new JTextField();
        memorySizeField.setPreferredSize(new Dimension(100, 20));
        replacementPolicyComboBox = new JComboBox<>(new String[]{"FIFO", "LRU"});
        replacementPolicyComboBox.setPreferredSize(new Dimension(100, 20));

        gbc1.anchor = GridBagConstraints.WEST;
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        gbc1.insets = new Insets(10,20,10,20);
        pagingPanel.add(new JLabel("Replacement Policy:"), gbc1);
        gbc1.gridx = 1;
        pagingPanel.add(replacementPolicyComboBox, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 2;
        gbc1.gridwidth = 2;
        gbc1.anchor = GridBagConstraints.CENTER;
        choosePolicyButton = new JButton("Set Policy");
        pagingPanel.add(choosePolicyButton, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 3;
        gbc1.anchor = GridBagConstraints.WEST;  // Align text to the left
        pagingPanel.add(new JLabel("Page Number:"), gbc1);
        gbc1.gridx = 1;
        pagingPanel.add(pageNumberField, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 4;
        pagingPanel.add(new JLabel("Data:"), gbc1);
        gbc1.gridx = 1;
        pagingPanel.add(dataField, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 5;
        pagingPanel.add(new JLabel("Memory Size:"), gbc1);
        gbc1.gridx = 1;
        pagingPanel.add(memorySizeField, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 6;
        gbc1.gridwidth = 2; // Span across both columns
        gbc1.anchor = GridBagConstraints.CENTER;
        setSizeButton = new JButton("Set size");
        pagingPanel.add(setSizeButton, gbc1);


        // SEGMENTING PANEL - BOTTOM
        JPanel segmentationPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        segmentationPanel.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        segmentationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        freeBlocksBaseAddressField = new JTextField();
        freeBlocksBaseAddressField.setPreferredSize(new Dimension(100, 20));
        freeBlocksSizeField = new JTextField();
        freeBlocksSizeField.setPreferredSize(new Dimension(100, 20));
        segmentSizeField = new JTextField();
        segmentSizeField.setPreferredSize(new Dimension(100, 20));
        segmentDataField = new JTextField();
        segmentDataField.setPreferredSize(new Dimension(100, 20));
        segmentAllocationPolicy = new JComboBox<>(new String[] {"BestFit", "FirstFit"});
        segmentAllocationPolicy.setPreferredSize(new Dimension(100, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,20,10,20);
        segmentationPanel.add(new JLabel("Allocation policy:"), gbc);
        gbc.gridx = 1;
        segmentationPanel.add(segmentAllocationPolicy, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        chooseSegmentingPolicyButton = new JButton("Set Policy");
        segmentationPanel.add(chooseSegmentingPolicyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        segmentationPanel.add(new JLabel("Block base address:"), gbc);
        gbc.gridx = 1;
        segmentationPanel.add(freeBlocksBaseAddressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        segmentationPanel.add(new JLabel("Block size:"), gbc);
        gbc.gridx = 1;
        segmentationPanel.add(freeBlocksSizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        setParametersButton = new JButton("Set parameters");
        segmentationPanel.add(setParametersButton, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 5;
        segmentationPanel.add(new JLabel("Segment Size:"), gbc);
        gbc.gridx = 1;
        segmentationPanel.add(segmentSizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        segmentationPanel.add(new JLabel("Segment Data:"), gbc);
        gbc.gridx = 1;
        segmentationPanel.add(segmentDataField, gbc);


        splitPane.setTopComponent(pagingPanel);
        splitPane.setBottomComponent(segmentationPanel);
        add(splitPane, BorderLayout.AFTER_LINE_ENDS);

        // OUTPUT TEXT AREA PANEL
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        Border border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Output", TitledBorder.CENTER, TitledBorder.TOP);

        scrollPane.setBorder(border);
        add(scrollPane, BorderLayout.CENTER);

        //BUTTON PANEL
        JPanel buttonPanel = new JPanel();
        requestPageButton = new JButton("Request Page");
        requestSegmentButton = new JButton("Request segment");
        displayMemoryButton = new JButton("Display Memory");
        displaySimulationButton = new JButton("Simulation");

        buttonPanel.add(requestPageButton);
        buttonPanel.add(requestSegmentButton);
        buttonPanel.add(displayMemoryButton);
        buttonPanel.add(displaySimulationButton);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    public JComboBox<String> getMemoryTypeComboBox() {
        return memoryTypeComboBox;
    }

    public JButton getSetMemoryTypeButton() {
        return setMemoryTypeButton;
    }

    public JButton getRequestPageButton() {
        return requestPageButton;
    }
    public JButton getRequestSegmentButton(){
        return requestSegmentButton;
    }

    public JButton getDisplayMemoryButton() {
        return displayMemoryButton;
    }

    public JButton getChoosePolicyButton() {
        return choosePolicyButton;
    }

    public JButton getChooseSegmentingPolicyButton() {
        return chooseSegmentingPolicyButton;
    }

    public JTextField getPageNumberField() {
        return pageNumberField;
    }

    public JTextField getDataField() {
        return dataField;
    }

    public JTextField getMemorySizeField() {
        return memorySizeField;
    }

    public JButton getSetSizeButton() {
        return setSizeButton;
    }

    public JButton getSetParametersButton() {
        return setParametersButton;
    }

    public JButton getDisplaySimulationButton() {
        return displaySimulationButton;
    }

    public JTextField getFreeBlocksBaseAddressField() {
        return freeBlocksBaseAddressField;
    }

    public JTextField getFreeBlocksSizeField() {
        return freeBlocksSizeField;
    }

    public JTextField getSegmentSizeField() {
        return segmentSizeField;
    }

    public JTextField getSegmentDataField() {
        return segmentDataField;
    }

    public JComboBox<String> getReplacementPolicyComboBox() {
        return replacementPolicyComboBox;
    }

    public JComboBox<String> getSegmentAllocationPolicy() {
        return segmentAllocationPolicy;
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }

}
