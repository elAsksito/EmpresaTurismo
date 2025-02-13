# Empresa Turismo - Documentación

## Descripción
Este proyecto es una aplicación de reservas turísticas que expone servicios a través de REST y SOAP. Implementa autenticación, gestión de destinos turísticos, reservas y usuarios.

## Tecnologías Utilizadas
- **Backend:** Java con Spring Boot
- **Seguridad:** JWT para autenticación y autorización
- **Protocolos:** REST y SOAP
- **Base de Datos:** MySQL
- **Contenerización:** Docker

## Configuración de Puertos
- **Aplicación REST:** `http://localhost:9999`
- **Servicios SOAP:** `http://localhost:9001/ws/`
  - Autenticación: `http://localhost:9001/ws/auth`
  - Usuarios: `http://localhost:9001/ws/usuario`
  - Destinos Turísticos: `http://localhost:9001/ws/destino`
  - Reservas: `http://localhost:9001/ws/reserva`

## Endpoints REST
### Autenticación (`/auth`)
- **POST** `/auth/register` → Registra un nuevo usuario.
- **POST** `/auth/login` → Inicia sesión y devuelve un token JWT.

### Usuarios (`/usuarios`)
- **GET** `/usuarios/{id}` → Obtiene un usuario por ID.
- **GET** `/usuarios/email/{email}` → Obtiene un usuario por email.

### Destinos Turísticos (`/destinos`)
- **POST** `/destinos` → Crea un nuevo destino turístico (**Admin**).
- **GET** `/destinos` → Lista todos los destinos turísticos.
- **GET** `/destinos/{id}` → Obtiene un destino por ID.

### Reservas (`/reservas`)
- **POST** `/reservas` → Crea una reserva.
- **GET** `/reservas/usuario/{usuarioId}` → Lista reservas por usuario.
- **GET** `/reservas/{id}` → Obtiene una reserva por ID.
- **PUT** `/reservas/{id}` → Actualiza el estado de una reserva (**Admin**).

## Despliegue con Docker
Ejecutar el siguiente comando para construir y desplegar el proyecto:

```sh
docker-compose up --build
```

## Configuración Adicional
En la carpeta `configuration` se encuentran los archivos de preferencias para **Postman** y **SoapUI**, los cuales pueden ser importados para facilitar las pruebas de los servicios REST y SOAP.