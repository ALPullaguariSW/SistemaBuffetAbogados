package buffeteabogados.vista;

import buffeteabogados.controlador.CasoController;
import buffeteabogados.modelo.Caso;
import buffeteabogados.modelo.Cliente;
import buffeteabogados.modelo.Empleado;
import buffeteabogados.util.EstilosSistema;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionCasos extends JDialog {

    private JTextField txtTitulo, txtBuscar;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbCliente, cmbEmpleado, cmbTipoCaso, cmbEstado, cmbPrioridad;
    private JSpinner spinnerDiaInicio, spinnerMesInicio, spinnerAnioInicio, spinnerEstimacionCosto;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaCasos;
    private DefaultTableModel modeloTabla;
    private JLabel lblContador, lblModo;

    private final CasoController controller;
    private int casoSeleccionadoId = -1;

    private final Map<String, Integer> mapaClientes = new HashMap<>();
    private final Map<String, Integer> mapaEmpleados = new HashMap<>();

    public GestionCasos(JFrame parent) {
        super(parent, "Gestión de Casos", true);
        this.controller = new CasoController(this);

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
        txtTitulo = EstilosSistema.crearCampoTexto("Título descriptivo del caso");
        txtDescripcion = EstilosSistema.crearAreaTexto("Detalles y descripción del caso...");
        txtBuscar = EstilosSistema.crearCampoTexto("Buscar por título del caso...");

        cmbCliente = new JComboBox<>();
        cmbEmpleado = new JComboBox<>();
        cmbTipoCaso = new JComboBox<>(new String[]{"Civil", "Penal", "Laboral", "Mercantil", "Familia"});
        cmbEstado = new JComboBox<>(new String[]{"Activo", "En Proceso", "Pendiente", "Cerrado"});
        cmbPrioridad = new JComboBox<>(new String[]{"Baja", "Media", "Alta", "Urgente"});

        Calendar cal = Calendar.getInstance();
        spinnerDiaInicio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
        spinnerMesInicio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1));
        spinnerAnioInicio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.YEAR), 2000, cal.get(Calendar.YEAR) + 5, 1));
        spinnerEstimacionCosto = new JSpinner(new SpinnerNumberModel(500.0, 0.0, 100000.0, 100.0));

        btnGuardar = EstilosSistema.crearBotonPrincipal("Guardar Caso", EstilosSistema.COLOR_ACCENT);
        btnActualizar = EstilosSistema.crearBotonPrincipal("Actualizar Caso", EstilosSistema.COLOR_PRIMARIO);
        btnEliminar = EstilosSistema.crearBotonSecundario("Eliminar");
        btnLimpiar = EstilosSistema.crearBotonSecundario("Limpiar");

        lblModo = new JLabel("Modo: Creación");
        lblModo.setFont(EstilosSistema.FUENTE_PEQUEÑA);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Título", "Cliente", "Abogado", "Estado", "Prioridad", "Fecha Inicio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCasos = new JTable(modeloTabla);

        lblContador = new JLabel("Total: 0 casos");
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
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Caso"));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(txtTitulo, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        form.add(cmbCliente, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Abogado:"), gbc);
        gbc.gridx = 1;
        form.add(cmbEmpleado, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Tipo de Caso:"), gbc);
        gbc.gridx = 1;
        form.add(cmbTipoCaso, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        form.add(cmbEstado, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Prioridad:"), gbc);
        gbc.gridx = 1;
        form.add(cmbPrioridad, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Fecha de Inicio:"), gbc);
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setOpaque(false);
        panelFecha.add(spinnerDiaInicio);
        panelFecha.add(spinnerMesInicio);
        panelFecha.add(spinnerAnioInicio);
        gbc.gridx = 1;
        form.add(panelFecha, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Costo Estimado ($):"), gbc);
        gbc.gridx = 1;
        form.add(spinnerEstimacionCosto, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Descripción:"), gbc);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setPreferredSize(new Dimension(10, 100));
        gbc.gridx = 1;
        form.add(scrollDesc, gbc);

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

        JScrollPane scrollPane = new JScrollPane(tablaCasos);
        EstilosSistema.estilizarTabla(tablaCasos, scrollPane);

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblContador, BorderLayout.SOUTH);

        return panel;
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> guardarCaso());
        btnActualizar.addActionListener(e -> actualizarCaso());
        btnEliminar.addActionListener(e -> eliminarCaso());
        btnLimpiar.addActionListener(e -> {
            limpiarFormulario();
            cambiarModoCreacion();
        });

        tablaCasos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    cargarCasoSeleccionado();
                }
            }
        });
    }

    private void guardarCaso() {
        Caso caso = construirCasoDesdeFormulario();
        if (caso == null) {
            return;
        }

        if (controller.registrarCaso(caso)) {
            limpiarFormulario();
        }
    }

    private void actualizarCaso() {
        if (casoSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un caso de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Caso caso = construirCasoDesdeFormulario();
        if (caso == null) {
            return;
        }
        caso.setId(casoSeleccionadoId);

        if (controller.actualizarCaso(caso)) {
            limpiarFormulario();
            cambiarModoCreacion();
        }
    }

    private void eliminarCaso() {
        if (casoSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un caso de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tituloCaso = txtTitulo.getText();
        controller.eliminarCaso(casoSeleccionadoId, tituloCaso);
    }

    private void cargarCasoSeleccionado() {
        int fila = tablaCasos.getSelectedRow();
        if (fila != -1) {
            casoSeleccionadoId = (int) modeloTabla.getValueAt(fila, 0);
            controller.cargarCasoParaEditar(casoSeleccionadoId);
            cambiarModoEdicion();
        }
    }

    private Caso construirCasoDesdeFormulario() {
        Date fechaInicio = obtenerFecha();
        if (fechaInicio == null) {
            JOptionPane.showMessageDialog(this, "Fecha de inicio no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Caso caso = new Caso();
        caso.setTitulo(txtTitulo.getText());
        caso.setDescripcion(txtDescripcion.getText());

        String clienteSeleccionado = (String) cmbCliente.getSelectedItem();
        caso.setClienteId(mapaClientes.getOrDefault(clienteSeleccionado, 0));

        String empleadoSeleccionado = (String) cmbEmpleado.getSelectedItem();
        caso.setAbogadoId(mapaEmpleados.getOrDefault(empleadoSeleccionado, 0));

        caso.setTipoCaso((String) cmbTipoCaso.getSelectedItem());
        caso.setEstado((String) cmbEstado.getSelectedItem());
        caso.setPrioridad((String) cmbPrioridad.getSelectedItem());
        caso.setEstimacionCosto((Double) spinnerEstimacionCosto.getValue());
        caso.setFechaInicio(fechaInicio);

        return caso;
    }

    private Date obtenerFecha() {
        try {
            int dia = (int) spinnerDiaInicio.getValue();
            int mes = (int) spinnerMesInicio.getValue();
            int anio = (int) spinnerAnioInicio.getValue();
            Calendar cal = Calendar.getInstance();
            cal.set(anio, mes - 1, dia);
            return new Date(cal.getTimeInMillis());
        } catch (Exception e) {
            return null;
        }
    }

    public void cargarCasosEnTabla(List<Caso> casos) {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Caso c : casos) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getTitulo(), c.getClienteNombre(), c.getAbogadoNombre(),
                c.getEstado(), c.getPrioridad(), sdf.format(c.getFechaInicio())
            });
        }
        actualizarContadorCasos(casos.size());
    }

    public void cargarClientesEnCombo(List<Cliente> clientes) {
        cmbCliente.removeAllItems();
        mapaClientes.clear();
        cmbCliente.addItem("-- Seleccionar Cliente --");
        for (Cliente cliente : clientes) {
            String nombre = cliente.getNombreCompleto();
            cmbCliente.addItem(nombre);
            mapaClientes.put(nombre, cliente.getId());
        }
    }

    public void cargarAbogadosEnCombo(List<Empleado> abogados) {
        cmbEmpleado.removeAllItems();
        mapaEmpleados.clear();
        cmbEmpleado.addItem("-- Seleccionar Abogado --");
        for (Empleado abogado : abogados) {
            String nombre = abogado.getNombreCompleto();
            cmbEmpleado.addItem(nombre);
            mapaEmpleados.put(nombre, abogado.getId());
        }
    }

    public void cargarDatosEnFormulario(Caso caso) {
        txtTitulo.setText(caso.getTitulo());
        txtDescripcion.setText(caso.getDescripcion());
        cmbTipoCaso.setSelectedItem(caso.getTipoCaso());
        cmbEstado.setSelectedItem(caso.getEstado());
        cmbPrioridad.setSelectedItem(caso.getPrioridad());
        spinnerEstimacionCosto.setValue(caso.getEstimacionCosto());

        if (caso.getFechaInicio() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(caso.getFechaInicio());
            spinnerDiaInicio.setValue(cal.get(Calendar.DAY_OF_MONTH));
            spinnerMesInicio.setValue(cal.get(Calendar.MONTH) + 1);
            spinnerAnioInicio.setValue(cal.get(Calendar.YEAR));
        }

        String nombreCliente = obtenerNombrePorId(mapaClientes, caso.getClienteId());
        cmbCliente.setSelectedItem(nombreCliente);

        String nombreAbogado = obtenerNombrePorId(mapaEmpleados, caso.getAbogadoId());
        cmbEmpleado.setSelectedItem(nombreAbogado);
    }

    private String obtenerNombrePorId(Map<String, Integer> mapa, int id) {
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            if (entry.getValue().equals(id)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void limpiarFormulario() {
        txtTitulo.setText("");
        txtDescripcion.setText("");
        cmbCliente.setSelectedIndex(0);
        cmbEmpleado.setSelectedIndex(0);
        cmbTipoCaso.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        cmbPrioridad.setSelectedIndex(0);
        spinnerEstimacionCosto.setValue(500.0);
        tablaCasos.clearSelection();
        casoSeleccionadoId = -1;
    }

    public void cambiarModoCreacion() {
        lblModo.setText("Modo: Creación");
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
    }

    public void cambiarModoEdicion() {
        lblModo.setText("Modo: Edición (ID: " + casoSeleccionadoId + ")");
        btnGuardar.setVisible(false);
        btnActualizar.setVisible(true);
        btnEliminar.setVisible(true);
    }

    public void actualizarContadorCasos(int total) {
        lblContador.setText("Total: " + total + " casos");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
