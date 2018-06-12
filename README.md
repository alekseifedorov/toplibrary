This is a  program in Java which counts top javascript libraries used in web pages found on Google.

1. How to build
    Type 'mvn clean install' to build toplibrary-1.0.jar.

2. How to run
 - Type 'mvn exec:java -Dexec.mainClass="assignment.toplibrary.TopLibrary" ' to run from maven.
 - Execute run.bat or type 'java -classpath target/toplibrary-1.0.jar assignment.toplibrary.TopLibrary'.


3. In real application, the following libraries and frameworks should be introduced:
 - Spring DI
 - Spring Boot to ease building, deploy, logging etc.
 - A task execution framework e.g. Spring TaskExecutor or Quarz, instead of the default JDK executors.
 - A web crawler, e.g. jsoup, to download and parse the html content.
 - A logging framework
 - More tests
 - etc 

4. The output format is following:
  <.js filename> <counter>