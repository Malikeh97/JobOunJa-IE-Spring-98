FROM maven as maven
COPY ./pom.xml ./pom.xml
# fetch all dependencies
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package

FROM tomcat:9.0
ENV test host.docker.internal
COPY --from=maven /target/ali.malikeh.war /usr/local/tomcat/webapps/ROOT.war
COPY --from=maven /target/ali.malikeh /usr/local/tomcat/webapps/ROOT