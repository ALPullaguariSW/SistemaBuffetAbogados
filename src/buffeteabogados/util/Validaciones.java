package buffeteabogados.util;

import java.util.regex.Pattern;

/**
 * Clase utilitaria para validaciones
 * @author axel_
 */
public class Validaciones {
    
    // Patrones de expresiones regulares
    private static final Pattern PATRON_EMAIL = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private static final Pattern PATRON_DUI = Pattern.compile(
        "^\\d{8}-\\d{1}$"
    );
    
    private static final Pattern PATRON_TELEFONO = Pattern.compile(
        "^[267]\\d{3}-\\d{4}$"
    );
    
    private static final Pattern PATRON_NOMBRE = Pattern.compile(
        "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1\\s]+$"
    );
    
    private static final Pattern PATRON_USUARIO = Pattern.compile(
        "^[a-zA-Z0-9_]{3,20}$"
    );
    
    private static final Pattern PATRON_IP = Pattern.compile(
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );
    
    /**
     * Verifica si un campo está vacío o es null
     * @param campo valor a verificar
     * @return true si está vacío
     */
    public static boolean campoVacio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
    
    /**
     * Verifica la longitud mínima de un campo
     * @param campo valor a verificar
     * @param longitudMinima longitud mínima requerida
     * @return true si cumple la longitud mínima
     */
    public static boolean longitudMinima(String campo, int longitudMinima) {
        return campo != null && campo.trim().length() >= longitudMinima;
    }
    
    /**
     * Verifica la longitud máxima de un campo
     * @param campo valor a verificar
     * @param longitudMaxima longitud máxima permitida
     * @return true si cumple la longitud máxima
     */
    public static boolean longitudMaxima(String campo, int longitudMaxima) {
        return campo != null && campo.trim().length() <= longitudMaxima;
    }
    
    /**
     * Valida formato de email
     * @param email email a validar
     * @return true si es válido
     */
    public static boolean correoValido(String email) {
        if (campoVacio(email)) {
            return false;
        }
        return PATRON_EMAIL.matcher(email.trim()).matches();
    }
    
    /**
     * Valida formato de DUI salvadoreño
     * @param dui DUI a validar (formato: 12345678-9)
     * @return true si es válido
     */
    public static boolean duiValido(String dui) {
        if (campoVacio(dui)) {
            return false;
        }
        return PATRON_DUI.matcher(dui.trim()).matches();
    }
    
    /**
     * Valida formato de teléfono salvadoreño
     * @param telefono teléfono a validar (formato: 2XXX-XXXX, 6XXX-XXXX, 7XXX-XXXX)
     * @return true si es válido
     */
    public static boolean telefonoValido(String telefono) {
        if (campoVacio(telefono)) {
            return false;
        }
        return PATRON_TELEFONO.matcher(telefono.trim()).matches();
    }
    
    /**
     * Valida formato de nombre (solo letras y espacios)
     * @param nombre nombre a validar
     * @return true si es válido
     */
    public static boolean nombreValido(String nombre) {
        if (campoVacio(nombre)) {
            return false;
        }
        return PATRON_NOMBRE.matcher(nombre.trim()).matches() 
               && longitudMinima(nombre, 2) 
               && longitudMaxima(nombre, 50);
    }
    
    /**
     * Valida formato de usuario (letras, números y guión bajo)
     * @param usuario usuario a validar
     * @return true si es válido
     */
    public static boolean usuarioValido(String usuario) {
        if (campoVacio(usuario)) {
            return false;
        }
        return PATRON_USUARIO.matcher(usuario.trim()).matches();
    }
    
    /**
     * Valida contraseña (mínimo 6 caracteres)
     * @param password contraseña a validar
     * @return true si es válida
     */
    public static boolean passwordValida(String password) {
        return longitudMinima(password, 6);
    }
    
    /**
     * Valida puerto de red
     * @param puerto puerto a validar
     * @return true si es válido (1-65535)
     */
    public static boolean puertoValido(String puerto) {
        if (campoVacio(puerto)) {
            return false;
        }
        
        try {
            int port = Integer.parseInt(puerto.trim());
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Valida dirección IP
     * @param ip dirección IP a validar
     * @return true si es válida
     */
    public static boolean ipValida(String ip) {
        if (campoVacio(ip)) {
            return false;
        }
        return PATRON_IP.matcher(ip.trim()).matches();
    }
    
    /**
     * Valida que una fecha no sea futura
     * @param fecha fecha en formato String
     * @return true si no es futura
     */
    public static boolean fechaNoFutura(String fecha) {
        // TODO: Implementar validación de fecha
        return true; // Por ahora acepta cualquier fecha
    }
    
    /**
     * Genera mensaje de error para campo vacío
     * @param nombreCampo nombre del campo
     * @return mensaje de error
     */
    public static String mensajeCampoVacio(String nombreCampo) {
        return "El campo '" + nombreCampo + "' es obligatorio";
    }
    
    /**
     * Genera mensaje de error para longitud mínima
     * @param nombreCampo nombre del campo
     * @param longitudMinima longitud mínima requerida
     * @return mensaje de error
     */
    public static String mensajeLongitudMinima(String nombreCampo, int longitudMinima) {
        return "El campo '" + nombreCampo + "' debe tener al menos " + longitudMinima + " caracteres";
    }
    
    /**
     * Genera mensaje de error para longitud máxima
     * @param nombreCampo nombre del campo
     * @param longitudMaxima longitud máxima permitida
     * @return mensaje de error
     */
    public static String mensajeLongitudMaxima(String nombreCampo, int longitudMaxima) {
        return "El campo '" + nombreCampo + "' no puede exceder " + longitudMaxima + " caracteres";
    }
    
    /**
     * Genera mensaje de error para formato inválido
     * @param nombreCampo nombre del campo
     * @param formatoEsperado formato esperado
     * @return mensaje de error
     */
    public static String mensajeFormatoInvalido(String nombreCampo, String formatoEsperado) {
        return "El campo '" + nombreCampo + "' debe tener el formato: " + formatoEsperado;
    }
}