FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon --stacktrace

FROM openjdk:11
EXPOSE 9090:9090
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/gateway.jar
ENTRYPOINT ["java","-jar","/app/gateway.jar"]
