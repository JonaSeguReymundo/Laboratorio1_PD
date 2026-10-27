package org.example.screen;

import org.example.Connection;

import org.example.screen.components.Buttons;
import org.example.screen.components.PanelResult;
import org.example.screen.components.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import java.awt.datatransfer.StringSelection;

public class Window extends JFrame {

    private static final String[] EJEMPLOS = {
            "x",
            "log(x)",
            "3*x^2",
            "2*x"
    };

    private final TextField txtEntrada =
            new TextField("Ej: 5*x^2");

    private final PanelResult panelResultado =
            new PanelResult();

    private final JLabel lblEstado =
            new JLabel(" ");

    public Window(Connection motor) {

        super("Calculo simbolico en Prolog");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        txtEntrada.addActionListener(
                event -> ejecutar(motor, "d")
        );

        JPanel contenido = new JPanel();

        contenido.setLayout(
                new BoxLayout(
                        contenido,
                        BoxLayout.Y_AXIS
                )
        );

        contenido.setOpaque(false);

        agregar(
                contenido,
                etiqueta("Funcion f(x)"),
                6
        );

        agregar(
                contenido,
                txtEntrada,
                8
        );

        agregar(
                contenido,
                crearEjemplos(),
                18
        );

        agregar(
                contenido,
                crearBotones(motor),
                22
        );

        agregar(
                contenido,
                panelResultado,
                10
        );

        agregar(
                contenido,
                crearPie(),
                0
        );

        JPanel raiz = new JPanel(
                new BorderLayout(0, 20)
        );

        raiz.setBackground(Theme.BG);

        raiz.setBorder(
                new EmptyBorder(
                        24,
                        28,
                        24,
                        28
                )
        );

        raiz.add(
                crearEncabezado(),
                BorderLayout.NORTH
        );

        raiz.add(
                contenido,
                BorderLayout.CENTER
        );

        setContentPane(raiz);

        setMinimumSize(
                new Dimension(660, 520)
        );

        pack();

        setLocationRelativeTo(null);
    }

    private JPanel crearEncabezado() {

        JLabel titulo =
                new JLabel("Calculo simbolico");

        titulo.setFont(
                Theme.sans(Font.BOLD, 24)
        );

        titulo.setForeground(Theme.TEXT);

        JLabel subtitulo = new JLabel(
                "Derivadas e integrales resueltas con reglas en Prolog"
        );

        subtitulo.setFont(
                Theme.sans(Font.PLAIN, 13)
        );

        subtitulo.setForeground(Theme.MUTED);

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setOpaque(false);

        panel.add(titulo);

        panel.add(
                Box.createVerticalStrut(4)
        );

        panel.add(subtitulo);

        return panel;
    }

    private JPanel crearEjemplos() {

        JPanel panel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        6,
                        0
                )
        );

        panel.setOpaque(false);

        JLabel etiqueta =
                new JLabel("Ejemplos:");

        etiqueta.setForeground(Theme.MUTED);

        etiqueta.setFont(
                Theme.sans(Font.PLAIN, 12)
        );

        panel.add(etiqueta);

        for (String ejemplo : EJEMPLOS) {

            Buttons boton = new Buttons(
                    ejemplo,
                    Theme.CARD,
                    Theme.TEXT,
                    12,
                    6,
                    11
            );

            boton.addActionListener(event -> {

                txtEntrada.setText(ejemplo);

                txtEntrada.requestFocusInWindow();

            });

            panel.add(boton);
        }

        return panel;
    }

    private JPanel crearBotones(Connection motor) {

        Buttons btnDerivar = new Buttons(
                "Derivar",
                Theme.BLUE,
                Theme.BG,
                26,
                11,
                14
        );

        Buttons btnIntegrar = new Buttons(
                "Integrar",
                Theme.GREEN,
                Theme.BG,
                26,
                11,
                14
        );

        btnDerivar.addActionListener(
                event -> ejecutar(motor, "d")
        );

        btnIntegrar.addActionListener(
                event -> ejecutar(motor, "s")
        );

        JPanel panel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                )
        );

        panel.setOpaque(false);

        panel.add(btnDerivar);
        panel.add(btnIntegrar);

        return panel;
    }

    private JPanel crearPie() {

        lblEstado.setFont(
                Theme.sans(Font.PLAIN, 12)
        );

        lblEstado.setForeground(Theme.MUTED);

        Buttons btnCopiar = new Buttons(
                "Copiar",
                Theme.CARD,
                Theme.TEXT,
                16,
                7,
                12
        );

        btnCopiar.addActionListener(
                event -> copiarResultado()
        );

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setOpaque(false);

        panel.add(
                lblEstado,
                BorderLayout.WEST
        );

        panel.add(
                btnCopiar,
                BorderLayout.EAST
        );

        return panel;
    }

    private void ejecutar(
            Connection motor,
            String predicado
    ) {

        String entrada =
                txtEntrada.getText().trim();

        String nombre = predicado.equals("d")
                ? "Derivada"
                : "Integral";

        panelResultado.setTitulo(
                nombre + " de f(x)"
        );

        if (entrada.isEmpty()) {

            mostrar(
                    "",
                    "Escribe una funcion primero.",
                    Theme.AMBER
            );

            return;
        }

        try {

            String resultado =
                    motor.consultar(
                            predicado,
                            entrada
                    );

            if (resultado.startsWith("Sin solucion")) {

                mostrar(
                        "",
                        resultado,
                        Theme.AMBER
                );

            } else {

                mostrar(
                        resultado,
                        "Operacion completada",
                        Theme.GREEN
                );
            }

        } catch (Exception exception) {

            mostrar(
                    "",
                    "Expresion invalida: "
                            + exception.getMessage(),
                    Theme.RED
            );

            exception.printStackTrace();
        }
    }

    private void mostrar(
            String resultado,
            String estado,
            Color colorEstado
    ) {

        panelResultado.setTexto(resultado);

        lblEstado.setText(estado);

        lblEstado.setForeground(colorEstado);
    }

    private void copiarResultado() {

        String texto =
                panelResultado.getTexto();

        if (texto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay ningun resultado para copiar.",
                    "Sin resultado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(texto),
                        null
                );

        lblEstado.setText(
                "Copiado al portapapeles"
        );

        lblEstado.setForeground(Theme.BLUE);
    }

    private static void agregar(
            JPanel panel,
            JComponent componente,
            int espacioDespues
    ) {

        componente.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(componente);

        if (espacioDespues > 0) {

            panel.add(
                    Box.createVerticalStrut(
                            espacioDespues
                    )
            );
        }
    }

    private static JLabel etiqueta(String texto) {

        JLabel etiqueta = new JLabel(texto);

        etiqueta.setFont(
                Theme.sans(Font.BOLD, 12)
        );

        etiqueta.setForeground(Theme.MUTED);

        return etiqueta;
    }
}