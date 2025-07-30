package buffetabogados.modelo;

import java.sql.Date;

/**
 * Clase que representa un cliente del buffet de abogados
 */
public class Cliente {
    private int id;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String correo;
    private String dui;
    private Date fechaNacimiento;
    private Date fechaRegistro;
    
    // Constructor por defecto
    public Cliente() {}
    
    // Constructor con parámetros básicos
    public Cliente(String nombres, String apellidos, String direccion, String telefono, 
                  String correo, String dui, Date fechaNacimiento) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.dui = dui;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    // Constructor completo
    public Cliente(int id, String nombres, String apellidos, String direccion, String telefono, 
                  String correo, String dui, Date fechaNacimiento, Date fechaRegistro) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.dui = dui;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getDui() {
        return dui;
    }
    
    public void setDui(String dui) {
        this.dui = dui;
    }
    
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    /**
     * Obtiene el nombre completo del cliente
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    /**
     * Valida que el DUI tenga el formato correcto (9 dígitos)
     */
    public boolean duiValido() {
        return dui != null && dui.matches("\\d{9}");
    }
    
    /**
     * Valida que el correo tenga formato válido
     */
    public boolean correoValido() {
        return correo != null && correo.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * Valida que el teléfono tenga formato válido
     */
    public boolean telefonoValido() {
        return telefono != null && telefono.matches("\\d{8}");
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", dui='" + dui + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
} 