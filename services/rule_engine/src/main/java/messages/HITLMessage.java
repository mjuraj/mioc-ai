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
public class HITLMessage extends Message {
    @PrimaryKey
    String id = Utils.randomString();
    String messageId;
    String text;

    public HITLMessage(String messageId, String text) {
        this.messageId = messageId;
        this.text = text;
    }
}
