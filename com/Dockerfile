# Imagen base de Java
FROM eclipse-temurin:17-jdk-alpine

# Crea un directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/com-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]