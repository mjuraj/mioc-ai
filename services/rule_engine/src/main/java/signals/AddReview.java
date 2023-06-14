package signals;

import java.time.LocalDateTime;
import com.mindsmiths.sdk.core.api.Message;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReview extends Message {
    String gender;
    Integer age;
    Integer rating;
    String feedback;
    LocalDateTime timestamp;
}
