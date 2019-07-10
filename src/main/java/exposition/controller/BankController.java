package exposition.controller;

import command.DepositCommand;
import exposition.dto.BankRequestDTO;
import exposition.dto.BankResponseDTO;
import infra.MoneyJPA;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    public BankController(DepositCommand depositCommand) {
        this.depositCommand = depositCommand;
    }

    @PostMapping(path = "/{clientId}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BankResponseDTO> deposit(@PathVariable(name = "clientId") String clientId, @RequestBody @Valid BankRequestDTO bankRequestDTO, ServletRequest servletRequest) throws Exception {

        MoneyJPA moneyToDeposit = new MoneyJPA(bankRequestDTO.getMoney(), bankRequestDTO.getCents());
        depositCommand.deposit(clientId, moneyToDeposit);

        BankResponseDTO responseDTO = null;
        return new ResponseEntity<>(responseDTO, OK);

    }
}
