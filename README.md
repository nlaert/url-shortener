![Java CI with Gradle](https://github.com/nlaert/url-shortener/workflows/Java%20CI%20with%20Gradle/badge.svg)

## This is the Runtime Revolution code challenge.

The goal is to do a URL shortener like http://bitly.com/ but with a very simple Frontend
that only needs a form to enter the URL to short and receive the new URL.

### To build and run the project:

Open a terminal in the project root and run the following commands:
* `gradlew build` for build
* `gradlew bootJar` for executing the project

The application will be listening in <http://localhost:8080> by default

### Running tests:

Open a terminal in the project root and run the following command:
* `gradlew test`

### Checking the documentation:

Open a terminal in the project root and run the following commands:
* `gradlew javadoc` for generating the javadoc. It can be found in `build\docs\javadoc\index.html`

API Documentation can be found in <http://localhost:8080/swagger-ui.html>

