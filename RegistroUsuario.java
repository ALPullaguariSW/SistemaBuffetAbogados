package buffetabogados.vista;

import buffetabogados.controlador.LoginController;
import buffetabogados.modelo.Usuario;
import buffetabogados.util.Validaciones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroUsuario extends JDialog {
    
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JLabel lblMensaje;
    
    private LoginController controller;
    private JFrame parent;
    
    public RegistroUsuario(JFrame parent) {
        super(parent, "Registro de Usuario", true);
        this.parent = parent;
        this.controller = new LoginController();
        
        initComponents();
        setupEventListeners();
        
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        JLabel titulo = new JLabel("Registro de Usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 51, 102));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombres
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Nombres:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombres = new JTextField(20);
        mainPanel.add(txtNombres, gbc);
        
        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Apellidos:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtApellidos = new JTextField(20);
        mainPanel.add(txtApellidos, gbc);
        
        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtUsuario = new JTextField(20);
        mainPanel.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPassword = new JPasswordField(20);
        mainPanel.add(txtPassword, gbc);
        
        // Confirmar contraseña
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Confirmar Contraseña:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtConfirmarPassword = new JPasswordField(20);
        mainPanel.add(txtConfirmarPassword, gbc);
        
        // Rol
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Rol:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] roles = {"Cliente", "Empleado", "Abogado"};
        cmbRol = new JComboBox<>(roles);
        mainPanel.add(cmbRol, gbc);
        
        // Mensaje
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblMensaje = new JLabel(" ");
        lblMensaje.setForeground(Color.RED);
        mainPanel.add(lblMensaje, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(0, 51, 102));
        btnRegistrar.setForeground(Color.WHITE);
        btnCancelar = new JButton("Cancelar");
        
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void registrarUsuario() {
        try {
            // Limpiar mensaje anterior
            lblMensaje.setText(" ");
            
            // Obtener datos
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String usuario = txtUsuario.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirmarPassword = new String(txtConfirmarPassword.getPassword());
            String rol = (String) cmbRol.getSelectedItem();
            
            // Validaciones
            if (Validaciones.esCampoVacio(nombres, "Nombres") ||
                Validaciones.esCampoVacio(apellidos, "Apellidos") ||
                Validaciones.esCampoVacio(usuario, "Usuario") ||
                Validaciones.esCampoVacio(password, "Contraseña")) {
                lblMensaje.setText("Todos los campos son obligatorios");
                return;
            }
            
            if (!Validaciones.esNombreValido(nombres)) {
                lblMensaje.setText("Nombres contiene caracteres inválidos");
                return;
            }
            
            if (!Validaciones.esNombreValido(apellidos)) {
                lblMensaje.setText("Apellidos contiene caracteres inválidos");
                return;
            }
            
            if (usuario.length() < 4) {
                lblMensaje.setText("El usuario debe tener al menos 4 caracteres");
                return;
            }
            
            if (!Validaciones.esPasswordValido(password)) {
                lblMensaje.setText("La contraseña debe tener al menos 6 caracteres");
                return;
            }
            
            if (!password.equals(confirmarPassword)) {
                lblMensaje.setText("Las contraseñas no coinciden");
                return;
            }
            
            // Crear usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombres(nombres);
            nuevoUsuario.setApellidos(apellidos);
            nuevoUsuario.setUsuario(usuario);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setRol(rol);
            nuevoUsuario.setActivo(true);
            
            // Registrar
            if (controller.registrarUsuario(nuevoUsuario)) {
                JOptionPane.showMessageDialog(this, 
                    "Usuario registrado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                lblMensaje.setText("Error al registrar. El usuario puede ya existir");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            lblMensaje.setText("Error interno del sistema");
        }
    }
}