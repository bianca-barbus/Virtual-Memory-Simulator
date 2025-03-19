package gui;

import logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private VirtualMemoryManager virtualMemoryManager;
    private View view;
    private int memorySize;
    private boolean isSegmented;

    public Controller(VirtualMemoryManager virtualMemoryManager, View view, int memorySize) {
        this.virtualMemoryManager = virtualMemoryManager;
        this.view = view;
        this.memorySize = memorySize;
        this.isSegmented = false;

        initializeVirtualMemoryManager();

        view.getRequestPageButton().addActionListener(new RequestPageListener());
        view.getRequestSegmentButton().addActionListener(new RequestSegmentListener());
        view.getDisplayMemoryButton().addActionListener(new DisplayMemoryListener());
        view.getChoosePolicyButton().addActionListener(new SetPolicyListener());
        view.getChooseSegmentingPolicyButton().addActionListener(new setSegmentingPolicyListener());
        view.getSetSizeButton().addActionListener(new SetSizeListener());
        view.getSetMemoryTypeButton().addActionListener(new SetMemoryTypeListener());
        view.getSetParametersButton().addActionListener(new SetParametersListener());
        view.getDisplaySimulationButton().addActionListener(new DisplaySimulationListener());
    }

    private void initializeVirtualMemoryManager() {
        if (!isSegmented) {
            String selectedPolicy = (String) view.getReplacementPolicyComboBox().getSelectedItem();
            PRA pageReplacementPolicy = "FIFO".equals(selectedPolicy) ? new FIFOReplacement() : new LRUReplacement();
            virtualMemoryManager = new VirtualMemoryManager(memorySize, pageReplacementPolicy);
        }
        else{
            String selectedPolicy = (String) view.getSegmentAllocationPolicy().getSelectedItem();
            AllocationPolicy allocationPolicy = "BestFit".equals(selectedPolicy) ? new BestFitPolicy() : new FirstFitPolicy();
            virtualMemoryManager = new VirtualMemoryManager(null, allocationPolicy);
        }
    }

    private class SetSizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String sizeText = view.getMemorySizeField().getText();
                int newSize = Integer.parseInt(sizeText);

                if (newSize <= 0) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid positive memory size.", "Invalid Size", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                memorySize = newSize;
                initializeVirtualMemoryManager();
                JOptionPane.showMessageDialog(view, "Memory size set to " + memorySize + " pages.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Please enter a valid integer for memory size.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SetParametersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String freeBlockBaseAddress = view.getFreeBlocksBaseAddressField().getText();
                int baseAddress = Integer.parseInt(freeBlockBaseAddress);

                String freeBlockSize = view.getFreeBlocksSizeField().getText();
                int blockSize = Integer.parseInt(freeBlockSize);

                if (blockSize <= 0 && baseAddress < 0) {
                    JOptionPane.showMessageDialog(view, "Please enter valid parameters", "Invalid parameters", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                virtualMemoryManager.addFreeBlock(baseAddress, blockSize);
                JOptionPane.showMessageDialog(view, "Parameter set");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Please enter valid parameters", "Invalid parameters", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private class RequestPageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int pageNumber = Integer.parseInt(view.getPageNumberField().getText());
                String data = view.getDataField().getText();
                if (data.isEmpty()) data = null;

                virtualMemoryManager.requestPage(pageNumber, data);
                JOptionPane.showMessageDialog(view, "Page requested successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid page number!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private class RequestSegmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int segmentSize = Integer.parseInt(view.getSegmentSizeField().getText());
                String data = view.getSegmentDataField().getText();
                if (data.isEmpty()) data = null;

                virtualMemoryManager.requestSegment(segmentSize, data);
                JOptionPane.showMessageDialog(view, "Segment requested successfully");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid segment size", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private class DisplayMemoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String memoryState = virtualMemoryManager.displayMemoryState();
            view.getOutputTextArea().setText(memoryState);
        }
    }
    private class DisplaySimulationListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            MemorySimulator simulator = new MemorySimulator();
            String memoryState = simulator.runSimulation();
            view.getOutputTextArea().setText(memoryState);
        }
    }

    private class SetPolicyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String policy = (String) view.getReplacementPolicyComboBox().getSelectedItem();
            PRA newPolicy = "FIFO".equals(policy) ? new FIFOReplacement() : new LRUReplacement();
            virtualMemoryManager = new VirtualMemoryManager(memorySize, newPolicy);
            JOptionPane.showMessageDialog(view, "Replacement policy set to " + policy + ".");
        }
    }
    private class setSegmentingPolicyListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String policy = (String) view.getSegmentAllocationPolicy().getSelectedItem();
            AllocationPolicy newPolicy = "BestFit".equals(policy) ? new BestFitPolicy() : new FirstFitPolicy();
            virtualMemoryManager = new VirtualMemoryManager(null, newPolicy);
            JOptionPane.showMessageDialog(view, "Allocation policy set to " + policy + ".");
        }
    }

    private class SetMemoryTypeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String memoryType = (String) view.getMemoryTypeComboBox().getSelectedItem();
            isSegmented = "SEGMENTING".equals(memoryType);
            initializeVirtualMemoryManager();
            JOptionPane.showMessageDialog(view, "Memory type set to " + memoryType + ".");
        }
    }
}
