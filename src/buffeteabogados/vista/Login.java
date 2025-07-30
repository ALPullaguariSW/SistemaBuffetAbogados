package buffeteabogados.vista;

import buffeteabogados.modelo.Usuario;
import buffeteabogados.controlador.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de inicio de sesión
 * @author axel_
 */
public class Login extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrarse;
    private JButton btnOlvidarPassword;
    private JButton btnConfigurarConexion;
    private JLabel lblEstado;
    
    private LoginController controller;
    
    public Login() {
        this.controller = new LoginController(this);
        
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        
        setTitle("Sistema de Gestión - Buffet de Abogados");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // Campos de entrada
        txtUsuario = new JTextField(20);
        txtPassword = new JPasswordField(20);
        
        // Botones
        btnIngresar = new JButton("Ingresar");
        btnRegistrarse = new JButton("Registrarse");
        btnOlvidarPassword = new JButton("¿Olvidaste tu contraseña?");
        btnConfigurarConexion = new JButton("Configurar Conexión");
        
        // Label de estado
        lblEstado = new JLabel("Ingrese sus credenciales");
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstado.setForeground(Color.BLUE);
        
        // Configurar botón por defecto
        getRootPane().setDefaultButton(btnIngresar);
        
        // Configurar estilos
        configurarEstilos();
    }
    
    private void configurarEstilos() {
        // Estilo para botones principales
        btnIngresar.setBackground(new Color(70, 130, 180));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 12));
        btnIngresar.setFocusPainted(false);
        
        btnRegistrarse.setBackground(new Color(34, 139, 34));
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 12));
        btnRegistrarse.setFocusPainted(false);
        
        // Estilo para botones secundarios
        btnOlvidarPassword.setFont(new Font("Arial", Font.PLAIN, 10));
        btnOlvidarPassword.setForeground(new Color(0, 102, 204));
        btnOlvidarPassword.setBorderPainted(false);
        btnOlvidarPassword.setContentAreaFilled(false);
        
        btnConfigurarConexion.setFont(new Font("Arial", Font.PLAIN, 10));
        btnConfigurarConexion.setForeground(Color.GRAY);
        btnConfigurarConexion.setBorderPainted(false);
        btnConfigurarConexion.setContentAreaFilled(false);
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(new Color(240, 248, 255));
        panelTitulo.setBorder(BorderFactory.createEtchedBorder());
        
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Buffet de Abogados");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblSubtitulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(lblSubtitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con formulario
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panelCentral.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelCentral.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelCentral.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelCentral.add(txtPassword, gbc);
        
        // Estado
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelCentral.add(lblEstado, gbc);
        
        // Botones principales
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelCentral.add(btnIngresar, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelCentral.add(btnRegistrarse, gbc);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior con opciones adicionales
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.add(btnOlvidarPassword);
        panelInferior.add(new JLabel(" | "));
        panelInferior.add(btnConfigurarConexion);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        // Evento para botón ingresar
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
        
        // Evento para botón registrarse
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
        
        // Evento para olvidar contraseña
        btnOlvidarPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRecuperarPassword();
            }
        });
        
        // Evento para configurar conexión
        btnConfigurarConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirConfiguracionConexion();
            }
        });
    }
    
    private void autenticarUsuario() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Cambiar estado
        lblEstado.setText("Validando credenciales...");
        lblEstado.setForeground(Color.ORANGE);
        btnIngresar.setEnabled(false);
        
        // Simular pequeña pausa para mostrar el estado
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Autenticar usuario
                Usuario usuarioAutenticado = controller.autenticarUsuario(usuario, password);
                
                if (usuarioAutenticado != null) {
                    lblEstado.setText("✓ Acceso concedido");
                    lblEstado.setForeground(Color.GREEN);
                    
                    // Abrir dashboard después de un breve momento
                    Timer dashboardTimer = new Timer(1000, ev -> {
                        abrirDashboard(usuarioAutenticado);
                    });
                    dashboardTimer.setRepeats(false);
                    dashboardTimer.start();
                } else {
                    lblEstado.setText("Credenciales incorrectas");
                    lblEstado.setForeground(Color.RED);
                    btnIngresar.setEnabled(true);
                    txtPassword.setText("");
                    txtPassword.requestFocus();
                }
                
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void abrirDashboard(Usuario usuario) {
        // Crear y mostrar dashboard
        new Dashboard(usuario);
        
        // Cerrar ventana de login
        dispose();
    }
    
    private void abrirRegistro() {
        new RegistroUsuario(this);
    }
    
    private void abrirRecuperarPassword() {
        JOptionPane.showMessageDialog(this,
            "Función de recuperación de contraseña en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirConfiguracionConexion() {
        JOptionPane.showMessageDialog(this,
            "Configuración de conexión a base de datos en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Limpia los campos del formulario
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
        lblEstado.setText("Ingrese sus credenciales");
        lblEstado.setForeground(Color.BLUE);
        btnIngresar.setEnabled(true);
        txtUsuario.requestFocus();
    }
    
    /**
     * Establece el foco en el campo de usuario
     */
    public void enfocarUsuario() {
        txtUsuario.requestFocus();
    }
}