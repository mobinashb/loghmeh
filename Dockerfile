FROM maven AS build
WORKDIR /loghmeh
COPY pom.xml /loghmeh/pom.xml
RUN mvn dependency:go-offline -B
COPY src/ /loghmeh/src/
RUN mvn package
VOLUME /loghmeh
FROM tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /loghmeh/target/loghmeh-1.0.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080