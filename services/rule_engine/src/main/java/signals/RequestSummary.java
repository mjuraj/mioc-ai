package signals;

import java.time.LocalDateTime;

import com.mindsmiths.sdk.core.api.Message;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSummary extends Message {
    LocalDateTime fromTimestamp;
}
