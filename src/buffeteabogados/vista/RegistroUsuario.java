/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package buffeteabogados.vista;
import buffeteabogados.modelo.Usuario;
import buffeteabogados.controlador.LoginController;
import buffeteabogados.util.Validaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author axel_
 */
public class RegistroUsuario extends javax.swing.JDialog {
    
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JLabel lblEstado;
    
    private LoginController controller;
    private JFrame parent;
    
    /**
     * Creates new form RegistroUsuario
     */
    public RegistroUsuario(JFrame parent) {
        super(parent, "Registro de Usuario", true);
        this.parent = parent;
        this.controller = new LoginController((Login) parent);
        
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Campos de texto
        txtNombres = new JTextField(20);
        txtApellidos = new JTextField(20);
        txtUsuario = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtConfirmarPassword = new JPasswordField(20);
        
        // ComboBox para rol
        String[] roles = {"Cliente", "Empleado", "Abogado"};
        cmbRol = new JComboBox<>(roles);
        cmbRol.setSelectedIndex(0); // Cliente por defecto
        
        // Botones
        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");
        
        // Label de estado
        lblEstado = new JLabel("Complete todos los campos para registrarse");
        lblEstado.setForeground(Color.BLUE);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Configurar botón registrar como predeterminado
        getRootPane().setDefaultButton(btnRegistrar);
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        JLabel lblTitulo = new JLabel("REGISTRO DE USUARIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Separador
        JSeparator separador = new JSeparator();
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(separador, gbc);
        
        // Nombres
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Nombres:*"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtNombres, gbc);
        
        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Apellidos:*"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtApellidos, gbc);
        
        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Usuario:*"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Contraseña:*"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtPassword, gbc);
        
        // Confirmar contraseña
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Confirmar:*"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtConfirmarPassword, gbc);
        
        // Rol
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Rol:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(cmbRol, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblEstado, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);
        
        // Panel de ayuda
        JPanel panelAyuda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblAyuda = new JLabel("Los campos marcados con * son obligatorios");
        lblAyuda.setFont(new Font("Arial", Font.PLAIN, 10));
        lblAyuda.setForeground(Color.GRAY);
        panelAyuda.add(lblAyuda);
        
        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelAyuda, BorderLayout.NORTH);
    }
    
    private void configurarEventos() {
        // Evento para botón registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        
        // Evento para botón cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Eventos de validación en tiempo real
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarCampoEnTiempoReal();
            }
        });
        
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarCampoEnTiempoReal();
            }
        });
        
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarCampoEnTiempoReal();
            }
        });
        
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarCampoEnTiempoReal();
            }
        });
        
        txtConfirmarPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarCampoEnTiempoReal();
            }
        });
    }
    
    private void validarCampoEnTiempoReal() {
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());
        
        // Validar que todos los campos estén completos
        if (Validaciones.campoVacio(nombres) || Validaciones.campoVacio(apellidos) ||
            Validaciones.campoVacio(usuario) || Validaciones.campoVacio(password) ||
            Validaciones.campoVacio(confirmarPassword)) {
            
            lblEstado.setText("Complete todos los campos obligatorios");
            lblEstado.setForeground(Color.ORANGE);
            btnRegistrar.setEnabled(false);
            return;
        }
        
        // Validar formato de nombres
        if (!Validaciones.nombreValido(nombres)) {
            lblEstado.setText("Los nombres solo pueden contener letras y espacios");
            lblEstado.setForeground(Color.RED);
            btnRegistrar.setEnabled(false);
            return;
        }
        
        // Validar formato de apellidos
        if (!Validaciones.nombreValido(apellidos)) {
            lblEstado.setText("Los apellidos solo pueden contener letras y espacios");
            lblEstado.setForeground(Color.RED);
            btnRegistrar.setEnabled(false);
            return;
        }
        
        // Validar formato de usuario
        if (!Validaciones.usuarioValido(usuario)) {
            lblEstado.setText("El usuario debe tener al menos 3 caracteres (letras, números, _)");
            lblEstado.setForeground(Color.RED);
            btnRegistrar.setEnabled(false);
            return;
        }
        
        // Validar longitud de contraseña
        if (!Validaciones.passwordValida(password)) {
            lblEstado.setText("La contraseña debe tener al menos 6 caracteres");
            lblEstado.setForeground(Color.RED);
            btnRegistrar.setEnabled(false);
            return;
        }
        
        // Validar que las contraseñas coincidan
        if (!password.equals(confirmarPassword)) {
            lblEstado.setText("Las contraseñas no coinciden");
            lblEstado.setForeground(Color.RED);
            btnRegistrar.setEnabled(false);
            return;
        }
        
        // Si llegamos aquí, todo está válido
        lblEstado.setText("✓ Formulario válido - Listo para registrar");
        lblEstado.setForeground(Color.GREEN);
        btnRegistrar.setEnabled(true);
    }
    
    private void registrarUsuario() {
        // Validar una vez más antes de registrar
        if (!validarFormulario()) {
            return;
        }
        
        // Crear objeto usuario
        Usuario nuevoUsuario = new Usuario(
            txtNombres.getText().trim(),
            txtApellidos.getText().trim(),
            txtUsuario.getText().trim(),
            new String(txtPassword.getPassword()),
            (String) cmbRol.getSelectedItem()
        );
        
        // Intentar registrar
        boolean exito = controller.registrarUsuario(nuevoUsuario);
        
        if (exito) {
            // Limpiar formulario
            limpiarFormulario();
            
            // Cerrar ventana
            dispose();
        }
    }
    
    private boolean validarFormulario() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());
        
        // Validar campos obligatorios
        if (Validaciones.campoVacio(nombres)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Nombres"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        
        if (Validaciones.campoVacio(apellidos)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Apellidos"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        
        if (Validaciones.campoVacio(usuario)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Usuario"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtUsuario.requestFocus();
            return false;
        }
        
        if (Validaciones.campoVacio(password)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Contraseña"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return false;
        }
        
        // Validar formatos
        if (!Validaciones.nombreValido(nombres)) {
            JOptionPane.showMessageDialog(this,
                "Los nombres solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        
        if (!Validaciones.nombreValido(apellidos)) {
            JOptionPane.showMessageDialog(this,
                "Los apellidos solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        
        if (!Validaciones.usuarioValido(usuario)) {
            JOptionPane.showMessageDialog(this,
                "El usuario debe contener al menos 3 caracteres (letras, números y guiones bajos)",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtUsuario.requestFocus();
            return false;
        }
        
        if (!Validaciones.passwordValida(password)) {
            JOptionPane.showMessageDialog(this,
                "La contraseña debe tener al menos 6 caracteres",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return false;
        }
        
        // Validar que las contraseñas coincidan
        if (!password.equals(confirmarPassword)) {
            JOptionPane.showMessageDialog(this,
                "Las contraseñas no coinciden",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtConfirmarPassword.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        txtConfirmarPassword.setText("");
        cmbRol.setSelectedIndex(0);
        lblEstado.setText("Complete todos los campos para registrarse");
        lblEstado.setForeground(Color.BLUE);
        btnRegistrar.setEnabled(true);
    }
}