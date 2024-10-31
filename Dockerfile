FROM eclipse-temurin:17-jdk

COPY target/Intelligent-Ecommerce-Application-0.0.1-SNAPSHOT.jar /app.jar
ARG OPENAI_API_KEY
ENV OPENAI_API_KEY=${OPENAI_API_KEY}
EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]

