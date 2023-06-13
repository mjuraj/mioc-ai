package hitl.models;

import com.mindsmiths.discordAdapter.DiscordAdapterAPI;
import com.mindsmiths.discordAdapter.SendChannelMessage;
import com.mindsmiths.discordAdapter.components.Button;
import com.mindsmiths.sdk.utils.Utils;
import hitl.HITLPlugin;
import lombok.Data;
import lombok.NoArgsConstructor;
import messages.ReceivedMessage;
import messages.SentMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class HITLChannel {
    private String id = Utils.randomString();
    private String channelId;
    private String categoryId;
    private String agentId;
    private String name;
    private ChannelType channelType;
    public Map<String, String> messageMapping = new HashMap<>();

    // Operational data
    private String requestId;

    public HITLChannel(String agentId, String name, ChannelType channelType) {
        this.agentId = agentId;
        this.name = name;
        this.channelType = channelType;
    }

    public HITLChannel(String agentId, String name, String requestId, String categoryId, ChannelType channelType) {
        this.agentId = agentId;
        this.name = name;
        this.requestId = requestId;
        this.categoryId = categoryId;
        this.channelType = channelType;
    }

    public void sendMessageToChannel(ReceivedMessage message) {
        SendChannelMessage channelMessage = DiscordAdapterAPI.sendChannelMessage(message.getText(), channelId, HITLPlugin.USER_BOT);
        messageMapping.put(message.getMessageId(), channelMessage.getId());
    }

    public void sendMessageToChannel(SentMessage message) {
        SendChannelMessage channelMessage = DiscordAdapterAPI.sendChannelMessage(message.getText(), channelId, HITLPlugin.SYSTEM_BOT);
        messageMapping.put(message.getMessageId(), channelMessage.getId());
    }

    public void sendButtonsMessageToChannel(String text, List<Button> buttons) {
        SendChannelMessage channelMessage = DiscordAdapterAPI.sendChannelMessage(text, channelId, buttons, HITLPlugin.SYSTEM_BOT);
    }

    public void replyToMessage(String replyMessageId, String text) {
        if (messageMapping.containsKey(replyMessageId))
            DiscordAdapterAPI.replyToChannelMessage(channelId, messageMapping.get(replyMessageId), text, HITLPlugin.USER_BOT);
        else
            DiscordAdapterAPI.sendChannelMessage(text, channelId, HITLPlugin.USER_BOT);
    }
}
