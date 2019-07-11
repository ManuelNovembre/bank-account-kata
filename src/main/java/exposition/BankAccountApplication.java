package exposition;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"repository", "model", "exposition", "application", "steps"})
@EnableJpaRepositories(basePackages = {"repository"})
@EntityScan(basePackages = { "model" })
public class BankAccountApplication extends SpringBootServletInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BankAccountApplication.class);
    }

    public static void main(String[] args) {
        LOGGER.debug("Starting Spring application main...");
        SpringApplication.run(BankAccountApplication.class, args);
    }
}
