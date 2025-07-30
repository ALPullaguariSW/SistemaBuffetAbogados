package buffeteabogados.modelo;

import java.sql.Timestamp;

public class Audiencia {
    private int id;
    private String tipo;
    private Timestamp fechaHora;
    private String lugar;
    private int casoId;
    private int abogadoId;
    private String estado;
    private String observaciones;

    // Campos adicionales para mostrar en tablas
    private String casoTitulo;
    private String abogadoNombre;

    // Constructores, Getters y Setters...
    public Audiencia() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public int getCasoId() { return casoId; }
    public void setCasoId(int casoId) { this.casoId = casoId; }
    public int getAbogadoId() { return abogadoId; }
    public void setAbogadoId(int abogadoId) { this.abogadoId = abogadoId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getCasoTitulo() { return casoTitulo; }
    public void setCasoTitulo(String casoTitulo) { this.casoTitulo = casoTitulo; }
    public String getAbogadoNombre() { return abogadoNombre; }
    public void setAbogadoNombre(String abogadoNombre) { this.abogadoNombre = abogadoNombre; }
}