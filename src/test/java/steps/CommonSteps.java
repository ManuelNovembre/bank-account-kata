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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountApplication.class)
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class CommonSteps {
    private static final org.slf4j.Logger LOGGER = getLogger(CommonSteps.class);
    @Autowired
    private BankAccountSpringDataRepository bankAccountSpringDataRepository;


    @Given("^a bank client \"([^\"]*)\" has (\\d+).(\\d+) in is account$")
    public void a_bank_client_something_has_in_is_account(String clientId, Integer initialAmount, Integer initialCents) throws Throwable {
        MoneyJPA money = new MoneyJPA(initialAmount, initialCents);

        BankAccountJPA bankAccount = new BankAccountJPA(clientId, money);

        BankAccountJPA savedBankAccount = bankAccountSpringDataRepository.save(bankAccount);

        MoneyJPA savedmoney = savedBankAccount.getMoney();
        Integer savedAmount = savedmoney.getEuros();
        Integer savedCents = savedmoney.getCents();

        assertThat(savedBankAccount.getClientId()).isEqualTo(clientId);
        assertThat(savedAmount).isEqualTo(initialAmount);
        assertThat(savedCents).isEqualTo(initialCents);
    }


    @Then("^\"([^\"]*)\" has \\+?(-?\\d+).(\\d+) in his account$")
    public void something_has_in_his_account(String clientId, Integer finalAmount, Integer finalCents) throws Throwable {
        BankAccountJPA savedBankAccount = bankAccountSpringDataRepository.findById(clientId).orElse(null);
        assertThat(savedBankAccount).isNotNull();

        MoneyJPA savedAccount = savedBankAccount.getMoney();

        assertThat(savedAccount.getEuros()).isEqualTo(finalAmount);
        assertThat(savedAccount.getCents()).isEqualTo(finalCents);
    }

}
