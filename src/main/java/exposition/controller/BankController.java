package exposition.controller;

import exposition.dto.BankRequestDTO;
import exposition.dto.BankResponseDTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "bank")
@RestController
@RequestMapping("/bank")
public class BankController {
	public BankController() {
	}

	@PostMapping(path = "/{clientId}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<BankResponseDTO> deposit(@RequestBody @Valid BankRequestDTO bankRequestDTO, ServletRequest servletRequest) {

		BankResponseDTO responseDTO = null;

		return new ResponseEntity<>(responseDTO, OK);

	}
}
