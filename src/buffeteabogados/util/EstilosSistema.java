package buffeteabogados.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.table.JTableHeader;

/**
 * Clase utilitaria para manejar estilos y colores consistentes en todo el
 * sistema. Paleta de colores y estilos orientados a un bufete de abogados:
 * profesional, serio y moderno.
 */
public class EstilosSistema {

    // ===== PALETA DE COLORES - ELEGANTE Y PROFESIONAL =====
    // Colores principales: Azul marino (confianza, seriedad) y grises (neutralidad, profesionalismo)
    public static final Color COLOR_PRIMARIO = new Color(28, 51, 80);        // Azul marino profundo
    public static final Color COLOR_PRIMARIO_OSCURO = new Color(17, 33, 54);  // Azul aún más oscuro para hover
    public static final Color COLOR_SECUNDARIO = new Color(112, 128, 144);      // Gris pizarra (Slate Gray)

    // Color de acento: Dorado/Bronce sutil (prestigio, éxito)
    public static final Color COLOR_ACCENT = new Color(205, 179, 128);       // Dorado pálido
    public static final Color COLOR_ACCENT_OSCURO = new Color(185, 161, 115); // Dorado más oscuro para hover

    // Colores de estado
    public static final Color COLOR_EXITO = new Color(34, 139, 34);           // Verde bosque
    public static final Color COLOR_ERROR = new Color(178, 34, 34);            // Rojo ladrillo
    public static final Color COLOR_ADVERTENCIA = new Color(255, 140, 0);     // Naranja oscuro

    // Colores de fondo y UI
    public static final Color COLOR_FONDO_PRINCIPAL = new Color(245, 245, 245); // Gris muy claro (casi blanco)
    public static final Color COLOR_FONDO_SECUNDARIO = new Color(230, 230, 230); // Gris un poco más oscuro
    public static final Color COLOR_FONDO_CARD = Color.WHITE;

    // Colores de texto
    public static final Color COLOR_TEXTO_PRINCIPAL = new Color(51, 51, 51);     // Negro suave
    public static final Color COLOR_TEXTO_SECUNDARIO = new Color(102, 102, 102);  // Gris oscuro
    public static final Color COLOR_TEXTO_PLACEHOLDER = new Color(180, 180, 180); // Gris claro para placeholders
    public static final Color COLOR_TEXTO_BLANCO = Color.WHITE;

    // Colores de borde
    public static final Color COLOR_BORDE = new Color(204, 204, 204);
    public static final Color COLOR_BORDE_FOCUS = COLOR_PRIMARIO;

    // ===== FUENTES =====
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_PEQUEÑA = new Font("Segoe UI", Font.PLAIN, 12);

