package buffeteabogados.vista;

import buffeteabogados.modelo.Usuario;
import buffeteabogados.util.EstilosSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Dashboard extends JFrame {

    private final Usuario usuarioActual;

    public Dashboard(Usuario usuario) {
        this.usuarioActual = usuario;

        EstilosSistema.aplicarLookAndFeel();
        configurarVentana();
        crearUI();

        setVisible(true);// AÑADIR ESTA LÍNEA AL FINAL DEL CONSTRUCTOR
        pack(); // Esto ajusta el tamaño de la ventana al contenido
        setLocationRelativeTo(getParent()); // Centra la ventana después de ajustar el tamaño
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
        setTitle("Sistema de Gestión Legal - Panel de Control");
        setMinimumSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void crearUI() {
        JMenuBar menuBar = crearBarraMenu();
        setJMenuBar(menuBar);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(EstilosSistema.COLOR_FONDO_PRINCIPAL);
        panelPrincipal.setBorder(new EmptyBorder(15, 25, 15, 25));

        JPanel panelEncabezado = crearPanelEncabezado();
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);

        JPanel panelModulos = crearPanelModulos();
        panelPrincipal.add(panelModulos, BorderLayout.CENTER);

        JLabel lblFooter = new JLabel("© 2024 Bufete de Abogados. Todos los derechos reservados.", SwingConstants.CENTER);
        lblFooter.setFont(EstilosSistema.FUENTE_PEQUEÑA);
        lblFooter.setForeground(EstilosSistema.COLOR_TEXTO_SECUNDARIO);
        panelPrincipal.add(lblFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JMenuBar crearBarraMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        itemSalir.addActionListener(e -> System.exit(0));
        menuSistema.add(itemCerrarSesion);
        menuSistema.add(itemSalir);

        mb.add(menuSistema);
        return mb;
    }

    private JPanel crearPanelEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 20, 0));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuarioActual.getNombreCompleto());
        lblBienvenida.setFont(EstilosSistema.FUENTE_TITULO);
        lblBienvenida.setForeground(EstilosSistema.COLOR_TEXTO_PRINCIPAL);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy 'a las' hh:mm a", new Locale("es", "ES"));

        JLabel lblFecha = new JLabel(LocalDateTime.now().format(formatter));
        lblFecha.setFont(EstilosSistema.FUENTE_NORMAL);
        lblFecha.setForeground(EstilosSistema.COLOR_TEXTO_SECUNDARIO);
        lblFecha.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel panelInfoUsuario = new JPanel(new GridLayout(2, 1));
        panelInfoUsuario.setOpaque(false);
        panelInfoUsuario.add(lblBienvenida);
        panelInfoUsuario.add(lblFecha);

        panel.add(panelInfoUsuario, BorderLayout.WEST);
        return panel;
    }

    private JPanel crearPanelModulos() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 25, 25));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JButton btnClientes = crearBotonModulo("👥", "Gestión de Clientes", "Administrar información de clientes.", () -> new GestionClientes(this).setVisible(true));
        JButton btnEmpleados = crearBotonModulo("👨‍💼", "Gestión de Empleados", "Administrar personal del bufete.", () -> new GestionEmpleados(this).setVisible(true));
        JButton btnCasos = crearBotonModulo("📋", "Gestión de Casos", "Crear y dar seguimiento a casos legales.", () -> new GestionCasos(this).setVisible(true));
        JButton btnAudiencias = crearBotonModulo("⚖️", "Gestión de Audiencias", "Programar y administrar audiencias.", () -> new GestionAudiencias(this).setVisible(true));
        JButton btnReportes = crearBotonModulo("📊", "Reportes", "Generar informes y estadísticas.", () -> new GestionReportes(this).setVisible(true));
        JButton btnConfig = crearBotonModulo("⚙️", "Configuración", "Ajustes del sistema y conexión.", () -> new ConfiguracionConexion(this).setVisible(true));

        panel.add(btnClientes);
        panel.add(btnEmpleados);
        panel.add(btnCasos);
        panel.add(btnAudiencias);
        panel.add(btnReportes);
        panel.add(btnConfig);

        if (usuarioActual.esCliente()) {
            btnEmpleados.setEnabled(false);
            btnConfig.setEnabled(false);
        }

        return panel;
    }

    private JButton crearBotonModulo(String icono, String titulo, String descripcion, Runnable accion) {
        JPanel panelContenido = new JPanel(new BorderLayout(0, 5));
        panelContenido.setOpaque(false);
        panelContenido.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 48));
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcono.setForeground(EstilosSistema.COLOR_PRIMARIO);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(EstilosSistema.FUENTE_SUBTITULO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(EstilosSistema.COLOR_TEXTO_PRINCIPAL);

        JLabel lblDescripcion = new JLabel("<html><p style='width:160px; text-align:center;'>" + descripcion + "</p></html>");
        lblDescripcion.setFont(EstilosSistema.FUENTE_PEQUEÑA);
        lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescripcion.setForeground(EstilosSistema.COLOR_TEXTO_SECUNDARIO);

        panelContenido.add(lblIcono, BorderLayout.NORTH);
        panelContenido.add(lblTitulo, BorderLayout.CENTER);
        panelContenido.add(lblDescripcion, BorderLayout.SOUTH);

        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.add(panelContenido, BorderLayout.CENTER);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(EstilosSistema.COLOR_BORDE));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addActionListener(e -> accion.run());

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(EstilosSistema.COLOR_FONDO_SECUNDARIO);
                boton.setBorder(BorderFactory.createLineBorder(EstilosSistema.COLOR_PRIMARIO, 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
                boton.setBorder(BorderFactory.createLineBorder(EstilosSistema.COLOR_BORDE));
            }
        });

        return boton;
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cerrar la sesión?",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
