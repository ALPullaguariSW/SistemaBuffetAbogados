package buffetabogados.vista;

import buffetabogados.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard principal del sistema - Ventana principal después del login
 */
public class Dashboard extends JFrame {
    
    private Usuario usuarioActual;
    private JLabel lblBienvenida;
    private JMenuBar menuBar;
    
    // Paneles principales
    private JPanel panelPrincipal;
    private JPanel panelModulos;
    private JPanel panelEstadisticas;
    
    // Botones de módulos
    private JButton btnClientes;
    private JButton btnEmpleados;
    private JButton btnCasos;
    private JButton btnAudiencias;
    private JButton btnReportes;
    private JButton btnConfiguracion;
    
    public Dashboard(Usuario usuario) {
        this.usuarioActual = usuario;
        
        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        aplicarPermisosSegunRol();
        
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Buffet de Abogados - Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void inicializarComponentes() {
        // Menu Bar
        menuBar = new JMenuBar();
        
        // Menu Sistema
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        JMenuItem itemSalir = new JMenuItem("Salir");
        menuSistema.add(itemCerrarSesion);
        menuSistema.addSeparator();
        menuSistema.add(itemSalir);
        
        // Menu Gestión
        JMenu menuGestion = new JMenu("Gestión");
        JMenuItem itemClientes = new JMenuItem("Clientes");
        JMenuItem itemEmpleados = new JMenuItem("Empleados");
        JMenuItem itemCasos = new JMenuItem("Casos");
        JMenuItem itemAudiencias = new JMenuItem("Audiencias");
        menuGestion.add(itemClientes);
        menuGestion.add(itemEmpleados);
        menuGestion.add(itemCasos);
        menuGestion.add(itemAudiencias);
        
        // Menu Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteClientes = new JMenuItem("Reporte de Clientes");
        JMenuItem itemReporteCasos = new JMenuItem("Reporte de Casos");
        JMenuItem itemReporteGeneral = new JMenuItem("Reporte General");
        menuReportes.add(itemReporteClientes);
        menuReportes.add(itemReporteCasos);
        menuReportes.add(itemReporteGeneral);
        
        // Menu Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        menuAyuda.add(itemAcercaDe);
        
        menuBar.add(menuSistema);
        menuBar.add(menuGestion);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
        
        // Labels principales
        lblBienvenida = new JLabel("Bienvenido, " + usuarioActual.getNombreCompleto());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(0, 102, 204));
        
        // Botones de módulos
        btnClientes = crearBotonModulo("👥 Gestión de Clientes", 
            "Registrar, editar y consultar información de clientes", new Color(52, 152, 219));
        btnEmpleados = crearBotonModulo("👨‍💼 Gestión de Empleados", 
            "Administrar información del personal", new Color(46, 204, 113));
        btnCasos = crearBotonModulo("📋 Gestión de Casos", 
            "Crear y dar seguimiento a casos legales", new Color(155, 89, 182));
        btnAudiencias = crearBotonModulo("⚖️ Gestión de Audiencias", 
            "Programar y administrar audiencias", new Color(230, 126, 34));
        btnReportes = crearBotonModulo("📊 Reportes", 
            "Generar reportes del sistema", new Color(231, 76, 60));
        btnConfiguracion = crearBotonModulo("⚙️ Configuración", 
            "Configurar conexión y parámetros del sistema", new Color(149, 165, 166));
        
        // Eventos de menú
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        itemSalir.addActionListener(e -> System.exit(0));
        itemClientes.addActionListener(e -> abrirGestionClientes());
        itemEmpleados.addActionListener(e -> abrirGestionEmpleados());
        itemCasos.addActionListener(e -> abrirGestionCasos());
        itemAudiencias.addActionListener(e -> abrirGestionAudiencias());
        itemReporteClientes.addActionListener(e -> generarReporteClientes());
        itemReporteCasos.addActionListener(e -> generarReporteCasos());
        itemReporteGeneral.addActionListener(e -> generarReporteGeneral());
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());
    }
    
    private JButton crearBotonModulo(String titulo, String descripcion, Color color) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.setPreferredSize(new Dimension(280, 120));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Título del botón
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Descripción del botón
        JLabel lblDescripcion = new JLabel("<html><center>" + descripcion + "</center></html>");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 10));
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        
        boton.add(lblTitulo, BorderLayout.CENTER);
        boton.add(lblDescripcion, BorderLayout.SOUTH);
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelSuperior.setBackground(Color.WHITE);
        
        // Información del usuario
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelUsuario.setBackground(Color.WHITE);
        panelUsuario.add(lblBienvenida);
        
        JLabel lblRol = new JLabel("Rol: " + usuarioActual.getRol());
        lblRol.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRol.setForeground(Color.GRAY);
        panelUsuario.add(lblRol);
        
        // Fecha y hora
        JLabel lblFecha = new JLabel(java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFecha.setForeground(Color.GRAY);
        
        panelSuperior.add(panelUsuario, BorderLayout.WEST);
        panelSuperior.add(lblFecha, BorderLayout.EAST);
        
        // Panel central con módulos
        panelModulos = new JPanel(new GridLayout(2, 3, 20, 20));
        panelModulos.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelModulos.setBackground(new Color(236, 240, 241));
        
        panelModulos.add(btnClientes);
        panelModulos.add(btnEmpleados);
        panelModulos.add(btnCasos);
        panelModulos.add(btnAudiencias);
        panelModulos.add(btnReportes);
        panelModulos.add(btnConfiguracion);
        
        // Panel de estadísticas (simple)
        panelEstadisticas = new JPanel(new FlowLayout());
        panelEstadisticas.setBackground(new Color(52, 73, 94));
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel lblEstadisticas = new JLabel("📊 Dashboard - Sistema de Gestión Legal");
        lblEstadisticas.setForeground(Color.WHITE);
        lblEstadisticas.setFont(new Font("Arial", Font.BOLD, 12));
        panelEstadisticas.add(lblEstadisticas);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelModulos, BorderLayout.CENTER);
        add(panelEstadisticas, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        btnClientes.addActionListener(e -> abrirGestionClientes());
        btnEmpleados.addActionListener(e -> abrirGestionEmpleados());
        btnCasos.addActionListener(e -> abrirGestionCasos());
        btnAudiencias.addActionListener(e -> abrirGestionAudiencias());
        btnReportes.addActionListener(e -> abrirReportes());
        btnConfiguracion.addActionListener(e -> abrirConfiguracion());
    }
    
    private void aplicarPermisosSegunRol() {
        if (usuarioActual.esCliente()) {
            // Los clientes solo pueden ver sus casos y reportes
            btnEmpleados.setEnabled(false);
            btnConfiguracion.setEnabled(false);
            
            btnClientes.setText("👤 Mi Perfil");
            btnCasos.setText("📋 Mis Casos");
            btnReportes.setText("📊 Mis Reportes");
            
            // Ocultar botones no permitidos
            btnEmpleados.setVisible(false);
            btnConfiguracion.setVisible(false);
        }
    }
    
    // Métodos de acción para cada módulo
    private void abrirGestionClientes() {
        new GestionClientes();
    }
    
    private void abrirGestionEmpleados() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Gestión de Empleados\n(En desarrollo)",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionCasos() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Gestión de Casos\n(En desarrollo)",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionAudiencias() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Gestión de Audiencias\n(En desarrollo)",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirReportes() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Reportes\n(En desarrollo)",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirConfiguracion() {
        ConfiguracionConexion configDialog = new ConfiguracionConexion(this);
        configDialog.setVisible(true);
    }
    
    private void generarReporteClientes() {
        JOptionPane.showMessageDialog(this,
            "Generando Reporte de Clientes...\n(En desarrollo)",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void generarReporteCasos() {
        JOptionPane.showMessageDialog(this,
            "Generando Reporte de Casos...\n(En desarrollo)",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void generarReporteGeneral() {
        JOptionPane.showMessageDialog(this,
            "Generando Reporte General...\n(En desarrollo)",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "Sistema de Buffet de Abogados v1.0\n" +
            "Desarrollado en Java con Swing\n" +
            "Arquitectura MVC\n\n" +
            "© 2024 - Todos los derechos reservados",
            "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
            new Login();
        }
    }
}