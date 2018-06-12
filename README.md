This is a program in Java which counts top javascript libraries used in web pages found on Google.

1. How to build
    Type 'mvn clean org.apache.maven.plugins:maven-dependency-plugin:2.7:copy-dependencies install' to build toplibrary-1.0.jar and its dependencies


2. How to run
 - Type 'mvn exec:java -Dexec.mainClass="assignment.toplibrary.TopLibrary" ' to run from maven.
 - Execute run.bat.


3. In real application, the following parts should be reconsidered:
 - A task execution framework should be used instead of the default JDK executors, e.g. Spring TaskExecutor or Quarz.
 - A web crawler, e.g. jsoup, should be used to download and parse html pages.