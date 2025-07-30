/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package buffeteabogados.vista;

import buffeteabogados.modelo.Cliente;
import buffeteabogados.controlador.ClienteController;
import buffeteabogados.util.EstilosSistema;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Calendar;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author axel_
 */
public class GestionClientes extends JDialog {

    private JTextField txtNombres, txtApellidos, txtTelefono, txtCorreo, txtDui, txtBuscar;
    private JTextArea txtDireccion;
    private JSpinner spinnerDia, spinnerMes, spinnerAnio;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JLabel lblContador, lblModo;

    private ClienteController controller;
    private int clienteSeleccionadoId = -1;

    /**
     * Creates new form GestionClientes
     */
    public GestionClientes(JFrame parent) {
        super(parent, "Gestión de Clientes", true);
        this.controller = new ClienteController(this);

        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        controller.cargarClientes(); // Carga inicial de datos
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
        // Formulario
        txtNombres = EstilosSistema.crearCampoTexto("Nombres del cliente");
        txtApellidos = EstilosSistema.crearCampoTexto("Apellidos del cliente");
        txtDireccion = EstilosSistema.crearAreaTexto("Dirección completa");
        txtTelefono = EstilosSistema.crearCampoTexto("Ej: 7777-7777");
        txtCorreo = EstilosSistema.crearCampoTexto("correo@ejemplo.com");
        txtDui = EstilosSistema.crearCampoTexto("Ej: 12345678-9");
        txtBuscar = EstilosSistema.crearCampoTexto("Buscar por nombre o apellido...");

        Calendar cal = Calendar.getInstance();
        spinnerDia = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        spinnerMes = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spinnerAnio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.YEAR) - 30, 1920, cal.get(Calendar.YEAR), 1));

        btnGuardar = EstilosSistema.crearBotonPrincipal("Guardar", EstilosSistema.COLOR_ACCENT);
        btnActualizar = EstilosSistema.crearBotonPrincipal("Actualizar", EstilosSistema.COLOR_PRIMARIO);
        btnEliminar = EstilosSistema.crearBotonSecundario("Eliminar");
        btnLimpiar = EstilosSistema.crearBotonSecundario("Limpiar");

        lblModo = new JLabel("Modo: Creación");
        lblModo.setFont(EstilosSistema.FUENTE_PEQUEÑA);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombres", "Apellidos", "Teléfono", "Correo", "DUI"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes = new JTable(modeloTabla);

        lblContador = new JLabel("Total: 0 clientes");
        lblContador.setFont(EstilosSistema.FUENTE_PEQUEÑA);

        cambiarModoCreacion();
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);

        // Panel Izquierdo: Formulario
        JPanel panelFormulario = crearPanelFormulario();

        // Panel Derecho: Tabla
        JPanel panelTabla = crearPanelTabla();

        // JSplitPane para dividir
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFormulario, panelTabla);
        splitPane.setDividerLocation(420);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(EstilosSistema.COLOR_FONDO_CARD);
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

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
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        form.add(txtNombres, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        form.add(txtApellidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("DUI:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        form.add(txtDui, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        form.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        form.add(txtTelefono, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        form.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        form.add(txtCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        form.add(new JLabel("Fecha Nacimiento:"), gbc);
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setOpaque(false);
        panelFecha.add(spinnerDia);
        panelFecha.add(spinnerMes);
        panelFecha.add(spinnerAnio);
        gbc.gridx = 1;
        gbc.gridy = 5;
        form.add(panelFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Dirección:"), gbc);
        JScrollPane scrollDireccion = new JScrollPane(txtDireccion);
        scrollDireccion.setPreferredSize(new Dimension(10, 80));
        gbc.gridx = 1;
        gbc.gridy = 6;
        form.add(scrollDireccion, gbc);

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

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        EstilosSistema.estilizarTabla(tablaClientes, scrollPane);

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblContador, BorderLayout.SOUTH);

        return panel;
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> guardarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> {
            limpiarFormulario();
            cambiarModoCreacion();
        });

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    cargarClienteSeleccionado();
                }
            }
        });
    }

    private void guardarCliente() {
        Date fechaNac = obtenerFechaNacimiento();
        if (fechaNac == null) {
            JOptionPane.showMessageDialog(this, "Fecha de nacimiento no válida.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = controller.registrarCliente(
                txtNombres.getText(), txtApellidos.getText(), txtDireccion.getText(),
                txtTelefono.getText(), txtCorreo.getText(), txtDui.getText(), fechaNac
        );

        if (exito) {
            limpiarFormulario();
        }
    }

    private void actualizarCliente() {
        if (clienteSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Date fechaNac = obtenerFechaNacimiento();
        if (fechaNac == null) {
            JOptionPane.showMessageDialog(this, "Fecha de nacimiento no válida.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = controller.actualizarCliente(
                clienteSeleccionadoId, txtNombres.getText(), txtApellidos.getText(), txtDireccion.getText(),
                txtTelefono.getText(), txtCorreo.getText(), txtDui.getText(), fechaNac
        );

        if (exito) {
            limpiarFormulario();
            cambiarModoCreacion();
        }
    }

    private void eliminarCliente() {
        if (clienteSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombreCompleto = txtNombres.getText() + " " + txtApellidos.getText();
        controller.eliminarCliente(clienteSeleccionadoId, nombreCompleto);
    }

    private void cargarClienteSeleccionado() {
        int fila = tablaClientes.getSelectedRow();
        if (fila != -1) {
            clienteSeleccionadoId = (int) modeloTabla.getValueAt(fila, 0);
            controller.cargarClienteParaEditar(clienteSeleccionadoId);
            cambiarModoEdicion();
        }
    }

    private Date obtenerFechaNacimiento() {
        try {
            int dia = (int) spinnerDia.getValue();
            int mes = (int) spinnerMes.getValue();
            int anio = (int) spinnerAnio.getValue();
            Calendar cal = Calendar.getInstance();
            cal.set(anio, mes - 1, dia);
            return new Date(cal.getTimeInMillis());
        } catch (Exception e) {
            return null;
        }
    }

    // MÉTODOS PÚBLICOS (LLAMADOS POR EL CONTROLADOR)
    public void cargarClientesEnTabla(List<Cliente> clientes) {
        modeloTabla.setRowCount(0);
        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                c.getId(), c.getNombres(), c.getApellidos(),
                c.getTelefono(), c.getCorreo(), c.getDui()
            });
        }
        actualizarContadorClientes(clientes.size());
    }

    public void cargarDatosEnFormulario(Cliente cliente) {
        txtNombres.setText(cliente.getNombres());
        txtApellidos.setText(cliente.getApellidos());
        txtDui.setText(cliente.getDui());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreo.setText(cliente.getCorreo());
        txtDireccion.setText(cliente.getDireccion());

        if (cliente.getFechaNacimiento() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(cliente.getFechaNacimiento());
            spinnerDia.setValue(cal.get(Calendar.DAY_OF_MONTH));
            spinnerMes.setValue(cal.get(Calendar.MONTH) + 1);
            spinnerAnio.setValue(cal.get(Calendar.YEAR));
        }
    }

    public void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDui.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        tablaClientes.clearSelection();
        clienteSeleccionadoId = -1;
    }

    public void cambiarModoCreacion() {
        lblModo.setText("Modo: Creación");
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
    }

    public void cambiarModoEdicion() {
        lblModo.setText("Modo: Edición (ID: " + clienteSeleccionadoId + ")");
        btnGuardar.setVisible(false);
        btnActualizar.setVisible(true);
        btnEliminar.setVisible(true);
    }

    public void actualizarContadorClientes(int total) {
        lblContador.setText("Total: " + total + " clientes");
    }    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
