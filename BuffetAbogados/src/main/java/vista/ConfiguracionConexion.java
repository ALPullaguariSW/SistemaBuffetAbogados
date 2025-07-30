package buffetabogados.vista;

import buffetabogados.modelo.Conexion;
import buffetabogados.util.Validaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana para configurar la conexión a la base de datos
 */
public class ConfiguracionConexion extends JDialog {
    
    private JComboBox<String> cmbTipoDB;
    private JTextField txtHost;
    private JTextField txtPuerto;
    private JTextField txtNombreDB;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnProbarConexion;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblEstado;
    
    private Conexion conexion;
    
    public ConfiguracionConexion(JFrame parent) {
        super(parent, "Configuración de Conexión", true);
        this.conexion = Conexion.getInstancia();
        
        inicializarComponentes();
        configurarLayout();
        cargarConfiguracionActual();
        configurarEventos();
        
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void inicializarComponentes() {
        // ComboBox para tipo de base de datos
        String[] tiposDB = {"MySQL", "PostgreSQL", "SQLite"};
        cmbTipoDB = new JComboBox<>(tiposDB);
        
        // Campos de texto
        txtHost = new JTextField(20);
        txtPuerto = new JTextField(10);
        txtNombreDB = new JTextField(20);
        txtUsuario = new JTextField(20);
        txtPassword = new JPasswordField(20);
        
        // Botones
        btnProbarConexion = new JButton("Probar Conexión");
        btnGuardar = new JButton("Guardar Configuración");
        btnCancelar = new JButton("Cancelar");
        
        // Label de estado
        lblEstado = new JLabel("Estado: No configurado");
        lblEstado.setForeground(Color.GRAY);
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        JLabel lblTitulo = new JLabel("Configuración de Base de Datos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Tipo de base de datos
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 1;
        panelPrincipal.add(new JLabel("Tipo de Base de Datos:"), gbc);
        
        gbc.gridx = 1;
        panelPrincipal.add(cmbTipoDB, gbc);
        
        // Host
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Host/IP:"), gbc);
        
        gbc.gridx = 1;
        panelPrincipal.add(txtHost, gbc);
        
        // Puerto
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(new JLabel("Puerto:"), gbc);
        
        gbc.gridx = 1;
        panelPrincipal.add(txtPuerto, gbc);
        
        // Nombre de base de datos
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelPrincipal.add(new JLabel("Nombre de BD:"), gbc);
        
        gbc.gridx = 1;
        panelPrincipal.add(txtNombreDB, gbc);
        
        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelPrincipal.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        panelPrincipal.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        panelPrincipal.add(txtPassword, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblEstado, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnProbarConexion);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarConfiguracionActual() {
        cmbTipoDB.setSelectedItem(conexion.getTipoDB());
        txtHost.setText(conexion.getHost());
        txtPuerto.setText(conexion.getPuerto());
        txtNombreDB.setText(conexion.getNombreDB());
        txtUsuario.setText(conexion.getUsuario());
        txtPassword.setText(conexion.getPassword());
        
        // Configurar campos según tipo de BD
        configurarCamposSegunTipoDB();
    }
    
    private void configurarEventos() {
        // Evento para cambiar tipo de BD
        cmbTipoDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarCamposSegunTipoDB();
            }
        });
        
        // Evento para probar conexión
        btnProbarConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                probarConexion();
            }
        });
        
        // Evento para guardar configuración
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarConfiguracion();
            }
        });
        
        // Evento para cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void configurarCamposSegunTipoDB() {
        String tipoDB = (String) cmbTipoDB.getSelectedItem();
        
        if ("SQLite".equals(tipoDB)) {
            txtHost.setEnabled(false);
            txtPuerto.setEnabled(false);
            txtUsuario.setEnabled(false);
            txtPassword.setEnabled(false);
            
            txtHost.setText("localhost");
            txtPuerto.setText("3306");
            txtUsuario.setText("");
            txtPassword.setText("");
        } else {
            txtHost.setEnabled(true);
            txtPuerto.setEnabled(true);
            txtUsuario.setEnabled(true);
            txtPassword.setEnabled(true);
            
            if ("MySQL".equals(tipoDB)) {
                txtPuerto.setText("3306");
            } else if ("PostgreSQL".equals(tipoDB)) {
                txtPuerto.setText("5432");
            }
        }
    }
    
    private void probarConexion() {
        if (!validarCampos()) {
            return;
        }
        
        btnProbarConexion.setEnabled(false);
        lblEstado.setText("Estado: Probando conexión...");
        lblEstado.setForeground(Color.BLUE);
        
        // Ejecutar en hilo separado para no bloquear la UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boolean exito = conexion.probarConexion(
                    (String) cmbTipoDB.getSelectedItem(),
                    txtHost.getText().trim(),
                    txtPuerto.getText().trim(),
                    txtNombreDB.getText().trim(),
                    txtUsuario.getText().trim(),
                    new String(txtPassword.getPassword())
                );
                
                if (exito) {
                    lblEstado.setText("Estado: Conexión exitosa ✓");
                    lblEstado.setForeground(Color.GREEN);
                    JOptionPane.showMessageDialog(ConfiguracionConexion.this,
                        "Conexión exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    lblEstado.setText("Estado: Error de conexión ✗");
                    lblEstado.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(ConfiguracionConexion.this,
                        "Error al conectar con la base de datos.\nVerifique los parámetros.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                btnProbarConexion.setEnabled(true);
            }
        });
    }
    
    private void guardarConfiguracion() {
        if (!validarCampos()) {
            return;
        }
        
        conexion.guardarConfiguracion(
            (String) cmbTipoDB.getSelectedItem(),
            txtHost.getText().trim(),
            txtPuerto.getText().trim(),
            txtNombreDB.getText().trim(),
            txtUsuario.getText().trim(),
            new String(txtPassword.getPassword())
        );
        
        JOptionPane.showMessageDialog(this,
            "Configuración guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
    
    private boolean validarCampos() {
        String tipoDB = (String) cmbTipoDB.getSelectedItem();
        String host = txtHost.getText().trim();
        String puerto = txtPuerto.getText().trim();
        String nombreDB = txtNombreDB.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Validar nombre de base de datos
        if (Validaciones.campoVacio(nombreDB)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Nombre de Base de Datos"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtNombreDB.requestFocus();
            return false;
        }
        
        // Para SQLite no necesitamos validar otros campos
        if ("SQLite".equals(tipoDB)) {
            return true;
        }
        
        // Validar host
        if (Validaciones.campoVacio(host)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Host/IP"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtHost.requestFocus();
            return false;
        }
        
        if (!Validaciones.ipValida(host) && !"localhost".equals(host)) {
            JOptionPane.showMessageDialog(this,
                "El formato de la IP no es válido",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtHost.requestFocus();
            return false;
        }
        
        // Validar puerto
        if (Validaciones.campoVacio(puerto)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Puerto"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtPuerto.requestFocus();
            return false;
        }
        
        if (!Validaciones.puertoValido(puerto)) {
            JOptionPane.showMessageDialog(this,
                "El puerto debe ser un número entre 1 y 65535",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtPuerto.requestFocus();
            return false;
        }
        
        // Validar usuario
        if (Validaciones.campoVacio(usuario)) {
            JOptionPane.showMessageDialog(this,
                Validaciones.mensajeCampoVacio("Usuario"),
                "Error", JOptionPane.ERROR_MESSAGE);
            txtUsuario.requestFocus();
            return false;
        }
        
        return true;
    }
} 