package steps;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		strict = true,
		features = "classpath:features/",
		//        tags = {"@MN"},
		plugin = { "pretty", "html:target/cucumber", "json:target/cucumber-reports/cucumber.json" },
		glue = "steps")
public class CucumberTestRunner {
}
