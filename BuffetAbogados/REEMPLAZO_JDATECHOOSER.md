# Reemplazo de JDateChooser con Componentes Nativos

## Problema
`JDateChooser` requiere la librería externa `JCalendar`, lo que puede causar problemas de dependencias en NetBeans y otros IDEs.

## Solución
Usar componentes nativos de Java Swing que están disponibles por defecto en NetBeans.

## Alternativas Nativas Implementadas

### 1. JSpinner con SpinnerDateModel (RECOMENDADO)
```java
// Crear selector de fecha
JSpinner spinnerFecha = ComponentesFecha.crearSelectorFecha(null);

// Obtener fecha
Date fecha = ComponentesFecha.obtenerFecha(spinnerFecha);
```

**Ventajas:**
- ✅ Nativo de Java Swing
- ✅ Funciona perfectamente en NetBeans
- ✅ Formato personalizable
- ✅ Validación automática de fechas
- ✅ Fácil de usar

### 2. JSpinner Especializado para Fechas de Nacimiento
```java
// Para fechas de nacimiento (rango: 120 años atrás hasta hoy)
JSpinner spinnerNacimiento = ComponentesFecha.crearSelectorFechaNacimiento();

// Obtener fecha
Date fechaNacimiento = ComponentesFecha.obtenerFecha(spinnerNacimiento);
```

### 3. Campos Separados (Día, Mes, Año)
```java
// Crear panel con campos separados
JPanel panelFecha = ComponentesFecha.crearPanelFechaCamposSeparados(null);

// Obtener fecha
Date fecha = ComponentesFecha.obtenerFechaDeCamposSeparados(panelFecha);
```

**Ventajas:**
- ✅ Interfaz más familiar para algunos usuarios
- ✅ Control granular sobre cada componente

### 4. JFormattedTextField
```java
// Crear campo formateado
JFormattedTextField campoFecha = ComponentesFecha.crearCampoFechaFormateado(null);

// Obtener fecha
Date fecha = ComponentesFecha.obtenerFechaDeFormattedField(campoFecha);
```

## Cómo Reemplazar JDateChooser Existente

### Antes (con JDateChooser):
```java
// ANTIGUO - Requiere librería externa
import com.toedter.calendar.JDateChooser;

JDateChooser dateChooser = new JDateChooser();
dateChooser.setDate(new Date());

// Obtener fecha
Date fecha = dateChooser.getDate();
```

### Después (con componentes nativos):
```java
// NUEVO - Solo componentes nativos
import buffetabogados.util.ComponentesFecha;

JSpinner spinnerFecha = ComponentesFecha.crearSelectorFecha(new Date());

// Obtener fecha
Date fecha = ComponentesFecha.obtenerFecha(spinnerFecha);
```

## Conversión para Base de Datos

```java
// Convertir java.util.Date a java.sql.Date
Date fechaUtil = ComponentesFecha.obtenerFecha(spinnerFecha);
java.sql.Date fechaSQL = ComponentesFecha.convertirASqlDate(fechaUtil);

// Usar en DAO
cliente.setFechaNacimiento(fechaSQL);
```

## Ejemplo Completo en un Formulario

```java
public class FormularioCliente extends JDialog {
    private JSpinner spinnerFechaNacimiento;
    
    private void initComponents() {
        // ... otros componentes ...
        
        // Campo fecha de nacimiento
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Fecha Nacimiento:"), gbc);
        
        gbc.gridx = 1;
        spinnerFechaNacimiento = ComponentesFecha.crearSelectorFechaNacimiento();
        add(spinnerFechaNacimiento, gbc);
    }
    
    private void guardarCliente() {
        // Obtener fecha
        Date fechaNacimiento = ComponentesFecha.obtenerFecha(spinnerFechaNacimiento);
        
        // Validar (opcional)
        if (!Validaciones.fechaNoFutura(fechaNacimiento)) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura");
            return;
        }
        
        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(ComponentesFecha.convertirASqlDate(fechaNacimiento));
        
        // Guardar en base de datos...
    }
}
```

## Archivos Creados

1. **`ComponentesFecha.java`** - Clase utilitaria con métodos estáticos
2. **`EjemploUsoFecha.java`** - Ejemplo funcional de todos los componentes

## Ejecutar el Ejemplo

Para ver todos los componentes en acción:

```java
// Ejecutar desde NetBeans o línea de comandos
java buffetabogados.vista.EjemploUsoFecha
```

## Ventajas de Esta Solución

✅ **Sin dependencias externas** - Solo usa Java Swing nativo  
✅ **Compatible con NetBeans** - No problemas con GUI Builder  
✅ **Reutilizable** - Métodos estáticos fáciles de usar  
✅ **Validación integrada** - Los componentes validan automáticamente  
✅ **Formato consistente** - Todos usan formato dd/MM/yyyy  
✅ **Conversión automática** - Incluye conversión a java.sql.Date  

## Migración Paso a Paso

1. **Agregar** `ComponentesFecha.java` al proyecto
2. **Reemplazar** imports de `JDateChooser`
3. **Cambiar** creación de componentes:
   - `new JDateChooser()` → `ComponentesFecha.crearSelectorFecha(null)`
4. **Cambiar** obtención de valores:
   - `dateChooser.getDate()` → `ComponentesFecha.obtenerFecha(spinner)`
5. **Probar** la funcionalidad

¡Listo! Ahora tu proyecto funciona sin dependencias externas.