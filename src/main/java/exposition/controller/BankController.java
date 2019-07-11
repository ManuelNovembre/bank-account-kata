package exposition.controller;

import application.command.DepositCommand;
import application.command.WithdrawCommand;
import application.query.GetHistoryQuery;
import exposition.dto.request.MoneyRequestDTO;
import exposition.dto.response.BankResponseDTO;
import exposition.dto.response.HistoryResponseDTO;
import exposition.dto.response.MoneyResponseDTO;
import exposition.dto.response.OperationResponseDTO;
import infra.MoneyJPA;
import infra.OperationJPA;
import io.swagger.annotations.Api;
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

@Api(tags = "bank")
@RestController
@RequestMapping("/bank")
public class BankController {
	private static final org.slf4j.Logger LOGGER = getLogger(BankController.class);

	private final DepositCommand depositCommand;
	private final WithdrawCommand withdrawCommand;
	private final GetHistoryQuery hitstoryQuery;

	public BankController(DepositCommand depositCommand, WithdrawCommand withdrawCommand, GetHistoryQuery hitstoryQuery) {
		this.depositCommand = depositCommand;
		this.withdrawCommand = withdrawCommand;
		this.hitstoryQuery = hitstoryQuery;
	}

	@PostMapping(path = "/{clientId}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<BankResponseDTO> deposit(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid MoneyRequestDTO moneyRequestDTO, ServletRequest servletRequest) throws Exception {

		MoneyJPA moneyToDeposit = new MoneyJPA(moneyRequestDTO.getMoney(), moneyRequestDTO.getCents());
		depositCommand.deposit(clientId, moneyToDeposit);

		BankResponseDTO responseDTO = null;
		return new ResponseEntity<>(responseDTO, OK);
	}

	@PostMapping(path = "/{clientId}/withdraw", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<BankResponseDTO> withdraw(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid MoneyRequestDTO moneyRequestDTO, ServletRequest servletRequest) throws Exception {

		MoneyJPA moneyToDeposit = new MoneyJPA(moneyRequestDTO.getMoney(), moneyRequestDTO.getCents());
		withdrawCommand.withdraw(clientId, moneyToDeposit);

		BankResponseDTO responseDTO = null;
		return new ResponseEntity<>(responseDTO, OK);

	}

	@GetMapping(path = "/{clientId}/history")
	public @ResponseBody ResponseEntity<HistoryResponseDTO> getHistory(@PathVariable(name = "clientId") String clientId) throws Exception {

		List<OperationJPA> operations = hitstoryQuery.getHistoryOperations(clientId);

		List<OperationResponseDTO> operationsDTO = new ArrayList<>();
		for (OperationJPA operation : operations) {
			final MoneyJPA moneyJPA = Optional.ofNullable(operation.getMoney()).orElse(new MoneyJPA());
			operationsDTO.add(OperationResponseDTO.builder()
												  .date(operation.getDate())
												  .operationType(operation.getOperationType())
												  .money(MoneyResponseDTO.builder()
																		 .euros(moneyJPA.getEuros())
																		 .cents(moneyJPA.getCents())
																		 .build())
												  .build());
		}

		HistoryResponseDTO responseDTO = HistoryResponseDTO.builder()
														   .history(operationsDTO)
														   .build();

		return new ResponseEntity<>(responseDTO, OK);

	}
}
