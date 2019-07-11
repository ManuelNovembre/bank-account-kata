package steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import exposition.BankAccountApplication;
import infra.BankAccountJPA;
import infra.MoneyJPA;
import infra.OperationJPA;
import io.restassured.RestAssured;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import repository.BankAccountSpringDataRepository;
import repository.OperationSpringDataRepository;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountApplication.class)
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class GetHistorySteps {

    @Autowired
    private OperationSpringDataRepository operationSpringDataRepository;
    @Autowired
    private BankAccountSpringDataRepository bankAccountSpringDataRepository;
    private static final org.slf4j.Logger LOGGER = getLogger(GetHistorySteps.class);
    private String uri;
    private static final String HISTORY_END_POINT = "/bank/{clientId}/history";
    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
        uri = "http://localhost:" + port;
    }

    @Given("^a bank client \"([^\"]*)\" has the following operations saved$")
    public void aBankClientHasTheFollowingOperationsSaved(String clientId, DataTable operations) throws Throwable {

        BankAccountJPA bankAccountJPA = bankAccountSpringDataRepository.save(BankAccountJPA.builder().clientId(clientId).build());


        List<Map<String, String>> operationList = operations.asMaps(String.class, String.class);

        List<OperationJPA> operationToSave = new ArrayList<>();

        for (Map<String, String> cucumberOperation : operationList) {
            operationToSave.add(
                    OperationJPA.builder()
                            .Id(UUID.randomUUID().toString())
                            .date(LocalDate.parse(cucumberOperation.get("date"), DateTimeFormatter.ISO_DATE))
                            .moneyJPA(MoneyJPA.builder()
                                    .euros(mapToInteger(cucumberOperation.get("euros")))
                                    .cents(mapToInteger(cucumberOperation.get("cent")))
                                    .build())
                            .bankAccount(bankAccountJPA)
                            .operationType(cucumberOperation.get("operationType"))
                            .build()
            );
        }

        operationSpringDataRepository.saveAll(operationToSave);
        List<OperationJPA> savedOperations = operationSpringDataRepository.findAll();

        assertThat(savedOperations.size()).isEqualTo(savedOperations.size());
    }

    private Integer mapToInteger(String value) {
        return Optional.ofNullable(value)
                .map(Integer::valueOf)
                .orElse(0);
    }

    @When("^\"([^\"]*)\" check his history$")
    public void checkHisHistory(String clientId) throws Throwable {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(uri + HISTORY_END_POINT, clientId)
                .then()
                .statusCode(200);
    }


    @Then("^systems returns the following operations$")
    public void systemsReturnsTheFollowingOperations(DataTable expectedHistoryDTO) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
