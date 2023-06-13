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
public class ReceivedMessage extends Message {
    @PrimaryKey
    String id = Utils.randomString();
    String messageId;
    String text;
    String contactName;
    boolean processed;

    public ReceivedMessage(String messageId, String text) {
        this.messageId = messageId;
        this.text = text;
    }

    public ReceivedMessage(String messageId, String text, String contactName) {
        this.messageId = messageId;
        this.text = text;
        this.contactName = contactName;
    }
}