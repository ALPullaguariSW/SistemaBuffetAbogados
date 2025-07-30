package buffetabogados.controlador;

import buffetabogados.modelo.Cliente;
import buffetabogados.modelo.ClienteDAO;
import buffetabogados.util.Validaciones;
import buffetabogados.vista.GestionClientes;

import javax.swing.JOptionPane;
import java.sql.Date;
import java.util.List;

/**
 * Controlador para la gestión de clientes
 */
public class ClienteController {
    
    private GestionClientes vista;
    private ClienteDAO clienteDAO;
    
    public ClienteController(GestionClientes vista) {
        this.vista = vista;
        this.clienteDAO = new ClienteDAO();
    }
    
    /**
     * Carga todos los clientes en la tabla
     */
    public void cargarClientes() {
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        vista.cargarClientesEnTabla(clientes);
        vista.actualizarContadorClientes(clientes.size());
    }
    
    /**
     * Busca clientes por nombre
     */
    public void buscarClientes(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            cargarClientes();
            return;
        }
        
        List<Cliente> clientes = clienteDAO.buscarPorNombre(termino.trim());
        vista.cargarClientesEnTabla(clientes);
        vista.actualizarContadorClientes(clientes.size());
    }
    
    /**
     * Registra un nuevo cliente
     */
    public boolean registrarCliente(String nombres, String apellidos, String direccion, 
                                  String telefono, String correo, String dui, Date fechaNacimiento) {
        
        // Validaciones básicas
        if (!validarDatosCliente(nombres, apellidos, direccion, telefono, correo, dui, fechaNacimiento)) {
            return false;
        }
        
        // Verificar si el DUI ya existe
        if (clienteDAO.existeDui(dui)) {
            JOptionPane.showMessageDialog(vista,
                "Ya existe un cliente registrado con el DUI: " + dui,
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Crear objeto cliente
        Cliente cliente = new Cliente(nombres.trim(), apellidos.trim(), direccion.trim(), 
                                    telefono.trim(), correo.trim(), dui.trim(), fechaNacimiento);
        
        // Intentar insertar
        boolean exito = clienteDAO.insertar(cliente);
        
        if (exito) {
            JOptionPane.showMessageDialog(vista,
                "Cliente registrado exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Recargar la tabla
            cargarClientes();
            
            // Limpiar formulario
            vista.limpiarFormulario();
            
            return true;
        } else {
            JOptionPane.showMessageDialog(vista,
                "Error al registrar el cliente. Verifique los datos.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Actualiza un cliente existente
     */
    public boolean actualizarCliente(int id, String nombres, String apellidos, String direccion, 
                                   String telefono, String correo, String dui, Date fechaNacimiento) {
        
        // Validaciones básicas
        if (!validarDatosCliente(nombres, apellidos, direccion, telefono, correo, dui, fechaNacimiento)) {
            return false;
        }
        
        // Verificar si el DUI ya existe (excluyendo el cliente actual)
        if (clienteDAO.existeDui(dui, id)) {
            JOptionPane.showMessageDialog(vista,
                "Ya existe otro cliente registrado con el DUI: " + dui,
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Crear objeto cliente con ID
        Cliente cliente = new Cliente(id, nombres.trim(), apellidos.trim(), direccion.trim(), 
                                    telefono.trim(), correo.trim(), dui.trim(), fechaNacimiento, null);
        
        // Intentar actualizar
        boolean exito = clienteDAO.actualizar(cliente);
        
        if (exito) {
            JOptionPane.showMessageDialog(vista,
                "Cliente actualizado exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Recargar la tabla
            cargarClientes();
            
            // Limpiar formulario
            vista.limpiarFormulario();
            vista.cambiarModoCreacion();
            
            return true;
        } else {
            JOptionPane.showMessageDialog(vista,
                "Error al actualizar el cliente. Verifique los datos.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Elimina un cliente
     */
    public boolean eliminarCliente(int id, String nombreCompleto) {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro que desea eliminar al cliente:\n" + nombreCompleto + "?\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = clienteDAO.eliminar(id);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista,
                    "Cliente eliminado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // Recargar la tabla
                cargarClientes();
                
                // Limpiar formulario
                vista.limpiarFormulario();
                vista.cambiarModoCreacion();
                
                return true;
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al eliminar el cliente. Puede tener casos asociados.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return false;
    }
    
    /**
     * Carga los datos de un cliente en el formulario para editar
     */
    public void cargarClienteParaEditar(int id) {
        Cliente cliente = clienteDAO.obtenerPorId(id);
        
        if (cliente != null) {
            vista.cargarDatosEnFormulario(cliente);
            vista.cambiarModoEdicion();
        } else {
            JOptionPane.showMessageDialog(vista,
                "No se pudo cargar la información del cliente",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Valida los datos del cliente
     */
    private boolean validarDatosCliente(String nombres, String apellidos, String direccion, 
                                       String telefono, String correo, String dui, Date fechaNacimiento) {
        
        // Validar campos obligatorios
        if (Validaciones.campoVacio(nombres)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Nombres"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (Validaciones.campoVacio(apellidos)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Apellidos"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (Validaciones.campoVacio(direccion)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Dirección"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (Validaciones.campoVacio(telefono)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Teléfono"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (Validaciones.campoVacio(dui)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("DUI"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar formatos
        if (!Validaciones.nombreValido(nombres)) {
            JOptionPane.showMessageDialog(vista,
                "Los nombres solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.nombreValido(apellidos)) {
            JOptionPane.showMessageDialog(vista,
                "Los apellidos solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.duiValido(dui)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeDuiInvalido(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.telefonoValido(telefono)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeTelefonoInvalido(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar correo si no está vacío
        if (!Validaciones.campoVacio(correo) && !Validaciones.correoValido(correo)) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCorreoInvalido(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar fecha de nacimiento
        if (fechaNacimiento == null) {
            JOptionPane.showMessageDialog(vista,
                "La fecha de nacimiento es obligatoria",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.fechaNoFutura(fechaNacimiento)) {
            JOptionPane.showMessageDialog(vista,
                "La fecha de nacimiento no puede ser futura",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.fechaNoMuyAntigua(fechaNacimiento)) {
            JOptionPane.showMessageDialog(vista,
                "La fecha de nacimiento no puede ser muy antigua (más de 100 años)",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Obtiene el total de clientes registrados
     */
    public int obtenerTotalClientes() {
        return clienteDAO.obtenerTotal();
    }
    
    /**
     * Exporta los datos de clientes (para reportes futuros)
     */
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDAO.obtenerTodos();
    }
}