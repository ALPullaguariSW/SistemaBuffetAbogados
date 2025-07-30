package buffeteabogados.controlador;

import buffeteabogados.modelo.Caso;
import buffeteabogados.modelo.CasoDAO;
import buffeteabogados.modelo.ClienteDAO;
import buffeteabogados.modelo.EmpleadoDAO;
import buffeteabogados.util.Validaciones;
import buffeteabogados.vista.GestionCasos;
import java.util.List;
import javax.swing.JOptionPane;

public class CasoController {
    
    private final CasoDAO casoDAO;
    private final ClienteDAO clienteDAO;
    private final EmpleadoDAO empleadoDAO;
    private final GestionCasos vista;
    
    public CasoController(GestionCasos vista) {
        this.casoDAO = new CasoDAO();
        this.clienteDAO = new ClienteDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.vista = vista;
    }
    
    public void inicializar() {
        cargarCasos();
        cargarCombos();
    }
    
    public void cargarCasos() {
        List<Caso> casos = casoDAO.obtenerTodos();
        vista.cargarCasosEnTabla(casos);
    }
    
    private void cargarCombos() {
        vista.cargarClientesEnCombo(clienteDAO.obtenerTodos());
        // Aquí podrías filtrar para que solo salgan empleados con cargo 'Abogado'
        vista.cargarAbogadosEnCombo(empleadoDAO.obtenerTodos());
    }
    
    public boolean registrarCaso(Caso caso) {
        if (!validarDatos(caso)) {
            return false;
        }
        
        if (casoDAO.insertar(caso)) {
            JOptionPane.showMessageDialog(vista, "Caso registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarCasos();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar el caso.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void cargarCasoParaEditar(int id) {
        Caso caso = casoDAO.obtenerPorId(id);
        if (caso != null) {
            vista.cargarDatosEnFormulario(caso);
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar la información del caso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean actualizarCaso(Caso caso) {
        if (!validarDatos(caso)) {
            return false;
        }
        
        if (casoDAO.actualizar(caso)) {
            JOptionPane.showMessageDialog(vista, "Caso actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarCasos();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el caso.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void eliminarCaso(int id, String tituloCaso) {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro que desea eliminar el caso: '" + tituloCaso + "'?\nEsta acción no se puede deshacer.",
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (casoDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(vista, "Caso eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarCasos();
                vista.limpiarFormulario();
                vista.cambiarModoCreacion();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el caso. Puede que tenga audiencias asociadas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validarDatos(Caso caso) {
        if (Validaciones.campoVacio(caso.getTitulo()) || caso.getClienteId() == 0 || caso.getAbogadoId() == 0) {
            JOptionPane.showMessageDialog(vista, "Título, cliente y abogado son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Añadir más validaciones si es necesario
        return true;
    }
}