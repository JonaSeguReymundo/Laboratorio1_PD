package org.example;

import org.example.screen.Window;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                Connection connection = new Connection();

                Window window = new Window(connection);

                window.setVisible(true);

            } catch (Exception exception) {

                JOptionPane.showMessageDialog(
                        null,
                        "No se pudo iniciar la aplicacion:\n"
                                + exception.getMessage(),
                        "Error de inicializacion",
                        JOptionPane.ERROR_MESSAGE
                );

                exception.printStackTrace();
            }
        });
    }
}