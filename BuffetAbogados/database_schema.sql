-- =====================================================
-- ESQUEMA DE BASE DE DATOS - SISTEMA BUFFET DE ABOGADOS
-- =====================================================
-- Versión: 1.0
-- Fecha: 30-07-2025
-- Descripción: Esquema completo para el sistema de gestión de buffet de abogados
-- Compatible con: MySQL 8.0+, PostgreSQL, SQLite

-- Configuración inicial
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS `buffet_abogados` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE `buffet_abogados`;

-- =====================================================
-- TABLA: usuarios
-- Descripción: Almacena información de usuarios del sistema
-- =====================================================
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `usuario` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(50) DEFAULT 'Empleado',
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  `fecha_creacion` datetime DEFAULT CURRENT_TIMESTAMP,
  `ultimo_acceso` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario` (`usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: clientes
-- Descripción: Almacena información de clientes del buffet
-- =====================================================
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `direccion` text,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `dui` varchar(20) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` varchar(20) DEFAULT NULL,
  `provincia` varchar(100) DEFAULT NULL,
  `estado_civil` varchar(50) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`),
  UNIQUE KEY `dui` (`dui`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: empleados
-- Descripción: Almacena información de empleados del buffet
-- =====================================================
CREATE TABLE `empleados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `dui` varchar(20) DEFAULT NULL,
  `direccion` text,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` varchar(20) DEFAULT NULL,
  `provincia` varchar(100) DEFAULT NULL,
  `tipo_empleado` varchar(100) DEFAULT NULL,
  `estado_civil` varchar(50) DEFAULT NULL,
  `fecha_contratacion` date DEFAULT NULL,
  `salario` decimal(10,2) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `dui` (`dui`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: casos
-- Descripción: Almacena información de casos legales
-- =====================================================
CREATE TABLE `casos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_caso` varchar(50) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `descripcion` text,
  `observaciones` text,
  `tipo_caso` varchar(100) DEFAULT NULL,
  `estado` varchar(50) DEFAULT 'Activo',
  `prioridad` varchar(50) DEFAULT 'Media',
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin_estimada` date DEFAULT NULL,
  `fecha_cierre` date DEFAULT NULL,
  `cliente_id` int NOT NULL,
  `empleado_id` int NOT NULL,
  `juzgado` varchar(255) DEFAULT NULL,
  `juez` varchar(255) DEFAULT NULL,
  `honorarios` decimal(10,2) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_caso` (`numero_caso`),
  KEY `cliente_id` (`cliente_id`),
  KEY `empleado_id` (`empleado_id`),
  CONSTRAINT `casos_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `casos_ibfk_2` FOREIGN KEY (`empleado_id`) REFERENCES `empleados` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: audiencias
-- Descripción: Almacena información de audiencias programadas
-- =====================================================
CREATE TABLE `audiencias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_audiencia` varchar(50) NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `sala` varchar(100) DEFAULT NULL,
  `juzgado` varchar(255) DEFAULT NULL,
  `juez` varchar(255) DEFAULT NULL,
  `caso_id` int NOT NULL,
  `empleado_id` int NOT NULL,
  `estado` varchar(50) DEFAULT 'Programada',
  `observaciones` text,
  `resultado` text,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_audiencia` (`numero_audiencia`),
  KEY `caso_id` (`caso_id`),
  KEY `empleado_id` (`empleado_id`),
  CONSTRAINT `audiencias_ibfk_1` FOREIGN KEY (`caso_id`) REFERENCES `casos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `audiencias_ibfk_2` FOREIGN KEY (`empleado_id`) REFERENCES `empleados` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- DATOS INICIALES
-- =====================================================

-- Insertar usuario administrador por defecto
INSERT INTO `usuarios` (`nombres`, `apellidos`, `email`, `usuario`, `password`, `rol`, `activo`) VALUES
('Administrador', 'Sistema', 'admin@buffet.com', 'admin', 'admin123', 'Abogado', 1);

-- Insertar algunos empleados de ejemplo
INSERT INTO `empleados` (`nombres`, `apellidos`, `email`, `telefono`, `dui`, `tipo_empleado`, `estado`) VALUES
('Dr. Roberto', 'Martínez', 'roberto.martinez@buffet.com', '71234567', '12345678-9', 'Abogado', 1),
('Lic. Ana', 'Silva', 'ana.silva@buffet.com', '72345678', '23456789-0', 'Secretaria', 1),
('Dr. Luis', 'Rodríguez', 'luis.rodriguez@buffet.com', '73456789', '34567890-1', 'Abogado', 1);

-- Insertar algunos clientes de ejemplo
INSERT INTO `clientes` (`nombres`, `apellidos`, `telefono`, `correo`, `dui`, `estado`) VALUES
('Juan', 'Pérez', '74567890', 'juan.perez@email.com', '45678901-2', 1),
('María', 'González', '75678901', 'maria.gonzalez@email.com', '56789012-3', 1),
('Carlos', 'López', '76789012', 'carlos.lopez@email.com', '67890123-4', 1);

-- Insertar algunos casos de ejemplo
INSERT INTO `casos` (`numero_caso`, `titulo`, `descripcion`, `tipo_caso`, `estado`, `prioridad`, `cliente_id`, `empleado_id`, `fecha_inicio`) VALUES
('CIV-2024-001', 'Demanda por Incumplimiento de Contrato', 'Demanda presentada por incumplimiento de contrato de compraventa de vehículo.', 'Civil', 'Activo', 'Alta', 1, 1, '2024-01-15'),
('PEN-2024-002', 'Proceso Penal por Estafa', 'Proceso penal por presunta estafa en transacción comercial.', 'Penal', 'En Proceso', 'Urgente', 2, 3, '2024-01-20'),
('LAB-2024-003', 'Demanda Laboral por Despido', 'Demanda laboral por despido injustificado.', 'Laboral', 'Pendiente', 'Media', 3, 1, '2024-01-25');

-- Insertar algunas audiencias de ejemplo
INSERT INTO `audiencias` (`numero_audiencia`, `tipo`, `fecha_hora`, `sala`, `juzgado`, `caso_id`, `empleado_id`, `estado`) VALUES
('AUD-2024-001', 'Preliminar', '2024-02-15 09:00:00', 'Sala 1', 'Juzgado Primero de lo Civil de San Salvador', 1, 1, 'Programada'),
('AUD-2024-002', 'Principal', '2024-02-20 14:00:00', 'Sala 2', 'Juzgado Segundo de lo Penal de San Salvador', 2, 3, 'Programada'),
('AUD-2024-003', 'Conciliación', '2024-02-25 10:00:00', 'Sala 3', 'Juzgado de Trabajo de San Salvador', 3, 1, 'Programada');

-- =====================================================
-- ÍNDICES ADICIONALES PARA OPTIMIZACIÓN
-- =====================================================

-- Índices para búsquedas frecuentes
CREATE INDEX idx_casos_estado ON casos(estado);
CREATE INDEX idx_casos_tipo ON casos(tipo_caso);
CREATE INDEX idx_audiencias_fecha ON audiencias(fecha_hora);
CREATE INDEX idx_audiencias_estado ON audiencias(estado);
CREATE INDEX idx_clientes_nombres ON clientes(nombres, apellidos);
CREATE INDEX idx_empleados_nombres ON empleados(nombres, apellidos);

-- =====================================================
-- VISTAS ÚTILES
-- =====================================================

-- Vista para casos con información completa
CREATE VIEW vista_casos_completos AS
SELECT 
    c.id,
    c.numero_caso,
    c.titulo,
    c.descripcion,
    c.tipo_caso,
    c.estado,
    c.prioridad,
    c.fecha_inicio,
    c.fecha_fin_estimada,
    CONCAT(cl.nombres, ' ', cl.apellidos) AS cliente_nombre,
    CONCAT(e.nombres, ' ', e.apellidos) AS empleado_nombre,
    c.juzgado,
    c.juez,
    c.honorarios
FROM casos c
JOIN clientes cl ON c.cliente_id = cl.id
JOIN empleados e ON c.empleado_id = e.id
WHERE c.activo = 1;

-- Vista para audiencias con información completa
CREATE VIEW vista_audiencias_completas AS
SELECT 
    a.id,
    a.numero_audiencia,
    a.tipo,
    a.fecha_hora,
    a.sala,
    a.juzgado,
    a.juez,
    a.estado,
    c.numero_caso,
    c.titulo AS caso_titulo,
    CONCAT(cl.nombres, ' ', cl.apellidos) AS cliente_nombre,
    CONCAT(e.nombres, ' ', e.apellidos) AS empleado_nombre
FROM audiencias a
JOIN casos c ON a.caso_id = c.id
JOIN clientes cl ON c.cliente_id = cl.id
JOIN empleados e ON a.empleado_id = e.id;

COMMIT;

-- =====================================================
-- FIN DEL ESQUEMA
-- ===================================================== 