package buffeteabogados.vista;

import buffeteabogados.controlador.ReporteController;
import buffeteabogados.util.EstilosSistema;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

// Corrección: Debe ser un JDialog para funcionar correctamente desde el Dashboard
public class GestionReportes extends JDialog {

    private final ReporteController controller;
    private JComboBox<String> cmbTipoReporte;
    private JTextArea txtVistaPrevia;
    private JButton btnGenerar, btnGuardar, btnImprimir, btnLimpiar;
    private boolean reporteGenerado = false;
    // ... otros componentes como spinners, etc.

    // Corrección: El constructor debe coincidir con el llamado desde Dashboard
    public GestionReportes(JFrame parent) {
        super(parent, "Gestión de Reportes", true);

        // Corrección: El controlador se instancia sin parámetros, como en tu código original
        this.controller = new ReporteController();

        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();// AÑADIR ESTA LÍNEA AL FINAL DEL CONSTRUCTOR
        
        setLocationRelativeTo(getParent()); // Centra la ventana después de ajustar el tamaño
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
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Usar DISPOSE_ON_CLOSE para JDialog
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);
    }

    private void inicializarComponentes() {
        // Corrección: Se crean los componentes usando los métodos de EstilosSistema
        cmbTipoReporte = new JComboBox<>(new String[]{
            "Reporte de Clientes" // Puedes añadir más opciones si tu controller las soporta
        });

        btnGenerar = EstilosSistema.crearBotonPrincipal("GENERAR REPORTE", EstilosSistema.COLOR_ACCENT);
        btnGuardar = EstilosSistema.crearBotonSecundario("GUARDAR");
        btnImprimir = EstilosSistema.crearBotonSecundario("IMPRIMIR");
        btnLimpiar = EstilosSistema.crearBotonSecundario("LIMPIAR");

        txtVistaPrevia = new JTextArea(); // El estilo se aplica al JScrollPane que lo contiene
        txtVistaPrevia.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtVistaPrevia.setEditable(false);

        // Estado inicial de los botones
        btnGuardar.setEnabled(false);
        btnImprimir.setEnabled(false);
    }

    // Este método ya no es necesario, ya que usamos EstilosSistema
    // private JButton crearBoton(String texto, Color color) { ... }
    private void configurarLayout() {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelContenedor.setOpaque(false);

        // Panel superior para los controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelControles.setOpaque(false);
        panelControles.add(new JLabel("Tipo de Reporte:"));
        panelControles.add(cmbTipoReporte);
        panelControles.add(btnGenerar);

        // Panel derecho para acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelAcciones.setOpaque(false);
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnImprimir);
        panelAcciones.add(btnLimpiar);

        // Panel de cabecera que contiene controles y acciones
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setOpaque(false);
        panelCabecera.add(panelControles, BorderLayout.WEST);
        panelCabecera.add(panelAcciones, BorderLayout.EAST);

        panelContenedor.add(panelCabecera, BorderLayout.NORTH);

        // Área de vista previa con scroll y estilo
        JScrollPane scrollPane = new JScrollPane(txtVistaPrevia);
        EstilosSistema.estilizarTabla(new JTable(), scrollPane); // Aplica estilo al borde y fondo del scroll

        panelContenedor.add(scrollPane, BorderLayout.CENTER);

        add(panelContenedor, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnGenerar.addActionListener((ActionEvent e) -> generarReporte());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardarReporte());
        btnImprimir.addActionListener(e -> imprimirReporte());
    }

    private void generarReporte() {
        String tipoReporte = (String) cmbTipoReporte.getSelectedItem();
        String contenidoReporte = "";

        if (tipoReporte == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de reporte.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Corrección: Se llama al método del controlador que SÍ existe en tu código
        if ("Reporte de Clientes".equals(tipoReporte)) {
            contenidoReporte = controller.generarReporteClientes();
        } else {
            // Aquí puedes añadir más llamadas a otros métodos del controlador si los tienes
            // ej: else if ("Reporte de Casos".equals(tipoReporte)) { ... }
            contenidoReporte = "Este tipo de reporte aún no está implementado.";
        }

        txtVistaPrevia.setText(contenidoReporte);
        txtVistaPrevia.setCaretPosition(0); // Mueve el cursor al inicio del texto
        reporteGenerado = true;
        btnGuardar.setEnabled(true);
        btnImprimir.setEnabled(true);
    }

    // Los métodos guardarReporte, imprimirReporte y limpiarFormulario se mantienen como los tenías,
    // ya que su lógica es correcta y pertenece a la vista (manejo de archivos y UI).
    private void guardarReporte() {
        /* ... Tu código original ... */ }

    private void imprimirReporte() {
        /* ... Tu código original ... */ }

    private void limpiarFormulario() {
        /* ... Tu código original ... */ }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
