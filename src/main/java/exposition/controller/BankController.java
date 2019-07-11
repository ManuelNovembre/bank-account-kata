package exposition.controller;

import application.command.CreateAccountCommand;
import application.command.DepositCommand;
import application.command.WithdrawCommand;
import application.query.GetAccountQuery;
import application.query.GetHistoryQuery;
import exposition.dto.request.MoneyRequestDTO;
import exposition.dto.response.BankResponseDTO;
import exposition.dto.response.HistoryResponseDTO;
import exposition.dto.response.MoneyResponseDTO;
import exposition.dto.response.OperationResponseDTO;
import io.swagger.annotations.Api;
import model.BankAccount;
import model.Money;
import model.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Bank")
@RestController
@RequestMapping("/bank")
public class BankController {
	private static final org.slf4j.Logger LOGGER = getLogger(BankController.class);

	private final DepositCommand depositCommand;
	private final WithdrawCommand withdrawCommand;
	private final GetHistoryQuery hitstoryQuery;
	private final CreateAccountCommand createAccountCommand;
	private final GetAccountQuery getAccountQuery;

	public BankController(DepositCommand depositCommand, WithdrawCommand withdrawCommand, GetHistoryQuery hitstoryQuery, CreateAccountCommand createAccountCommand, GetAccountQuery getAccountQuery) {
		this.depositCommand = depositCommand;
		this.withdrawCommand = withdrawCommand;
		this.hitstoryQuery = hitstoryQuery;
		this.createAccountCommand = createAccountCommand;
		this.getAccountQuery = getAccountQuery;
	}

	@PostMapping(value = "/{clientId}/account", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<BankResponseDTO> createAccount(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid MoneyRequestDTO moneyRequestDTO, ServletRequest servletRequest) throws Exception {

		Money moneyToDeposit = new Money(moneyRequestDTO.getMoney(), moneyRequestDTO.getCents());
		createAccountCommand.create(clientId, moneyToDeposit);

		BankResponseDTO responseDTO = null;
		return new ResponseEntity<>(responseDTO, OK);
	}

	@GetMapping(value = "/{clientId}/account")
	public ResponseEntity<BankResponseDTO> getAccount(@PathVariable(name = "clientId") String clientId) throws Exception {

		final BankAccount bankAccount = this.getAccountQuery.getGetAccountQuery(clientId);

		final Money money = Optional.ofNullable(bankAccount.getMoney()).orElse(new Money());
		BankResponseDTO responseDTO = BankResponseDTO.builder()
													 .clientId(bankAccount.getClientId())
													 .money(MoneyResponseDTO.builder()
																			.euros(money.getEuros())
																			.cents(money.getCents())
																			.build())
													 .build();
		return new ResponseEntity<>(responseDTO, OK);
	}

	@PostMapping(value = "/{clientId}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<BankResponseDTO> deposit(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid MoneyRequestDTO moneyRequestDTO, ServletRequest servletRequest) throws Exception {

		Money moneyToDeposit = new Money(moneyRequestDTO.getMoney(), moneyRequestDTO.getCents());
		depositCommand.deposit(clientId, moneyToDeposit);

		BankResponseDTO responseDTO = null;
		return new ResponseEntity<>(responseDTO, OK);
	}

	@PostMapping(value = "/{clientId}/withdraw", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<BankResponseDTO> withdraw(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid MoneyRequestDTO moneyRequestDTO, ServletRequest servletRequest) throws Exception {

		Money moneyToDeposit = new Money(moneyRequestDTO.getMoney(), moneyRequestDTO.getCents());
		withdrawCommand.withdraw(clientId, moneyToDeposit);

		BankResponseDTO responseDTO = null;
		return new ResponseEntity<>(responseDTO, OK);

	}

	@GetMapping(value = "/{clientId}/history")
	public @ResponseBody
	ResponseEntity<HistoryResponseDTO> getHistory(@PathVariable(name = "clientId") String clientId) throws Exception {

		List<Operation> operations = hitstoryQuery.getHistoryOperations(clientId);

		List<OperationResponseDTO> operationsDTO = new ArrayList<>();
		for (Operation operation : operations) {
			final Money money = Optional.ofNullable(operation.getMoney()).orElse(new Money());
			operationsDTO.add(OperationResponseDTO.builder()
												  .date(operation.getDate())
												  .operationType(operation.getOperationType())
												  .money(MoneyResponseDTO.builder()
																		 .euros(money.getEuros())
																		 .cents(money.getCents())
																		 .build())
												  .build());
		}

		HistoryResponseDTO responseDTO = HistoryResponseDTO.builder()
														   .history(operationsDTO)
														   .build();

		return new ResponseEntity<>(responseDTO, OK);

	}
}
