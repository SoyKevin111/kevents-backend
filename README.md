¡Bienvenido a la evaluación técnica de **Desarrollador Fullstack**! para SASF
Este ejercicio tiene como objetivo conocer tus habilidades en desarrollo backend, usando buenas prácticas de ingeniería de software.

## Parte 1 – Backend (Java + Spring Boot)  

Construir una API RESTful bajo el contexto `/kevents` que permita gestionar:

- Usuarios  
- Eventos  
- Reservas  

### Requisitos funcionales  

#### 1. Autenticación y autorización
- Registro y login con JWT  
- Roles: `ADMIN`, `ORGANIZER`, `ATTENDEE`  
- Solo `ADMIN` puede crear usuarios con rol ORGANIZER/ADMIN  
- Organizadores (`ORGANIZER`) pueden crear y administrar sus propios eventos  
- Asistentes (`ATTENDEE`) pueden reservar plazas en eventos y ver sus reservas  

#### 2. Endpoints requeridos (prefijo `/kevents`)  

##### Usuarios (ADMIN)
- `POST /kevents/users`: Crear usuario (ADMIN)  
- `GET /kevents/users`: Listar usuarios (ADMIN)  
- `GET /kevents/users/{id}`: Ver usuario (ADMIN o el propio usuario)  

##### Eventos
- `POST /kevents/events`: Crear evento (ORGANIZER)  
- `GET /kevents/events`: Listar eventos (público; con filtros: fecha, localización, organizador)  
- `GET /kevents/events/{id}`: Detalle de evento  
- `PUT /kevents/events/{id}`: Editar evento (solo owner ORGANIZER o ADMIN)  
- `DELETE /kevents/events/{id}`: Eliminar evento (solo owner ORGANIZER o ADMIN)  

##### Reservas
- `POST /kevents/reservations`: Crear reserva (ATTENDEE, asociada a evento y usuario)  
- `GET /kevents/reservations`: Listar reservas del usuario autenticado (ATTENDEE)  
- `GET /kevents/reservations/event/{eventId}`: Reservas de un evento (ORGANIZER o ADMIN)  
- `PUT /kevents/reservations/{id}`: Actualizar reserva (ej: cambiar estado o número de asistentes)  
- `DELETE /kevents/reservations/{id}`: Cancelar reserva (usuario o ADMIN)  

---

### Entidades mínimas  

#### `User`
- id  
- username  
- email  
- password (encriptada)  
- role (ATTENDEE, ORGANIZER, ADMIN)  

#### `Event`
- id  
- title  
- description  
- location  
- capacity  
- startDateTime  
- endDateTime  
- createdAt  
- organizer (User)  

#### `Reservation`
- id  
- event (Event)  
- attendee (User)  
- seats (int)  
- status (PENDING, CONFIRMED, CANCELLED)  
- createdAt  

---

### Requisitos técnicos  

- Spring Boot 3  
- Spring Data JPA (H2 para pruebas o PostgreSQL en producción)  
- Spring Security con JWT  
- Validaciones con `javax.validation`  
- Arquitectura en capas (`controller`, `service`, `repository`, `dto`, `mapper`)  
- Swagger u OpenAPI para documentación (ej: `/swagger-ui.html`)  
- Manejo de errores global con `@ControllerAdvice`  