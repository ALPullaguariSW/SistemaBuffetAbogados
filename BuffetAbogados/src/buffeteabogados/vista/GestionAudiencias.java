package buffeteabogados.vista;

import buffeteabogados.controlador.AudienciaController;
import buffeteabogados.modelo.Audiencia;
import buffeteabogados.modelo.Caso;
import buffeteabogados.modelo.Empleado;
import buffeteabogados.util.EstilosSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionAudiencias extends JDialog {

    private JTextField txtLugar, txtBuscar;
    private JTextArea txtObservaciones;
    private JComboBox<String> cmbCaso, cmbAbogado, cmbTipo, cmbEstado;
    private JSpinner spinnerDia, spinnerMes, spinnerAnio, spinnerHora, spinnerMinuto;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaAudiencias;
    private DefaultTableModel modeloTabla;
    private JLabel lblContador, lblModo;

    private final AudienciaController controller;
    private int audienciaSeleccionadaId = -1;

    private final Map<String, Integer> mapaCasos = new HashMap<>();
    private final Map<String, Integer> mapaAbogados = new HashMap<>();

    public GestionAudiencias(JFrame parent) {
        super(parent, "Gestión de Audiencias", true);
        this.controller = new AudienciaController(this);

        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        controller.inicializar();
        // AÑADIR ESTA LÍNEA AL FINAL DEL CONSTRUCTOR
        
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
        setSize(1280, 720);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        txtLugar = EstilosSistema.crearCampoTexto("Ej: Sala 3, Centro Judicial");
        txtObservaciones = EstilosSistema.crearAreaTexto("Notas u observaciones relevantes...");
        txtBuscar = EstilosSistema.crearCampoTexto("Buscar por tipo o lugar...");

        cmbCaso = new JComboBox<>();
        cmbAbogado = new JComboBox<>();
        cmbTipo = new JComboBox<>(new String[]{"Preliminar", "Principal", "Sentencia", "Conciliación"});
        cmbEstado = new JComboBox<>(new String[]{"Programada", "Realizada", "Cancelada", "Reprogramada"});

        Calendar cal = Calendar.getInstance();
        spinnerDia = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
        spinnerMes = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1));
        spinnerAnio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.YEAR), cal.get(Calendar.YEAR), cal.get(Calendar.YEAR) + 5, 1));
        spinnerHora = new JSpinner(new SpinnerNumberModel(9, 0, 23, 1));
        spinnerMinuto = new JSpinner(new SpinnerNumberModel(0, 0, 59, 15));

        btnGuardar = EstilosSistema.crearBotonPrincipal("Guardar", EstilosSistema.COLOR_ACCENT);
        btnActualizar = EstilosSistema.crearBotonPrincipal("Actualizar", EstilosSistema.COLOR_PRIMARIO);
        btnEliminar = EstilosSistema.crearBotonSecundario("Eliminar");
        btnLimpiar = EstilosSistema.crearBotonSecundario("Limpiar");

        lblModo = new JLabel("Modo: Creación");
        lblModo.setFont(EstilosSistema.FUENTE_PEQUEÑA);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Caso", "Tipo", "Fecha", "Hora", "Lugar", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAudiencias = new JTable(modeloTabla);

        lblContador = new JLabel("Total: 0 audiencias");
        lblContador.setFont(EstilosSistema.FUENTE_PEQUEÑA);

        cambiarModoCreacion();
    }

    private void configurarLayout() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        ((JPanel) contentPane).setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelFormulario(), crearPanelTabla());
        splitPane.setDividerLocation(450);
        contentPane.add(splitPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(EstilosSistema.COLOR_FONDO_CARD);
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Audiencia"));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Caso Asociado:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(cmbCaso, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Abogado a Cargo:"), gbc);
        gbc.gridx = 1;
        form.add(cmbAbogado, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Tipo de Audiencia:"), gbc);
        gbc.gridx = 1;
        form.add(cmbTipo, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        form.add(cmbEstado, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Lugar:"), gbc);
        gbc.gridx = 1;
        form.add(txtLugar, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Fecha y Hora:"), gbc);
        JPanel panelFechaHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFechaHora.setOpaque(false);
        panelFechaHora.add(spinnerDia);
        panelFechaHora.add(spinnerMes);
        panelFechaHora.add(spinnerAnio);
        panelFechaHora.add(new JLabel(" - "));
        panelFechaHora.add(spinnerHora);
        panelFechaHora.add(new JLabel(":"));
        panelFechaHora.add(spinnerMinuto);
        gbc.gridx = 1;
        form.add(panelFechaHora, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Observaciones:"), gbc);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setPreferredSize(new Dimension(10, 80));
        gbc.gridx = 1;
        form.add(scrollObs, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnGuardar);

        panel.add(lblModo, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);

        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.setOpaque(false);
        panelBusqueda.add(new JLabel("Buscar:"), BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(tablaAudiencias);
        EstilosSistema.estilizarTabla(tablaAudiencias, scrollPane);

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblContador, BorderLayout.SOUTH);

        return panel;
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> guardarAudiencia());
        btnActualizar.addActionListener(e -> actualizarAudiencia());
        btnEliminar.addActionListener(e -> eliminarAudiencia());
        btnLimpiar.addActionListener(e -> {
            limpiarFormulario();
            cambiarModoCreacion();
        });
        tablaAudiencias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    cargarAudienciaSeleccionada();
                }
            }
        });
    }

    private void guardarAudiencia() {
        Audiencia audiencia = construirAudienciaDesdeFormulario();
        if (audiencia == null) {
            return;
        }

        if (controller.registrarAudiencia(audiencia)) {
            limpiarFormulario();
        }
    }

    private void actualizarAudiencia() {
        if (audienciaSeleccionadaId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una audiencia de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Audiencia audiencia = construirAudienciaDesdeFormulario();
        if (audiencia == null) {
            return;
        }
        audiencia.setId(audienciaSeleccionadaId);

        if (controller.actualizarAudiencia(audiencia)) {
            limpiarFormulario();
            cambiarModoCreacion();
        }
    }

    private void eliminarAudiencia() {
        if (audienciaSeleccionadaId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una audiencia de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.eliminarAudiencia(audienciaSeleccionadaId);
    }

    private void cargarAudienciaSeleccionada() {
        int fila = tablaAudiencias.getSelectedRow();
        if (fila != -1) {
            audienciaSeleccionadaId = (int) modeloTabla.getValueAt(fila, 0);
            controller.cargarAudienciaParaEditar(audienciaSeleccionadaId);
            cambiarModoEdicion();
        }
    }

    private Audiencia construirAudienciaDesdeFormulario() {
        Timestamp fechaHora = obtenerFechaHora();
        if (fechaHora == null) {
            return null;
        }

        Audiencia audiencia = new Audiencia();
        audiencia.setTipo((String) cmbTipo.getSelectedItem());
        audiencia.setLugar(txtLugar.getText());
        audiencia.setEstado((String) cmbEstado.getSelectedItem());
        audiencia.setObservaciones(txtObservaciones.getText());

        String casoSeleccionado = (String) cmbCaso.getSelectedItem();
        audiencia.setCasoId(mapaCasos.getOrDefault(casoSeleccionado, 0));

        String abogadoSeleccionado = (String) cmbAbogado.getSelectedItem();
        audiencia.setAbogadoId(mapaAbogados.getOrDefault(abogadoSeleccionado, 0));

        audiencia.setFechaHora(fechaHora);
        return audiencia;
    }

    private Timestamp obtenerFechaHora() {
        try {
            int dia = (int) spinnerDia.getValue();
            int mes = (int) spinnerMes.getValue();
            int anio = (int) spinnerAnio.getValue();
            int hora = (int) spinnerHora.getValue();
            int min = (int) spinnerMinuto.getValue();

            Calendar cal = Calendar.getInstance();
            cal.set(anio, mes - 1, dia, hora, min, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return new Timestamp(cal.getTimeInMillis());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "La fecha y hora no son válidas.", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void cargarAudienciasEnTabla(List<Audiencia> audiencias) {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("hh:mm a");
        for (Audiencia aud : audiencias) {
            modeloTabla.addRow(new Object[]{
                aud.getId(), aud.getCasoTitulo(), aud.getTipo(),
                sdfFecha.format(aud.getFechaHora()),
                sdfHora.format(aud.getFechaHora()),
                aud.getLugar(), aud.getEstado()
            });
        }
        actualizarContadorAudiencias(audiencias.size());
    }

    public void cargarCasosEnCombo(List<Caso> casos) {
        cmbCaso.removeAllItems();
        mapaCasos.clear();
        cmbCaso.addItem("-- Seleccionar Caso --");
        for (Caso caso : casos) {
            String titulo = caso.getTitulo();
            cmbCaso.addItem(titulo);
            mapaCasos.put(titulo, caso.getId());
        }
    }

    public void cargarAbogadosEnCombo(List<Empleado> abogados) {
        cmbAbogado.removeAllItems();
        mapaAbogados.clear();
        cmbAbogado.addItem("-- Seleccionar Abogado --");
        for (Empleado abogado : abogados) {
            String nombre = abogado.getNombreCompleto();
            cmbAbogado.addItem(nombre);
            mapaAbogados.put(nombre, abogado.getId());
        }
    }

    public void cargarDatosEnFormulario(Audiencia audiencia) {
        txtLugar.setText(audiencia.getLugar());
        txtObservaciones.setText(audiencia.getObservaciones());
        cmbTipo.setSelectedItem(audiencia.getTipo());
        cmbEstado.setSelectedItem(audiencia.getEstado());

        if (audiencia.getFechaHora() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(audiencia.getFechaHora());
            spinnerDia.setValue(cal.get(Calendar.DAY_OF_MONTH));
            spinnerMes.setValue(cal.get(Calendar.MONTH) + 1);
            spinnerAnio.setValue(cal.get(Calendar.YEAR));
            spinnerHora.setValue(cal.get(Calendar.HOUR_OF_DAY));
            spinnerMinuto.setValue(cal.get(Calendar.MINUTE));
        }

        cmbCaso.setSelectedItem(obtenerLlavePorValor(mapaCasos, audiencia.getCasoId()));
        cmbAbogado.setSelectedItem(obtenerLlavePorValor(mapaAbogados, audiencia.getAbogadoId()));
    }

    private String obtenerLlavePorValor(Map<String, Integer> mapa, int valor) {
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            if (entry.getValue().equals(valor)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void limpiarFormulario() {
        txtLugar.setText("");
        txtObservaciones.setText("");
        cmbCaso.setSelectedIndex(0);
        cmbAbogado.setSelectedIndex(0);
        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tablaAudiencias.clearSelection();
        audienciaSeleccionadaId = -1;
    }

    public void cambiarModoCreacion() {
        lblModo.setText("Modo: Creación");
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
    }

    public void cambiarModoEdicion() {
        lblModo.setText("Modo: Edición (ID: " + audienciaSeleccionadaId + ")");
        btnGuardar.setVisible(false);
        btnActualizar.setVisible(true);
        btnEliminar.setVisible(true);
    }

    public void actualizarContadorAudiencias(int total) {
        lblContador.setText("Total: " + total + " audiencias");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
