
# Documentación del Proyecto: Gestión de Usuarios

## Especificaciones Técnicas del Proyecto

- **Java**: 17
- **Spring Boot**: 3.3.0
- **Maven**: 3.0
- **Base de Datos**: PostgreSQL 15

## Prerrequisitos

- **Maven**: 3.0
- **JDK**: 17

## Documentación en Swagger

La documentación de la API se encuentra en la siguiente ruta:

(http://localhost:8080/swagger-ui/index.html#/)

## Editor de Texto

Puedes utilizar **Visual Studio Code** o tu IDE preferido.

## Configuración de Maven

### Clonación del Repositorio

Clona el repositorio ejecutando el siguiente comando:

```bash
git clone https://github.com/sebassuaza98/backend_api_usuarios.git
```

Una vez clonado el repositorio, abre tu IDE de preferencia, carga el proyecto y en la raíz del proyecto ejecuta el siguiente comando para construirlo:

```bash
mvn clean install
```

## Descripción del Proyecto

Este proyecto es una plataforma de **Gestión de Usuarios** desarrollada en Java, que permite a los usuarios con el rol de **Administrador** gestionar usuarios. Los administradores pueden crear usuarios con roles como **Administrador** o **Ciudadano**, además de editar su estado, datos personales o cambiarles el rol.

### Características principales:
- Registro e inicio de sesión de usuarios.
- Visualización de usuarios.
- Actualización de usuarios.

## Tecnologías Usadas

- **Backend**: Java 17, Spring Boot (Spring Data JPA, Spring Security, Spring Web).
- **Base de Datos**: PostgreSQL para el almacenamiento de datos persistentes.
- **Frontend**: Angular para la interfaz de usuario.

## Arquitectura del Sistema

Este proyecto sigue el patrón de arquitectura de capas utilizando el patrón **MVC** (Modelo-Vista-Controlador). La comunicación entre las capas de la aplicación se realiza de la siguiente manera:

1. **Controladores**: Manejan las solicitudes HTTP y responden a las solicitudes del usuario.
2. **Servicios**: Contienen la lógica de negocio.
3. **Repositorios**: Interactúan con la base de datos utilizando JPA.

## Modelo de Base de Datos

### UserModel (Entidad: users)

La clase `UserModel` representa a un usuario en el sistema, almacenando su información personal y de contacto. Los atributos clave son:

- `userId`: Identificador único del usuario.
- `name`: Nombre del usuario.
- `lastName`: Apellido del usuario.
- `password`: Contraseña del usuario.
- `phone`: Número de teléfono del usuario (máximo 15 caracteres).
- `address`: Dirección física del usuario.
- `email`: Correo electrónico del usuario.
- `date`: Fecha de creación del usuario.
- `state`: Estado de la cuenta (activo o inactivo).
- `role`: Rol del usuario (por ejemplo, "Administrador", "Ciudadano").

### PasswordResetToken (Entidad: password_reset_token)

La clase `PasswordResetToken` maneja los tokens de restablecimiento de contraseñas asociados a los usuarios. Los atributos clave son:

- `id`: Identificador único del token.
- `token`: Cadena única que representa el token de restablecimiento.
- `user`: Relación con el usuario correspondiente, utilizando una clave foránea (user_id).
- `expiryDate`: Fecha y hora en que expira el token.
- `used`: Estado que indica si el token ha sido utilizado. Por defecto es **false**.

## Manual de Instalación y Configuración

### Requisitos previos

- **Java**: 17
- **Maven**: Para la construcción del proyecto.

### Configuración del entorno

1. Clona el repositorio del proyecto:

    ```bash
    git clone https://github.com/sebassuaza98/backend_api_usuarios.git
    ```

2. Instala las dependencias con Maven:

    ```bash
    mvn clean install
    ```

3. Configura la base de datos en el archivo `application.properties`:

    ```properties
    spring.application.name=login
    spring.datasource.url=jdbc:postgresql://34.42.245.106:5432/apibd
    spring.datasource.username=postgres
    spring.datasource.password=12345
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    ```

4. Ejecución de la aplicación:

    ```bash
    mvn spring-boot:run
    ```

