package buffetabogados.modelo;

import java.sql.Date;

/**
 * Clase que representa un empleado del buffet de abogados
 */
public class Empleado {
    private int id;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String dui;
    private Date fechaNacimiento;
    private String genero;
    private String provincia;
    private String tipoEmpleado;
    private String estadoCivil;
    private Date fechaRegistro;
    
    // Constructor por defecto
    public Empleado() {}
    
    // Constructor con parámetros básicos
    public Empleado(String nombres, String apellidos, String telefono, String dui, 
                   Date fechaNacimiento, String genero, String provincia, 
                   String tipoEmpleado, String estadoCivil) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.dui = dui;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.provincia = provincia;
        this.tipoEmpleado = tipoEmpleado;
        this.estadoCivil = estadoCivil;
    }
    
    // Constructor completo
    public Empleado(int id, String nombres, String apellidos, String telefono, String dui, 
                   Date fechaNacimiento, String genero, String provincia, 
                   String tipoEmpleado, String estadoCivil, Date fechaRegistro) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.dui = dui;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.provincia = provincia;
        this.tipoEmpleado = tipoEmpleado;
        this.estadoCivil = estadoCivil;
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
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getProvincia() {
        return provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    public String getTipoEmpleado() {
        return tipoEmpleado;
    }
    
    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }
    
    public String getEstadoCivil() {
        return estadoCivil;
    }
    
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    /**
     * Obtiene el nombre completo del empleado
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
     * Valida que el teléfono tenga formato válido
     */
    public boolean telefonoValido() {
        return telefono != null && telefono.matches("\\d{8}");
    }
    
    /**
     * Verifica si el empleado es abogado
     */
    public boolean esAbogado() {
        return "Abogado".equals(tipoEmpleado);
    }
    
    /**
     * Verifica si el empleado es secretario
     */
    public boolean esSecretario() {
        return "Secretario".equals(tipoEmpleado);
    }
    
    /**
     * Verifica si el empleado es asistente
     */
    public boolean esAsistente() {
        return "Asistente".equals(tipoEmpleado);
    }
    
    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", dui='" + dui + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", genero='" + genero + '\'' +
                ", provincia='" + provincia + '\'' +
                ", tipoEmpleado='" + tipoEmpleado + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
} 