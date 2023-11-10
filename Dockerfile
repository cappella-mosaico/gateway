FROM openjdk:11
EXPOSE 9090:9090
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/gateway.jar
ENTRYPOINT ["java","-jar","/app/gateway.jar"]
