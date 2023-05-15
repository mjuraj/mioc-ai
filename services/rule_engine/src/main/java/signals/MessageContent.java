package signals;

import java.io.Serializable;
import com.mindsmiths.infobipAdapter.api.MessageWithButtons.Action;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageContent implements Serializable {

    String body;
    Action action;

}
