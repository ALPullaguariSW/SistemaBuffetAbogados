package buffetabogados.vista;

import buffetabogados.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    
    private Usuario usuarioActual;
    private JLabel lblBienvenida;
    private JMenuBar menuBar;
    private JPanel mainPanel;
    
    public Dashboard(Usuario usuarioObj) {
        this.usuarioActual = usuarioObj;
        
        initComponents();
        configurarPermisos();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("Sistema de Gestión - Buffet de Abogados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel lblTitulo = new JLabel("BUFFET DE ABOGADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        
        lblBienvenida = new JLabel("Bienvenido: " + usuarioActual.getNombreCompleto() + " (" + usuarioActual.getRol() + ")");
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenida.setForeground(Color.WHITE);
        
        headerPanel.add(lblTitulo, BorderLayout.WEST);
        headerPanel.add(lblBienvenida, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Menu Bar
        crearMenuBar();
        setJMenuBar(menuBar);
        
        // Panel principal con módulos
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        crearModulos();
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(0, 51, 102));
        footerPanel.add(new JLabel("© 2024 Sistema de Gestión Legal"));
        ((JLabel) footerPanel.getComponent(0)).setForeground(Color.WHITE);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void crearMenuBar() {
        menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        
        // Menú Gestión
        JMenu menuGestion = new JMenu("Gestión");
        
        JMenuItem itemClientes = new JMenuItem("Gestión de Clientes");
        itemClientes.addActionListener(e -> abrirGestionClientes());
        
        JMenuItem itemEmpleados = new JMenuItem("Gestión de Empleados");
        itemEmpleados.addActionListener(e -> abrirGestionEmpleados());
        
        JMenuItem itemCasos = new JMenuItem("Gestión de Casos");
        itemCasos.addActionListener(e -> abrirGestionCasos());
        
        JMenuItem itemAudiencias = new JMenuItem("Gestión de Audiencias");
        itemAudiencias.addActionListener(e -> abrirGestionAudiencias());
        
        menuGestion.add(itemClientes);
        menuGestion.add(itemEmpleados);
        menuGestion.add(itemCasos);
        menuGestion.add(itemAudiencias);
        
        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReportes = new JMenuItem("Generar Reportes");
        itemReportes.addActionListener(e -> abrirReportes());
        menuReportes.add(itemReportes);
        
        // Menú Usuario
        JMenu menuUsuario = new JMenu("Usuario");
        JMenuItem itemCambiarPassword = new JMenuItem("Cambiar Contraseña");
        itemCambiarPassword.addActionListener(e -> cambiarPassword());
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        
        menuUsuario.add(itemCambiarPassword);
        menuUsuario.addSeparator();
        menuUsuario.add(itemCerrarSesion);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuGestion);
        menuBar.add(menuReportes);
        menuBar.add(menuUsuario);
    }
    
    private void crearModulos() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Título de módulos
        JLabel lblModulos = new JLabel("MÓDULOS DEL SISTEMA");
        lblModulos.setFont(new Font("Arial", Font.BOLD, 20));
        lblModulos.setForeground(new Color(0, 51, 102));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblModulos, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Primera fila de módulos
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(crearBotonModulo("Gestión de Clientes", "Administrar información de clientes", e -> abrirGestionClientes()), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(crearBotonModulo("Gestión de Empleados", "Administrar información de empleados", e -> abrirGestionEmpleados()), gbc);
        
        gbc.gridx = 2;
        mainPanel.add(crearBotonModulo("Gestión de Casos", "Administrar casos legales", e -> abrirGestionCasos()), gbc);
        
        // Segunda fila de módulos
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(crearBotonModulo("Gestión de Audiencias", "Programar y gestionar audiencias", e -> abrirGestionAudiencias()), gbc);
        
        gbc.gridx = 1;
        mainPanel.add(crearBotonModulo("Reportes", "Generar reportes del sistema", e -> abrirReportes()), gbc);
        
        gbc.gridx = 2;
        mainPanel.add(crearBotonModulo("Configuración", "Configurar sistema", e -> abrirConfiguracion()), gbc);
    }
    
    private JPanel crearBotonModulo(String titulo, String descripcion, ActionListener action) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(250, 150));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 51, 102), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 51, 102));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblDescripcion = new JLabel("<html><center>" + descripcion + "</center></html>");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDescripcion.setForeground(Color.GRAY);
        lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton btnAcceder = new JButton("Acceder");
        btnAcceder.setBackground(new Color(0, 51, 102));
        btnAcceder.setForeground(Color.WHITE);
        btnAcceder.setFont(new Font("Arial", Font.BOLD, 12));
        btnAcceder.addActionListener(action);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblDescripcion, BorderLayout.CENTER);
        panel.add(btnAcceder, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void configurarPermisos() {
        // Configurar permisos según el rol del usuario
        if (!usuarioActual.esAdmin()) {
            // Los clientes solo pueden ver sus propios casos
            if ("Cliente".equals(usuarioActual.getRol())) {
                // Deshabilitar ciertos módulos para clientes
            }
        }
    }
    
    // Métodos para abrir módulos
    private void abrirGestionClientes() {
        JOptionPane.showMessageDialog(this, "Abriendo Gestión de Clientes...\n(En desarrollo)", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionEmpleados() {
        JOptionPane.showMessageDialog(this, "Abriendo Gestión de Empleados...\n(En desarrollo)", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionCasos() {
        JOptionPane.showMessageDialog(this, "Abriendo Gestión de Casos...\n(En desarrollo)", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionAudiencias() {
        JOptionPane.showMessageDialog(this, "Abriendo Gestión de Audiencias...\n(En desarrollo)", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirReportes() {
        JOptionPane.showMessageDialog(this, "Abriendo Reportes...\n(En desarrollo)", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirConfiguracion() {
        // Abrir ventana de configuración de conexión
        new ConfiguracionConexion(this);
    }
    
    private void cambiarPassword() {
        JOptionPane.showMessageDialog(this, "Cambiar Contraseña...\n(En desarrollo)", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea cerrar sesión?", 
            "Cerrar Sesión", 
            JOptionPane.YES_NO_OPTION);
            
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            new Login();
        }
    }
}