package buffeteabogados.controlador;

import buffeteabogados.modelo.Cliente;
import buffeteabogados.modelo.ClienteDAO;
import buffeteabogados.util.Validaciones;
import buffeteabogados.vista.GestionClientes;

import javax.swing.JOptionPane;
import java.sql.Date;
import java.util.List;

public class ClienteController {
    
    private final GestionClientes vista;
    private final ClienteDAO clienteDAO;
    
    public ClienteController(GestionClientes vista) {
        this.vista = vista;
        this.clienteDAO = new ClienteDAO();
    }
    
    public void cargarClientes() {
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        vista.cargarClientesEnTabla(clientes);
    }
    
    public void buscarClientes(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            cargarClientes();
            return;
        }
        // Asumiendo que tienes un método buscarPorNombre en tu ClienteDAO
        // List<Cliente> clientes = clienteDAO.buscarPorNombre(termino.trim());
        // vista.cargarClientesEnTabla(clientes);
        JOptionPane.showMessageDialog(vista, "Funcionalidad de búsqueda no implementada en el DAO.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean registrarCliente(String nombres, String apellidos, String direccion, 
                                  String telefono, String correo, String dui, Date fechaNacimiento) {
        if (!validarDatosCliente(nombres, apellidos, direccion, telefono, correo, dui, fechaNacimiento)) {
            return false;
        }
        
        Cliente cliente = new Cliente(nombres, apellidos, direccion, telefono, correo, dui, fechaNacimiento);
        
        boolean exito = clienteDAO.insertar(cliente);
        if (exito) {
            JOptionPane.showMessageDialog(vista, "Cliente registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarClientes();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean actualizarCliente(int id, String nombres, String apellidos, String direccion, 
                                   String telefono, String correo, String dui, Date fechaNacimiento) {
        if (!validarDatosCliente(nombres, apellidos, direccion, telefono, correo, dui, fechaNacimiento)) {
            return false;
        }
        
        Cliente cliente = new Cliente(nombres, apellidos, direccion, telefono, correo, dui, fechaNacimiento);
        cliente.setId(id);
        
        boolean exito = clienteDAO.actualizar(cliente);
        if (exito) {
            JOptionPane.showMessageDialog(vista, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarClientes();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void eliminarCliente(int id, String nombreCompleto) {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro que desea eliminar al cliente: " + nombreCompleto + "?\nEsta acción no se puede deshacer.",
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = clienteDAO.eliminar(id);
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarClientes();
                vista.limpiarFormulario();
                vista.cambiarModoCreacion();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el cliente. Puede que tenga casos asociados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void cargarClienteParaEditar(int id) {
        Cliente cliente = clienteDAO.obtenerPorId(id);
        if (cliente != null) {
            vista.cargarDatosEnFormulario(cliente);
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar la información del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarDatosCliente(String nombres, String apellidos, String direccion, 
                                       String telefono, String correo, String dui, Date fechaNacimiento) {
        if (Validaciones.campoVacio(nombres) || Validaciones.campoVacio(apellidos) || Validaciones.campoVacio(direccion) ||
            Validaciones.campoVacio(telefono) || Validaciones.campoVacio(dui) || fechaNacimiento == null) {
            JOptionPane.showMessageDialog(vista, "Todos los campos marcados con (*) son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Validaciones.correoValido(correo) && !correo.isEmpty()) {
             JOptionPane.showMessageDialog(vista, "El formato del correo electrónico no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Puedes añadir más validaciones específicas aquí si lo deseas
        return true;
    }
}