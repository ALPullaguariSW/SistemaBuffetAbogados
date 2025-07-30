package buffetabogados.vista;

import buffetabogados.util.ComponentesFecha;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Ejemplo de cómo usar componentes de fecha nativos de Java Swing
 * en lugar de JDateChooser (que requiere librerías externas)
 */
public class EjemploUsoFecha extends JFrame {
    
    // Diferentes tipos de componentes de fecha
    private JSpinner spinnerFecha;
    private JSpinner spinnerFechaNacimiento;
    private JPanel panelFechaSeparada;
    private JFormattedTextField campoFechaFormateado;
    
    public EjemploUsoFecha() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Ejemplo de Componentes de Fecha Nativos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        JLabel titulo = new JLabel("Ejemplos de Componentes de Fecha Nativos");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Ejemplo 1: JSpinner básico para fechas
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("1. JSpinner básico:"), gbc);
        
        gbc.gridx = 1;
        spinnerFecha = ComponentesFecha.crearSelectorFecha(new Date());
        mainPanel.add(spinnerFecha, gbc);
        
        // Ejemplo 2: JSpinner para fecha de nacimiento
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("2. Fecha de nacimiento:"), gbc);
        
        gbc.gridx = 1;
        spinnerFechaNacimiento = ComponentesFecha.crearSelectorFechaNacimiento();
        mainPanel.add(spinnerFechaNacimiento, gbc);
        
        // Ejemplo 3: Campos separados (día, mes, año)
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("3. Campos separados:"), gbc);
        
        gbc.gridx = 1;
        panelFechaSeparada = ComponentesFecha.crearPanelFechaCamposSeparados(new Date());
        mainPanel.add(panelFechaSeparada, gbc);
        
        // Ejemplo 4: JFormattedTextField
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("4. Campo formateado:"), gbc);
        
        gbc.gridx = 1;
        campoFechaFormateado = ComponentesFecha.crearCampoFechaFormateado(new Date());
        mainPanel.add(campoFechaFormateado, gbc);
        
        // Botón para mostrar valores
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton btnMostrarValores = new JButton("Mostrar Valores Seleccionados");
        btnMostrarValores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarValores();
            }
        });
        mainPanel.add(btnMostrarValores, gbc);
        
        // Área de texto para mostrar resultados
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        JTextArea areaResultados = new JTextArea(10, 40);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Courier", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        mainPanel.add(scrollPane, gbc);
        
        // Guardar referencia al área de resultados
        this.putClientProperty("areaResultados", areaResultados);
        
        add(mainPanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void mostrarValores() {
        JTextArea area = (JTextArea) this.getClientProperty("areaResultados");
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== VALORES SELECCIONADOS ===\n\n");
        
        // Spinner básico
        Date fecha1 = ComponentesFecha.obtenerFecha(spinnerFecha);
        sb.append("1. JSpinner básico: ").append(fecha1).append("\n");
        sb.append("   SQL Date: ").append(ComponentesFecha.convertirASqlDate(fecha1)).append("\n\n");
        
        // Spinner fecha nacimiento
        Date fecha2 = ComponentesFecha.obtenerFecha(spinnerFechaNacimiento);
        sb.append("2. Fecha nacimiento: ").append(fecha2).append("\n");
        sb.append("   SQL Date: ").append(ComponentesFecha.convertirASqlDate(fecha2)).append("\n\n");
        
        // Campos separados
        Date fecha3 = ComponentesFecha.obtenerFechaDeCamposSeparados(panelFechaSeparada);
        sb.append("3. Campos separados: ").append(fecha3).append("\n");
        sb.append("   SQL Date: ").append(ComponentesFecha.convertirASqlDate(fecha3)).append("\n\n");
        
        // Campo formateado
        Date fecha4 = ComponentesFecha.obtenerFechaDeFormattedField(campoFechaFormateado);
        sb.append("4. Campo formateado: ").append(fecha4).append("\n");
        sb.append("   SQL Date: ").append(ComponentesFecha.convertirASqlDate(fecha4)).append("\n\n");
        
        sb.append("=== CÓDIGO DE EJEMPLO ===\n\n");
        sb.append("// Crear componente:\n");
        sb.append("JSpinner spinnerFecha = ComponentesFecha.crearSelectorFecha(null);\n\n");
        sb.append("// Obtener valor:\n");
        sb.append("Date fecha = ComponentesFecha.obtenerFecha(spinnerFecha);\n\n");
        sb.append("// Para base de datos:\n");
        sb.append("java.sql.Date sqlDate = ComponentesFecha.convertirASqlDate(fecha);\n");
        
        area.setText(sb.toString());
        area.setCaretPosition(0);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new EjemploUsoFecha().setVisible(true);
            }
        });
    }
}