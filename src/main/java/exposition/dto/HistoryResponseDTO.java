package exposition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryResponseDTO {
	private List<OperationResponseDTO> history;

}
