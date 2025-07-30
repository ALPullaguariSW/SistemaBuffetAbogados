package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Ventana de login para el sistema de buffet de abogados
 */
public class Login extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrar;
    private JButton btnOlvidePassword;
    private JButton btnConfigurarConexion;
    private JLabel lblEstado;
    
    public Login() {
        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Buffet de Abogados - Login");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void inicializarComponentes() {
        // Campos de texto
        txtUsuario = new JTextField(20);
        txtPassword = new JPasswordField(20);
        
        // Botones
        btnIngresar = new JButton("Ingresar");
        btnRegistrar = new JButton("Registrarse");
        btnOlvidePassword = new JButton("¿Olvidaste tu contraseña?");
        btnConfigurarConexion = new JButton("Configurar Conexión");
        
        // Label de estado
        lblEstado = new JLabel("Bienvenido al Sistema de Buffet de Abogados");
        lblEstado.setForeground(Color.BLUE);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Configurar botón ingresar como predeterminado
        getRootPane().setDefaultButton(btnIngresar);
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Título
        JLabel lblTitulo = new JLabel("BUFFET DE ABOGADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Sistema de Gestión Legal");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        gbc.gridy = 1;
        panelPrincipal.add(lblSubtitulo, gbc);
        
        // Separador
        JSeparator separador = new JSeparator();
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(separador, gbc);
        
        // Usuario
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtPassword, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblEstado, gbc);
        
        // Panel de botones principales
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnIngresar);
        panelBotones.add(btnRegistrar);
        
        gbc.gridy = 6;
        panelPrincipal.add(panelBotones, gbc);
        
        // Panel de botones secundarios
        JPanel panelBotonesSecundarios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonesSecundarios.add(btnOlvidePassword);
        panelBotonesSecundarios.add(btnConfigurarConexion);
        
        gbc.gridy = 7;
        panelPrincipal.add(panelBotonesSecundarios, gbc);
        
        // Información de usuario por defecto
        JLabel lblInfo = new JLabel("Usuario por defecto: admin | Contraseña: admin123");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 10));
        lblInfo.setForeground(Color.GRAY);
        gbc.gridy = 8;
        panelPrincipal.add(lblInfo, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void configurarEventos() {
        // Evento para botón ingresar
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
        
        // Evento para botón registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
        
        // Evento para botón olvidé contraseña
        btnOlvidePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRecuperacionPassword();
            }
        });
        
        // Evento para botón configurar conexión
        btnConfigurarConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirConfiguracionConexion();
            }
        });
        
        // Eventos de teclado
        txtUsuario.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPassword.requestFocus();
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        txtPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autenticarUsuario();
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
    private void autenticarUsuario() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Validar campos
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor complete todos los campos",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Por ahora, validación simple para el usuario admin
        if ("admin".equals(usuario) && "admin123".equals(password)) {
            lblEstado.setText("Autenticación exitosa");
            lblEstado.setForeground(Color.GREEN);
            
            JOptionPane.showMessageDialog(this,
                "Bienvenido al sistema, " + usuario,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Aquí se abriría el dashboard
            abrirDashboard(usuario, "Abogado");
            
        } else {
            lblEstado.setText("Usuario o contraseña incorrectos");
            lblEstado.setForeground(Color.RED);
            
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos",
                "Error", JOptionPane.ERROR_MESSAGE);
            
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
    
    private void abrirRegistro() {
        // TODO: Implementar ventana de registro
        JOptionPane.showMessageDialog(this,
            "Funcionalidad de registro en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirRecuperacionPassword() {
        // TODO: Implementar ventana de recuperación de contraseña
        JOptionPane.showMessageDialog(this,
            "Funcionalidad de recuperación de contraseña en desarrollo",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirConfiguracionConexion() {
        ConfiguracionConexion configDialog = new ConfiguracionConexion(this);
        configDialog.setVisible(true);
    }
    
    private void abrirDashboard(String usuario, String rol) {
        // TODO: Implementar dashboard
        JOptionPane.showMessageDialog(this,
            "Dashboard en desarrollo\nUsuario: " + usuario + "\nRol: " + rol,
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
} 