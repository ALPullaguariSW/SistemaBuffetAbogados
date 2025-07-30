package buffeteabogados.util;

import java.util.regex.Pattern;

/**
 * Clase con métodos de validación para el sistema
 */
public class Validaciones {
    
    // Patrones de validación
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern DUI_PATTERN = Pattern.compile("\\d{9}");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("\\d{8}");
    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    
    /**
     * Valida que el campo no esté vacío
     */
    public static boolean campoVacio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
    
    /**
     * Valida que el campo tenga longitud mínima
     */
    public static boolean longitudMinima(String campo, int longitud) {
        return campo != null && campo.trim().length() >= longitud;
    }
    
    /**
     * Valida que el campo tenga longitud máxima
     */
    public static boolean longitudMaxima(String campo, int longitud) {
        return campo != null && campo.trim().length() <= longitud;
    }
    
    /**
     * Valida formato de correo electrónico
     */
    public static boolean correoValido(String correo) {
        return correo != null && EMAIL_PATTERN.matcher(correo).matches();
    }
    
    /**
     * Valida formato de DUI (9 dígitos)
     */
    public static boolean duiValido(String dui) {
        return dui != null && DUI_PATTERN.matcher(dui).matches();
    }
    
    /**
     * Valida formato de teléfono (8 dígitos)
     */
    public static boolean telefonoValido(String telefono) {
        return telefono != null && TELEFONO_PATTERN.matcher(telefono).matches();
    }
    
    /**
     * Valida que el nombre contenga solo letras y espacios
     */
    public static boolean nombreValido(String nombre) {
        return nombre != null && NOMBRE_PATTERN.matcher(nombre.trim()).matches();
    }
    
    /**
     * Valida que el usuario contenga solo letras, números y guiones bajos
     */
    public static boolean usuarioValido(String usuario) {
        return usuario != null && usuario.matches("^[a-zA-Z0-9_]+$") && usuario.length() >= 3;
    }
    
    /**
     * Valida que la contraseña tenga al menos 6 caracteres
     */
    public static boolean passwordValida(String password) {
        return password != null && password.length() >= 6;
    }
    
    /**
     * Valida que el puerto sea un número válido
     */
    public static boolean puertoValido(String puerto) {
        try {
            int puertoNum = Integer.parseInt(puerto);
            return puertoNum > 0 && puertoNum <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Valida que la IP tenga formato válido
     */
    public static boolean ipValida(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }
        
        String[] partes = ip.split("\\.");
        if (partes.length != 4) {
            return false;
        }
        
        try {
            for (String parte : partes) {
                int num = Integer.parseInt(parte);
                if (num < 0 || num > 255) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Valida que la fecha no sea futura
     */
    public static boolean fechaNoFutura(java.util.Date fecha) {
        if (fecha == null) return false;
        java.util.Date hoy = new java.util.Date();
        return !fecha.after(hoy);
    }
    
    /**
     * Valida que la fecha no sea muy antigua (más de 100 años)
     */
    public static boolean fechaNoMuyAntigua(java.util.Date fecha) {
        if (fecha == null) return false;
        java.util.Date hoy = new java.util.Date();
        java.util.Date limite = new java.util.Date(hoy.getTime() - (100L * 365 * 24 * 60 * 60 * 1000));
        return !fecha.before(limite);
    }
    
    /**
     * Obtiene mensaje de error para campo vacío
     */
    public static String mensajeCampoVacio(String nombreCampo) {
        return "El campo " + nombreCampo + " es obligatorio";
    }
    
    /**
     * Obtiene mensaje de error para longitud mínima
     */
    public static String mensajeLongitudMinima(String nombreCampo, int longitud) {
        return "El campo " + nombreCampo + " debe tener al menos " + longitud + " caracteres";
    }
    
    /**
     * Obtiene mensaje de error para formato inválido
     */
    public static String mensajeFormatoInvalido(String nombreCampo) {
        return "El formato del campo " + nombreCampo + " no es válido";
    }
    
    /**
     * Obtiene mensaje de error para correo inválido
     */
    public static String mensajeCorreoInvalido() {
        return "El formato del correo electrónico no es válido";
    }
    
    /**
     * Obtiene mensaje de error para DUI inválido
     */
    public static String mensajeDuiInvalido() {
        return "El DUI debe contener exactamente 9 dígitos";
    }
    
    /**
     * Obtiene mensaje de error para teléfono inválido
     */
    public static String mensajeTelefonoInvalido() {
        return "El teléfono debe contener exactamente 8 dígitos";
    }
} 