# BuenasLecturas
Aplicación web desarrollada con Spring Boot. La idea es gestionar una biblioteca virtual para dar seguimiento a tus lecturas y gestionar reseñas.

Requisitos
Java 17 o superior
Maven

Pasos para correr la applicacion

Clonar el repositorio
Ejecutar el proyecto (Asegurarse de tener configurado Maven correctamente).

Ejecutar la aplicación desde tu IDE:

Una vez que se levante el servidor, abrir en el navegador:

http://localhost:8080/login

Desde allí podés:

Registrarte: /register

Iniciar sesión: /login

Empezar a utilizar la applicacion

Base de datos

En el application properties, pegar estos datos:

spring.application.name=buenasLecturas
spring.datasource.url=jdbc:h2:~/buenasLecturas
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true

Notas

Las contraseñas se almacenan de forma segura con BCrypt.

La búsqueda de libros se realiza usando la API pública de Open Library.
