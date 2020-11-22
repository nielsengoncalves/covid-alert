# Corona API

## Requirements
To run this project it's required to have `Java 15` installed.


## Building the application
To build the application and generating the jar file, just run:

```
./gradlew build
```

## Running the application
After building the application just run the following command:
```
java -jar build/libs/covid-alert-0.0.1-SNAPSHOT.jar
```

## Running the tests
To run the tests, just use the following command:

```
./gradlew test
```

## Features
The application exposes the following API `/api/{countryCode}/{date}`

Example:
```
curl -i -XGET localhost:8080/api/BR/2020-11-10                                                                                                                     17:35:14
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 22 Nov 2020 20:35:58 GMT

{"countryName":"Brazil","alertLevel":"YELLOW"}
```

## Tools used
- [Java 15](https://openjdk.java.net/projects/jdk/15/)
- [Gradle](https://gradle.org/)
- [Spring](https://spring.io/)
- [Junit 5](https://junit.org/junit5/)
- [RestAssured](https://rest-assured.io/)
- [Mock-Server](https://www.mock-server.com/)
- [Lombok](https://projectlombok.org/)
- [Retrofit](https://square.github.io/retrofit/)