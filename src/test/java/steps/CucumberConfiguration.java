package steps;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@ComponentScan(basePackages = {"repository", "model", "exposition", "application", "steps"})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"repository"})
@EntityScan(basePackages = { "model" })
public class CucumberConfiguration {
}
