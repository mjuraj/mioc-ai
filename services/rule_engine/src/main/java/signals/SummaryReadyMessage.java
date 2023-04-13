package signals;

import com.mindsmiths.sdk.core.api.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SummaryReadyMessage extends Message {
    int numberOfEntries;
    int totalAverage;
    String feedback;
}
