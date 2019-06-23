# we are extending everything from tomcat:8.0 image ...
FROM tomcat:8.0
MAINTAINER nikhilketagani
# COPY path-to-your-application-war path-to-webapps-in-docker-tomcat
COPY project-1.0-RAMA.war /usr/local/tomcat/webapps/
