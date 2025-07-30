// Archivo: buffeteabogados/controlador/EmpleadoController.java
package buffeteabogados.controlador;

import buffeteabogados.modelo.Empleado;
import buffeteabogados.modelo.EmpleadoDAO;
import buffeteabogados.util.Validaciones;
import buffeteabogados.vista.GestionEmpleados;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class EmpleadoController {

    private final GestionEmpleados vista;
    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController(GestionEmpleados vista) {
        this.vista = vista;
        this.empleadoDAO = new EmpleadoDAO();
    }

    public void cargarEmpleados() {
        List<Empleado> empleados = empleadoDAO.obtenerTodos();
        vista.cargarEmpleadosEnTabla(empleados);
    }

    public boolean registrarEmpleado(String nombres, String apellidos, String cargo, String telefono, String email, double salario, boolean activo, Date fechaContratacion) {
        if (!validarDatos(nombres, apellidos, cargo, telefono, email, fechaContratacion)) {
            return false;
        }

        Empleado empleado = new Empleado();
        empleado.setNombres(nombres);
        empleado.setApellidos(apellidos);
        empleado.setCargo(cargo);
        empleado.setTelefono(telefono);
        empleado.setEmail(email);
        empleado.setSalario(salario);
        empleado.setActivo(activo);
        empleado.setFechaContratacion(fechaContratacion);

        if (empleadoDAO.insertar(empleado)) {
            JOptionPane.showMessageDialog(vista, "Empleado registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarEmpleados();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void cargarEmpleadoParaEditar(int id) {
        Empleado empleado = empleadoDAO.obtenerPorId(id);
        if (empleado != null) {
            vista.cargarDatosEnFormulario(empleado);
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo cargar la información del empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean actualizarEmpleado(int id, String nombres, String apellidos, String cargo, String telefono, String email, double salario, boolean activo, Date fechaContratacion) {
        if (!validarDatos(nombres, apellidos, cargo, telefono, email, fechaContratacion)) {
            return false;
        }

        Empleado empleado = new Empleado();
        empleado.setId(id);
        empleado.setNombres(nombres);
        empleado.setApellidos(apellidos);
        empleado.setCargo(cargo);
        empleado.setTelefono(telefono);
        empleado.setEmail(email);
        empleado.setSalario(salario);
        empleado.setActivo(activo);
        empleado.setFechaContratacion(fechaContratacion);

        if (empleadoDAO.actualizar(empleado)) {
            JOptionPane.showMessageDialog(vista, "Empleado actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarEmpleados();
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void eliminarEmpleado(int id, String nombreCompleto) {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro que desea desactivar al empleado: " + nombreCompleto + "?",
                "Confirmar Desactivación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (empleadoDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(vista, "Empleado desactivado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarEmpleados();
                vista.limpiarFormulario();
                vista.cambiarModoCreacion();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al desactivar al empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarDatos(String nombres, String apellidos, String cargo, String telefono, String email, Date fechaContratacion) {
        if (Validaciones.campoVacio(nombres) || Validaciones.campoVacio(apellidos) || Validaciones.campoVacio(cargo) || fechaContratacion == null) {
            JOptionPane.showMessageDialog(vista, "Nombres, apellidos, cargo y fecha son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!email.isEmpty() && !Validaciones.correoValido(email)) {
            JOptionPane.showMessageDialog(vista, "El formato del correo electrónico no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