    // ===== MÉTODOS PARA CREAR COMPONENTES ESTILIZADOS =====
    /**
     * Aplica un Look and Feel moderno y plano al inicio de la aplicación.
     */
    public static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un botón principal (para acciones primarias como "Ingresar",
     * "Guardar").
     *
     * @param texto El texto del botón.
     * @param colorFondo El color de fondo del botón.
     * @return un JButton estilizado.
     */
    public static JButton crearBotonPrincipal(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(COLOR_TEXTO_BLANCO);
        boton.setBackground(colorFondo);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);

        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorFondo.darker(), 1),
                new EmptyBorder(8, 18, 8, 18)
        ));

        Color colorHover = colorFondo.darker();

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
        return boton;
    }

    /**
     * Crea un botón secundario (para acciones como "Cancelar", "Limpiar").
     *
     * @param texto El texto del botón.
     * @return un JButton estilizado.
     */
    public static JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(COLOR_TEXTO_PRINCIPAL);
        boton.setBackground(COLOR_FONDO_SECUNDARIO);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);

        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                new EmptyBorder(8, 18, 8, 18)
        ));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BORDE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_FONDO_SECUNDARIO);
            }
        });
        return boton;
    }

    /**
     * Crea un botón que parece un link, para acciones no críticas.
     *
     * @param texto El texto del link.
     * @return un JButton estilizado como link.
     */
    public static JButton crearBotonLink(String texto) {
        JButton boton = new JButton("<html><u>" + texto + "</u></html>");
        boton.setFont(FUENTE_PEQUEÑA);
        boton.setForeground(COLOR_SECUNDARIO);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setForeground(COLOR_PRIMARIO);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setForeground(COLOR_SECUNDARIO);
            }
        });
        return boton;
    }

    /**
     * Clase interna para crear un borde redondeado para los componentes.
     */
    private static class RoundedBorder implements Border {

        private final int radius;
        private final int strokeWidth;
        private final Color color;

        RoundedBorder(int radius) {
            this(radius, 0, null);
        }

        RoundedBorder(int radius, int strokeWidth, Color color) {
            this.radius = radius;
            this.strokeWidth = strokeWidth;
            this.color = color;
        }

        public Insets getBorderInsets(Component c) {
            int padding = radius / 2 + strokeWidth;
            return new Insets(padding, padding, padding, padding);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (strokeWidth > 0 && color != null) {
                g2.setColor(color);
                g2.setStroke(new BasicStroke(strokeWidth));
                g2.drawRoundRect(x + strokeWidth / 2, y + strokeWidth / 2, width - strokeWidth, height - strokeWidth, radius, radius);
            }
            g2.dispose();
        }
    }

    /**
     * Crea un campo de texto con placeholder y estilo moderno.
     */
    public static JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        personalizarCampo(campo, placeholder);
        return campo;
    }

    /**
     * Crea un campo de contraseña con placeholder y estilo moderno.
     */
    public static JPasswordField crearCampoPassword(String placeholder) {
        JPasswordField campo = new JPasswordField();
        personalizarCampo(campo, placeholder);
        return campo;
    }

    private static void personalizarCampo(JTextField campo, String placeholder) {
        campo.setFont(FUENTE_NORMAL);
        campo.setForeground(COLOR_TEXTO_PRINCIPAL);
        campo.setBackground(COLOR_FONDO_CARD);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                new EmptyBorder(10, 10, 10, 10) // Padding interno
        ));

        // Lógica del Placeholder (usando FocusListener)
        campo.setText(placeholder);
        campo.setForeground(COLOR_TEXTO_PLACEHOLDER);

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (getTextFromField(campo).equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXTO_PRINCIPAL);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDE_FOCUS, 2),
                        new EmptyBorder(9, 9, 9, 9)
                ));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (getTextFromField(campo).isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(COLOR_TEXTO_PLACEHOLDER);
                }
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDE, 1),
                        new EmptyBorder(10, 10, 10, 10)
                ));
            }
        });
    }

    private static String getTextFromField(JTextField field) {
        if (field instanceof JPasswordField) {
            return new String(((JPasswordField) field).getPassword());
        }
        return field.getText();
    }

    public static JTextArea crearAreaTexto(String placeholder) {
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(FUENTE_NORMAL);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Lógica de Placeholder similar a personalizarCampo
        area.setText(placeholder);
        area.setForeground(COLOR_TEXTO_PLACEHOLDER);
        area.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (area.getText().equals(placeholder)) {
                    area.setText("");
                    area.setForeground(COLOR_TEXTO_PRINCIPAL);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (area.getText().isEmpty()) {
                    area.setText(placeholder);
                    area.setForeground(COLOR_TEXTO_PLACEHOLDER);
                }
            }
        });
        return area;
    }

    /**
     * Crea un panel con estilo de tarjeta (fondo blanco, sombra/borde sutil).
     */
    public static JPanel crearPanelTarjeta() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_FONDO_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                new EmptyBorder(20, 20, 20, 20) // Padding interno generoso
        ));
        return panel;
    }

    /**
     * Crea un JLabel para títulos principales.
     */
    public static JLabel crearLabelTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_TITULO);
        label.setForeground(COLOR_TEXTO_PRINCIPAL);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Crea un JLabel para subtítulos o texto normal.
     */
    public static JLabel crearLabelNormal(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_NORMAL);
        label.setForeground(COLOR_TEXTO_SECUNDARIO);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Aplica un estilo profesional a una JTable.
     *
     * @param tabla La tabla a la que se aplicará el estilo.
     * @param scrollPane El JScrollPane que contiene la tabla.
     */
    public static void estilizarTabla(JTable tabla, JScrollPane scrollPane) {
        tabla.setBackground(COLOR_FONDO_CARD);
        tabla.setForeground(COLOR_TEXTO_PRINCIPAL);
        tabla.setGridColor(COLOR_BORDE);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(COLOR_ACCENT);
        tabla.setSelectionForeground(COLOR_PRIMARIO);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(FUENTE_BOTON);
        header.setForeground(COLOR_TEXTO_BLANCO);
        header.setBackground(COLOR_PRIMARIO);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO));

        scrollPane.getViewport().setBackground(COLOR_FONDO_CARD);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
    }
}
