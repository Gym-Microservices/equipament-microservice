# ⚡ Equipment Microservice

## 📋 Descripción

Microservicio para la gestión del equipamiento del gimnasio (Puerto 8083). Permite administrar el inventario de equipos, realizar reservas y controlar la disponibilidad en tiempo real.

## 🔗 Endpoints

### 🏋️‍♀️ Gestión de Equipamiento

- `POST /api/equipment` - Agregar nuevo equipamiento
- `GET /api/equipment` - Obtener todo el equipamiento
- `GET /api/equipment/{id}` - Obtener equipamiento por ID
- `PUT /api/equipment/{id}` - Actualizar información del equipamiento
- `DELETE /api/equipment/{id}` - Eliminar equipamiento

### 📦 Gestión de Reservas

- `POST /api/equipment/{id}/reserve/{quantity}` - Reservar cantidad específica de equipamiento
- `POST /api/equipment/{id}/return/{quantity}` - Devolver cantidad específica de equipamiento

### ✅ Consultas de Disponibilidad

- `GET /api/equipment/available/{minQuantity}` - Obtener equipamiento disponible con cantidad mínima

## 🛠️ Tecnologías

- Spring Boot
- Spring Data JPA
- H2 Database (en memoria)
- Eureka Client
