package steps;

import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import exposition.BankAccountApplication;
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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountApplication.class)
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class WithdrawSteps {
    public static final String WITHDRAW_END_POINT = "/bank/{clientId}/withdraw";
    private static final org.slf4j.Logger LOGGER = getLogger(WithdrawSteps.class);
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

    @When("^\"([^\"]*)\" withdraws (\\d+).(\\d+)$")
    public void something_withdraws_(String clientId, Integer withdrawamount, Integer withdrawcents) throws Throwable {
        Map<String, Integer> body = new HashMap<>();
        body.put("money", withdrawamount);
        body.put("cents", withdrawcents);
        RestAssured.given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri + WITHDRAW_END_POINT, clientId)
                .then()
                .statusCode(200);

    }
}
