package tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/main/resources/features",     // the path for the feature files
        glue = "steps",                             //the path of the step definition files
        monochrome = true,                          //display the console in the proper readable format
        plugin = {"pretty", "junit:target/JUnitReports/report.xml", "json: target/JSONReports/report.json"},
        tags = "@smoke"

)
public class RunnerSendEmails {

}
