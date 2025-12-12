# Sistema de Gestión de Bibliotecas (Backend)

Proyecto desarrollado para el curso de Desarrollo de los Componentes del Negocio. Este microservicio RESTful permite la gestión de libros, usuarios y préstamos de una biblioteca universitaria, asegurando los endpoints mediante JWT (JSON Web Tokens) y control de Roles.

## 📋 Tecnologías Utilizadas
* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.4.0
* **Base de Datos:** MySQL
* **Seguridad:** Spring Security + JWT
* **Persistencia:** Spring Data JPA / Hibernate
* **Herramientas:** Maven, Lombok, Postman

## ⚙️ Pre-requisitos
1.  Tener instalado **Java JDK 21**.
2.  Tener instalado **MySQL** y el servicio en ejecución.
3.  Tener un cliente API como **Postman**.

## 🚀 Guía de Instalación y Ejecución

### 1. Base de Datos (OBLIGATORIO)
El sistema crea la base de datos `bd_biblioteca` automáticamente al iniciar. Sin embargo, **es necesario insertar los roles manualmente** la primera vez para que el sistema funcione.

Ejecuta este script en tu gestor de base de datos (MySQL Workbench):

```sql
USE bd_biblioteca;

-- Insertar roles base del sistema
INSERT INTO rol (nombre) VALUES ('ADMIN');
INSERT INTO rol (nombre) VALUES ('USUARIO');

-- Verificar creación
SELECT * FROM rol;

```
### 2. Configuración (application.properties)
Asegúrate de que tu archivo src/main/resources/application.properties tenga esta configuración (ajusta tu usuario y contraseña):

spring.application.name=ms-biblioteca
server.port=9596

# Conexión a Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/bd_biblioteca?createDatabaseIfNotExist=true&serverTimezone=America/Lima
spring.datasource.username=root
spring.datasource.password=admin  <-- CAMBIA ESTO POR TU CONTRASEÑA

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Configuración JWT (Seguridad)
jwt.secret=mi_clave_super_secreta_123456789
jwt.expiration=3600000

## 3. Ejecución
   Desde IntelliJ IDEA: Busca la clase MsBibliotecaApplication.java y presiona el botón Run (▶).

Verificación: La consola debe mostrar Started MsBibliotecaApplication in... y el servidor estará activo en el puerto 9596.

🔒 Endpoints Principales (Pruebas con Postman)

### A. Autenticación (Público)
Registrar Usuario: POST http://localhost:9596/auth/register

    ```json
    { 
        "nombre": "Juan",
        "email": "juan@gmail.com",
        "password": "123"
    }
    ``

Login: POST http://localhost:9596/auth/login

    ```json 
    { 
        "email": "juan@gmail.com", 
        "password": "123" 
    }

Respuesta: Devuelve un { "token": "eyJhbGciOi..." }.

### B. Préstamos (Protegido - Requiere Token)
Ver Mis Préstamos: GET http://localhost:9596/prestamos/mis-prestamos?idUsuario=1

Auth: Seleccionar Bearer Token y pegar el token obtenido en el Login.

### C. Gestión Administrativa (Requiere Token de Rol ADMIN)
Para probar estos endpoints, el usuario autenticado debe tener el Rol 'ADMIN' en la base de datos.

* **Registrar un Libro:** `POST http://localhost:9596/libros`
    * *Auth:* Bearer Token (Admin)
    * *Body (JSON):*
    ```json
    {
        "titulo": "Programación en Java",
        "autor": "James Gosling",
        "categoria": "Tecnología",
        "codigo": "LIB-001",
        "stockTotal": 10,
        "stockDisponible": 10
    }
    ``

* **Registrar un Préstamo:** `POST http://localhost:9596/prestamos`
    * *Descripción:* Valida stock disponible y registra la transacción.
    * *Auth:* Bearer Token (Admin)
    * *Body (JSON):*
    ```json
    {
        "idUsuario": 1,
        "idLibro": 1,
        "fechaPrestamo": "2025-12-11",
        "fechaDevolucion": "2025-12-20"
    }
    ``
### 4. Devolución de Libros (Recuperar Stock)
Finaliza el ciclo de préstamo. El sistema valida el ID del préstamo, cambia el estado a `devuelto: true`, registra la fecha de devolución y **aumenta automáticamente el stock** del libro en 1.

* **Método:** `PUT`
* **URL:** `/prestamos/{id}/devolver`
* **Requiere Autenticación:** SÍ (Token Bearer en Header)
* **Body (Cuerpo):** No requiere (Se envía vacío).

**Ejemplo de Respuesta Exitosa (200 OK):**
```json
{
  "id": 1,
  "fechaPrestamo": "2025-12-11",
  "fechaDevolucion": "2025-12-15",
  "devuelto": true,
  "usuario": {
    "id": 1,
    "email": "alumno@idat.pe"
  },
  "libro": {
    "id": 1,
    "titulo": "Programación en Java",
    "stockDisponible": 10
  }
}

👥 Integrantes del Equipo:

[Rondón González Jhonny Jesús]

[Suyón Lascano, Pablo Martin]

[Pulache Arévalo, Erick Omar]

[Godoy Palacios, Antonio Joaquín]

[Ponce Huamani, Ronaldo]