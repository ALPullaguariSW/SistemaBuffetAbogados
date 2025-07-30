package buffeteabogados.vista;

import buffeteabogados.modelo.Usuario;
import buffeteabogados.modelo.UsuarioDAO;
import buffeteabogados.util.EstilosSistema;
import buffeteabogados.util.Validaciones;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class RegistroUsuario extends JDialog {

    private JTextField txtNombres, txtApellidos, txtCorreo, txtUsuario;
    private JPasswordField txtPassword, txtConfirmarPassword;
    private JButton btnRegistrar, btnCancelar;
    private JLabel lblMensaje;
    private final UsuarioDAO usuarioDAO;

    public RegistroUsuario(JFrame parent) {
        super(parent, "Registro de Nuevo Usuario", true);
        this.usuarioDAO = new UsuarioDAO();
        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        pack();
        setLocationRelativeTo(getParent());
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
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);
    }

    private void inicializarComponentes() {
        txtNombres = EstilosSistema.crearCampoTexto("Nombres completos");
        txtApellidos = EstilosSistema.crearCampoTexto("Apellidos completos");
        txtCorreo = EstilosSistema.crearCampoTexto("Correo electrónico");
        txtUsuario = EstilosSistema.crearCampoTexto("Nombre de usuario (sin espacios)");
        txtPassword = EstilosSistema.crearCampoPassword("Contraseña (mín. 6 caracteres)");
        txtConfirmarPassword = EstilosSistema.crearCampoPassword("Confirmar contraseña");
        btnRegistrar = EstilosSistema.crearBotonPrincipal("REGISTRAR", EstilosSistema.COLOR_PRIMARIO);
        btnCancelar = EstilosSistema.crearBotonSecundario("CANCELAR");
        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblMensaje.setFont(EstilosSistema.FUENTE_PEQUEÑA);
    }

    private void configurarLayout() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createTitledBorder("Crear una nueva cuenta"));
        panelPrincipal.setBackground(EstilosSistema.COLOR_FONDO_CARD);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        form.add(txtNombres, gbc);
        gbc.gridy++;
        form.add(txtApellidos, gbc);
        gbc.gridy++;
        form.add(txtCorreo, gbc);
        gbc.gridy++;
        form.add(txtUsuario, gbc);
        gbc.gridy++;
        form.add(txtPassword, gbc);
        gbc.gridy++;
        form.add(txtConfirmarPassword, gbc);
        gbc.gridy++;
        form.add(lblMensaje, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrar);

        panelPrincipal.add(form, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelContenedor.setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);
        panelContenedor.add(panelPrincipal, BorderLayout.CENTER);
        add(panelContenedor, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrarUsuario() {
        lblMensaje.setForeground(EstilosSistema.COLOR_ERROR);
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String correo = txtCorreo.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());

        if (Validaciones.campoVacio(nombres) || Validaciones.campoVacio(apellidos) || Validaciones.campoVacio(usuario) || Validaciones.campoVacio(password) || Validaciones.campoVacio(correo)) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }
        if (!Validaciones.correoValido(correo)) {
            lblMensaje.setText(Validaciones.mensajeCorreoInvalido());
            return;
        }
        if (!Validaciones.nombreValido(nombres) || !Validaciones.nombreValido(apellidos)) {
            lblMensaje.setText("Nombres y apellidos solo deben contener letras.");
            return;
        }
        if (!Validaciones.usuarioValido(usuario)) {
            lblMensaje.setText("Usuario inválido (mín. 3 caracteres, letras y números).");
            return;
        }
        if (!Validaciones.passwordValida(password)) {
            lblMensaje.setText(Validaciones.mensajeLongitudMinima("Contraseña", 6));
            return;
        }
        if (!password.equals(confirmarPassword)) {
            lblMensaje.setText("Las contraseñas no coinciden.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombres, apellidos, usuario, password, "Cliente");
        nuevoUsuario.setEmail(correo);

        if (usuarioDAO.insertar(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            lblMensaje.setText("Error al registrar. El usuario o correo ya existen.");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
