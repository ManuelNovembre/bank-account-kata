package steps;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = "classpath:features/",
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-reports/cucumber.json"},
        glue = "steps")
public class CucumberTest {
}
