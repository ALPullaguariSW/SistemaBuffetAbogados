package buffetabogados.vista;

import buffetabogados.modelo.Cliente;
import buffetabogados.controlador.ClienteController;

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
import com.toedter.calendar.JDateChooser;

/**
 * Ventana para la gestión completa de clientes (CRUD)
 */
public class GestionClientes extends JFrame {
    
    // Componentes del formulario
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextArea txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtDui;
    private JDateChooser dateChooserNacimiento;
    
    // Botones del formulario
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    
    // Componentes de búsqueda
    private JTextField txtBuscar;
    
    // Tabla y modelo
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    
    // Labels informativos
    private JLabel lblTitulo;
    private JLabel lblContador;
    private JLabel lblModo;
    
    // Controlador
    private ClienteController controller;
    
    // Variables de control
    private int clienteSeleccionadoId = -1;
    private boolean modoEdicion = false;
    
    public GestionClientes() {
        this.controller = new ClienteController(this);
        
        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        configurarTabla();
        
        // Cargar datos iniciales
        controller.cargarClientes();
        
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Gestión de Clientes - Buffet de Abogados");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void inicializarComponentes() {
        // Título
        lblTitulo = new JLabel("GESTIÓN DE CLIENTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 102, 204));
        
        // Campos del formulario
        txtNombres = new JTextField(20);
        txtApellidos = new JTextField(20);
        txtDireccion = new JTextArea(3, 20);
        txtDireccion.setLineWrap(true);
        txtDireccion.setWrapStyleWord(true);
        txtTelefono = new JTextField(15);
        txtCorreo = new JTextField(25);
        txtDui = new JTextField(15);
        dateChooserNacimiento = new JDateChooser();
        dateChooserNacimiento.setDateFormatString("dd/MM/yyyy");
        
        // Botones del formulario
        btnGuardar = new JButton("💾 Guardar");
        btnActualizar = new JButton("✏️ Actualizar");
        btnEliminar = new JButton("🗑️ Eliminar");
        btnLimpiar = new JButton("🔄 Limpiar");
        
        // Configurar colores de botones
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnActualizar.setBackground(new Color(52, 152, 219));
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnLimpiar.setBackground(new Color(149, 165, 166));
        btnLimpiar.setForeground(Color.WHITE);
        
        // Campo de búsqueda
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setBackground(new Color(155, 89, 182));
        btnBuscar.setForeground(Color.WHITE);
        
        // Labels informativos
        lblModo = new JLabel("Modo: Crear nuevo cliente");
        lblModo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblModo.setForeground(new Color(0, 102, 204));
        
        lblContador = new JLabel("Total de clientes: 0");
        lblContador.setFont(new Font("Arial", Font.PLAIN, 12));
        lblContador.setForeground(Color.GRAY);
        
        // Estado inicial de botones
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con título
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.add(lblTitulo);
        
        // Panel principal dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.4);
        
        // Panel izquierdo - Formulario
        JPanel panelFormulario = crearPanelFormulario();
        splitPane.setLeftComponent(panelFormulario);
        
        // Panel derecho - Tabla
        JPanel panelTabla = crearPanelTabla();
        splitPane.setRightComponent(panelTabla);
        
        // Panel inferior con información
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(new Color(236, 240, 241));
        panelInferior.add(lblContador);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        panel.setBackground(Color.WHITE);
        
        // Panel de campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombres
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Nombres:*"), gbc);
        gbc.gridx = 1;
        panelCampos.add(txtNombres, gbc);
        
        // Apellidos
        gbc.gridx = 0; gbc.gridy = 1;
        panelCampos.add(new JLabel("Apellidos:*"), gbc);
        gbc.gridx = 1;
        panelCampos.add(txtApellidos, gbc);
        
        // Dirección
        gbc.gridx = 0; gbc.gridy = 2;
        panelCampos.add(new JLabel("Dirección:*"), gbc);
        gbc.gridx = 1;
        JScrollPane scrollDireccion = new JScrollPane(txtDireccion);
        scrollDireccion.setPreferredSize(new Dimension(200, 60));
        panelCampos.add(scrollDireccion, gbc);
        
        // Teléfono
        gbc.gridx = 0; gbc.gridy = 3;
        panelCampos.add(new JLabel("Teléfono:*"), gbc);
        gbc.gridx = 1;
        panelCampos.add(txtTelefono, gbc);
        
        // Correo
        gbc.gridx = 0; gbc.gridy = 4;
        panelCampos.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        panelCampos.add(txtCorreo, gbc);
        
        // DUI
        gbc.gridx = 0; gbc.gridy = 5;
        panelCampos.add(new JLabel("DUI:*"), gbc);
        gbc.gridx = 1;
        panelCampos.add(txtDui, gbc);
        
        // Fecha de nacimiento
        gbc.gridx = 0; gbc.gridy = 6;
        panelCampos.add(new JLabel("Fecha Nacimiento:*"), gbc);
        gbc.gridx = 1;
        dateChooserNacimiento.setPreferredSize(new Dimension(150, 25));
        panelCampos.add(dateChooserNacimiento, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        // Panel de modo
        JPanel panelModo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelModo.setBackground(Color.WHITE);
        panelModo.add(lblModo);
        
        // Nota de campos obligatorios
        JLabel lblNota = new JLabel("Los campos marcados con * son obligatorios");
        lblNota.setFont(new Font("Arial", Font.PLAIN, 10));
        lblNota.setForeground(Color.GRAY);
        JPanel panelNota = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNota.setBackground(Color.WHITE);
        panelNota.add(lblNota);
        
        panel.add(panelCampos, BorderLayout.CENTER);
        panel.add(panelModo, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Panel contenedor para nota
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.add(panel, BorderLayout.CENTER);
        panelContenedor.add(panelNota, BorderLayout.SOUTH);
        
        return panelContenedor;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        // Crear tabla
        configurarTabla();
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setPreferredSize(new Dimension(600, 400));
        
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void configurarTabla() {
        // Definir columnas
        String[] columnas = {"ID", "Nombres", "Apellidos", "Teléfono", "Correo", "DUI", "Fecha Registro"};
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla no editable
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setRowHeight(25);
        
        // Configurar ancho de columnas
        tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(120); // Nombres
        tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(120); // Apellidos
        tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(100); // Teléfono
        tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(150); // Correo
        tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(100); // DUI
        tablaClientes.getColumnModel().getColumn(6).setPreferredWidth(120); // Fecha
        
        // Ocultar columna ID
        tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setWidth(0);
    }
    
    private void configurarEventos() {
        // Evento del botón guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });
        
        // Evento del botón actualizar
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });
        
        // Evento del botón eliminar
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });
        
        // Evento del botón limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                cambiarModoCreacion();
            }
        });
        
        // Evento del botón buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });
        
        // Evento de búsqueda en tiempo real
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    controller.cargarClientes();
                }
            }
        });
        
        // Evento de Enter en campo de búsqueda
        txtBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });
        
        // Evento de selección en tabla
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    editarClienteSeleccionado();
                }
            }
        });
        
        // Evento de selección de fila
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    clienteSeleccionadoId = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
                }
            }
        });
    }
    
    private void guardarCliente() {
        Date fechaNacimiento = null;
        if (dateChooserNacimiento.getDate() != null) {
            fechaNacimiento = new Date(dateChooserNacimiento.getDate().getTime());
        }
        
        controller.registrarCliente(
            txtNombres.getText(),
            txtApellidos.getText(),
            txtDireccion.getText(),
            txtTelefono.getText(),
            txtCorreo.getText(),
            txtDui.getText(),
            fechaNacimiento
        );
    }
    
    private void actualizarCliente() {
        Date fechaNacimiento = null;
        if (dateChooserNacimiento.getDate() != null) {
            fechaNacimiento = new Date(dateChooserNacimiento.getDate().getTime());
        }
        
        controller.actualizarCliente(
            clienteSeleccionadoId,
            txtNombres.getText(),
            txtApellidos.getText(),
            txtDireccion.getText(),
            txtTelefono.getText(),
            txtCorreo.getText(),
            txtDui.getText(),
            fechaNacimiento
        );
    }
    
    private void eliminarCliente() {
        if (clienteSeleccionadoId > 0) {
            String nombreCompleto = txtNombres.getText() + " " + txtApellidos.getText();
            controller.eliminarCliente(clienteSeleccionadoId, nombreCompleto);
        }
    }
    
    private void buscarClientes() {
        String termino = txtBuscar.getText().trim();
        controller.buscarClientes(termino);
    }
    
    private void editarClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            controller.cargarClienteParaEditar(id);
        }
    }
    
    // Métodos públicos para el controlador
    
    public void cargarClientesEnTabla(List<Cliente> clientes) {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Cliente cliente : clientes) {
            Object[] fila = {
                cliente.getId(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getDui(),
                cliente.getFechaRegistro() != null ? sdf.format(cliente.getFechaRegistro()) : ""
            };
            modeloTabla.addRow(fila);
        }
    }
    
    public void cargarDatosEnFormulario(Cliente cliente) {
        txtNombres.setText(cliente.getNombres());
        txtApellidos.setText(cliente.getApellidos());
        txtDireccion.setText(cliente.getDireccion());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreo.setText(cliente.getCorreo());
        txtDui.setText(cliente.getDui());
        
        if (cliente.getFechaNacimiento() != null) {
            dateChooserNacimiento.setDate(cliente.getFechaNacimiento());
        }
        
        clienteSeleccionadoId = cliente.getId();
    }
    
    public void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDui.setText("");
        dateChooserNacimiento.setDate(null);
        clienteSeleccionadoId = -1;
        
        // Limpiar selección de tabla
        tablaClientes.clearSelection();
    }
    
    public void cambiarModoCreacion() {
        modoEdicion = false;
        lblModo.setText("Modo: Crear nuevo cliente");
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
    }
    
    public void cambiarModoEdicion() {
        modoEdicion = true;
        lblModo.setText("Modo: Editar cliente seleccionado");
        btnGuardar.setVisible(false);
        btnActualizar.setVisible(true);
        btnEliminar.setVisible(true);
    }
    
    public void actualizarContadorClientes(int total) {
        lblContador.setText("Total de clientes: " + total);
    }
}