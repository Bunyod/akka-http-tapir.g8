FROM 'hseeberger/scala-sbt:11.0.3_1.2.8_2.12.9' as compile-step
LABEL image=build-env
#RUN sbt scalastyle test coverageReport coverageOff seed/assembly
RUN sbt compile test:compile tapir/assembly
FROM openjdk:11.0.4-slim as tapir-prod
RUN mkdir -p /opt/scala-service
COPY --from=build-env /root/tapir/target/scala-2.12/seed-assembly-0.1.jar /opt/scala-service/tapir-assembly-0.1.jar
CMD ["java","-jar", "-Dconfig.resource=prod/application.conf", "/opt/scala-service/tapir-assembly-0.1.jar"]
