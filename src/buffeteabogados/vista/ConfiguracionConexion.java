package buffeteabogados.vista;

import buffeteabogados.modelo.Conexion;
import buffeteabogados.util.EstilosSistema;
import buffeteabogados.util.Validaciones;
import javax.swing.*;
import java.awt.*;

public class ConfiguracionConexion extends JDialog {

    private JComboBox<String> cmbTipoDB;
    private JTextField txtHost, txtPuerto, txtNombreDB, txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnProbarGuardar, btnCancelar;

    private final Conexion conexion;

    public ConfiguracionConexion(JFrame parent) {
        super(parent, "Configuración de Base de Datos", true);

        // Corrección: La vista obtiene la instancia de Conexión directamente
        this.conexion = Conexion.getInstancia();

        configurarVentana();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();

        cargarConfiguracionActual();
        // AÑADIR ESTA LÍNEA AL FINAL DEL CONSTRUCTOR
        pack(); // Esto ajusta el tamaño de la ventana al contenido
        setLocationRelativeTo(getParent()); // Centra la ventana después de ajustar el tamaño
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
        
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        // Corrección: Usando EstilosSistema para todo
        cmbTipoDB = new JComboBox<>(new String[]{"SQLite", "MySQL"});
        txtHost = EstilosSistema.crearCampoTexto("localhost");
        txtPuerto = EstilosSistema.crearCampoTexto("3306");
        txtNombreDB = EstilosSistema.crearCampoTexto("buffet_abogados");
        txtUsuario = EstilosSistema.crearCampoTexto("root");
        txtPassword = EstilosSistema.crearCampoPassword("");

        btnProbarGuardar = EstilosSistema.crearBotonPrincipal("PROBAR Y GUARDAR", EstilosSistema.COLOR_PRIMARIO);
        btnCancelar = EstilosSistema.crearBotonSecundario("CANCELAR");
    }

    private void configurarLayout() {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelContenedor.setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);

        JPanel panelPrincipal = EstilosSistema.crearPanelTarjeta();
        panelPrincipal.setLayout(new BorderLayout(10, 15));
        panelPrincipal.setBorder(BorderFactory.createTitledBorder("Configuración de Conexión"));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0;
        gbc.gridx = 0;
        form.add(new JLabel("Motor DB:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        form.add(cmbTipoDB, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Host:"), gbc);
        gbc.gridx = 1;
        form.add(txtHost, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Puerto:"), gbc);
        gbc.gridx = 1;
        form.add(txtPuerto, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Nombre DB:"), gbc);
        gbc.gridx = 1;
        form.add(txtNombreDB, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        form.add(txtUsuario, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        form.add(txtPassword, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnProbarGuardar);

        panelPrincipal.add(form, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        panelContenedor.add(panelPrincipal, BorderLayout.CENTER);
        add(panelContenedor, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnProbarGuardar.addActionListener(e -> probarYGuardar());
        btnCancelar.addActionListener(e -> dispose());
        cmbTipoDB.addActionListener(e -> actualizarCamposDB());
    }

    private void cargarConfiguracionActual() {
        cmbTipoDB.setSelectedItem(conexion.getTipoDB());
        txtHost.setText(conexion.getHost());
        txtPuerto.setText(conexion.getPuerto());
        txtNombreDB.setText(conexion.getNombreDB());
        txtUsuario.setText(conexion.getUsuario());
        txtPassword.setText(conexion.getPassword());
        actualizarCamposDB();
    }

    private void probarYGuardar() {
        String tipoDB = (String) cmbTipoDB.getSelectedItem();
        String host = txtHost.getText();
        String puerto = txtPuerto.getText();
        String nombreDB = txtNombreDB.getText();
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        if (conexion.probarConexion(tipoDB, host, puerto, nombreDB, usuario, password)) {
            conexion.guardarConfiguracion(tipoDB, host, puerto, nombreDB, usuario, password);
            JOptionPane.showMessageDialog(this, "Conexión exitosa. Configuración guardada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCamposDB() {
        boolean esMySQL = "MySQL".equals(cmbTipoDB.getSelectedItem());
        txtHost.setEnabled(esMySQL);
        txtPuerto.setEnabled(esMySQL);
        txtUsuario.setEnabled(esMySQL);
        txtPassword.setEnabled(esMySQL);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
