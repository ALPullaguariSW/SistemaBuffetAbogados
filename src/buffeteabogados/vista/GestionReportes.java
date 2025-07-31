package buffeteabogados.vista;

import buffeteabogados.controlador.ReporteController;
import buffeteabogados.util.EstilosSistema;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Corrección: Debe ser un JDialog para funcionar correctamente desde el Dashboard
public class GestionReportes extends JDialog {

    private final ReporteController controller;
    private JComboBox<String> cmbTipoReporte;
    private JTextArea txtVistaPrevia;
    private JButton btnGenerar, btnGuardar, btnImprimir, btnLimpiar;
    private boolean reporteGenerado = false;

    public GestionReportes(JFrame parent) {
        super(parent, "Gestión de Reportes", true);
        this.controller = new ReporteController();

        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        
        setLocationRelativeTo(getParent());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void configurarVentana() {
        setSize(1024, 768);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);
    }

    private void inicializarComponentes() {
        // Añadimos los nuevos tipos de reporte que creamos en el controlador
        cmbTipoReporte = new JComboBox<>(new String[]{
            "Resumen General del Sistema",
            "Listado de Clientes",
            "Listado de Empleados",
            "Listado de Casos"
        });

        btnGenerar = EstilosSistema.crearBotonPrincipal("GENERAR REPORTE", EstilosSistema.COLOR_ACCENT);
        btnGuardar = EstilosSistema.crearBotonSecundario("GUARDAR");
        btnImprimir = EstilosSistema.crearBotonSecundario("IMPRIMIR");
        btnLimpiar = EstilosSistema.crearBotonSecundario("LIMPIAR");

        txtVistaPrevia = new JTextArea();
        txtVistaPrevia.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtVistaPrevia.setEditable(false);

        btnGuardar.setEnabled(false);
        btnImprimir.setEnabled(false);
    }

    private void configurarLayout() {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelContenedor.setOpaque(false);

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelControles.setOpaque(false);
        panelControles.add(new JLabel("Tipo de Reporte:"));
        panelControles.add(cmbTipoReporte);
        panelControles.add(btnGenerar);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelAcciones.setOpaque(false);
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnImprimir);
        panelAcciones.add(btnLimpiar);

        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setOpaque(false);
        panelCabecera.add(panelControles, BorderLayout.WEST);
        panelCabecera.add(panelAcciones, BorderLayout.EAST);

        panelContenedor.add(panelCabecera, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(txtVistaPrevia);
        EstilosSistema.estilizarTabla(new JTable(), scrollPane);

        panelContenedor.add(scrollPane, BorderLayout.CENTER);
        add(panelContenedor, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnGenerar.addActionListener(e -> generarReporte());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardarReporte());
        btnImprimir.addActionListener(e -> imprimirReporte());
    }

    private void generarReporte() {
        String tipoReporte = (String) cmbTipoReporte.getSelectedItem();
        if (tipoReporte == null) return;

        // La vista solo pide el reporte y lo muestra, no sabe cómo se hace
        String contenidoReporte = controller.generarReporte(tipoReporte);

        txtVistaPrevia.setText(contenidoReporte);
        txtVistaPrevia.setCaretPosition(0);
        reporteGenerado = true;
        btnGuardar.setEnabled(true);
        btnImprimir.setEnabled(true);
    }

    private void limpiarFormulario() {
        txtVistaPrevia.setText("");
        cmbTipoReporte.setSelectedIndex(0);
        reporteGenerado = false;
        btnGuardar.setEnabled(false);
        btnImprimir.setEnabled(false);
    }

    private void guardarReporte() {
        if (!reporteGenerado || txtVistaPrevia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debe generar un reporte.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte como Archivo de Texto");
        
        String nombreSugerido = "Reporte_" + ((String)cmbTipoReporte.getSelectedItem()).replace(" ", "_") + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt";
        fileChooser.setSelectedFile(new File(nombreSugerido));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivoParaGuardar = fileChooser.getSelectedFile();
            try (FileWriter fw = new FileWriter(archivoParaGuardar);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(txtVistaPrevia.getText());
                JOptionPane.showMessageDialog(this, "Reporte guardado exitosamente en:\n" + archivoParaGuardar.getAbsolutePath(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void imprimirReporte() {
        if (!reporteGenerado || txtVistaPrevia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debe generar un reporte.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // El método .print() abre el diálogo de impresión del sistema
            boolean impresionCompleta = txtVistaPrevia.print();
            if (impresionCompleta) {
                JOptionPane.showMessageDialog(this, "El reporte ha sido enviado a la impresora.", "Impresión Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La impresión fue cancelada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Error al intentar imprimir: " + ex.getMessage(), "Error de Impresión", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
