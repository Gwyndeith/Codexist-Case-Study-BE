FROM openjdk:22
VOLUME /tmp
EXPOSE 8070
ARG JAR_FILE=target/codexist-case-study-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} codexist-case-study.jar
ENTRYPOINT ["java","-jar","/codexist-case-study.jar"]