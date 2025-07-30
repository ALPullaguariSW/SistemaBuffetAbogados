package buffetabogados.util;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase utilitaria que proporciona componentes de fecha nativos de Java Swing
 * como alternativa a JDateChooser (que requiere librerías externas)
 */
public class ComponentesFecha {
    
    /**
     * Crea un JSpinner configurado para fechas
     * @param fechaInicial Fecha inicial (null para fecha actual)
     * @return JSpinner configurado para fechas
     */
    public static JSpinner crearSelectorFecha(Date fechaInicial) {
        Date fecha = fechaInicial != null ? fechaInicial : new Date();
        
        // Configurar rango de fechas (100 años atrás hasta 100 años adelante)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -100);
        Date fechaMinima = cal.getTime();
        
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 100);
        Date fechaMaxima = cal.getTime();
        
        SpinnerDateModel model = new SpinnerDateModel(fecha, fechaMinima, fechaMaxima, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        
        // Configurar formato de fecha
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        
        return spinner;
    }
    
    /**
     * Crea un JSpinner configurado para fechas de nacimiento
     * @return JSpinner configurado para fechas de nacimiento
     */
    public static JSpinner crearSelectorFechaNacimiento() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -25); // Por defecto, hace 25 años
        Date fechaDefecto = cal.getTime();
        
        // Rango: 120 años atrás hasta hoy
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -120);
        Date fechaMinima = cal.getTime();
        
        Date fechaMaxima = new Date(); // Hoy
        
        SpinnerDateModel model = new SpinnerDateModel(fechaDefecto, fechaMinima, fechaMaxima, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        
        return spinner;
    }
    
    /**
     * Crea un panel con múltiples campos para fecha (día, mes, año)
     * @param fechaInicial Fecha inicial (null para fecha actual)
     * @return JPanel con campos separados para día, mes y año
     */
    public static JPanel crearPanelFechaCamposSeparados(Date fechaInicial) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        Calendar cal = Calendar.getInstance();
        if (fechaInicial != null) {
            cal.setTime(fechaInicial);
        }
        
        // Campo día
        SpinnerNumberModel diaModel = new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1);
        JSpinner spinnerDia = new JSpinner(diaModel);
        spinnerDia.setPreferredSize(new Dimension(60, 25));
        
        // Campo mes
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        JComboBox<String> comboMes = new JComboBox<>(meses);
        comboMes.setSelectedIndex(cal.get(Calendar.MONTH));
        
        // Campo año
        SpinnerNumberModel añoModel = new SpinnerNumberModel(cal.get(Calendar.YEAR), 1900, 2100, 1);
        JSpinner spinnerAño = new JSpinner(añoModel);
        spinnerAño.setPreferredSize(new Dimension(80, 25));
        
        panel.add(new JLabel("Día:"));
        panel.add(spinnerDia);
        panel.add(new JLabel("Mes:"));
        panel.add(comboMes);
        panel.add(new JLabel("Año:"));
        panel.add(spinnerAño);
        
        // Guardar referencias para poder obtener la fecha después
        panel.putClientProperty("spinnerDia", spinnerDia);
        panel.putClientProperty("comboMes", comboMes);
        panel.putClientProperty("spinnerAño", spinnerAño);
        
        return panel;
    }
    
    /**
     * Crea un JFormattedTextField para fechas
     * @param fechaInicial Fecha inicial (null para fecha actual)
     * @return JFormattedTextField configurado para fechas
     */
    public static JFormattedTextField crearCampoFechaFormateado(Date fechaInicial) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter formatter = new DateFormatter(formato);
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        
        JFormattedTextField campo = new JFormattedTextField(formatter);
        campo.setValue(fechaInicial != null ? fechaInicial : new Date());
        campo.setColumns(10);
        
        return campo;
    }
    
    /**
     * Obtiene la fecha de un JSpinner de fecha
     * @param spinner El JSpinner configurado para fechas
     * @return La fecha seleccionada
     */
    public static Date obtenerFecha(JSpinner spinner) {
        return (Date) spinner.getValue();
    }
    
    /**
     * Obtiene la fecha de un panel de campos separados
     * @param panel El panel creado con crearPanelFechaCamposSeparados()
     * @return La fecha seleccionada
     */
    public static Date obtenerFechaDeCamposSeparados(JPanel panel) {
        JSpinner spinnerDia = (JSpinner) panel.getClientProperty("spinnerDia");
        JComboBox<String> comboMes = (JComboBox<String>) panel.getClientProperty("comboMes");
        JSpinner spinnerAño = (JSpinner) panel.getClientProperty("spinnerAño");
        
        if (spinnerDia == null || comboMes == null || spinnerAño == null) {
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, (Integer) spinnerDia.getValue());
        cal.set(Calendar.MONTH, comboMes.getSelectedIndex());
        cal.set(Calendar.YEAR, (Integer) spinnerAño.getValue());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    /**
     * Obtiene la fecha de un JFormattedTextField
     * @param campo El campo formateado para fechas
     * @return La fecha seleccionada
     */
    public static Date obtenerFechaDeFormattedField(JFormattedTextField campo) {
        try {
            campo.commitEdit();
            return (Date) campo.getValue();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Convierte java.util.Date a java.sql.Date
     * @param fecha La fecha a convertir
     * @return java.sql.Date para usar en base de datos
     */
    public static java.sql.Date convertirASqlDate(Date fecha) {
        return fecha != null ? new java.sql.Date(fecha.getTime()) : null;
    }
}