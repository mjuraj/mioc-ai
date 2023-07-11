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
public class SentMessage extends Message {
    @PrimaryKey
    String id = Utils.randomString();
    String messageId;
    String text;
    boolean processed;

    public SentMessage(String messageId, String text) {
        this.messageId = messageId;
        this.text = text;
    }
}
