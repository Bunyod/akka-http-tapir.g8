FROM 'hseeberger/scala-sbt:11.0.3_1.2.8_2.12.9' as compile-step
LABEL image=build-env
#RUN sbt scalastyle test coverageReport coverageOff seed/assembly
RUN sbt compile test:compile seed/assembly
FROM openjdk:8u181-slim as seed-prod
RUN mkdir -p /opt/scala-service
COPY --from=build-env /root/seed/target/scala-2.12/seed-assembly-0.1.jar /opt/scala-service/seed-assembly-0.1.jar
CMD ["java","-jar", "-Dconfig.resource=prod/application.conf", "/opt/scala-service/seed-assembly-0.1.jar"]
