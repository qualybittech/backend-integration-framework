# Backend-integration-framework

Project to test the backend Rest services using rest assured and other libraries.

**Pre-requisites: **
1) Java 8
2) Maven
3) Intellj or Eclipse or any other IDE of your choice

**Steps:**
1) Now run the below command to execute the automated scripts
   mvn clean test "-Denv=qa" "-Dcucumber.options=@smoke" --info
2) HTML report would be generated in below folder
   ..\backend-integration-framework\build\Logs\test-output\
   
   Also we to run with other environment please change the parameter -Denv=dev
   
**Note:**
** To run the scripts from IDE open the "RunCucumberTest.java" file from ..src\test\java\org\backendintegrationframework
