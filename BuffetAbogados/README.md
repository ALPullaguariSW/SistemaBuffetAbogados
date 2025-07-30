# Sistema de Buffet de Abogados

Sistema completo de gestión para un buffet de abogados desarrollado en Java con arquitectura MVC, utilizando Java Swing para la interfaz gráfica.

## 🏗️ Arquitectura

El proyecto sigue el patrón Modelo-Vista-Controlador (MVC):

- **`modelo/`**: Clases de datos y lógica de negocio
- **`vista/`**: Interfaces gráficas con Java Swing
- **`controlador/`**: Controladores de eventos y lógica de presentación
- **`util/`**: Utilidades, conexión a BD y validaciones

## 🗄️ Base de Datos

El sistema soporta múltiples motores de base de datos:

- **MySQL**: Para entornos de producción
- **PostgreSQL**: Para entornos empresariales
- **SQLite**: Para desarrollo local (modo por defecto)

### Configuración de Base de Datos

1. Ejecutar la aplicación
2. Hacer clic en "Configurar Conexión"
3. Seleccionar el tipo de base de datos
4. Ingresar los parámetros de conexión
5. Probar la conexión
6. Guardar la configuración

## 👤 Usuarios y Roles

### Usuario Administrador por Defecto
- **Usuario**: `admin`
- **Contraseña**: `admin123`
- **Rol**: `Abogado` (acceso completo)

### Roles del Sistema
- **Cliente**: Solo visualiza sus casos y reportes
- **Empleado**: Gestión interna limitada
- **Abogado**: Acceso total al sistema (dashboard completo)

## 🚀 Funcionalidades

### 1. Autenticación
- Login con usuario y contraseña
- Validación contra base de datos
- Opción "¿Olvidaste tu contraseña?"
- Registro de nuevos usuarios

### 2. Gestión de Clientes
- Registro con validaciones completas
- Campos: Nombres, Apellidos, Dirección, Teléfono, Correo, DUI, Fecha de nacimiento
- CRUD completo con tabla de datos

### 3. Gestión de Empleados
- Registro con información detallada
- Campos: Nombres, Apellidos, Teléfono, DUI, Fecha de nacimiento, Género, Provincia, Tipo de empleado, Estado civil
- CRUD completo con tabla de datos

### 4. Gestión de Casos
- Creación y seguimiento de casos legales
- Asignación a clientes y empleados
- Estados: Activo, En Proceso, Cerrado, Pendiente
- Tipos de caso configurables

### 5. Gestión de Audiencias
- Programación de audiencias
- Tipos: Preliminar, Principal, Sentencia, Medidas Cautelares
- Asociación con casos específicos
- Información de juzgado

### 6. Reportes
- Generación de reportes PDF
- Reportes por cliente
- Reportes del sistema completo
- Opciones de guardar e imprimir

### 7. Recuperación de Contraseña
- Sistema de recuperación por PIN
- Verificación en múltiples pasos
- Generación de PIN aleatorio

## 📋 Requisitos del Sistema

### Software Requerido
- **Java**: JDK 8 o superior
- **NetBeans**: IDE recomendado (opcional)
- **Base de Datos**: MySQL, PostgreSQL o SQLite

### Drivers de Base de Datos
Para usar MySQL o PostgreSQL, agregar los drivers correspondientes al classpath:

- **MySQL**: `mysql-connector-java-8.0.xx.jar`
- **PostgreSQL**: `postgresql-42.x.x.jar`
- **SQLite**: `sqlite-jdbc-3.xx.x.jar` (incluido por defecto)

## 🛠️ Instalación y Configuración

### 1. Clonar o Descargar el Proyecto
```bash
git clone [URL_DEL_REPOSITORIO]
cd BuffetAbogados
```

### 2. Configurar en NetBeans
1. Abrir NetBeans
2. File → Open Project
3. Seleccionar la carpeta `BuffetAbogados`
4. Configurar como proyecto Java con Ant

### 3. Configurar Drivers de BD (Opcional)
Si usará MySQL o PostgreSQL:
1. Descargar el driver correspondiente
2. Agregar al classpath del proyecto
3. Configurar en NetBeans: Properties → Libraries → Add JAR/Folder

### 4. Ejecutar la Aplicación
1. Ejecutar la clase `BuffetAbogados`
2. O hacer clic en "Run Project" en NetBeans

## 🎯 Uso del Sistema

### Primer Inicio
1. Ejecutar la aplicación
2. Usar las credenciales por defecto:
   - Usuario: `admin`
   - Contraseña: `admin123`
3. Configurar la conexión a base de datos
4. Comenzar a usar el sistema

### Configuración de Base de Datos
1. En la pantalla de login, hacer clic en "Configurar Conexión"
2. Seleccionar el tipo de base de datos
3. Ingresar los parámetros:
   - **Host/IP**: Dirección del servidor
   - **Puerto**: Puerto del servicio (MySQL: 3306, PostgreSQL: 5432)
   - **Nombre de BD**: Nombre de la base de datos
   - **Usuario**: Usuario de la base de datos
   - **Contraseña**: Contraseña del usuario
4. Probar la conexión
5. Guardar la configuración

### Para SQLite (Modo Local)
- No requiere configuración adicional
- Se crea automáticamente el archivo `.db`
- Ideal para desarrollo y pruebas

## 📁 Estructura del Proyecto

```
BuffetAbogados/
├── src/
│   └── main/
│       └── java/
│           ├── BuffetAbogados.java          # Clase principal
│           ├── modelo/                       # Modelos de datos
│           │   ├── Conexion.java
│           │   ├── Usuario.java
│           │   ├── Cliente.java
│           │   ├── Empleado.java
│           │   ├── Caso.java
│           │   └── Audiencia.java
│           ├── vista/                        # Interfaces gráficas
│           │   ├── Login.java
│           │   ├── ConfiguracionConexion.java
│           │   └── [Otras vistas...]
│           ├── controlador/                  # Controladores
│           │   └── [Controladores...]
│           └── util/                         # Utilidades
│               └── Validaciones.java
├── configuracion.properties                 # Configuración de BD
├── buffet_abogados.db                       # Base SQLite (se crea automáticamente)
└── README.md
```

## 🔧 Características Técnicas

### Validaciones Implementadas
- Campos obligatorios
- Formato de correo electrónico
- Formato de DUI (9 dígitos)
- Formato de teléfono (8 dígitos)
- Validación de IP y puertos
- Validación de fechas
- Longitud mínima y máxima de campos

### Manejo de Errores
- Control de excepciones robusto
- Mensajes de error descriptivos
- Validaciones en tiempo real
- Logs de errores en consola

### Interfaz de Usuario
- Diseño moderno y intuitivo
- Navegación clara
- Botones con iconos y colores
- Responsive y accesible

## 🚧 Funcionalidades en Desarrollo

- Dashboard completo con módulos
- Gestión de entrevistas
- Gestión de evidencias
- Reportes PDF avanzados
- Sistema de notificaciones
- Backup automático de datos

## 📞 Soporte

Para reportar problemas o solicitar nuevas funcionalidades:
1. Crear un issue en el repositorio
2. Describir el problema o solicitud
3. Incluir información del sistema y pasos para reproducir

## 📄 Licencia

Este proyecto está desarrollado para fines educativos y de demostración.

---

**Desarrollado con ❤️ en Java** 