package messages;

import com.mindsmiths.sdk.core.api.Message;
import com.mindsmiths.sdk.core.db.PrimaryKey;
import com.mindsmiths.sdk.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ButtonAnswer extends Message {
    @PrimaryKey
    String id = Utils.randomString();
    String messageId;
    String answer;
    String replyTo;
    String buttonText;

    public ButtonAnswer(String answer, String buttonText) {
        this.answer = answer;
        this.buttonText = buttonText;
    }

    public ButtonAnswer(String messageId, String answer, String buttonText) {
        this(answer, buttonText);
        this.messageId = messageId;
    }

    public ButtonAnswer(String messageId, String answer, String replyTo, String buttonText) {
        this(answer, buttonText);
        this.replyTo = replyTo;
        this.messageId = messageId;
    }
}
