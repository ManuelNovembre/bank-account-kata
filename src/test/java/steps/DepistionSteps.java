package steps;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import exposition.BankAccountApplication;
import infra.BankAccountJPA;
import infra.MoneyJPA;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import repository.BankAccountSpringDataRepository;

import javax.servlet.ServletResponseWrapper;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountApplication.class)
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class DepistionSteps {
    public static final String DEPOSIT_END_POINT = "/bank/{clientId}/deposit";
    private static final org.slf4j.Logger LOGGER = getLogger(DepistionSteps.class);
    @Autowired
    private BankAccountSpringDataRepository bankAccountSpringDataRepository;

    @LocalServerPort
    private int port;

    private String uri;

    @Before
    public void setUp() {
        RestAssured.port = port;
        uri = "http://localhost:" + port;
    }


    @When("^\"([^\"]*)\" deposits (\\d+).(\\d+)$")
    public void something_deposits_(String clientId, Integer depositsamount, Integer depositscents) throws Throwable {
        Map<String, Integer> body = new HashMap<>();
        body.put("money", depositsamount);
        body.put("cents", depositscents);
        RestAssured.given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri + DEPOSIT_END_POINT, clientId)
                .then()
                .statusCode(200);
    }
}
