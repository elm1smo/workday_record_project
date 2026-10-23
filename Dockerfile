FROM eclipse-temurin:17.0.20.1_1-jre-alpine-3.24

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiando el archivo .jar generado al contenedor
COPY target/db_horarios_v2-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
