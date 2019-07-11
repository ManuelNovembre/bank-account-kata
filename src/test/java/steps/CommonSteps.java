package steps;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import exposition.BankAccountApplication;
import model.BankAccount;
import model.Money;
import model.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import repository.BankAccountSpringDataRepository;
import repository.OperationSpringDataRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountApplication.class)
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class CommonSteps {
    private static final org.slf4j.Logger LOGGER = getLogger(CommonSteps.class);
    @Autowired
    private BankAccountSpringDataRepository bankAccountSpringDataRepository;
    @Autowired
    private OperationSpringDataRepository operationSpringDataRepository;

    @After
    public void tearDown() throws Exception {
        bankAccountSpringDataRepository.deleteAll();
        operationSpringDataRepository.deleteAll();
    }

    @Given("^a bank client \"([^\"]*)\" has (\\d+).(\\d+) in is account$")
    public void a_bank_client_something_has_in_is_account(String clientId, Integer initialAmount, Integer initialCents) throws Throwable {
        Money money = new Money(initialAmount, initialCents);

        BankAccount bankAccount = new BankAccount(clientId, money);

        BankAccount savedBankAccount = bankAccountSpringDataRepository.save(bankAccount);

        Money savedmoney = savedBankAccount.getMoney();
        Integer savedAmount = savedmoney.getEuros();
        Integer savedCents = savedmoney.getCents();

        assertThat(savedBankAccount.getClientId()).isEqualTo(clientId);
        assertThat(savedAmount).isEqualTo(initialAmount);
        assertThat(savedCents).isEqualTo(initialCents);
    }


    @Then("^\"([^\"]*)\" has \\+?(-?\\d+).(\\d+) in his account$")
    public void something_has_in_his_account(String clientId, Integer finalAmount, Integer finalCents) throws Throwable {
        BankAccount savedBankAccount = bankAccountSpringDataRepository.findById(clientId).orElse(null);
        assertThat(savedBankAccount).isNotNull();

        Money savedAccount = savedBankAccount.getMoney();

        assertThat(savedAccount.getEuros()).isEqualTo(finalAmount);
        assertThat(savedAccount.getCents()).isEqualTo(finalCents);
    }

    @Then("the operation is saved in his history$")
    public void theOperationIsSavedInHisHistory(DataTable expectedOperations) throws Throwable {
        List<Map<String, String>> operationList = expectedOperations.asMaps(String.class, String.class);

        Map<String, String> cucumberOperation = operationList.get(0);
        Operation operation = Operation.builder()
                                       .Id(UUID.randomUUID().toString())
                                       .date(Optional.ofNullable(cucumberOperation.get("date")).map(e -> LocalDate.parse(e, DateTimeFormatter.BASIC_ISO_DATE)).orElse(null))
                                       .money(Money.builder()
                            .euros(mapToInteger(cucumberOperation.get("euros")))
                            .cents(mapToInteger(cucumberOperation.get("cent")))
                            .build())
                                       .operationType(cucumberOperation.get("operationType"))
                                       .build();

        List<Operation> savedOperations = operationSpringDataRepository.findAll();
        assertThat(savedOperations).isNotEmpty();

        assertThat(savedOperations.stream().anyMatch(e -> StringUtils.equals(e.getOperationType(), operation.getOperationType()))).isTrue();
    }

    private Integer mapToInteger(String value) {
        return Optional.ofNullable(value)
                .map(Integer::valueOf)
                .orElse(0);
    }
}
