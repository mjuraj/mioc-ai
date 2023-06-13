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
public class GPTResult extends Message {
    @PrimaryKey
    String id = Utils.randomString();
    String text;

    public GPTResult(String text) {
        this.text = text;
    }
}