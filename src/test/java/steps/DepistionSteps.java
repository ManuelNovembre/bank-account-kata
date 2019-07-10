package steps;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import exposition.BankAccountApplication;
import infra.BankAccountJPA;
import infra.MoneyJPA;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountApplication.class)
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class DepistionSteps {
    public static final String DEPOSIT_END_POINT = "/bank/{clientId}/deposit";
    private static final org.slf4j.Logger LOGGER = getLogger(DepistionSteps.class);
    @Autowired
    private BankAccountSpringDataRepository bankAccountSpringDataRepository;

    @LocalServerPort
    private int port;

    private Response response;
    private String uri;

    @Before
    public void setUp() {
        RestAssured.port = port;
        uri = "http://localhost:" + port;
    }

    @Given("^a bank client \"([^\"]*)\" has (.+).(.+) in is account$")
    public void a_bank_client_something_has_in_is_account(String clientId, Integer initialamount, Integer initialcents) throws Throwable {
        MoneyJPA money = new MoneyJPA(initialamount, initialcents);

        BankAccountJPA bankAccount = new BankAccountJPA(clientId, money);

        BankAccountJPA savedBankAccount = bankAccountSpringDataRepository.save(bankAccount);

        MoneyJPA savedmoney = savedBankAccount.getMoney();
        Integer savedAmount = savedmoney.getAmount();
        Integer savedCents = savedmoney.getCents();

        assertThat(savedBankAccount.getClientId()).isEqualTo(clientId);
        assertThat(savedAmount).isEqualTo(initialamount);
        assertThat(savedCents).isEqualTo(initialcents);
    }

    @When("^\"([^\"]*)\" deposits (.+).(.+)$")
    public void something_deposits_(String clientId, Integer depositsamount, Integer depositscents) throws Throwable {

        Map<String, Integer> body = new HashMap<>();
        body.put("money", depositsamount);
        body.put("cents", depositscents);
        response = RestAssured.given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(uri + DEPOSIT_END_POINT, clientId);

    }

    @Then("^\"([^\"]*)\" has (.+).(.+) in his account$")
    public void something_has_in_his_account(String clientId, Integer finalamount, Integer finalcents) throws Throwable {
        BankAccountJPA savedBankAccount = bankAccountSpringDataRepository.findById(clientId).orElse(null);
        assertThat(savedBankAccount).isNotNull();

        MoneyJPA savedAccount = savedBankAccount.getMoney();

        assertThat(savedAccount.getAmount()).isEqualTo(finalamount);
        assertThat(savedAccount.getCents()).isEqualTo(finalcents);
    }

}
