package buffeteabogados.vista;

import buffeteabogados.modelo.UsuarioDAO;

import buffeteabogados.util.EstilosSistema;
import buffeteabogados.util.Validaciones;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RecuperacionPassword extends JDialog {

    private JTextField txtUsuario;
    private JTextField txtCorreo;
    private JTextField txtPin;
    private JPasswordField txtNuevaPassword;
    private JPasswordField txtConfirmarPassword;

    private JButton btnSolicitarPin;
    private JButton btnVerificarPin;
    private JButton btnCambiarPassword;
    private JButton btnCancelar;

    private JLabel lblEstado;
    private JLabel lblPaso;

    private String pinGenerado = "";
    private final UsuarioDAO usuarioDAO;

    public RecuperacionPassword(JFrame parent) {
        super(parent, "Recuperación de Contraseña", true);
        this.usuarioDAO = new UsuarioDAO();

        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        configurarVisibilidadPaso1();

        pack();
        setLocationRelativeTo(parent);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        setResizable(false);
        getContentPane().setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);
    }

    private void inicializarComponentes() {
        txtUsuario = EstilosSistema.crearCampoTexto("Usuario registrado");
        txtCorreo = EstilosSistema.crearCampoTexto("Correo electrónico asociado");
        txtPin = EstilosSistema.crearCampoTexto("PIN de 6 dígitos");
        txtNuevaPassword = EstilosSistema.crearCampoPassword("Nueva contraseña");
        txtConfirmarPassword = EstilosSistema.crearCampoPassword("Confirmar contraseña");

        btnSolicitarPin = EstilosSistema.crearBotonPrincipal("Solicitar PIN", EstilosSistema.COLOR_ACCENT);
        btnVerificarPin = EstilosSistema.crearBotonPrincipal("Verificar PIN", EstilosSistema.COLOR_ACCENT);
        btnCambiarPassword = EstilosSistema.crearBotonPrincipal("Cambiar Contraseña", EstilosSistema.COLOR_PRIMARIO);
        btnCancelar = EstilosSistema.crearBotonSecundario("Cancelar");

        lblEstado = EstilosSistema.crearLabelNormal("Ingrese su usuario y correo electrónico");
        lblPaso = new JLabel("Paso 1 de 3", JLabel.CENTER);
        lblPaso.setFont(EstilosSistema.FUENTE_SUBTITULO);
        lblPaso.setForeground(EstilosSistema.COLOR_TEXTO_PRINCIPAL);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = EstilosSistema.crearPanelTarjeta();
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de Recuperación"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        panelFormulario.add(txtUsuario, gbc);
        gbc.gridy++;
        panelFormulario.add(txtCorreo, gbc);
        gbc.gridy++;
        panelFormulario.add(txtPin, gbc);
        gbc.gridy++;
        panelFormulario.add(txtNuevaPassword, gbc);
        gbc.gridy++;
        panelFormulario.add(txtConfirmarPassword, gbc);

        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonesAccion.setOpaque(false);
        panelBotonesAccion.add(btnSolicitarPin);
        panelBotonesAccion.add(btnVerificarPin);
        panelBotonesAccion.add(btnCambiarPassword);

        gbc.gridy++;
        panelFormulario.add(panelBotonesAccion, gbc);

        JPanel panelBotonesNavegacion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonesNavegacion.setOpaque(false);
        panelBotonesNavegacion.add(btnCancelar);

        add(lblPaso, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(lblEstado, BorderLayout.SOUTH);

        gbc.gridy++;
        panelFormulario.add(panelBotonesNavegacion, gbc);
    }

    private void configurarEventos() {
        btnSolicitarPin.addActionListener(e -> solicitarPin());
        btnVerificarPin.addActionListener(e -> verificarPin());
        btnCambiarPassword.addActionListener(e -> cambiarPassword());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void solicitarPin() {
        String usuario = txtUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (Validaciones.campoVacio(usuario) || Validaciones.campoVacio(correo)) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean usuarioValido = usuarioDAO.verificarUsuarioYCorreo(usuario, correo);

        if (usuarioValido) {
            pinGenerado = generarPin();
            JOptionPane.showMessageDialog(this,
                    "Se ha enviado un PIN a su correo electrónico.\n"
                    + "(Simulación - PIN para pruebas: " + pinGenerado + ")",
                    "PIN Enviado", JOptionPane.INFORMATION_MESSAGE);
            avanzarPaso2();
        } else {
            JOptionPane.showMessageDialog(this, "El usuario y el correo no coinciden o no existen.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarPin() {
        String pinIngresado = txtPin.getText().trim();
        if (pinIngresado.equals(pinGenerado)) {
            JOptionPane.showMessageDialog(this, "PIN verificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            avanzarPaso3();
        } else {
            JOptionPane.showMessageDialog(this, "PIN incorrecto. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarPassword() {
        String nuevaPassword = new String(txtNuevaPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());

        if (!Validaciones.passwordValida(nuevaPassword)) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!nuevaPassword.equals(confirmarPassword)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = usuarioDAO.actualizarPassword(txtUsuario.getText(), nuevaPassword);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Contraseña cambiada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generarPin() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void configurarVisibilidadPaso1() {
        txtUsuario.setEnabled(true);
        txtCorreo.setEnabled(true);
        txtPin.setEnabled(false);
        txtNuevaPassword.setEnabled(false);
        txtConfirmarPassword.setEnabled(false);
        btnSolicitarPin.setVisible(true);
        btnVerificarPin.setVisible(false);
        btnCambiarPassword.setVisible(false);
        lblPaso.setText("Paso 1 de 3");
        lblEstado.setText("Ingrese su usuario y correo electrónico.");
    }

    private void avanzarPaso2() {
        txtUsuario.setEnabled(false);
        txtCorreo.setEnabled(false);
        txtPin.setEnabled(true);
        txtNuevaPassword.setEnabled(false);
        txtConfirmarPassword.setEnabled(false);
        btnSolicitarPin.setVisible(false);
        btnVerificarPin.setVisible(true);
        btnCambiarPassword.setVisible(false);
        lblPaso.setText("Paso 2 de 3");
        lblEstado.setText("Ingrese el PIN enviado a su correo.");
        txtPin.requestFocus();
    }

    private void avanzarPaso3() {
        txtPin.setEnabled(false);
        txtNuevaPassword.setEnabled(true);
        txtConfirmarPassword.setEnabled(true);
        btnSolicitarPin.setVisible(false);
        btnVerificarPin.setVisible(false);
        btnCambiarPassword.setVisible(true);
        lblPaso.setText("Paso 3 de 3");
        lblEstado.setText("Ingrese y confirme su nueva contraseña.");
        txtNuevaPassword.requestFocus();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
