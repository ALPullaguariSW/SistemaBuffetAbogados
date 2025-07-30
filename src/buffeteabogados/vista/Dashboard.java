package buffeteabogados.vista;

import buffeteabogados.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard principal del sistema
 * @author axel_
 */
public class Dashboard extends JFrame {
    
    private Usuario usuarioActual;
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuSistema, menuAyuda;
    private JMenuItem itemSalir, itemCambiarPassword, itemAcercaDe;
    
    // Botones del dashboard
    private JButton btnGestionClientes;
    private JButton btnGestionEmpleados;
    private JButton btnGestionCasos;
    private JButton btnGestionAudiencias;
    private JButton btnReportes;
    private JButton btnConfiguracion;
    
    public Dashboard(Usuario usuario) {
        this.usuarioActual = usuario;
        
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        aplicarPermisosSegunRol();
        
        setTitle("Sistema de Gestión - Buffet de Abogados");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        // Crear menú
        menuBar = new JMenuBar();
        
        // Menú Archivo
        menuArchivo = new JMenu("Archivo");
        itemSalir = new JMenuItem("Salir");
        menuArchivo.add(itemSalir);
        
        // Menú Sistema
        menuSistema = new JMenu("Sistema");
        itemCambiarPassword = new JMenuItem("Cambiar Contraseña");
        menuSistema.add(itemCambiarPassword);
        
        // Menú Ayuda
        menuAyuda = new JMenu("Ayuda");
        itemAcercaDe = new JMenuItem("Acerca de");
        menuAyuda.add(itemAcercaDe);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuSistema);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
        
        // Crear botones del dashboard
        btnGestionClientes = new JButton("Gestión de Clientes");
        btnGestionEmpleados = new JButton("Gestión de Empleados");
        btnGestionCasos = new JButton("Gestión de Casos");
        btnGestionAudiencias = new JButton("Gestión de Audiencias");
        btnReportes = new JButton("Reportes PDF");
        btnConfiguracion = new JButton("Configuración del Sistema");
        
        // Configurar iconos y estilos
        configurarEstilosBotones();
    }
    
    private void configurarEstilosBotones() {
        JButton[] botones = {btnGestionClientes, btnGestionEmpleados, btnGestionCasos, 
                           btnGestionAudiencias, btnReportes, btnConfiguracion};
        
        for (JButton boton : botones) {
            boton.setPreferredSize(new Dimension(200, 80));
            boton.setFont(new Font("Arial", Font.BOLD, 12));
            boton.setBackground(new Color(70, 130, 180));
            boton.setForeground(Color.WHITE);
            boton.setFocusPainted(false);
            boton.setBorder(BorderFactory.createRaisedBevelBorder());
        }
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con información del usuario
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(240, 248, 255));
        panelSuperior.setBorder(BorderFactory.createEtchedBorder());
        
        JLabel lblBienvenida = new JLabel("Bienvenido: " + usuarioActual.getNombreCompleto() + 
                                        " (" + usuarioActual.getRol() + ")");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 14));
        lblBienvenida.setForeground(new Color(0, 102, 204));
        panelSuperior.add(lblBienvenida);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central con botones
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Primera fila
        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(btnGestionClientes, gbc);
        
        gbc.gridx = 1;
        panelCentral.add(btnGestionEmpleados, gbc);
        
        gbc.gridx = 2;
        panelCentral.add(btnGestionCasos, gbc);
        
        // Segunda fila
        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(btnGestionAudiencias, gbc);
        
        gbc.gridx = 1;
        panelCentral.add(btnReportes, gbc);
        
        gbc.gridx = 2;
        panelCentral.add(btnConfiguracion, gbc);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(new Color(240, 248, 255));
        JLabel lblEstado = new JLabel("Sistema de Gestión para Buffet de Abogados - Versión 1.0");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 10));
        lblEstado.setForeground(Color.GRAY);
        panelInferior.add(lblEstado);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        // Eventos de menú
        itemSalir.addActionListener(e -> System.exit(0));
        
        itemCambiarPassword.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Función en desarrollo",
                "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        
        itemAcercaDe.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Sistema de Gestión para Buffet de Abogados\n" +
                "Versión 1.0\n" +
                "Desarrollado con Java Swing",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Eventos de botones
        btnGestionClientes.addActionListener(e -> abrirGestionClientes());
        btnGestionEmpleados.addActionListener(e -> abrirGestionEmpleados());
        btnGestionCasos.addActionListener(e -> abrirGestionCasos());
        btnGestionAudiencias.addActionListener(e -> abrirGestionAudiencias());
        btnReportes.addActionListener(e -> abrirReportes());
        btnConfiguracion.addActionListener(e -> abrirConfiguracion());
    }
    
    private void aplicarPermisosSegunRol() {
        String rol = usuarioActual.getRol();
        
        switch (rol) {
            case "Cliente":
                // Los clientes solo ven sus casos y reportes
                btnGestionClientes.setEnabled(false);
                btnGestionEmpleados.setEnabled(false);
                btnGestionCasos.setText("Mis Casos");
                btnGestionAudiencias.setEnabled(false);
                btnReportes.setText("Mi Reporte");
                btnConfiguracion.setEnabled(false);
                break;
                
            case "Empleado":
                // Los empleados tienen acceso limitado
                btnGestionEmpleados.setEnabled(false);
                btnConfiguracion.setEnabled(false);
                break;
                
            case "Abogado":
                // Los abogados tienen acceso total
                // Todos los botones quedan habilitados
                break;
        }
    }
    
    // Métodos para abrir diferentes módulos
    private void abrirGestionClientes() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Gestión de Clientes en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionEmpleados() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Gestión de Empleados en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionCasos() {
        if (usuarioActual.esCliente()) {
            JOptionPane.showMessageDialog(this,
                "Vista de casos del cliente en desarrollo",
                "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Módulo de Gestión de Casos en desarrollo",
                "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void abrirGestionAudiencias() {
        JOptionPane.showMessageDialog(this,
            "Módulo de Gestión de Audiencias en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirReportes() {
        if (usuarioActual.esCliente()) {
            JOptionPane.showMessageDialog(this,
                "Reporte personal del cliente en desarrollo",
                "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Módulo de Reportes PDF en desarrollo",
                "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void abrirConfiguracion() {
        JOptionPane.showMessageDialog(this,
            "Configuración del sistema en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}