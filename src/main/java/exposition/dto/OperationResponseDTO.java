package exposition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationResponseDTO {
	private LocalDate date;
	private String operationType;
	private MoneyResponseDTO money;
}
