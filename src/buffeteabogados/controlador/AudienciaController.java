package buffeteabogados.controlador;

import buffeteabogados.modelo.Audiencia;
import buffeteabogados.modelo.AudienciaDAO;
import buffeteabogados.modelo.CasoDAO;
import buffeteabogados.modelo.EmpleadoDAO;
import buffeteabogados.util.Validaciones;
import buffeteabogados.vista.GestionAudiencias;
import java.util.List;
import javax.swing.JOptionPane;

public class AudienciaController {
    
    private final AudienciaDAO audienciaDAO;
    private final CasoDAO casoDAO;
    private final EmpleadoDAO empleadoDAO;
    private final GestionAudiencias vista;
    
    public AudienciaController(GestionAudiencias vista) {
        this.audienciaDAO = new AudienciaDAO();
        this.casoDAO = new CasoDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.vista = vista;
    }
    
    public void inicializar() {
        cargarAudiencias();
        cargarCombos();
    }
    
    public void cargarAudiencias() {
        List<Audiencia> audiencias = audienciaDAO.obtenerTodas();
        vista.cargarAudienciasEnTabla(audiencias);
    }
    
    private void cargarCombos() {
        vista.cargarCasosEnCombo(casoDAO.obtenerTodos());
        vista.cargarAbogadosEnCombo(empleadoDAO.obtenerTodos());
    }
    
    public boolean registrarAudiencia(Audiencia audiencia) {
        if (!validarDatos(audiencia)) {
            return false;
        }
        
        if (audienciaDAO.insertar(audiencia)) {
            JOptionPane.showMessageDialog(vista, "Audiencia registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarAudiencias();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar la audiencia.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void cargarAudienciaParaEditar(int id) {
        Audiencia audiencia = audienciaDAO.obtenerPorId(id);
        if (audiencia != null) {
            vista.cargarDatosEnFormulario(audiencia);
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar la información de la audiencia.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean actualizarAudiencia(Audiencia audiencia) {
        if (!validarDatos(audiencia)) {
            return false;
        }
        
        if (audienciaDAO.actualizar(audiencia)) {
            JOptionPane.showMessageDialog(vista, "Audiencia actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarAudiencias();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar la audiencia.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void eliminarAudiencia(int id) {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro que desea eliminar esta audiencia?\nEsta acción no se puede deshacer.",
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (audienciaDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(vista, "Audiencia eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarAudiencias();
                vista.limpiarFormulario();
                vista.cambiarModoCreacion();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la audiencia.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarDatos(Audiencia audiencia) {
        if (Validaciones.campoVacio(audiencia.getTipo()) || audiencia.getCasoId() == 0 || audiencia.getAbogadoId() == 0) {
            JOptionPane.showMessageDialog(vista, "El tipo, caso asociado y abogado son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (audiencia.getFechaHora() == null) {
            JOptionPane.showMessageDialog(vista, "La fecha y hora no son válidas.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}