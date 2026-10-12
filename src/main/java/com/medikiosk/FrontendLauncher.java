
package com.medikiosk;

import com.medikiosk.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FrontendLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                System.err.println("Using default Swing theme.");
            }

            new MainFrame().setVisible(true);
        });
    }
}
