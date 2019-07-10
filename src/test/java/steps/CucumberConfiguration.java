package steps;

import exposition.controller.BankController;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@ComponentScan(basePackages = {"repository", "infra", "exposition", "command"})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"repository"})
@EntityScan(basePackages = {"infra"})
public class CucumberConfiguration {
}
