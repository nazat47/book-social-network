# build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY /book-network/pom.xml .
RUN mvn dependency:go-offline
COPY /book-network/src ./src
RUN mvn clean package -DskipTests

# runtime stage
FROM amazoncorretto:17
#argument for passing values through env
ARG PROFILE=dev
ARG APP_VERSION=1.0.1
WORKDIR /app
COPY --from=build /build/target/book-network-*.jar /app/
EXPOSE 9000
ENV DB_URL=jdbc:postgresql://postgres_sql_bsn:5432/book_social_network
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar
