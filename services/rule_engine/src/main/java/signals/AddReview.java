package signals;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.sdk.core.api.Message;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReview extends Message {
    String email;
    String gender;
    Integer age;
    Integer rating;
    String feedback;
}
