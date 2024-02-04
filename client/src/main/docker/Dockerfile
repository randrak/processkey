FROM openjdk:8-jre
MAINTAINER processkey-client
COPY maven /usr/src/${appname}
VOLUME ["/usr/src/${appname}/data"]
WORKDIR /usr/src/${appname}
CMD ["java","-cp","${project.build.finalName}.${project.packaging}:lib/*","br.processkey.client.DirectoryMonitorClient"]
