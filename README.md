# Backend-integration-framework

Project to test the backend Rest services using rest assured and other libraries.

Pre-requisites: 
1) Java 8
2) Chrome Browser
3) Maven
4) Intellj or any other IDE

Steps:
1) Check for the Chrome browser version
2) Download the respective driver from below link
   https://chromedriver.chromium.org/downloads
    Ex: For Windows download chromedriver_win32.zip 
3) Extract and place the driver in below path
   ..\backend-integration-framework\drivers
4) Now run the below command to execute the automated scripts
   mvn clean test "-Denv=qa" "-Dcucumber.options=@smoke" --info
5) HTML report would be generated in below folder
   ..\backend-integration-framework\build\Logs\test-output\S
   
Note:
** To run the scripts from IDE open the "RunCucumberTest.java" file from ..src\test\java\org\backendintegrationframework
