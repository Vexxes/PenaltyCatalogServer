FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8088:8088
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/de.vexxes.penaltycatalogserver-0.0.1.jar /app/penaltycatalogserver.jar
#COPY --from=build ./src/build/libs/*.jar ./app/penaltycatalogserver.jar

ENTRYPOINT ["java","-jar","/app/penaltycatalogserver.jar"]