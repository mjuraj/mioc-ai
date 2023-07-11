package messages;
import com.mindsmiths.discordAdapter.components.Button;
import com.mindsmiths.sdk.core.api.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscordButtonAnswer extends Message {
    private Button button;
    private com.mindsmiths.discordAdapter.message.Message message;
}
