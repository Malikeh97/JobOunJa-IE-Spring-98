docker run --rm -it -v $(pwd):/project -v ~/.m2:/root/.m2 -w /project maven mvn package
docker run -it -v $(pwd)/target/ali.malikeh.war:/usr/local/tomcat/webapps/ROOT.war \
-v $(pwd)/target/ali.malikeh:/usr/local/tomcat/webapps/ROOT tomcat:9.0