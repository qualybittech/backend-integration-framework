package org.backendintegrationframework;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {
        "junit:build/Logs/CCPServices/test-output/Cucumber.xml"},
        monochrome = true,
        glue = {"org.backendintegrationframework.stepdefenitions"},
        features = {"src/test/resources/features"},
        tags = ("@smoke"))
public class RunCucumberTest {

}
