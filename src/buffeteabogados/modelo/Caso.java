package buffeteabogados.modelo;

import java.sql.Date;

public class Caso {
    private int id;
    private String titulo;
    private String descripcion;
    private String tipoCaso;
    private String estado;
    private Date fechaInicio;
    private Date fechaCierre;
    private int clienteId;
    private int abogadoId;
    private String prioridad;
    private double estimacionCosto;

    // Campos adicionales para mostrar en tablas
    private String clienteNombre;
    private String abogadoNombre;

    // Constructores, Getters y Setters...
    public Caso() {}
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipoCaso() { return tipoCaso; }
    public void setTipoCaso(String tipoCaso) { this.tipoCaso = tipoCaso; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(Date fechaCierre) { this.fechaCierre = fechaCierre; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public int getAbogadoId() { return abogadoId; }
    public void setAbogadoId(int abogadoId) { this.abogadoId = abogadoId; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public double getEstimacionCosto() { return estimacionCosto; }
    public void setEstimacionCosto(double estimacionCosto) { this.estimacionCosto = estimacionCosto; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public String getAbogadoNombre() { return abogadoNombre; }
    public void setAbogadoNombre(String abogadoNombre) { this.abogadoNombre = abogadoNombre; }
}