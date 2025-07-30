package buffeteabogados.vista;

import buffeteabogados.controlador.EmpleadoController;
import buffeteabogados.modelo.Empleado;
import buffeteabogados.util.EstilosSistema;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class GestionEmpleados extends JDialog {

    private JTextField txtNombres, txtApellidos, txtEmail, txtTelefono, txtCargo, txtBuscar;
    private JSpinner spinnerDia, spinnerMes, spinnerAnio, spinnerSalario;
    private JCheckBox chkActivo;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JLabel lblContador, lblModo;

    private final EmpleadoController controller;
    private int empleadoSeleccionadoId = -1;

    public GestionEmpleados(JFrame parent) {
        super(parent, "Gestión de Empleados", true);
        this.controller = new EmpleadoController(this);

        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        controller.cargarEmpleados();
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
        setSize(1200, 700);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        txtNombres = EstilosSistema.crearCampoTexto("Nombres del empleado");
        txtApellidos = EstilosSistema.crearCampoTexto("Apellidos del empleado");
        txtEmail = EstilosSistema.crearCampoTexto("correo@bufete.com");
        txtTelefono = EstilosSistema.crearCampoTexto("Ej: 7777-7777");
        txtCargo = EstilosSistema.crearCampoTexto("Ej: Abogado Senior");
        txtBuscar = EstilosSistema.crearCampoTexto("Buscar por nombre o apellido...");

        Calendar cal = Calendar.getInstance();
        spinnerDia = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
        spinnerMes = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1));
        spinnerAnio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.YEAR), 1950, cal.get(Calendar.YEAR), 1));
        spinnerSalario = new JSpinner(new SpinnerNumberModel(1000.0, 365.0, 50000.0, 100.0));

        chkActivo = new JCheckBox("Empleado Activo");
        chkActivo.setSelected(true);
        chkActivo.setOpaque(false);

        btnGuardar = EstilosSistema.crearBotonPrincipal("GUARDAR", EstilosSistema.COLOR_ACCENT);
        btnActualizar = EstilosSistema.crearBotonPrincipal("ACTUALIZAR", EstilosSistema.COLOR_PRIMARIO);
        btnEliminar = EstilosSistema.crearBotonSecundario("DESACTIVAR");
        btnLimpiar = EstilosSistema.crearBotonSecundario("LIMPIAR");

        lblModo = new JLabel("Modo: Creación");
        lblModo.setFont(EstilosSistema.FUENTE_PEQUEÑA);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombres", "Apellidos", "Cargo", "Teléfono", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEmpleados = new JTable(modeloTabla);

        lblContador = new JLabel("Total: 0 empleados");
        lblContador.setFont(EstilosSistema.FUENTE_PEQUEÑA);

        cambiarModoCreacion();
    }

    private void configurarLayout() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        ((JPanel) contentPane).setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelFormulario(), crearPanelTabla());
        splitPane.setDividerLocation(420);
        contentPane.add(splitPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(EstilosSistema.COLOR_FONDO_CARD);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Nombres:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        form.add(txtNombres, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 1;
        form.add(txtApellidos, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1;
        form.add(txtCargo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        form.add(txtTelefono, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        form.add(txtEmail, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Fecha Contratación:"), gbc);
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setOpaque(false);
        panelFecha.add(spinnerDia);
        panelFecha.add(spinnerMes);
        panelFecha.add(spinnerAnio);
        gbc.gridx = 1;
        form.add(panelFecha, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Salario ($):"), gbc);
        gbc.gridx = 1;
        form.add(spinnerSalario, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        form.add(chkActivo, gbc);

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

        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        EstilosSistema.estilizarTabla(tablaEmpleados, scrollPane);

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblContador, BorderLayout.SOUTH);

        return panel;
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> guardarEmpleado());
        btnActualizar.addActionListener(e -> actualizarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnLimpiar.addActionListener(e -> {
            limpiarFormulario();
            cambiarModoCreacion();
        });

        tablaEmpleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    cargarEmpleadoSeleccionado();
                }
            }
        });
    }

    private void guardarEmpleado() {
        Date fechaContratacion = obtenerFecha();
        if (fechaContratacion == null) {
            return;
        }

        boolean exito = controller.registrarEmpleado(
                txtNombres.getText(), txtApellidos.getText(), txtCargo.getText(),
                txtTelefono.getText(), txtEmail.getText(), (Double) spinnerSalario.getValue(),
                chkActivo.isSelected(), fechaContratacion
        );

        if (exito) {
            limpiarFormulario();
        }
    }

    private void actualizarEmpleado() {
        if (empleadoSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Date fechaContratacion = obtenerFecha();
        if (fechaContratacion == null) {
            return;
        }

        boolean exito = controller.actualizarEmpleado(
                empleadoSeleccionadoId, txtNombres.getText(), txtApellidos.getText(), txtCargo.getText(),
                txtTelefono.getText(), txtEmail.getText(), (Double) spinnerSalario.getValue(), chkActivo.isSelected(), fechaContratacion
        );

        if (exito) {
            limpiarFormulario();
            cambiarModoCreacion();
        }
    }

    private void eliminarEmpleado() {
        if (empleadoSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombreCompleto = txtNombres.getText() + " " + txtApellidos.getText();
        controller.eliminarEmpleado(empleadoSeleccionadoId, nombreCompleto);
    }

    private void cargarEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada != -1) {
            empleadoSeleccionadoId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            controller.cargarEmpleadoParaEditar(empleadoSeleccionadoId);
            cambiarModoEdicion();
        }
    }

    private Date obtenerFecha() {
        try {
            int dia = (int) spinnerDia.getValue();
            int mes = (int) spinnerMes.getValue();
            int anio = (int) spinnerAnio.getValue();
            Calendar cal = Calendar.getInstance();
            cal.set(anio, mes - 1, dia);
            return new Date(cal.getTimeInMillis());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fecha no válida.", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // --- MÉTODOS PÚBLICOS (LLAMADOS POR EL CONTROLADOR) ---
    public void cargarEmpleadosEnTabla(List<Empleado> empleados) {
        modeloTabla.setRowCount(0);
        for (Empleado emp : empleados) {
            modeloTabla.addRow(new Object[]{
                emp.getId(), emp.getNombres(), emp.getApellidos(),
                emp.getCargo(), emp.getTelefono(), emp.getEmail()
            });
        }
        actualizarContadorEmpleados(empleados.size());
    }

    public void cargarDatosEnFormulario(Empleado empleado) {
        txtNombres.setText(empleado.getNombres());
        txtApellidos.setText(empleado.getApellidos());
        txtEmail.setText(empleado.getEmail());
        txtTelefono.setText(empleado.getTelefono());
        txtCargo.setText(empleado.getCargo());
        spinnerSalario.setValue(empleado.getSalario());
        chkActivo.setSelected(empleado.isActivo());

        if (empleado.getFechaContratacion() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(empleado.getFechaContratacion());
            spinnerDia.setValue(cal.get(Calendar.DAY_OF_MONTH));
            spinnerMes.setValue(cal.get(Calendar.MONTH) + 1);
            spinnerAnio.setValue(cal.get(Calendar.YEAR));
        }
    }

    public void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCargo.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        spinnerSalario.setValue(1000.0);
        chkActivo.setSelected(true);
        tablaEmpleados.clearSelection();
        empleadoSeleccionadoId = -1;
    }

    public void cambiarModoCreacion() {
        lblModo.setText("Modo: Creación");
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
    }

    public void cambiarModoEdicion() {
        lblModo.setText("Modo: Edición (ID: " + empleadoSeleccionadoId + ")");
        btnGuardar.setVisible(false);
        btnActualizar.setVisible(true);
        btnEliminar.setVisible(true);
    }

    public void actualizarContadorEmpleados(int total) {
        lblContador.setText("Total: " + total + " empleados");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
