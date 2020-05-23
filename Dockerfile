FROM maven AS build
WORKDIR /loghmeh
COPY . .
RUN mvn package
FROM tomcat
COPY --from=build /loghmeh/target/loghmeh-1.0.war ./webapps/ROOT.war
CMD ["bin/catalina.sh", "run"]
EXPOSE 80