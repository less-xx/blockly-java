FROM openjdk:24-jdk-slim

COPY pipeline-executor-MAVEN_VAR_project.version_END.jar /pipeline-executor/launcher.jar
COPY scripts/docker-entrypoint.sh /

RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]