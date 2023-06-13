package signals;

import com.mindsmiths.discordAdapter.components.Button;
import com.mindsmiths.sdk.core.api.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscordButtonMessageRequest extends Message {
    String text;
    List<Button> buttons;
}
