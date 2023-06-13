package hitl;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class Settings {
    public static final Map<String, String> env = System.getenv();
    public static String GUILD_ID = env.get("GUILD_ID");
    public static String DISCORD_BOT_TOKEN = env.get("DISCORD_BOT_TOKEN");
    public static String[] DISCORD_SENDER_BOT_TOKENS = env.get("DISCORD_SENDER_BOTS").split(",");
}