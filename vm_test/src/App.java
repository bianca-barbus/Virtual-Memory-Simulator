import gui.Controller;
import gui.View;
import logic.FIFOReplacement;
import logic.VirtualMemoryManager;

import javax.swing.*;

public class App {
    public static void main(String[] args){
        VirtualMemoryManager model = new VirtualMemoryManager(3, new FIFOReplacement());
        View view = new View();
        new Controller(model, view, 3);

        SwingUtilities.invokeLater(() -> view.setVisible(true));
    }
}
