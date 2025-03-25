import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TableroDibujo extends JFrame {
    private ListaLigada formas = new ListaLigada();
    private String formaSeleccionada = "Círculo"; 
    private int xInicio, yInicio;
    
    private ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
    public TableroDibujo() {
        setTitle("Tablero de Dibujo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel panelBotones = new JPanel();
        
        String[] formasDisponibles = {"Círculo", "Cuadrado", "Línea"};
        JComboBox<String> comboFormas = new JComboBox<>(formasDisponibles);
        comboFormas.addActionListener(e -> formaSeleccionada = (String) comboFormas.getSelectedItem());

        
        JButton btnEliminar = new JButton(cargarIcono("/Icons/basura.png", 20, 20));
        JButton btnGuardar = new JButton(cargarIcono("/Icons/descarga-en-la-nube.png", 20, 20));
        JButton btnCargar = new JButton(cargarIcono("/Icons/cargando.png", 20, 20));

        panelBotones.add(comboFormas);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCargar);
        add(panelBotones, BorderLayout.NORTH);

        
        JPanel panelDibujo = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(255, 182, 193)); 
                Nodo actual = formas.getCabeza();
                while (actual != null) {
                    if (actual.tipo.equals("Círculo")) {
                        g.drawOval(actual.x1, actual.y1, actual.x2 - actual.x1, actual.y2 - actual.y1);
                    } else if (actual.tipo.equals("Cuadrado")) {
                        g.drawRect(actual.x1, actual.y1, actual.x2 - actual.x1, actual.y2 - actual.y1);
                    } else if (actual.tipo.equals("Línea")) {
                        g.drawLine(actual.x1, actual.y1, actual.x2, actual.y2);
                    }
                    actual = actual.siguiente;
                }
            }
        };
        add(panelDibujo, BorderLayout.CENTER);

        
        btnEliminar.addActionListener(e -> {
            formas.eliminarUltimo();
            panelDibujo.repaint();
        });

        btnGuardar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showSaveDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                try {
                    formas.guardarDibujo(fileChooser.getSelectedFile().getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCargar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showOpenDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                try {
                    formas.cargarDibujo(fileChooser.getSelectedFile().getAbsolutePath());
                    panelDibujo.repaint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        
        panelDibujo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                xInicio = e.getX();
                yInicio = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                int xFin = e.getX();
                int yFin = e.getY();
                formas.agregar(formaSeleccionada, xInicio, yInicio, xFin, yFin);
                panelDibujo.repaint();
            }
        });
    }
}