FROM tomcat:9.0

COPY ./target/ali.malikeh.war /usr/local/tomcat/webapps/ROOT.war
