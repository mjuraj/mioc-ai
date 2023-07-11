package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import hitl.Settings;
import lombok.Data;

import java.util.HashMap;

@Data
public class HITL extends Agent {
    public static final String ID = "HITL";

    private static String systemBotId;
    private static String userBotId;
    private static String guildId;

    public HashMap<String, String> channelMapping = new HashMap<>();
    public HashMap<String, String> callMapping = new HashMap<>(); // Used for mapping 

    public HITL() {
        super(ID);
//        initialize();
    }

//    private void initialize() {
//        systemBotId = Settings.DISCORD_BOT_TOKEN;
//        userBotId = Settings.DISCORD_SENDER_BOT_TOKENS[0];
//        guildId = Settings.GUILD_ID;
//    }


}
