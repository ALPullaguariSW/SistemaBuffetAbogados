package buffeteabogados.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase modelo para Usuario
 * @author axel_
 */
public class Usuario {
    
    private int id;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String password;
    private String rol;
    private LocalDateTime fechaCreacion;
    
    // Constructores
    public Usuario() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Usuario(String nombres, String apellidos, String usuario, String password, String rol) {
        this();
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }
    
    public Usuario(int id, String nombres, String apellidos, String usuario, String password, String rol) {
        this(nombres, apellidos, usuario, password, rol);
        this.id = id;
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
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    // Métodos auxiliares
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    public boolean esAdmin() {
        return "Abogado".equals(rol);
    }
    
    public boolean esCliente() {
        return "Cliente".equals(rol);
    }
    
    public boolean esEmpleado() {
        return "Empleado".equals(rol);
    }
    
    public String getFechaCreacionFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaCreacion.format(formatter);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", usuario='" + usuario + '\'' +
                ", rol='" + rol + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}