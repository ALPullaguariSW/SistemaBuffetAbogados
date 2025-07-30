package buffetabogados.modelo;

import java.sql.Date;

/**
 * Clase que representa un caso legal del buffet de abogados
 */
public class Caso {
    private int id;
    private String nombre;
    private Date fecha;
    private String preambulo;
    private String estado;
    private int empleadoId;
    private int clienteId;
    private String tipoCaso;
    private Date fechaCreacion;
    
    // Referencias a objetos relacionados
    private Empleado empleado;
    private Cliente cliente;
    
    // Constructor por defecto
    public Caso() {}
    
    // Constructor con parámetros básicos
    public Caso(String nombre, Date fecha, String preambulo, String estado, 
               int empleadoId, int clienteId, String tipoCaso) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.preambulo = preambulo;
        this.estado = estado;
        this.empleadoId = empleadoId;
        this.clienteId = clienteId;
        this.tipoCaso = tipoCaso;
    }
    
    // Constructor completo
    public Caso(int id, String nombre, Date fecha, String preambulo, String estado, 
               int empleadoId, int clienteId, String tipoCaso, Date fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.preambulo = preambulo;
        this.estado = estado;
        this.empleadoId = empleadoId;
        this.clienteId = clienteId;
        this.tipoCaso = tipoCaso;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getPreambulo() {
        return preambulo;
    }
    
    public void setPreambulo(String preambulo) {
        this.preambulo = preambulo;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getEmpleadoId() {
        return empleadoId;
    }
    
    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }
    
    public int getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getTipoCaso() {
        return tipoCaso;
    }
    
    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    /**
     * Obtiene el nombre del empleado asignado
     */
    public String getNombreEmpleado() {
        return empleado != null ? empleado.getNombreCompleto() : "No asignado";
    }
    
    /**
     * Obtiene el nombre del cliente
     */
    public String getNombreCliente() {
        return cliente != null ? cliente.getNombreCompleto() : "No asignado";
    }
    
    /**
     * Verifica si el caso está activo
     */
    public boolean estaActivo() {
        return "Activo".equals(estado);
    }
    
    /**
     * Verifica si el caso está cerrado
     */
    public boolean estaCerrado() {
        return "Cerrado".equals(estado);
    }
    
    /**
     * Verifica si el caso está en proceso
     */
    public boolean estaEnProceso() {
        return "En Proceso".equals(estado);
    }
    
    /**
     * Obtiene el estado formateado para mostrar
     */
    public String getEstadoFormateado() {
        switch (estado) {
            case "Activo":
                return "🟢 Activo";
            case "En Proceso":
                return "🟡 En Proceso";
            case "Cerrado":
                return "🔴 Cerrado";
            case "Pendiente":
                return "⚪ Pendiente";
            default:
                return estado;
        }
    }
    
    @Override
    public String toString() {
        return "Caso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", preambulo='" + preambulo + '\'' +
                ", estado='" + estado + '\'' +
                ", empleadoId=" + empleadoId +
                ", clienteId=" + clienteId +
                ", tipoCaso='" + tipoCaso + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
} 