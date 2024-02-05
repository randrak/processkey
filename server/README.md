# server
A dockerized Java standalone application with Maven build

[![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Java Development Kit 1.8](https://img.shields.io/badge/JDK-1.8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## What is this?
This project shows how to automate the creation of a Docker image with a Java command line application using Maven.

- Creates a docker image that runs a Java [main method](src/main/java/br/processkey/server/Server.java) on start of the container and terminates after the program is finished
  - Example application that writes the environment variables from the Docker container to a test file.
  - Also outputs some log statements
- Uses the fabric8 [maven-docker-plugin](https://github.com/fabric8io/docker-maven-plugin)
  - Build the image
  - Start a container for an [integration test](src/test/java/br/processkey/server/SimpleDataProcessorTest.java)
  - Has a plain [Dockerfile](src/main/docker/Dockerfile) to configure the image
  - Uses the [maven-dependency-plugin](https://maven.apache.org/plugins/maven-dependency-plugin/) to collect the JARs needed for the application
  - Uses an [assembly.xml](src/main/assembly.xml) file to copy all files to the image 
- Uses [logback](https://logback.qos.ch/) for logging (pending)
  - Utilizing 'logback.xml' to automatically configure the log level for multiple container runs. 
  - Log file is created in the mounted directory of the host.

## Build
Checkout the project and build it using the [Takari Maven Wrapper](https://github.com/takari/maven-wrapper).

Inside the root directory of the project type the following:

```
./mvnw clean verify
```

If this was successful you should see something like:

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

Verify that a Docker image was created:

```
docker images
```

The new image should be listed with two tags:

```
REPOSITORY                        TAG
processkey/server     0.1.0-SNAPSHOT
processkey/server     latest
```

Now run the application by typing:

```
docker run \
--volume "$(pwd)"/data:/usr/src/server/data \
--user $UID:$UID \
--env my_var=abc123 \
processkey/server
```

After running the container there should be three files in the [data](data) dircetory of the project:

```
hello-world.log
hello-world.txt
logback.xml
```
