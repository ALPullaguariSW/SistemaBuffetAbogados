package buffetabogados.modelo;

import java.sql.Date;

/**
 * Clase que representa una audiencia legal
 */
public class Audiencia {
    private int id;
    private Date fecha;
    private String tipoAudiencia;
    private String juzgado;
    private int casoId;
    private Date fechaCreacion;
    
    // Referencia al caso relacionado
    private Caso caso;
    
    // Constructor por defecto
    public Audiencia() {}
    
    // Constructor con parámetros básicos
    public Audiencia(Date fecha, String tipoAudiencia, String juzgado, int casoId) {
        this.fecha = fecha;
        this.tipoAudiencia = tipoAudiencia;
        this.juzgado = juzgado;
        this.casoId = casoId;
    }
    
    // Constructor completo
    public Audiencia(int id, Date fecha, String tipoAudiencia, String juzgado, 
                    int casoId, Date fechaCreacion) {
        this.id = id;
        this.fecha = fecha;
        this.tipoAudiencia = tipoAudiencia;
        this.juzgado = juzgado;
        this.casoId = casoId;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getTipoAudiencia() {
        return tipoAudiencia;
    }
    
    public void setTipoAudiencia(String tipoAudiencia) {
        this.tipoAudiencia = tipoAudiencia;
    }
    
    public String getJuzgado() {
        return juzgado;
    }
    
    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }
    
    public int getCasoId() {
        return casoId;
    }
    
    public void setCasoId(int casoId) {
        this.casoId = casoId;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Caso getCaso() {
        return caso;
    }
    
    public void setCaso(Caso caso) {
        this.caso = caso;
    }
    
    /**
     * Obtiene el nombre del caso relacionado
     */
    public String getNombreCaso() {
        return caso != null ? caso.getNombre() : "Caso no encontrado";
    }
    
    /**
     * Verifica si la audiencia es de tipo preliminar
     */
    public boolean esPreliminar() {
        return "Preliminar".equals(tipoAudiencia);
    }
    
    /**
     * Verifica si la audiencia es de tipo principal
     */
    public boolean esPrincipal() {
        return "Principal".equals(tipoAudiencia);
    }
    
    /**
     * Verifica si la audiencia es de tipo sentencia
     */
    public boolean esSentencia() {
        return "Sentencia".equals(tipoAudiencia);
    }
    
    /**
     * Obtiene el tipo de audiencia formateado para mostrar
     */
    public String getTipoAudienciaFormateado() {
        switch (tipoAudiencia) {
            case "Preliminar":
                return "🔵 Preliminar";
            case "Principal":
                return "🟡 Principal";
            case "Sentencia":
                return "🔴 Sentencia";
            case "Medidas Cautelares":
                return "🟢 Medidas Cautelares";
            default:
                return tipoAudiencia;
        }
    }
    
    /**
     * Verifica si la audiencia es hoy
     */
    public boolean esHoy() {
        if (fecha == null) return false;
        java.util.Date hoy = new java.util.Date();
        return fecha.equals(new Date(hoy.getTime()));
    }
    
    /**
     * Verifica si la audiencia es en el futuro
     */
    public boolean esFutura() {
        if (fecha == null) return false;
        java.util.Date hoy = new java.util.Date();
        return fecha.after(new Date(hoy.getTime()));
    }
    
    /**
     * Verifica si la audiencia es en el pasado
     */
    public boolean esPasada() {
        if (fecha == null) return false;
        java.util.Date hoy = new java.util.Date();
        return fecha.before(new Date(hoy.getTime()));
    }
    
    @Override
    public String toString() {
        return "Audiencia{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", tipoAudiencia='" + tipoAudiencia + '\'' +
                ", juzgado='" + juzgado + '\'' +
                ", casoId=" + casoId +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
} 