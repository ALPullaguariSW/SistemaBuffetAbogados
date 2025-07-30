# Sistema de Buffet de Abogados

Sistema completo de gestión para un buffet de abogados desarrollado en Java con arquitectura MVC, utilizando Java Swing para la interfaz gráfica. El sistema cuenta con una estandarización completa de estilos profesionales y soporte para múltiples bases de datos.

## 🎨 Sistema de Estilos Estandarizado

El proyecto implementa un sistema de estilos completamente estandarizado a través de la clase `EstilosSistema.java` que proporciona:

### Paleta de Colores Profesional
- **Color Primario**: Azul marino profundo (#1C3350) - Confianza y seriedad
- **Color Secundario**: Gris pizarra (#708090) - Neutralidad y profesionalismo  
- **Color de Acento**: Dorado pálido (#CDB380) - Prestigio y éxito
- **Colores de Estado**: Verde bosque (éxito), Rojo ladrillo (error), Naranja oscuro (advertencia)

### Componentes Pre-estilizados
- `crearBotonPrincipal()` - Botones de acción primaria
- `crearBotonSecundario()` - Botones de acción secundaria
- `crearCampoTexto()` - Campos de texto con placeholders
- `crearAreaTexto()` - Áreas de texto estilizadas
- `crearPanelTarjeta()` - Paneles con diseño de tarjeta
- `crearLabelTitulo()` - Etiquetas de título
- `estilizarTabla()` - Tablas con diseño profesional

## 🏗️ Arquitectura

El proyecto sigue el patrón Modelo-Vista-Controlador (MVC):

- **`modelo/`**: Clases de datos y DAOs para acceso a base de datos
- **`vista/`**: Interfaces gráficas con Java Swing estandarizadas
- **`controlador/`**: Controladores de eventos y lógica de presentación
- **`util/`**: Utilidades, conexión a BD, validaciones y estilos

## 🗄️ Base de Datos

El sistema soporta múltiples motores de base de datos:

- **MySQL**: Para entornos de producción (configurado por defecto)
- **PostgreSQL**: Para entornos empresariales
- **SQLite**: Para desarrollo local

### Configuración de Base de Datos

#### Opción 1: Docker (Recomendado)
El proyecto incluye un archivo `docker-compose.yml` que configura automáticamente:

```bash
# Iniciar servicios de base de datos
docker-compose up -d

# Acceder a phpMyAdmin
# URL: http://localhost:8080
# Usuario: root
# Contraseña: pwd123
```

#### Opción 2: Configuración Manual
1. Ejecutar la aplicación
2. Hacer clic en "Configurar Conexión"
3. Seleccionar el tipo de base de datos
4. Ingresar los parámetros de conexión
5. Probar la conexión
6. Guardar la configuración

### Esquema de Base de Datos

El proyecto incluye un archivo `database_schema.sql` con el esquema completo de la base de datos:

#### Tablas Principales
- **`usuarios`**: Usuarios del sistema con roles y autenticación
- **`clientes`**: Información completa de clientes del buffet
- **`empleados`**: Personal del buffet con roles y especialidades
- **`casos`**: Casos legales con asignación de clientes y empleados
- **`audiencias`**: Programación de audiencias con fechas y lugares

#### Características del Esquema
- **Relaciones**: Claves foráneas entre tablas para integridad referencial
- **Índices**: Optimización para búsquedas frecuentes
- **Vistas**: Vistas predefinidas para consultas complejas
- **Datos de ejemplo**: Incluye datos iniciales para pruebas
- **Restricciones**: Validaciones a nivel de base de datos

#### Ejecutar el Esquema
```sql
-- En MySQL/phpMyAdmin
SOURCE database_schema.sql;

-- O importar directamente desde phpMyAdmin
-- File: database_schema.sql
```

## 📦 Archivos JAR Incluidos

El proyecto incluye los siguientes archivos JAR necesarios:

- **`sqlite-jdbc-3.50.3.0.jar`** (14MB) - Driver para SQLite
- **`mysql-connector-j-9.4.0.jar`** (2.5MB) - Driver para MySQL
- **`flatlaf-3.6.jar`** (904KB) - Look and Feel moderno

### Configuración en NetBeans

Los archivos JAR están configurados en `nbproject/project.properties`:

```properties
file.reference.flatlaf-3.6.jar=D:\\flatlaf-3.6.jar
file.reference.mysql-connector-j-9.4.0.jar=D:\\mysql-connector-j-9.4.0\\mysql-connector-j-9.4.0\\mysql-connector-j-9.4.0.jar
file.reference.sqlite-jdbc-3.50.3.0.jar=D:\\sqlite-jdbc-3.50.3.0.jar
```

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
- CRUD completo con tabla de datos estilizada

### 3. Gestión de Empleados
- Registro con información detallada
- Campos: Nombres, Apellidos, Teléfono, DUI, Fecha de nacimiento, Género, Provincia, Tipo de empleado, Estado civil
- CRUD completo con tabla de datos estilizada

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
- **Java**: JDK 24 o superior
- **NetBeans**: IDE recomendado (opcional)
- **Docker**: Para servicios de base de datos (opcional)
- **Base de Datos**: MySQL, PostgreSQL o SQLite

### Drivers de Base de Datos
Los drivers están incluidos en el proyecto:

- **MySQL**: `mysql-connector-j-9.4.0.jar` ✅
- **SQLite**: `sqlite-jdbc-3.50.3.0.jar` ✅
- **PostgreSQL**: Requiere descarga adicional

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
4. El proyecto se configura automáticamente con los JARs incluidos

### 3. Configurar Base de Datos

#### Opción A: Usar Docker (Recomendado)
```bash
# Iniciar servicios
docker-compose up -d

# Verificar que los servicios estén corriendo
docker-compose ps

# Importar esquema de base de datos
# Acceder a phpMyAdmin: http://localhost:8080
# Importar archivo: database_schema.sql
```

#### Opción B: Configuración Manual
1. Instalar MySQL/PostgreSQL localmente
2. Crear base de datos `buffet_abogados`
3. Configurar usuario y contraseña
4. Importar el esquema desde `database_schema.sql`

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
│   └── buffeteabogados/
│       ├── BuffetAbogados.java              # Clase principal
│       ├── modelo/                           # Modelos de datos
│       │   ├── Conexion.java                 # Gestión de conexiones BD
│       │   ├── Usuario.java
│       │   ├── Cliente.java
│       │   ├── Empleado.java
│       │   ├── Caso.java
│       │   ├── Audiencia.java
│       │   └── [DAO classes...]              # Data Access Objects
│       ├── vista/                            # Interfaces gráficas
│       │   ├── Login.java                    # Pantalla de login
│       │   ├── Dashboard.java                # Panel principal
│       │   ├── ConfiguracionConexion.java    # Configuración BD
│       │   ├── GestionClientes.java          # Gestión de clientes
│       │   ├── GestionEmpleados.java         # Gestión de empleados
│       │   ├── GestionCasos.java             # Gestión de casos
│       │   ├── GestionAudiencias.java        # Gestión de audiencias
│       │   ├── GestionReportes.java          # Generación de reportes
│       │   ├── RegistroUsuario.java          # Registro de usuarios
│       │   └── RecuperacionPassword.java     # Recuperación de contraseña
│       ├── controlador/                      # Controladores
│       │   ├── ClienteController.java
│       │   ├── EmpleadoController.java
│       │   ├── CasoController.java
│       │   ├── AudienciaController.java
│       │   └── ReporteController.java
│       └── util/                             # Utilidades
│           ├── EstilosSistema.java           # Sistema de estilos
│           └── Validaciones.java             # Validaciones
├── nbproject/                                # Configuración NetBeans
│   ├── project.properties                    # Propiedades del proyecto
│   ├── project.xml                          # Configuración XML
│   └── build-impl.xml                       # Scripts de build
├── configuracion.properties                 # Configuración de BD
├── database_schema.sql                      # Esquema completo de BD
├── docker-compose.yml                       # Servicios Docker
├── build.xml                                # Script de build Ant
├── manifest.mf                              # Manifest del JAR
├── sqlite-jdbc-3.50.3.0.jar                 # Driver SQLite
├── mysql-connector-j-9.4.0.jar              # Driver MySQL
├── flatlaf-3.6.jar                          # Look and Feel moderno
└── README.md
```

## 🔧 Características Técnicas

### Sistema de Estilos
- **Paleta de colores profesional** orientada a servicios legales
- **Componentes pre-estilizados** para consistencia visual
- **Look and Feel moderno** con FlatLaf
- **Responsive design** adaptado a diferentes resoluciones

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
- Diseño moderno y profesional
- Navegación clara e intuitiva
- Botones con iconos y colores estandarizados
- Tablas con diseño profesional
- Formularios con validación visual

## 🐳 Servicios Docker

El proyecto incluye configuración Docker para facilitar el desarrollo:

### Servicios Disponibles
- **MySQL 8.0**: Base de datos principal
- **phpMyAdmin**: Interfaz web para gestión de BD

### Comandos Docker
```bash
# Iniciar servicios
docker-compose up -d

# Ver logs
docker-compose logs

# Detener servicios
docker-compose down

# Reconstruir servicios
docker-compose up -d --build
```

### Configuración por Defecto
- **MySQL**: localhost:3306
- **phpMyAdmin**: http://localhost:8080
- **Usuario**: root
- **Contraseña**: pwd123
- **Base de datos**: buffet_abogados

## 🚧 Funcionalidades en Desarrollo

- Dashboard completo con módulos
- Gestión de entrevistas
- Gestión de evidencias
- Reportes PDF avanzados
- Sistema de notificaciones
- Backup automático de datos
- Integración con sistemas externos

## 📞 Soporte

Para reportar problemas o solicitar nuevas funcionalidades:
1. Crear un issue en el repositorio
2. Describir el problema o solicitud
3. Incluir información del sistema y pasos para reproducir

## 📄 Licencia

Este proyecto está desarrollado para fines educativos y de demostración.

---

**Desarrollado con ❤️ en Java con estilos profesionales** 