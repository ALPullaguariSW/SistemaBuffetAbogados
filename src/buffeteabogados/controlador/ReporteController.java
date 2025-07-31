package buffeteabogados.controlador;

import buffeteabogados.modelo.AudienciaDAO;
import buffeteabogados.modelo.Caso;
import buffeteabogados.modelo.CasoDAO;
import buffeteabogados.modelo.ClienteDAO;
import buffeteabogados.modelo.EmpleadoDAO;
import buffeteabogados.modelo.UsuarioDAO;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class ReporteController {
    
    private final ClienteDAO clienteDAO;
    private final CasoDAO casoDAO;
    private final EmpleadoDAO empleadoDAO;
    private final AudienciaDAO audienciaDAO;
    private final UsuarioDAO usuarioDAO;
    
    public ReporteController() {
        this.clienteDAO = new ClienteDAO();
        this.casoDAO = new CasoDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.audienciaDAO = new AudienciaDAO(); // Añadido
        this.usuarioDAO = new UsuarioDAO();     // Añadido
    }
    
    /**
     * Método principal que genera el contenido de un reporte según el tipo solicitado.
     * @param tipoReporte El nombre del reporte a generar.
     * @return Un String con el contenido formateado del reporte.
     */
    public String generarReporte(String tipoReporte) {
        switch (tipoReporte) {
            case "Resumen General del Sistema":
                return generarReporteResumenGeneral();
            case "Listado de Clientes":
                return generarReporteListadoClientes();
            case "Listado de Empleados":
                return generarReporteListadoEmpleados();
            case "Listado de Casos":
                return generarReporteListadoCasos();
            default:
                return "Tipo de reporte no reconocido.";
        }
    }
    
    // --- MÉTODOS PRIVADOS PARA GENERAR CADA TIPO DE REPORTE ---

    private String generarReporteResumenGeneral() {
        int totalClientes = clienteDAO.obtenerTodos().size();
        int totalEmpleados = empleadoDAO.obtenerTodos().size();
        List<Caso> todosLosCasos = casoDAO.obtenerTodos();
        int totalCasos = todosLosCasos.size();
        int totalAudiencias = audienciaDAO.obtenerTodas().size();
        
        double costosTotales = todosLosCasos.stream()
                .mapToDouble(Caso::getEstimacionCosto)
                .sum();
        
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        StringBuilder reporte = new StringBuilder();
        formatearEncabezado(reporte, "Resumen General del Sistema");
        
        reporte.append("ESTADÍSTICAS GENERALES:\n");
        reporte.append("------------------------\n");
        reporte.append(String.format("%-25s %d\n", "Total de Clientes Activos:", totalClientes));
        reporte.append(String.format("%-25s %d\n", "Total de Empleados Activos:", totalEmpleados));
        reporte.append(String.format("%-25s %d\n", "Total de Casos Registrados:", totalCasos));
        reporte.append(String.format("%-25s %d\n", "Total de Audiencias:", totalAudiencias));
        reporte.append("\n");
        
        reporte.append("FINANZAS:\n");
        reporte.append("----------\n");
        reporte.append(String.format("%-25s %s\n", "Costo Total Estimado:", currencyFormatter.format(costosTotales)));
        
        formatearPie(reporte);
        return reporte.toString();
    }
    
    private String generarReporteListadoClientes() {
        StringBuilder reporte = new StringBuilder();
        formatearEncabezado(reporte, "Listado de Clientes");
        
        reporte.append(String.format("%-5s %-30s %-15s %-30s\n", "ID", "NOMBRE COMPLETO", "TELÉFONO", "CORREO"));
        reporte.append("-".repeat(80)).append("\n");
        
        clienteDAO.obtenerTodos().forEach(cliente -> {
            reporte.append(String.format("%-5d %-30s %-15s %-30s\n",
                    cliente.getId(),
                    cliente.getNombreCompleto(),
                    cliente.getTelefono(),
                    cliente.getCorreo()));
        });
        
        formatearPie(reporte);
        return reporte.toString();
    }
    
    private String generarReporteListadoEmpleados() {
        StringBuilder reporte = new StringBuilder();
        formatearEncabezado(reporte, "Listado de Empleados");
        
        reporte.append(String.format("%-5s %-30s %-20s %-30s\n", "ID", "NOMBRE COMPLETO", "CARGO", "EMAIL"));
        reporte.append("-".repeat(85)).append("\n");
        
        empleadoDAO.obtenerTodos().forEach(emp -> {
            reporte.append(String.format("%-5d %-30s %-20s %-30s\n",
                    emp.getId(),
                    emp.getNombreCompleto(),
                    emp.getCargo(),
                    emp.getEmail()));
        });
        
        formatearPie(reporte);
        return reporte.toString();
    }

    private String generarReporteListadoCasos() {
        StringBuilder reporte = new StringBuilder();
        formatearEncabezado(reporte, "Listado de Casos");

        reporte.append(String.format("%-5s %-35s %-25s %-15s\n", "ID", "TÍTULO DEL CASO", "CLIENTE", "ESTADO"));
        reporte.append("-".repeat(85)).append("\n");

        casoDAO.obtenerTodos().forEach(caso -> {
            reporte.append(String.format("%-5d %-35s %-25s %-15s\n",
                    caso.getId(),
                    caso.getTitulo(),
                    caso.getClienteNombre(),
                    caso.getEstado()));
        });

        formatearPie(reporte);
        return reporte.toString();
    }
    
    // --- MÉTODOS DE AYUDA PARA FORMATEO ---
    
    private void formatearEncabezado(StringBuilder sb, String titulo) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sb.append("================================================================================\n");
        sb.append("                         REPORTE DEL SISTEMA DE GESTIÓN LEGAL                     \n");
        sb.append("================================================================================\n\n");
        sb.append(String.format(" TÍTULO: %s\n", titulo));
        sb.append(String.format(" FECHA DE GENERACIÓN: %s\n", sdf.format(new Date())));
        sb.append("\n--------------------------------------------------------------------------------\n\n");
    }
    
    private void formatearPie(StringBuilder sb) {
        sb.append("\n--------------------------------------------------------------------------------\n");
        sb.append("                             *** FIN DEL REPORTE ***                            \n");
    }
}