package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import infra.BankAccountJPA;
import infra.MoneyJPA;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import repository.BankAccountSpringDataRepository;


public class DepistionSteps {
    @Autowired
    private BankAccountSpringDataRepository bankAccountSpringDataRepository;

    @Given("^a bank client \"([^\"]*)\" has (.+).(.+) in is account$")
    public void a_bank_client_something_has_in_is_account(String clientName, Integer initialamount, Integer initialcents) throws Throwable {

        MoneyJPA money = new MoneyJPA(initialamount, initialcents);

        BankAccountJPA bankAccount = new BankAccountJPA(clientName, money);

        BankAccountJPA savedBankAccount = bankAccountSpringDataRepository.save(bankAccount);
        MoneyJPA savedmoney = savedBankAccount.getMoney();
        Integer savedAmount = savedmoney.getAmount();
        Integer savedCents = savedmoney.getCents();

        Assertions.assertThat(savedAmount).isEqualTo(initialamount);
        Assertions.assertThat(savedCents).isEqualTo(initialcents);
    }

    @When("^\"([^\"]*)\" deposits (.+).(.+)$")
    public void something_deposits_(String clientName, Integer depositsamount, Integer depositscents) throws Throwable {
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\" has (.+).(.+) in his account$")
    public void something_has_in_his_account(String clientName, Integer finalamount, Integer finalcents) throws Throwable {
        throw new PendingException();
    }

}
