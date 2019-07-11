package exposition.controller;

import command.DepositCommand;
import command.WithdrawCommand;
import exposition.dto.BankRequestDTO;
import exposition.dto.BankResponseDTO;
import exposition.dto.HistoryResponseDTO;
import infra.MoneyJPA;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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

    public BankController(DepositCommand depositCommand, WithdrawCommand withdrawCommand) {
        this.depositCommand = depositCommand;
        this.withdrawCommand = withdrawCommand;
    }

    @PostMapping(path = "/{clientId}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BankResponseDTO> deposit(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid BankRequestDTO bankRequestDTO, ServletRequest servletRequest) throws Exception {

        MoneyJPA moneyToDeposit = new MoneyJPA(bankRequestDTO.getMoney(), bankRequestDTO.getCents());
        depositCommand.deposit(clientId, moneyToDeposit);

        BankResponseDTO responseDTO = null;
        return new ResponseEntity<>(responseDTO, OK);
    }

    @PostMapping(path = "/{clientId}/withdraw", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BankResponseDTO> withdraw(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid BankRequestDTO bankRequestDTO, ServletRequest servletRequest) throws Exception {

        MoneyJPA moneyToDeposit = new MoneyJPA(bankRequestDTO.getMoney(), bankRequestDTO.getCents());
        withdrawCommand.withdraw(clientId, moneyToDeposit);

        BankResponseDTO responseDTO = null;
        return new ResponseEntity<>(responseDTO, OK);

    }

    @GetMapping(path = "/{clientId}/history", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<HistoryResponseDTO> getHistory(@PathVariable(name = "clientId") String clientId) throws Exception {


        HistoryResponseDTO responseDTO = null;

        return new ResponseEntity<>(responseDTO, OK);

    }
}
