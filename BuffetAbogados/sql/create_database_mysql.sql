-- Script para crear la base de datos MySQL para Buffet de Abogados
-- Ejecutar como administrador de MySQL

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS buffet_abogados
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE buffet_abogados;

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol ENUM('Cliente', 'Empleado', 'Abogado') DEFAULT 'Cliente',
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Crear tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100),
    dui VARCHAR(10) UNIQUE NOT NULL,
    fecha_nacimiento DATE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- Crear tabla de casos
CREATE TABLE IF NOT EXISTS casos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_caso VARCHAR(50) UNIQUE NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    tipo_caso VARCHAR(100) NOT NULL,
    estado ENUM('Abierto', 'En Proceso', 'Cerrado', 'Archivado') DEFAULT 'Abierto',
    fecha_inicio DATE NOT NULL,
    fecha_cierre DATE,
    cliente_id INT NOT NULL,
    abogado_responsable_id INT,
    monto_total DECIMAL(12,2) DEFAULT 0.00,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (abogado_responsable_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Crear tabla de audiencias
CREATE TABLE IF NOT EXISTS audiencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    caso_id INT NOT NULL,
    fecha_audiencia DATETIME NOT NULL,
    tipo_audiencia VARCHAR(100) NOT NULL,
    lugar VARCHAR(200) NOT NULL,
    descripcion TEXT,
    estado ENUM('Programada', 'Completada', 'Cancelada', 'Reprogramada') DEFAULT 'Programada',
    resultado TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (caso_id) REFERENCES casos(id) ON DELETE CASCADE
);

-- Crear tabla de empleados
CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT UNIQUE NOT NULL,
    codigo_empleado VARCHAR(20) UNIQUE NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    departamento VARCHAR(100),
    salario DECIMAL(10,2),
    fecha_ingreso DATE NOT NULL,
    fecha_salida DATE,
    telefono VARCHAR(20),
    direccion TEXT,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Insertar usuario administrador por defecto
INSERT IGNORE INTO usuarios (nombres, apellidos, usuario, password, rol) 
VALUES ('Administrador', 'Sistema', 'admin', 'admin123', 'Abogado');

-- Crear índices para mejorar rendimiento
CREATE INDEX idx_usuarios_usuario ON usuarios(usuario);
CREATE INDEX idx_clientes_dui ON clientes(dui);
CREATE INDEX idx_clientes_nombres ON clientes(nombres, apellidos);
CREATE INDEX idx_casos_numero ON casos(numero_caso);
CREATE INDEX idx_casos_cliente ON casos(cliente_id);
CREATE INDEX idx_audiencias_fecha ON audiencias(fecha_audiencia);
CREATE INDEX idx_empleados_codigo ON empleados(codigo_empleado);

-- Mostrar las tablas creadas
SHOW TABLES;