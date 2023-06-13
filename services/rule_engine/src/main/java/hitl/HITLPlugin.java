package hitl;

import agents.HITL;
import com.mindsmiths.sdk.core.api.Message;
import com.mindsmiths.sdk.messaging.Messaging;
import com.mindsmiths.sdk.utils.Utils;
import hitl.models.ChannelType;
import hitl.signals.UpdateChannel;
import hitl.signals.UpdateType;
import messages.ButtonAnswer;
import messages.ReceivedMessage;
import messages.SentMessage;

public class HITLPlugin {
    public static String SYSTEM_BOT = Settings.DISCORD_BOT_TOKEN;
    public static String USER_BOT = Settings.DISCORD_SENDER_BOT_TOKENS[0];

    public static void createChannel(String channelName) {
        createChannel(channelName, null);
    }


    public static void createChannel(String channelName, String categoryId) {
        send(new UpdateChannel(channelName, UpdateType.CREATE, categoryId, ChannelType.TEXT_CHANNEL));
    }

    public static void createCategory(String categoryName) {
        send(new UpdateChannel(categoryName, UpdateType.CREATE, null, ChannelType.CATEGORY));
    }

    public static void deleteChannel() {
        send(new UpdateChannel(UpdateType.DELETE));
    }

    public static void deleteChannel(String channelId) {
        send(new UpdateChannel(channelId, UpdateType.DELETE));
    }


    public static void sendMessage(String message) {
        registerMessage(new SentMessage(Utils.randomString(), message));
    }

    public static void registerMessage(SentMessage sentMessage) {
        send(sentMessage);
    }

    public static void registerMessage(ReceivedMessage receivedMessage) {
        send(receivedMessage);
    }

    public static void registerButtonAnswer(ButtonAnswer buttonAnswer) {
        send(buttonAnswer);
    }

    private static void send(Message message) {
        message.send(HITL.ID, Messaging.getInputTopicName("agents"));
    }
}
