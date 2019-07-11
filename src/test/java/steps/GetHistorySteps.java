package steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import exposition.BankAccountApplication;
import exposition.dto.response.HistoryResponseDTO;
import exposition.dto.response.MoneyResponseDTO;
import exposition.dto.response.OperationResponseDTO;
import model.BankAccount;
import model.Money;
import model.Operation;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import repository.BankAccountSpringDataRepository;
import repository.OperationSpringDataRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountApplication.class)
@ContextConfiguration(classes = { CucumberConfiguration.class })
public class GetHistorySteps {

	private static final org.slf4j.Logger LOGGER = getLogger(GetHistorySteps.class);
	private static final String HISTORY_END_POINT = "/bank/{clientId}/history";
	@Autowired
	private OperationSpringDataRepository operationSpringDataRepository;
	@Autowired
	private BankAccountSpringDataRepository bankAccountSpringDataRepository;
	private String uri;
	@LocalServerPort
	private int port;
	private Response response;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = port;
		uri = "http://localhost:" + port;
	}

	@Given("^a bank client \"([^\"]*)\" has the following operations saved$")
	public void aBankClientHasTheFollowingOperationsSaved(String clientId, DataTable operations) throws Throwable {

		BankAccount bankAccount = bankAccountSpringDataRepository.save(BankAccount.builder().clientId(clientId).build());

		List<Map<String, String>> operationList = operations.asMaps(String.class, String.class);

		List<Operation> operationToSave = new ArrayList<>();

		for (Map<String, String> cucumberOperation : operationList) {
			operationToSave.add(
					Operation.builder()
							 .Id(UUID.randomUUID().toString())
							 .date(LocalDate.parse(cucumberOperation.get("date"), DateTimeFormatter.ISO_DATE))
							 .money(Money.builder()
											.euros(mapToInteger(cucumberOperation.get("euros")))
											.cents(mapToInteger(cucumberOperation.get("cent")))
											.build())
							 .bankAccount(bankAccount)
							 .operationType(cucumberOperation.get("operationType"))
							 .build()
			);
		}

		operationSpringDataRepository.saveAll(operationToSave);
		List<Operation> savedOperations = operationSpringDataRepository.findAll();

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

		response = RestAssured.given()
							  .contentType(MediaType.APPLICATION_JSON_VALUE)
							  .when()
							  .get(uri + HISTORY_END_POINT, clientId);

	}

	@Then("^systems returns the following operations$")
	public void systemsReturnsTheFollowingOperations(DataTable expectedHistoryDTO) throws Throwable {
		List<Map<String, String>> operationList = expectedHistoryDTO.asMaps(String.class, String.class);

		List<OperationResponseDTO> ResultOperation = new ArrayList<>();
		operationList.forEach(cucumberOperation -> {
			ResultOperation.add(
					OperationResponseDTO.builder()
										.date(LocalDate.parse(cucumberOperation.get("date"), DateTimeFormatter.ISO_DATE))
										.money(MoneyResponseDTO.builder()
															   .euros(mapToInteger(cucumberOperation.get("euros")))
															   .cents(mapToInteger(cucumberOperation.get("cent")))
															   .build())
										.operationType(cucumberOperation.get("operationType"))
										.build()
			);

		});
		final HistoryResponseDTO expectedHistoryResponseDTO = HistoryResponseDTO.builder().history(ResultOperation).build();

		final String body = Optional.ofNullable(response.getBody())
									.map(ResponseBody::print)
									.orElse(StringUtils.EMPTY);
		assertThat(body).isNotEmpty();

		HistoryResponseDTO actualHistoryResponseDTO = null;
		try {
			actualHistoryResponseDTO = new ObjectMapper().readValue(body, HistoryResponseDTO.class);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			fail("can't map body response to DTO");
		}
		assertThat(actualHistoryResponseDTO).isEqualTo(expectedHistoryResponseDTO);

	}
}