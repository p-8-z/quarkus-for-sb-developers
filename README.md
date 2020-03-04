# Quarkus project

## Project was generated with commands:

```powershell
mvn io.quarkus:quarkus-maven-plugin:1.2.1.Final:create "-DprojectGroupId=sk.p8z" "-DprojectArtifactId=quarkus" "-DclassName=sk.p8z.quarkus.controllers.GreetingController" "-Dpath=/greeting"  "-Dextensions=spring-web,spring-di,spring-data-jpa,resteasy-jsonb,quarkus-jdbc-postgresql,hibernate-validator,quarkus-resteasy-jackson,quarkus-jackson"
cd quarkus
```
## Initialize Postgres Database

```powershell
cd database
docker-compose up -d
```

Open browser an go to [pgadmin](http://www.localhost:80)

Login with user: user@domain.com and password: SuperSecret

### Create DB Server
Object > Create > Server - this should open modal window for DB Connection

Go to `Connection` Tab
```
Host name/address = db
Maintenance database = docker
Username = admin
Password = admin
```

And Save.

### OpenApi support

Added Dependencies for OpenApi Annotations

```xml
<dependencies>
    ...
    <dependency>
      <groupId>io.swagger</groupId>
      <artifactId>swagger-annotations</artifactId>
      <version>${swagger-core-version}</version>
    </dependency>
    <dependency>
      <groupId>org.openapitools</groupId>
      <artifactId>jackson-databind-nullable</artifactId>
      <version>0.1.0</version>
    </dependency>
</dependencies>
```

Copied `openapi.yaml` definition to resource folder.

Added `openapi-generator-maven-plugin` to `pom.xml` `build > plugins`

## Description

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application is packageable using `./mvnw package`.
It produces the executable `quarkus-1.0-SNAPSHOT-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/quarkus-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .