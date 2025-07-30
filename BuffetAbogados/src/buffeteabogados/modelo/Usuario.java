package buffeteabogados.modelo;

import java.sql.Date;

public class Usuario {
    private int id;
    private String nombres;
    private String apellidos;
    private String email; // Campo añadido
    private String usuario;
    private String password;
    private String rol;
    private Date fechaCreacion;
    private boolean activo = true;

    public Usuario() {}

    public Usuario(String nombres, String apellidos, String usuario, String password, String rol) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public String getNombreCompleto() { return nombres + " " + apellidos; }
    public boolean esAdmin() { return "Abogado".equalsIgnoreCase(rol); }
    public boolean esCliente() { return "Cliente".equalsIgnoreCase(rol); }
}